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
public class HelloWorld {
    
    public static void main(String[] args)
    {

            Connector connector = new Connector();
            try
            {
            // connect to the server
            ConnectToAmARCServer.doIt(connector, "127.0.0.1", "1254");
            //retrieve mARC instance properties cf page 22 of API reference document
            connector.SERVER_GetProperties(null);
            String[] properties = connector.getDataByName("prop_name", -1);
            String[] values = connector.getDataByName("prop_value", -1);
            int i = 0;
            System.out.println("Connection was successfull.");
            for (String s: properties)
            {
              System.out.println(s+" : "+values[i++]);
            }
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
