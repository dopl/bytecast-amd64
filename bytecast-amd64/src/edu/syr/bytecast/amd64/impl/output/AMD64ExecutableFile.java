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
    
}
