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

import lexa.core.data.DataSet;
import lexa.core.data.SealedDataSet;
import lexa.core.data.ArrayDataSet;
import lexa.core.data.DataItem;
import lexa.core.data.config.ConfigDataArray;
import lexa.core.data.config.ConfigDataItem;
import lexa.core.data.config.ConfigDataSet;
import lexa.core.data.config.ConfigDataValue;
import lexa.core.data.exception.DataException;
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
}
