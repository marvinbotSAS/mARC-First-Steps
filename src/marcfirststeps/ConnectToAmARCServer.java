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
public class ConnectToAmARCServer {
    
     static public void main(String ip, String port)
     {
         if ( ip == null || ip.isEmpty())
         {
              ip = "127.0.0.1";
         }
         if ( port == null || port.isEmpty())
         {
             port = "1254";
         }
         Connector connector = new Connector();
        // STEP #0 connect to the server
        ConnectToAmARCServer.doIt(connector,ip, port);
     }
    /**
     * 
     * @param connector  the server
     * @param ip         the ip address
     * @param port       the port number
     */
    static public void doIt(Connector connector, String ip, String port)
    {
        connector.setIp(ip);
        connector.setPort(port);
        // connect to the server
        try
        {
            if ( !connector.connect() )
            {
                System.out.println("unable to connect to mARC server");
                System.exit(-1);
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
