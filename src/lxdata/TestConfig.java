/*
 *==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * TestConfig.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: May 2017
 *==============================================================================
 */
package lxdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import lexa.core.data.*;
import lexa.core.data.config.*;
import lexa.core.data.exception.DataException;
import lexa.core.data.io.DataWriter;
import lexa.test.TestAnnotation;
import lexa.test.TestClass;
import lexa.test.TestResult;

/**
 * Test handler for the {@link lexa.core.data.config} stack
 * @author william
 * @since 2017-05
 */
@TestAnnotation(setUp = "setUpClass",tearDown = "tearDownClass")
public class TestConfig
        extends TestClass
{
    private DataSet data;
    private ConfigDataSet config;

    /**
     * Set up test class
     * @return a {@link TestResult} with the results
     */
    public TestResult setUpClass()
    {
        DataSet d = new ArrayDataSet();
        TestResult populate = TestDataSet.populate(d);
        if (!populate.passed())
        {
            return populate;
        }
        this.data = new SealedDataSet(d);
        return TestResult.result(true);
    }

    /**
     * Tear down the test class
     * @return a {@link TestResult} with the results
     */
    public TestResult tearDownClass()
    {
        this.data = null;
        return TestResult.result(true);
    }

     /**
     * Set up for a single test
     * @return a {@link TestResult} with the results
     */
    public TestResult setUpTest()
    {
        this.config = new ConfigDataSet(this.data);
        return TestResult.result(true);
    }

     /**
     * Tear down for a single test
     * @return a {@link TestResult} with the results
     */
    public TestResult tearDownTest()
    {
        this.config = null;
        return TestResult.result(true);
    }

    /**
     * Create configuration data set
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 0, setUp = "setUpTest")
    public TestResult createConfig()
    {
        return TestResult.notNull(this.config);
    }

    /**
     * Check that the configuration is the same as the original data.
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 100)
    public TestResult configEqualsData()
    {
        return TestResult.result(this.data, this.config);
    }

    /**
     * Check the config has all been read.
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 150)
    public TestResult isRead()
    {
        return TestResult.result(true, this.config.isRead());
    }

    /**
     * Check that the config can be closed
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 200)
    public TestResult closeConfig()
    {
        try
        {
            this.config.close();
        }
        catch (DataException ex)
        {
            // if you can't close then an exception is thrown
            return TestResult.result(false);
        }
        return TestResult.result(true);
    }

    /**
     * Check that the config resets
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 300, tearDown = "tearDownTest")
    public TestResult resetConfig()
    {
        this.config.reset();
        return TestResult.result(false, this.config.isRead());
    }

    /**
     * Check that the config has an exception when closed and not read.
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 400, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult closeException()
    {
        try
        {
            this.config.close();
        }
        catch (DataException ex)
        {
            return TestResult.result(true);
        }
        return TestResult.result(false);
    }

     /**
     * Check that by copying config all of its conent is marked as read.
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 500, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult copyToRead()

     /**
     * Check that each config item contains it's correct path.
     * @return a {@link TestResult} with the results
     */
    {
        DataSet copy = new ArrayDataSet(this.config);
        return TestResult.all(
                TestResult.result(this.config.isRead()),
                TestResult.result(this.data, copy)
        );
    }

    /**
     * Check that the config contains the correct paths.
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 550, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult pathName()
    {
        return TestConfig.validateConfigPath(null, config);
    }

    /**
     * Check that each item in a config contains the correct path.
     * @param path
     *          the path to the config
     * @param config
     *          the config to check
     * @return a {@link TestResult} with the results
     */
    private static TestResult validateConfigPath(String path, ConfigDataSet config)
    {
        for (DataItem item : config)
        {
            String itemPath = path == null ? item.getKey() :
                    path + '.' + item.getKey();
            TestResult result =
                    TestConfig.validateConfigPath(itemPath, (ConfigDataItem)item);
            if (!result.passed())
            {
                return result;
            }
        }
        return TestResult.result(path == null ? "[root]" : path,
                config.getPath());
    }

    /**
     * Check that an item and its value contain the correct path.
     * @param path
     *          the path to the config
     * @param config
     *          the config to check
     * @return a {@link TestResult} with the results
     */
    private static TestResult validateConfigPath(String path, ConfigDataItem config)
    {
        return TestResult.all(
                TestConfig.validateConfigPath(path,
                        (ConfigDataValue)config.getValue()),
                TestResult.result(path, config.getPath())
        );
    }

    /**
     * Check that a value and its children contain the correct path.
     * @param path
     *          the path to the config
     * @param config
     *          the config to check
     * @return a {@link TestResult} with the results
     */
    private static TestResult validateConfigPath(String path, ConfigDataValue config)
    {
        TestResult result = TestResult.result(path, config.getPath());
        switch (config.getType())
        {
            case ARRAY :
            {
                return TestResult.all(
                        TestConfig.validateConfigPath(path, config.getArray()),
                        result);
            }
            case DATA_SET :
            {
                return TestResult.all(
                        TestConfig.validateConfigPath(path, config.getDataSet()),
                        result);
            }
        }
        return result;
    }

    /**
     * Check that each value in an array contains the correct path.
     * @param path
     *          the path to the config
     * @param config
     *          the config to check
     * @return a {@link TestResult} with the results
     */
    private static TestResult validateConfigPath(String path, ConfigDataArray config)
    {
        for (int index = 0; index < config.size(); index++)
        {
            String itemPath = path + ':' + Integer.toString(index);
            TestResult result =
                    TestConfig.validateConfigPath(itemPath, config.get(index));
            if (!result.passed())
            {
                return result;
            }
        }
        return TestResult.result(path, config.getPath());
    }

    /**
     * Check that empty config is marked as read.
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 600, tearDown = "tearDownTest")
    public TestResult emptyConfig()
    {
            this.config = new ConfigDataSet(null);
            return TestResult.result(this.config.isRead());
    }

    /**
     * Check that the config contains the correct types.
     * @return a {@link TestResult} with the results
     * @throws lexa.core.data.exception.DataException when it fails
     */
    @TestAnnotation(order = 650, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult validateTypeSinglePass() throws DataException
    {
        this.config.validateType("boolean", DataType.BOOLEAN);
        return TestResult.result(true);
    }

    /**
     * Check that the config throws an exception for incorrect types.
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 700, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult validateTypeSingleFail()
    {
        try
        {
            this.config.validateType("boolean", DataType.DOUBLE);
        }
        catch (DataException ex)
        {
            return TestResult.result(true);
        }
        return TestResult.result(false,true, "No exception");
    }

    /**
     * Check that the config contains the correct types.
     * @return a {@link TestResult} with the results
     * @throws lexa.core.data.exception.DataException when it fails
     */
    @TestAnnotation(order = 750, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult validateTypeMulti()
            throws DataException
    {
        this.config.validateType(
                "boolean", DataType.BOOLEAN,
                "NaN", DataType.DOUBLE
        );

        return TestResult.result(true);
    }

    /**
     * Check that the config throws an exception for missing items
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 800, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult validateTypeMissingItem()
    {
        try
        {
            // let the first pass
            this.config.validateType("xxx", DataType.BOOLEAN);

        }
        catch (DataException ex)
        {
            return TestResult.result(true);
        }
        return TestResult.result(false,true, "No exception");
    }

    /**
     * Check that the config throws an exception for incorrect number of arguments.
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 850, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult validateTypeOddArguments()
    {
        try
        {
            // let the first pass
            this.config.validateType("Boolean");

        }
        catch (DataException ex)
        {
            return TestResult.result(true);
        }
        return TestResult.result(false,true, "No exception");
    }

   /**
     * Check that the config throws an exception for name not a string
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 900, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult validateTypeNameNotString()
    {
        try
        {
            // let the first pass
            this.config.validateType(7, DataType.ARRAY);
        }
        catch (DataException ex)
        {
            return TestResult.result(true);
        }
        return TestResult.result(false,true, "No exception");
    }
   /**
     * Check that the config throws an exception for type not a DataType.
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 950, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult validateTypeNameTypeNotDataType()
    {
        try
        {
            // let the first pass
            this.config.validateType("Key");
        }
        catch (DataException ex)
        {
            return TestResult.result(true);
        }
        return TestResult.result(false,true, "No exception");
    }

   /**
     * Check that the config does not throw an exception for value the same as default
     * @return a {@link TestResult} with the results
     * @throws lexa.core.data.exception.DataException when it fails
     */
    @TestAnnotation(order = 1000, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult valueMatchesDefault()
            throws DataException
    {
        return TestResult.result(true,this.config.get("boolean", false).getBoolean());
    }

   /**
     * Check that the config does not throw an exception for value with null default
     * @return a {@link TestResult} with the results
     * @throws lexa.core.data.exception.DataException when it fails
     */
    @TestAnnotation(order = 1025, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult valueMatchesNull()
            throws DataException
    {
        return TestResult.result(true,this.config.get("boolean", null).getBoolean());
    }
   /**
     * Check that the config does throw an exception for value the different to default
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(order = 1050, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult valueMisatchesDefault()
    {
        try
        {
            this.config.get("boolean", 7);
        }
        catch (DataException ex)
        {
            return TestResult.result(true);
        }
        return TestResult.result(false,true, "No exception");
    }
   /**
     * Check that the config returns default when no value
     * @return a {@link TestResult} with the results
     * @throws lexa.core.data.exception.DataException when it fails
     */
    @TestAnnotation(order = 1100, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult valueGetDefault()
            throws DataException
    {
        return TestResult.result(76,this.config.get("xxx", 76).getInteger());
    }

   /**
     * Check that the config returns default when no value
     * @return a {@link TestResult} with the results
     * @throws java.io.IOException when it fails
     */
    @TestAnnotation(order = 1150,
            setUp = "setUpLoadFromFile",
            tearDown = "tearDownLoadFromFile")
    public TestResult loadFromFile() throws IOException

    {
        this.config = ConfigFactory.loadFromFile(
                new File("loadFromFile.test.lexa")
        );
        return TestResult.all(
                TestResult.result(this.data, this.config),
                TestResult.result("[loadFromFile.test.lexa]",
                        this.config.getPath())
        );
    }

    public TestResult setUpLoadFromFile()
            throws FileNotFoundException,
            IOException
    {
        File f = new File("loadFromFile.test.lexa");
        DataWriter dw  = new DataWriter(f);
        dw.write(this.data);
        dw.close();
        return TestResult.result(true,
                f.exists(), "File not created");
    }

    public TestResult tearDownLoadFromFile()
    {
        // delete the file
        File f = new File("loadFromFile.test.lexa");

        return TestResult.all(
                TestResult.result(true,f.delete(),"Failed to delete file"),
                this.tearDownTest()
        );
    }
}
