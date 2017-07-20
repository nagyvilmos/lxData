/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * TestDataIO.java (lxData)
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: July 2017
 *==============================================================================
 */

package lxdata;


import java.io.File;
import java.io.IOException;
import lexa.core.data.ArrayDataSet;
import lexa.core.data.DataSet;
import lexa.core.data.exception.DataException;
import lexa.core.data.io.DataInput;
import lexa.core.data.io.DataOutput;
import lexa.core.data.io.DataReader;
import lexa.core.data.io.DataWriter;
import lexa.test.TestAnnotation;
import lexa.test.TestClass;
import lexa.test.TestResult;

/**
 * Test handler for the {@link lexa.core.data.io} stack
 * @author  willaimnw
 * @since   2017-07
 */
public class TestDataIO
        extends TestClass
{
    private DataSet data;
    private File file;

    /**
     * Set up test class
     * @return a {@link TestResult} with the results
     */
    public TestResult setUpDataIO()
    {
        this.data = new ArrayDataSet();
        this.file = new File("dataio.test.lexa");

        return TestResult.all(
                TestResult.result(false, this.file.exists(), "test data file already exists"),
                TestDataSet.populate(this.data)
        );
    }
    /**
     * tear down test class
     * @return a {@link TestResult} with the results
     */
    public TestResult tearDownDataIO()
    {
        this.data = null;
        if (this.file == null || !this.file.exists())
            return TestResult.result(true);
        return TestResult.result(true, this.file.delete(),
                "Cannot delete the result file " + this.file);
    }

    /**
     * Check that the dataset can be written to a file
     * @return a {@link TestResult} with the results
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 10, setUp = "setUpDataIO")
    public TestResult writeToFile()
            throws IOException
    {
        DataWriter dw = new DataWriter(this.file);
        dw.write(this.data);
        dw.close();
        return TestResult.result(true);
    }

    /**
     * Check that the dataset can be read from a file
     * @return a {@link TestResult} with the results
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 20, tearDown = "tearDownDataIO")
    public TestResult readFromFile()
            throws IOException
    {
        DataReader dr = new DataReader(this.file, this.data.factory());
        DataSet read = dr.read();
        dr.close();
        return TestResult.result(this.data, read);
    }

    /**
     * Check that the dataset can be written to a file
     * @return a {@link TestResult} with the results
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 30, setUp = "setUpDataIO")
    public TestResult writeToFileSingle()
            throws IOException
    {
        DataWriter.writeDataFile(this.file, this.data);
        return TestResult.all(
                TestResult.result(true, this.file.canRead()),
                TestResult.result(true, this.file.canWrite())
                );
    }

    /**
     * Check that the dataset can be read from a file
     * @return a {@link TestResult} with the results
     * @throws IOException when an IO exception occurs
     */
    @TestAnnotation(order = 40, tearDown = "tearDownDataIO")
    public TestResult readFromFileSingle()
            throws IOException
    {
        return TestResult.result(this.data,
                DataReader.readDataFile(this.file)
        );
    }
    /**
     * Check that the dataset can be written to a file
     * @return  a {@link TestResult} with the results
     * @throws  IOException
     *          when an IO exception occurs
     * @throws  DataException
     *          when a data exception occurs
     */
    @TestAnnotation(order = 50, setUp = "setUpDataIO")
    public TestResult writeToBinary()
            throws IOException, DataException
    {
        try (DataOutput out = new DataOutput(this.file))
        {
            out.write(this.data);
            return TestResult.result(true, this.file.exists(), "File not created");
        }
    }

    /**
     * Check that the dataset can be read from a file
     * @return  a {@link TestResult} with the results
     * @throws  IOException
     *          when an IO exception occurs
     * @throws  DataException
     *          when a data exception occurs
     */
    @TestAnnotation(order = 60, tearDown = "tearDownDataIO")
    public TestResult readFromBinary()
            throws IOException, DataException
    {
        try (DataInput in = new DataInput(this.file))
        {
            return TestResult.result(this.data, in.read(), "Cannot read data");
        }
    }
    /**
     * Check that the dataset can be parsed from a string
     * @return  a {@link TestResult} with the results
     * @throws  IOException
     *          when an IO exception occurs
     * @throws  DataException
     *          when a data exception occurs
     */
    @TestAnnotation(order = 70)
    public TestResult parseString()
            throws IOException, DataException
    {
        String[] string = {
            "string a string",
            "int % 7",
            "sub {",
            "  a % 1",
            "  b $ 2.7",
            "}"
        };
        DataSet dataSet = new ArrayDataSet()
                  .put("string", "a string")
                  .put("int", 7)
                  .put("sub", new ArrayDataSet()
                        .put("a", 1)
                        .put("b", 2.7)
                  );
        return TestResult.result(dataSet, DataReader.parseString(string));
    }
}
