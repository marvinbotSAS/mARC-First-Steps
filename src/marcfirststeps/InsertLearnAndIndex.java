/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcfirststeps;
import mARC.Connector;
/**
 *
 * @author patrice
 */

public class InsertLearnAndIndex {
    
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
