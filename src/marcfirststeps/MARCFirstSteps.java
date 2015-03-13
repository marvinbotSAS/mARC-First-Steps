/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcfirststeps;
import mARC.Connector;
import java.security.SecureRandom;
import java.math.BigInteger;
/**
 *
 * @author patrice
 */
public class MARCFirstSteps {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // instantiate a server
        Connector connector = new Connector();
        
        
        // STEP #0 connect to the server
        ConnectToAmARCServer.doIt(connector, "127.0.0.1", "1254");
        // STEP #1 create the master table
        CreateAMasterTable.doIt(connector, "myTable","title CHAR 254, text STRING");
         // STEP #2 Insert Learn and Index random data
        String[] shapes = null;
        String[] activities = null;
        String title = null;
        String text = null;
        SecureRandom random = null;
        int numberOLines = 10000;
        for (int  i= 0; i < numberOLines ;i ++)
        {
            String[] colnames = new String[]{"title", "text"}; // the table fields where to insert
            random = new SecureRandom();
            title = new BigInteger(130, random).toString(32);
            random = new SecureRandom();
            text = new BigInteger(130, random).toString(32);
            String[] values = new String[]{title, text}; // the data to insert
            InsertLearnAndIndex.doIt(connector,"myTable", colnames, values,shapes, activities );
        }
        // STEP #3 : basic querying
        String[] format = new String[]{"format = title text"}; // the format to show the results
        String query = "orange agent Vietnam";
        String[][] results = null;
        BasicQuerying.doIt(connector, query, format, results);
    }
    
}
