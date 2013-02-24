/*
 * This file is part of Bytecast.
 *
 * Bytecast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bytecast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bytecast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.syr.bytecast.amd64;

import edu.syr.bytecast.interfaces.fsys.ExeObj;
import edu.syr.bytecast.interfaces.fsys.IBytecastFsys;
import edu.syr.bytecast.jimple.api.IJimple;

public class BytecastAmd64 {

    private IBytecastFsys m_fsys;
    private IJimple m_jimple;
    private String m_filepath;
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    if(args.length==0)
    {
        System.out.println("Error:No arguments specified");
        displayUsage();
    }
  }

  /**
   * Prints out the usage information of the system
   */
  public static void displayUsage()
  {
      System.out.println("");
  }
  
  
  /**
   * BytecastAMD64 constructor which is initialized with service interfaces from Fsys and Jimple
   * @param fsys
   * @param jimple 
   */
  public BytecastAmd64(IBytecastFsys fsys, IJimple jimple, String filePath)
  {
      this.m_fsys = fsys;
      this.m_jimple = jimple;
      this.m_filepath = filePath;
  }
    /**
     * This function runs the Bytecast system
     */
   public void run()
   {
       m_fsys.setFilepath(m_filepath);
       try{
       ExeObj exeObj = m_fsys.parse();
       long entryPoint = exeObj.getEntryPointIndex();
       }catch(Exception ex)
       {
           System.out.println(ex.getMessage());
           return;
       }
       
   }
   public void doFsys()
   {
        m_fsys.setFilepath(m_filepath);
       try{
            ExeObj exeObj = m_fsys.parse();
            long entryPoint = exeObj.getEntryPointIndex();
        }
        catch(Exception ex){
           System.out.println(ex.getMessage());
           return;
       }
   }
 
    
}
