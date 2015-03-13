/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcfirststeps;
import java.math.BigInteger;
import java.security.SecureRandom;
import mARC.Connector;
/**
 *
 * @author patrice
 */

public class InsertLearnAndIndex {
    
    /**
     * run a script to insert, learn and index
     * array 'values' must have linesToInsert lines at least
     */
    /**
     * 
     * @param ip server ip
     * @param port server port
     * @param linesToInsert  number of lines to insert
     * @param colnames  fields of the table where to insert data
     * @param values     the data. length of this string array mus be at least linesYoInsert.
     */
    public static void main(Connector connector, String tableName, String linesToInsert,String[] colnames, String[] values ) 
    {
        if (tableName == null || tableName.isEmpty() )
        {
            tableName = "myTable";
        }
       // STEP #2 Insert Learn and Index random data
       String[] shapes = null;
       String[] activities = null;
       String title = null;
       String text = null;
       SecureRandom random = null;
       int numberOfLines = 10000;
       if ( linesToInsert != null )
       {
           numberOfLines = Integer.parseInt(linesToInsert);
       }
       String[] cols = new String[]{"title","text"};
       if ( colnames !=null)
       {
           cols = colnames;
       }
       boolean  isRandom = false;
       if ( values == null )
       {
           isRandom = true;
       }
       if ( !isRandom && values.length < Integer.parseInt(linesToInsert ) )
       {
            System.out.println("ERROR. Aborting. data must be at least size of "+linesToInsert);
            return;
       }
       
       for (int  i= 0; i < numberOfLines ;i ++)
       {
           if ( isRandom )
           {
               random = new SecureRandom();
               title = new BigInteger(130, random).toString(32);
               random = new SecureRandom();
               text = new BigInteger(130, random).toString(32);
               values = new String[]{title, text}; // the data to insert
           }
           InsertLearnAndIndex.doIt(connector,tableName, cols, values,shapes, activities );
           System.out.println("indexation data for line #"+i+":");
           int j = 0;
           for ( String s : shapes)
           {
               System.out.println("shape :"+s+": "+activities[j++]);
           }
       }
        
        
    }
    /**
     * 
     * @param connector
     * @param colnames  
     * @param values   
     */
    /**
     * 
     * @param connector  IN :  the server
     * @param tableName  IN :  the master table name
     * @param colnames   IN :  the table fields where to insert
     * @param values     IN :  the data to insert in the preceding fields
     * @param shapes     OUT : the indexation data as an array of strings
     * @param activities OUT : their associated activities
     */
    static public void doIt(Connector connector, String tableName, String[] colnames, String[] values, String[] shapes, String[] activities)
    {
        try
        {
            connector. directExecute = true; // line by line mode
            connector.TABLE_Insert(tableName,colnames,values); // inserting in table the data inside defined fields
            String [] rowid = connector. getDataByName("rowid",-1); // we retrieve the line number where data was inserted
            // rowid[0] contains the line number where data was inserted
            String signal = values[0]+". "+values[1];
            connector.directExecute = false; // script mode
            connector.openScript(null); // script starts after this line
            connector.SESSION_Store(signal,"ranked",rowid[0]); // learning and indexing data
            connector.CONTEXTS_Fetch("-1","1","1");
            connector.CONTEXTS_Drop("1");
            connector.executeScript(); // execute script on mARC server
            shapes = connector. getDataByName("shapes",1); // the indexation data
            activities = connector. getDataByName("activity",1); // corresponding activity
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            if ( connector.result.mError )
            {
                System.out.println("mARC error occured : "+connector.getExecutionErrorMsg());
            }
        }
    }
    
}
