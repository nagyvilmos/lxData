/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * TestDataSet.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: May 2017
 *================================================================================
 */
package lxdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import lexa.core.data.DataSet;
import lexa.core.data.HashDataSet;
import lexa.core.data.ArrayDataSet;
import lexa.core.data.ArrayDataArray;
import lexa.core.data.DataType;
import lexa.core.data.io.DataReader;
import lexa.core.data.io.DataWriter;
import lexa.test.TestAnnotation;

/**
 * Test handler for the {@link lexa.core.data} stack
 * @author william
 * @since 2017-05
 */
@TestAnnotation(
        arguments = "dataSetTypes",
        setUp = "setUpDataSet",
        tearDown = "tearDownDataSet")
public class TestDataSet
        extends lexa.test.TestClass
{

    /**
     * Populate a data set with test data
     * @param data the {@link DataSet to populate}
     * @return {@code true} if successful, otherwise {@code false}
     */
    static Boolean populate(DataSet data)
    {
        data
                .put("boolean", true)
                .put("integer", 1)
                .put("long", 2L)
                .put("double",(Double)3.14)
                .put("date",new Date(8945094))
                .put("array", new ArrayDataArray()
                        .add("One")
                        .add(2)
                        .add(9876543210L)
                        .add(3.4)
                        .add(false)
                        .add(new ArrayDataArray(1,2))
                        .add(new ArrayDataSet().put("key","value"))
                        .add(new Date(6984564))
                )
                .put("dataset", new ArrayDataSet()
                        .put("null", null)
                        .put("extendedString", "1\\2#3-4\"5{6}7?8$9@a%b\nc")
                        .put("farewell", "So-long, farewell Adure!")
                )
                .put("string","test string");
        return true;
    }

    private DataSet data;
    private File file;

    /**
     * tear down test class
     * @param arg the type of data set
     * @return {@code true} if successful, otherwise {@code false}
     */
    public Boolean tearDownDataSet(Object arg)
    {
        this.data = null;
        if (this.file == null || !this.file.exists())
            return true;
        return this.file.delete();
    }

    /**
     * Get the types of dataset to be tested
     * @return an array of names.
     */
    public Object[] dataSetTypes()
    {
        return new Object[]{"array","hash"};
    }

    /**
     * Set up test class
     * @param arg the type of data set
     * @return {@code true} if successful, otherwise {@code false}
     */
    public Boolean setUpDataSet(Object arg)
    {
        String type = (String)arg;
        switch (type)
        {
        case "array" :
        {
            this.data = new ArrayDataSet();
            break;
        }
        case "hash" :
        {
            this.data = new HashDataSet();
            break;
        }
        default :
        {
            return false;
        }
        }
        this.file = new File(type + ".test.lexa");
        return TestDataSet.populate(this.data);
    }

    /**
     * Check that the data set is populated.
     * @param arg the type of data set
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(order = 0)
    public Boolean populated(Object arg)
    {
        return (this.data.size() == 8 &&
                this.data.get("boolean").getType().equals(DataType.BOOLEAN) &&
                this.data.get("integer").getType().equals(DataType.INTEGER) &&
                this.data.get("long").getType().equals(DataType.LONG) &&
                this.data.get("double").getType().equals(DataType.DOUBLE) &&
                this.data.get("date").getType().equals(DataType.DATE) &&
                this.data.get("array").getType().equals(DataType.ARRAY) &&
                this.data.get("dataset").getType().equals(DataType.DATA_SET) &&
                this.data.get("string").getType().equals(DataType.STRING) &&
                this.data.item("array:4").getType().equals(DataType.BOOLEAN) &&
                this.data.item("array:5:1").getType().equals(DataType.INTEGER) &&
                this.data.item("array:6.key").getType().equals(DataType.STRING) &&
                this.data.item("dataset.farewell").getType().equals(DataType.STRING));
    }

    /**
     * Check that the dataset can be printed out
     * @param arg the type of data set
     * @return {@code true} if successful, otherwise {@code false}
     * @throws java.io.FileNotFoundException if the file for output cannot be found
     */
    @TestAnnotation(order = 1, tearDown = "tearDownPrintFormatted")
    public Boolean printFormatted(Object arg)
            throws FileNotFoundException
    {
        try (PrintStream ps = new PrintStream("printFormatted.txt"))
        {
            this.data.printFormatted(ps);
        }
        return true;
    }

    /**
     * Delete the file created by printFormatted
     * @param arg the type of data set
     * @return {@code true} if successful, otherwise {@code false}
     */
    public Boolean tearDownPrintFormatted(Object arg)
    {
        File pf = new File("printFormatted.txt");
        return pf.delete();
    }

    /**
     * Check that the dataset can be written to a file
     * @param arg the type of data set
     * @return {@code true} if successful, otherwise {@code false}
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 2)
    public Boolean writeToFile(Object arg)
            throws IOException
    {
        DataWriter dw = new DataWriter(this.file);
        dw.write(this.data);
        dw.close();
        return true;
    }

    /**
     * Check that the dataset can be read from a file
     * @param arg the type of data set
     * @return {@code true} if successful, otherwise {@code false}
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 3)
    public Boolean readFromFile(Object arg)
            throws IOException
    {
        DataReader dr = new DataReader(this.file, this.data.factory());
        DataSet read = dr.read();
        dr.close();
        return this.data.equals(read);
    }
}
