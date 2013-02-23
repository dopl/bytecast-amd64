package edu.syr.bytecast.amd64.impl.parser;

/**
 * A factory to return all kinds of parsers.
 *
 * @author sheng
 */
public class ParserFactory {

    public static IAddressSizeOverridePrefixParser getAddressSizeOverridePrefixParser() {
        throw new UnsupportedOperationException("TODO");
    }

    public static IImmParser getImmParser() {
        throw new UnsupportedOperationException("TODO");
    }

    public static ILegacyPrefixParser getLegacyPrefixParser() {
        throw new UnsupportedOperationException("TODO");
    }

    public static ILockPrefixParser getLockPrefixParser() {
        throw new UnsupportedOperationException("TODO");
    }

    public static IModRmParser getModRmParser() {
        return new ModRmParserImpl();
    }

    public static IMoffsetParser getMoffsetParser() {
        throw new UnsupportedOperationException("TODO");
    }

    public static IOperandSizeOverridePrefixParser getOperandSizeOverridePrefixParser() {
        throw new UnsupportedOperationException("TODO");
    }

    public static IRepeatPrefixParser getRepeatPrefixParser() {
        throw new UnsupportedOperationException("TODO");
    }

    public static ISegmentOverridePrefixParser getSegmentOverridePrefixParser() {
        throw new UnsupportedOperationException("TODO");
    }

    public static IVexXopPrefixParser getVexXopPrefixParser() {
        throw new UnsupportedOperationException("TODO");
    }
}
