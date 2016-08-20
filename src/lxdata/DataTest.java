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
 *================================================================================
 */
package lxdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import lexa.core.data.DataSet;
import lexa.core.data.SimpleDataSet;
import lexa.core.data.SimpleValueArray;
import lexa.core.data.ValueArray;
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
	 * @throws java.io.FileNotFoundException when the file is not found
	 * @throws java.io.IOException when an IO exception occurs
	 */
	public static void main(String[] args)
			throws FileNotFoundException, IOException
	{
        DataSet print = new SimpleDataSet();
        System.out.println(print);
        print.put("1",2);
        System.out.println(print);

        DataSet data = new SimpleDataSet()
			.put("A", 1)
			.put("B", new SimpleValueArray()
				.add("One")
				.add(2)
				.add(9876543210L)
				.add(3.4)
				.add(false)
				.add(new SimpleValueArray(1,2))
				.add(new SimpleDataSet().put("key","value"))
				.add(new java.util.Date())
			)
			.put("C", new SimpleDataSet()
				.put("D", null)
				.put("E", "1\\2#3-4\"5{6}7?8$9@a%b\nc")
				.put("F", "So-long, farewell Adure!")
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
			System.err.println("Data sets do not match");
		} else {
			System.out.println("Tested okay!");
		}
	}
}
