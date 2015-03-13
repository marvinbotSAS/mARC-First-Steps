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
import mARC.Connector;

public class CreateAMasterTable {

    static public void main()
    {
        Connector connector = new Connector();

        // connect to the server
        ConnectToAmARCServer.doIt(connector, "127.0.0.1", "1254");
        String tableName = "myName";
        String structure = "title CHAR 254, text STRING";
        CreateAMasterTable.main(connector,tableName,structure);
    }
    /**
     * @param connector the server
     * @param tableName name of the table
     * @param structure its structure
     */
    static public void main(Connector connector, String tableName, String structure)
    {
 
        if ( tableName == null || tableName.isEmpty())
        {
            tableName = tableName = "myName";
        }
        // STEP #1 create the master table
        if ( structure == null || structure.isEmpty())
        {
            structure = "title CHAR 254, text STRING";
        }
        CreateAMasterTable.doIt(connector, tableName,structure);
    }
    /**
     * 
     * @param connector the server
     * @param tableName  the master table name
     * @param structure  its structure
     */
    static public void doIt(Connector connector, String tableName, String structure)
    {
        try
        {
            connector. directExecute = true; // line by line mode
            connector.TABLE_Create(tableName,"NULL","100000","MASTER", structure);
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
