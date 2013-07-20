/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.cloudws.test;

/**
 *
 * @author Josep
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClamScanTestCase {

    ClamScan scanner;

    public static void main(String[] args) throws Exception
    {
        ClamScanTestCase test = new ClamScanTestCase();
        test.setUp();
        test.testSuccess();
        test.testVirusFromFile();
    }
    
    public void setUp() {
        scanner = new ClamScan("ssgd", 3310, 60);
    }

        public void testSuccess() throws Exception {

        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < 10000; i++){
            sb.append("abcde12345");
        }

                InputStream is = new ByteArrayInputStream(sb.toString().getBytes());
                System.out.println(is);
        ScanResult result = scanner.scan(is);
        System.out.println(result.getStatus());
        System.out.println(result.getResult());
             
        }
       
        public void testVirus () throws Exception {
                InputStream is = new ByteArrayInputStream("X5O!P%@AP[4\\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*".getBytes());
               
        ScanResult result = scanner.scan(is);
         System.out.println(result.getStatus());
        System.out.println(result.getResult());
             

        }
       
        public void testVirusFromFile () throws Exception {
          
          File f  = new File("c:\\users\\josep\\virus.txt");
          FileInputStream fin = new FileInputStream(f);
          
          
           byte currentXMLBytes[] = getBytes(fin);
  ByteArrayInputStream byteArrayInputStream = new 
ByteArrayInputStream(currentXMLBytes);
          
//          byte[] filecontent = new byte[(int) f.length()];
//          int bytecount = fin.read(filecontent);
//
//          InputStream is = new ByteArrayInputStream(filecontent);
//               
//          
          
        ScanResult result = scanner.scan(currentXMLBytes);
         System.out.println(result.getStatus());
        System.out.println(result.getResult());
             

        }
        
        
        public static byte[] getBytes(InputStream is) throws IOException {

    int len;
    int size = 1024;
    byte[] buf;

    if (is instanceof ByteArrayInputStream) {
      size = is.available();
      buf = new byte[size];
      len = is.read(buf, 0, size);
    } else {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      buf = new byte[size];
      while ((len = is.read(buf, 0, size)) != -1) {
            bos.write(buf, 0, len);
        }
      buf = bos.toByteArray();
    }
    return buf;
  }
       
}
