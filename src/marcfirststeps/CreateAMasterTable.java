/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcfirststeps;

/**
 *
 * @author patrice
 */
import mARC.Connector;

public class CreateAMasterTable {
    
    /**
     * 
     * @param connector the server
     * @param tableName  the master table name
     * @param structure  its structure
     */
    static public void doIt(Connector connector, String tableName, String structure)
    {
        connector. directExecute = true; // line by line mode
        connector.TABLE_Create(tableName,"NULL","100000","MASTER", structure);

    }
}
