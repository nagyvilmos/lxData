/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataTest.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: April 2009
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2016-01-28   WNW 16-01       Update javadoc.
 * 2016-08-20   WNW 16-08       add printFormatted method to DataSet
 * 2016-09-12   WNW 16-09       Update for new config package and HashDataSet
 *================================================================================
 */
package lxdata;

import java.io.File;
import java.io.IOException;
import lexa.core.data.DataSet;
import lexa.core.data.SimpleDataSet;
import lexa.core.data.SimpleValueArray;
import lexa.core.data.config.ConfigDataSet;
import lexa.core.data.exception.DataException;
import lexa.core.data.io.DataReader;
import lexa.core.data.io.DataWriter;

/**
 *
 * @author william
 */
public class DataTest
{

	/**
	 * @param args the command line arguments
	 * @throws java.io.IOException when an IO exception occurs
	 */
	public static void main(String[] args)
			throws IOException
	{
         testSimpleDataSet();
         testHashDataSet();
	}
    
    private static void testSimpleDataSet() throws IOException
    {
        System.out.println("lxdata.DataTest.testSimpleDataSet()");
        DataSet data = test(new SimpleDataSet());
        ConfigDataSet config = new ConfigDataSet(data);
        config.printFormatted(System.out);
        try
        {
            config.close(); // should be okay
            config.reset();
        }
        catch (DataException ex)
        {
            System.out.println("failed to read config");
            ex.printStackTrace(System.out);
        }
        new SimpleDataSet(config); // reads everything
        try
        {
            System.out.println("Exception due");
            config.get("MISSING KEY"); // should cause an error too
            System.out.println("missing exception");
            config.close(); // should be okay
        }
        catch (DataException ex)
        {
            System.out.println("Exception occured");
        }
    }
    
    private static DataSet test(DataSet data) throws IOException
    {
        data
                .put("integer", 1)
                .put("array", new SimpleValueArray()
                        .add("One")
                        .add(2)
                        .add(9876543210L)
                        .add(3.4)
                        .add(false)
                        .add(new SimpleValueArray(1,2))
                        .add(new SimpleDataSet().put("key","value"))
                        .add(new java.util.Date())
                )
                .put("dataset", new SimpleDataSet()
                        .put("null", null)
                        .put("extendedString", "1\\2#3-4\"5{6}7?8$9@a%b\nc")
                        .put("farewell", "So-long, farewell Adure!")
                );

        //System.out.println(data);
        data.printFormatted(System.out);
        File test = new File("test.lexa");
        DataWriter dw = new DataWriter(test);
        dw.write(data);
        dw.close();
        DataReader dr = new DataReader(test);
        DataSet read = dr.read();
        //System.out.println(data);
        data.printFormatted(System.out);

        if (!data.equals(read))
        {
            System.out.println("Data sets do not match");
        } else {
            System.out.println("Tested okay!");
        }
        return data;
    }

    private static void testHashDataSet()
            throws IOException
    {
        System.out.println("lxdata.DataTest.testHashDataSet()");
        test(new lexa.core.data.HashDataSet());
    }
}
