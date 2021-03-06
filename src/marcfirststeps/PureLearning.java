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
public class PureLearning {
    
    public static void main()
    {
        Connector connector = new Connector();

        // connect to the server
        ConnectToAmARCServer.doIt(connector, "127.0.0.1", "1254");
        String signal = "the text to learn";
        String[] shapes =null ;
        String[] activities = null;
        PureLearning.main(connector, null, shapes, activities);
        if ( shapes != null )
        {
            System.out.println("indexation result :");
            int i = 0;
            for ( String s : shapes)
            {
                System.out.println(s+" : "+activities[i++]);
            }
        }
    }
    /**
     *  
     * @param connector  the server
     * @param signal     the text to learn
     * @param shapes     OUT : indexation data
     * @param activities OUT : associated activity
     */
    public static void main(Connector connector, String signal ,String[] shapes, String[] activities ) 
    {
        PureLearning.doIt(connector, signal, shapes, activities);
    }
    
    
    /**
     * 
     * @param connector  IN  : the server
     * @param signal     IN  : the text to learn
     * @param shapes     OUT : the indexation vector data
     * @param activities OUT : associated activites
     */
    static public void doIt(Connector connector,String signal, String[] shapes, String[] activities)
    {
        try
        {
            connector.directExecute = false; // script mode
            connector.openScript(null); // script starts after this line
            connector.SESSION_Store(signal,"ranked","-1"); // learning but NO indexation
            connector.CONTEXTS_Fetch("-1","1","1");
            connector.CONTEXTS_Drop("1");
            connector.executeScript(); // execute script on mARC server side
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
