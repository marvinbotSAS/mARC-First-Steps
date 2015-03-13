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
    
    static public void main()
    {
        Connector connector = new Connector();

        // connect to the server
        ConnectToAmARCServer.doIt(connector, "127.0.0.1", "1254");
        String[] fields = new String[]{"title","text"};
        String query = "orange agent Vietnam";
        String[][] results = null;
        BasicQuerying.main(connector, fields, query, results);
        
        String header = fields[0];
        for (int i = 1 ; i < fields.length;i++ )
        {
            header +=  " | " + fields[i];
        }
        System.out.println(header);
        int lineNumbers = results[0].length;
        
        for ( int col = 0; col < lineNumbers;col++)
        {
            String line = "";
            for (int i = 0; i < results.length ; i++)
            {
                line += results[i][col]+ " | ";
            }
            System.out.println(line);
        }

    }
    /**
     * 
     * @param connector  the server 
     * @param fields  format for the output according to the master table fields
     * @param query query
     * @param results OUT : results as a 2D array
     */
    static public void main(Connector connector, String[] fields, String query, String[][] results)
    {
         // STEP #3 : basic querying
        if ( fields == null )
        {
            fields = new String[]{"title", "text"};
        } // the format to show the results
        if ( query == null || query.isEmpty())
        {
            query = "orange agent Vietnam";
        }
        String f = "format = ";
        for (String s: fields)
        {
            f += s+" ";
        }
        String[] format = new String[]{f};
        BasicQuerying.doIt(connector, query, format, results);
    }
        

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
            String[] fields = format; // the fields to retrieve
            results = new String[fields.length][doc_number]; // 2D array containing the results for each field format
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
