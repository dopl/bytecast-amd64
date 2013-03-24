/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.decoder.DecoderFactory;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sheng
 */
public class AutoTest {

    private static class LineData {

        String address;
        String bytes;
        String instruction;
        String fields;
    }
    private static LineData lastData;
    private static long analyzedCount;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String expression = "(?x)^\\s*([\\da-fA-F]+):\\s+((?:[\\da-fA-F]{2}\\b\\s+?)+)(?:(?:\\s*$)|(?:\\s+(?:\\w+\\b\\s+)?(\\w+)\\b\\s*(?:$|(?:\\*?([^<\\s]+)\\s*.*$))))";//$|(?:([^<\\s]+)(?:\\s+<[^>]+>)?\\s*$)
        Pattern pattern = Pattern.compile(expression);

        // Read the test file.  ../a.out.onlypgmcode
        BufferedReader reader = new BufferedReader(new FileReader("../../../temp.objdump"));
        // BufferedReader reader = new BufferedReader(new FileReader("../../bytecast-documents/AsciiManip01Prototype/a.out.static.objdump"));

        // Read line by line
        String line = reader.readLine();
        while (line != null) {
            // Parse the line using Regular Expressions
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                // println("#1: " + matcher.group(1) + "  #2: " + matcher.group(2) + "  #3: " + matcher.group(3) + "  #4: " + matcher.group(4));
                // Get data.
                LineData data = new LineData();
                data.address = matcher.group(1);
                data.bytes = matcher.group(2);
                data.instruction = matcher.group(3);
                data.fields = matcher.group(4);
                if (data.instruction == null) {
                    // The line is still part of the last instruction.
                    mergeToLastLineData(data);
                } else {
                    // Analyze lastData.
                    analyzeData(lastData);
                    lastData = data;
                }
            } else {
                //System.out.println("Unsolved: " + line);
            }

            line = reader.readLine();
        }

        reader.close();

        println("Finished: " + analyzedCount + " analyzed.");
    }

    private static void mergeToLastLineData(LineData data) {
        lastData.bytes = lastData.bytes + " " + data.bytes;
    }

    private static void analyzeData(LineData data) throws EOFException {
        if (data == null) {
            return;
        }
        long address = Long.decode("0x" + data.address);
        List<Byte> bytes = ParserTestUtils.stringToByteList(data.bytes);
        IInstructionDecoder decoder = getDecoder(data.instruction);
        if (decoder == null) {
            //println("WARN:  fail to find decoder of " + data.instruction);
            return;
        }

        // Now parse
        InstructionByteListInputStream input = new InstructionByteListInputStream(bytes, address);
        analyzedCount++;
        try {
            IInstruction ins = decoder.decode(getContext(input), input);
            String result = InstructionTestUtils.toObjdumpOperands(ins);
            if (data.fields == null ? !result.isEmpty() : !result.replace(",<SectionName>", "").equalsIgnoreCase(String.valueOf(data.fields))) {
                println("ERROR: not match (" + data.address + ", " + data.instruction + ", " + data.fields + ", " + result + ")");
            }
        } catch (UnsupportedOperationException ex) {
            // Ignore
        } catch (Exception ex) {
            println("ERROR: exceptions when decoding (" + data.address + ", " + data.instruction + ", " + data.fields + ", " + ex.getMessage() + ")");
        }
    }

    private static IInstructionDecoder getDecoder(String instruction) {
        instruction = instruction.toUpperCase();
        Class<InstructionType> clazz = InstructionType.class;
        InstructionType type;
        do {
            if (instruction.equals("MOVZ")) {
                instruction = "MOVZX";
            } else if (instruction.equals("MOVS")) {
                instruction = "MOVSX";
            }
            type = null;
            try {
                type = Enum.valueOf(clazz, instruction);
            } catch (IllegalArgumentException ex) {
            }
            if (type != null) {
                IInstructionDecoder ret = DecoderFactory.getInstructionDecoder(type);
                if (ret != null) {
                    return ret;
                }
            }
            // Delete the last 'b' or 'l' or 'q' or 'w' and try again
            if (instruction.endsWith("B") || instruction.endsWith("L") || instruction.endsWith("Q") || instruction.endsWith("W")) {
                instruction = instruction.substring(0, instruction.length() - 1);
            } else {
                break;
            }
        } while (true);
        return null;
    }
    private static final List<Byte> LEGACY_PREFIX_LIST = Arrays.asList(
            (byte) 0x66, (byte) 0x67, (byte) 0xf0, (byte) 0xf3, (byte) 0xf2,
            (byte) 0x2e, (byte) 0x3Ee, (byte) 0x26, (byte) 0x64, (byte) 0x65,
            (byte) 0x36);

    static IInstructionContext getContext(IInstructionByteInputStream input) throws EOFException {
        IInstructionContext context = new InstructionContextImpl();
        byte b = input.peek();

        // Parse legacy prefixes.
        while (LEGACY_PREFIX_LIST.contains(b)) {
            ParserFactory.getLegacyPrefixParser().parse(context, input);
            b = input.peek();
        }

        // Parse REX prefix
        if (b >= (byte) 0x40 && b <= (byte) 0x4f) {
            ParserFactory.getRexPrefixParser().parse(context, input);
        }

        return context;
    }

    static void println(String str) {
        System.out.println(str);
        System.out.flush();;
    }
}
