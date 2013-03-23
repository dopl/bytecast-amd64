package edu.syr.bytecast.amd64.impl.parser;

/**
 * A factory to get all kinds of parsers.
 *
 * @author sheng
 */
public class ParserFactory {

    public static IAddressSizeOverridePrefixParser getAddressSizeOverridePrefixParser() {
        return new AddressSizeOverridePrefixParserImpl();
    }

    public static IDispParser getDispParser() {
        return new NumberParserImpl();
    }

    public static IImmParser getImmParser() {
        return new NumberParserImpl();
    }

    public static ILegacyPrefixParser getLegacyPrefixParser() {
        return new LegacyPrefixParserImpl();
    }

    public static ILockPrefixParser getLockPrefixParser() {
        throw new UnsupportedOperationException("Unsupported");
    }

    public static IModRmParser getModRmParser() {
        return new ModRmParserImpl();
    }

    public static IMoffsetParser getMoffsetParser() {
        return new NumberParserImpl();
    }

    public static IOperandSizeOverridePrefixParser getOperandSizeOverridePrefixParser() {
        return new OperandSizeOverridePrefixParserImpl();
    }

    public static IRegisterInOpcodeParser getRegisterInOpcodeParser() {
        return new RegisterInOpcodeParserImpl();
    }

    public static IRepeatPrefixParser getRepeatPrefixParser() {
        throw new UnsupportedOperationException("Unsupported");
    }

    public static IRexPrefixParser getRexPrefixParser() {
        return new RexPrefixParserImpl();
    }

    public static ISegmentOverridePrefixParser getSegmentOverridePrefixParser() {
        return new SegmentOverridePrefixParserImpl();
    }

    public static ISibParser getSibParser() {
        return new SibParserImpl();
    }

    public static IVexXopPrefixParser getVexXopPrefixParser() {
        throw new UnsupportedOperationException("Unsupported");
    }

    public static IRegImmParser getRegImmParser() {
        throw new UnsupportedOperationException("TODO");
    }

    public static IAddressFunctionParser getAddressFunctionParser() {
        return new AddressFunctionParserImpl();

    }
}
