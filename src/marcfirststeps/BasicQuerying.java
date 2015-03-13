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
public class BasicQuerying {
    
    /**
     * 
     * @param connector IN : the server
     * @param query     IN : the query
     * @param format    IN : the output format of the results according to the master table fields
     * @param results   OUT : a 2D array containing the retrieved documents from the query
     */
    static public void doIt(Connector connector, String query, String[] format,String[][] results )
    {
        try
        {
            connector.directExecute = false; // script mode
            connector.openScript(null); // script starts after this line
            connector.SESSION_Clear("contexts"); // we empty the contexts stack
            connector.SESSION_StringToContext(query,"false"); // the query
            connector.SESSION_ContextToDoc(); // retrieve documents associated with the query
            connector.RESULTS_GetProperties("count","1"); // retrieve the number of returned documents
            connector.CONTEXTS_SetProperties("1",format); // set the retrieve format of data
            connector.CONTEXTS_Fetch("-1","1","1"); // retrieve the documents from the query
            connector.CONTEXTS_Drop("1");
            connector.executeScript(); // execute script on mARC server side
            String[] doc_numbers = connector. getDataByName("count",3);
            int doc_number = Integer.parseInt(doc_numbers[0]); // the number of retrieved docs
            String[] fields = new String[]{"title","text"}; // the fields to retrieve
            results = new String[2][doc_number]; // 2D array containing the results for each field format
            // we retrieve the data for each field format inside the 2D array
            int i = 0;
            for (String s : fields)
            {
                results[i++] = connector.getDataByName(s, 5);
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
