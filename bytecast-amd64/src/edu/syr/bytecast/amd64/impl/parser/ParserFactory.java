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

    public static IRepeatPrefixParser getRepeatPrefixParser() {
        throw new UnsupportedOperationException("Unsupported");
    }

    public static ISegmentOverridePrefixParser getSegmentOverridePrefixParser() {
        return new SegmentOverridePrefixParserImpl();
    }

    public static IVexXopPrefixParser getVexXopPrefixParser() {
        throw new UnsupportedOperationException("Unsupported");
    }
}
