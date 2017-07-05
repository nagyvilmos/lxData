/*
 *=================================================================================
 * Lexa - Property of William Norman-Walker
 *---------------------------------------------------------------------------------
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
import lexa.test.TestResult;

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
     * @return a {@link TestResult} with the results
     */
    static TestResult populate(DataSet data)
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
                .put("string","test string")
                .put("NaN", Double.NaN)
                .put("Inf", Double.POSITIVE_INFINITY)
                .put("NegInf", Double.NEGATIVE_INFINITY)
                .put("emptyString", "");
        return TestResult.notNull(data);
    }

    private DataSet data;
    private File file;

    /**
     * tear down test class
     * @param arg the type of data set
     * @return a {@link TestResult} with the results
     */
    public TestResult tearDownDataSet(Object arg)
    {
        this.data = null;
        if (this.file == null || !this.file.exists())
            return TestResult.result(true);
        return TestResult.result(true, this.file.delete(),
                "Cannot delete the result file " + this.file);
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
     * @return a {@link TestResult} with the results
     */
    public TestResult setUpDataSet(Object arg)
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
            return TestResult.result(true, false, "Invalid type");
        }
        }
        this.file = new File(type + ".test.lexa");
        return TestDataSet.populate(this.data);
    }

    /**
     * Check that the data set is populated.
     * @param arg the type of data set
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 0)
    public TestResult populated(Object arg)
    {
        return TestResult.all(
                TestResult.result(12, this.data.size()),
                TestResult.result(DataType.BOOLEAN,
                        this.data.get("boolean").getType()),
                TestResult.result(DataType.INTEGER,
                        this.data.get("integer").getType()),
                TestResult.result(DataType.LONG,
                        this.data.get("long").getType()),
                TestResult.result(DataType.DOUBLE,
                        this.data.get("double").getType()),
                TestResult.result(DataType.DATE,
                        this.data.get("date").getType()),
                TestResult.result(DataType.ARRAY,
                        this.data.get("array").getType()),
                TestResult.result(DataType.DATA_SET,
                        this.data.get("dataset").getType()),
                TestResult.result(DataType.STRING,
                        this.data.get("string").getType()),
                TestResult.result(DataType.BOOLEAN,
                        this.data.item("array:4").getType()),
                TestResult.result(DataType.INTEGER,
                        this.data.item("array:5:1").getType()),
                TestResult.result(DataType.STRING,
                        this.data.item("array:6.key").getType()),
                TestResult.result(DataType.STRING,
                        this.data.item("dataset.farewell").getType()),
                TestResult.result(DataType.DOUBLE,
                        this.data.item("NaN").getType()),
                TestResult.result(DataType.DOUBLE,
                        this.data.item("Inf").getType()),
                TestResult.result(DataType.DOUBLE,
                        this.data.item("NegInf").getType()),
                TestResult.result(DataType.STRING,
                        this.data.item("emptyString").getType())
        );
    }

    /**
     * Check that the dataset can be printed out
     * @param arg the type of data set
     * @return a {@link TestResult} with the results
     * @throws java.io.FileNotFoundException if the file for output cannot be found
     */
    @TestAnnotation(order = 1, tearDown = "tearDownPrintFormatted")
    public TestResult printFormatted(Object arg)
            throws FileNotFoundException
    {
        try (PrintStream ps = new PrintStream("printFormatted.txt"))
        {
            this.data.printFormatted(ps);
        }
        return TestResult.result(true);
    }

    /**
     * Delete the file created by printFormatted
     * @param arg the type of data set
     * @return a {@link TestResult} with the results
     */
    public TestResult tearDownPrintFormatted(Object arg)
    {
        File pf = new File("printFormatted.txt");
        return TestResult.result(true, pf.delete(),
                "No file to delete");
    }

    /**
     * Check that the dataset can be written to a file
     * @param arg the type of data set
     * @return a {@link TestResult} with the results
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 2)
    public TestResult writeToFile(Object arg)
            throws IOException
    {
        DataWriter dw = new DataWriter(this.file);
        dw.write(this.data);
        dw.close();
        return TestResult.result(true);
    }

    /**
     * Check that the dataset can be read from a file
     * @param arg the type of data set
     * @return a {@link TestResult} with the results
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 3)
    public TestResult readFromFile(Object arg)
            throws IOException
    {
        DataReader dr = new DataReader(this.file, this.data.factory());
        DataSet read = dr.read();
        dr.close();
        return TestResult.result(this.data, read);
    }

    /**
     * Add an item to a clone and check the two are now different
     * @param arg the type of data set
     * @return a {@link TestResult} with the results
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 4)
    public TestResult addToClone(Object arg)
            throws IOException
    {
        DataSet clone = this.data.factory().clone(this.data);
        clone.put("new", 7403);
        return new TestResult (!clone.equals(this.data),
                "Clone is the same as data after add");
    }

    /**
     * Remove an item from a clone and check the two are now different
     * @param arg the type of data set
     * @return a {@link TestResult} with the results
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 5)
    public TestResult removeFromClone(Object arg)
            throws IOException
    {
        DataSet clone = this.data.factory().clone(this.data);
        clone.remove("integer");
        return new TestResult (!clone.equals(this.data),
                "Clone is the same as data after removal");
    }
    /**
     * Add an item to a clone and check the two are now different
     * @param arg the type of data set
     * @return a {@link TestResult} with the results
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 6)
    public TestResult addToCloneArray(Object arg)
            throws IOException
    {
        DataSet clone = this.data.factory().clone(this.data);
        clone.getArray("array").add(2);
        return new TestResult (!clone.equals(this.data),
                "Clone is the same as data after add");
    }

    /**
     * Remove an item from a clone and check the two are now different
     * @param arg the type of data set
     * @return a {@link TestResult} with the results
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 7)
    public TestResult removeFromCloneArray(Object arg)
            throws IOException
    {
        DataSet clone = this.data.factory().clone(this.data);
        clone.getArray("array").remove(2);
        return new TestResult (!clone.equals(this.data),
                "Clone is the same as data after removal");
    }
    /**
     * Add an item to a clone and check the two are now different
     * @param arg the type of data set
     * @return a {@link TestResult} with the results
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 8)
    public TestResult addToCloneDataSet(Object arg)
            throws IOException
    {
        DataSet clone = this.data.factory().clone(this.data);
        clone.getDataSet("dataset").put("x", 2);
        return new TestResult (!clone.equals(this.data),
                "Clone is the same as data after add");
    }

    /**
     * Remove an item from a clone and check the two are now different
     * @param arg the type of data set
     * @return a {@link TestResult} with the results
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 9)
    public TestResult removeFromCloneDataSet(Object arg)
            throws IOException
    {
        DataSet clone = this.data.factory().clone(this.data);
        clone.getDataSet("dataset").remove("farewell");
        return new TestResult (!clone.equals(this.data),
                "Clone is the same as data after removal");
    }
}
