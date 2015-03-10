/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lxdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import lexa.core.data.DataSet;
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
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	public static void main(String[] args)
			throws FileNotFoundException, IOException
	{
		DataSet data = new DataSet()
			.put("A", 1)
			.put("B", new ValueArray()
				.add("One")
				.add(2)
				.add(9876543210L)
				.add(3.4)
				.add(false)
				.add(new ValueArray(1,2))
				.add(new DataSet().put("key","value"))
				.add(new java.util.Date())
			)
			.put("C", new DataSet()
				.put("D", null)
				.put("E", "1\\2#3-4\"5{6}7?8$9@a%b\nc")
				.put("F", "So-long, farewell Adure!")
			);

		System.out.println(data);
		File test = new File("test.lexa");
		DataWriter dw = new DataWriter(test);
		dw.write(data);
		dw.close();
		DataReader dr = new DataReader(test);
		DataSet read = dr.read();
		System.out.println(read);
		if (!data.equals(read))
			System.err.println("Data sets do not match");
		
	}
}
