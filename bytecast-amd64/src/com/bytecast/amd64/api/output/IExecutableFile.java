/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.api.output;

import java.util.List;

/**
 *
 * @author Harsh
 */
public interface IExecutableFile {
    
    
    public List<ISection> getAllSections();
    
    public String getFileName();
    
    public String getExecFileFormat();
    
    
}
