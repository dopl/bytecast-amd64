package edu.syr.bytecast.amd64.test;

import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import java.util.List;

/**
 *
 * @author sheng
 */
public class IExecutableFileUtils {

    public static class CompareResult {

        private String message;
        private boolean passed;
        private int totalSectionCount;
        private int errorSectionCount;
        private int passedSectionCount;
        private int totalInstructionCount;
        private int errorInstructionCount;
        private int passedInstructionCount;

        /**
         * Returns a message when the sizes of two IExecutableFile are not the
         * same. Returns null otherwise.
         *
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Returns true if all cases are passed.
         *
         * @return the passed
         */
        public boolean isPassed() {
            return passed;
        }

        /**
         * Returns the total count of compared sections, whose sizes are matched with
         * each other.
         *
         * @return the totalSectionCount
         */
        public int getTotalSectionCount() {
            return totalSectionCount;
        }

        /**
         * Returns the error count of compared sections, whose sizes are matched with
         * each other.
         * @return the errorSectionCount
         */
        public int getErrorSectionCount() {
            return errorSectionCount;
        }

        /**
         * Returns the passed count of compared sections, whose sizes are matched with
         * each other.
         * @return the passedSectionCount
         */
        public int getPassedSectionCount() {
            return passedSectionCount;
        }

        /**
         * Returns the total count of compared instruction.
         * @return the totalInstructionCount
         */
        public int getTotalInstructionCount() {
            return totalInstructionCount;
        }

        /**
         * Returns the error count of compared instruction.
         * @return the errorInstructionCount
         */
        public int getErrorInstructionCount() {
            return errorInstructionCount;
        }

        /**
         * Returns the passed count of compared instruction.
         * @return the passedInstructionCount
         */
        public int getPassedInstructionCount() {
            return passedInstructionCount;
        }
    }

    /**
     * Compares sections and instructions inside the two IExecutableFile.
     * @param f1
     * @param f2
     * @return 
     */
    public static CompareResult compareSections(IExecutableFile f1, IExecutableFile f2) {
        CompareResult ret = new CompareResult();
        List<ISection> ss1 = f1.getSectionsWithInstructions(); // sections1
        List<ISection> ss2 = f2.getSectionsWithInstructions(); // sections2
        // Check section sizes
        int sectionSize = ss1.size();
        if (sectionSize != ss2.size()) {
            ret.passed = false;
            ret.message = "Sizes of sections are not the same.";
            return ret;
        }
        ret.totalSectionCount = sectionSize;
        for (int i = 0; i < sectionSize; i++) {
            List<MemoryInstructionPair> ps1 = ss1.get(i).getAllInstructionObjects(); // pair1
            List<MemoryInstructionPair> ps2 = ss2.get(i).getAllInstructionObjects(); // pair2
            // Check pair sizes
            int psSize = ps1.size();
            if (psSize != ps2.size()) {
                ret.passed = false;
                ret.errorSectionCount++;
                continue;
            }
            ret.totalInstructionCount += psSize;
            // Check each pair
            boolean sectionError = false;
            for (int j = 0; j < psSize; j++) {
                if (ps1.get(j).equals(ps2.get(j))) {
                    ret.passed = false;
                    sectionError = true;
                    ret.passedInstructionCount++;
                } else {
                    ret.errorInstructionCount++;
                }
            }
            if (sectionError) {
                ret.errorSectionCount++;
            } else {
                ret.passedSectionCount++;
            }
        }
        return ret;
    }
}
