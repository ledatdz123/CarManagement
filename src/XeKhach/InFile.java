/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XeKhach;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTable;

/**
 *
 * @author My-PC
 */
public class InFile {
    public static void XuatFileExcel(JTable table, File file) {
        try {
            FileWriter out = new FileWriter(file);
            for (int i = 0; i < table.getColumnCount(); i++) {
                out.write(table.getColumnName(i) + "\t");
            }
            out.write("\n");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {                    
                    out.write( table.getValueAt(i, j).toString() + "\t");
                }
                out.write("\n");
            }
            out.close();
            System.out.println("Ghi ra file: " + file);
        } catch (IOException ex) {
        }
    }
}
    
