/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.output;

import edu.syr.bytecast.amd64.api.constants.FileFormats;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.interfaces.fsys.ExeObjSegment;
import java.util.List;

/**
 *
 * @author bytecast
 */
public class AMD64ExecutableFile implements IExecutableFile {
    private final List<ExeObjSegment> m_rawDataSections;
    private List<ISection> m_sections;
    private String m_name;
    private String m_exeFileFormat;
    private List<IExecutableFile> m_dependentFiles;

    public AMD64ExecutableFile(List<ExeObjSegment> rawDataSections, List<ISection> m_sections, String m_name, String m_exeFileFormat, List<IExecutableFile> m_dependentFiles) {
        this.m_rawDataSections = rawDataSections;
        this.m_sections = m_sections;
        this.m_name = m_name;
        this.m_exeFileFormat = m_exeFileFormat;
        this.m_dependentFiles = m_dependentFiles;
    }
    
   

    @Override
    public String getFileName() {
        return m_name;
    }

    @Override
    public String getExecFileFormat() {
        return m_exeFileFormat;
    }

    @Override
    public List<IExecutableFile> getAllDependantFiles() {
        return m_dependentFiles;
    }

    @Override
    public List<ISection> getSectionsWithInstructions() {
         return m_sections;
    }

    @Override
    public List<ExeObjSegment> getSectionsWithRawData() {
        return m_rawDataSections;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.m_rawDataSections != null ? this.m_rawDataSections.hashCode() : 0);
        hash = 97 * hash + (this.m_sections != null ? this.m_sections.hashCode() : 0);
        hash = 97 * hash + (this.m_name != null ? this.m_name.hashCode() : 0);
        hash = 97 * hash + (this.m_exeFileFormat != null ? this.m_exeFileFormat.hashCode() : 0);
        hash = 97 * hash + (this.m_dependentFiles != null ? this.m_dependentFiles.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AMD64ExecutableFile other = (AMD64ExecutableFile) obj;
        if (this.m_rawDataSections != other.m_rawDataSections && (this.m_rawDataSections == null || !this.m_rawDataSections.equals(other.m_rawDataSections))) {
            return false;
        }
        if (this.m_sections != other.m_sections && (this.m_sections == null || !this.m_sections.equals(other.m_sections))) {
            return false;
        }
        if ((this.m_name == null) ? (other.m_name != null) : !this.m_name.equals(other.m_name)) {
            return false;
        }
        if ((this.m_exeFileFormat == null) ? (other.m_exeFileFormat != null) : !this.m_exeFileFormat.equals(other.m_exeFileFormat)) {
            return false;
        }
        if (this.m_dependentFiles != other.m_dependentFiles && (this.m_dependentFiles == null || !this.m_dependentFiles.equals(other.m_dependentFiles))) {
            return false;
        }
        return true;
    }
    
}
