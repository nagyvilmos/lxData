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
     * @return {@code true} if successful, otherwise {@code false}
     */
    public Boolean setUpClass()
    {
        DataSet d = new ArrayDataSet();
        if (!TestDataSet.populate(d))
        {
            return false;
        }
        this.data = new SealedDataSet(d);
        return true;
    }

    /**
     * Tear down the test class
     * @return {@code true} if successful, otherwise {@code false}
     */
    public Boolean tearDownClass()
    {
        this.data = null;
        return true;
    }

     /**
     * Set up for a single test
     * @return {@code true} if successful, otherwise {@code false}
     */
    public Boolean setUpTest()
    {
        this.config = new ConfigDataSet(this.data);
        return true;
    }

     /**
     * Tear down for a single test
     * @return {@code true} if successful, otherwise {@code false}
     */
    public Boolean tearDownTest()
    {
        this.config = null;
        return true;
    }

    /**
     * Create configuration data set
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(order = 0, setUp = "setUpTest")
    public Boolean createConfig()
    {
        return (this.config != null);
    }

    /**
     * Check that the configuration is the same as the original data.
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(order = 100)
    public Boolean configEqualsData()
    {
        return this.config.equals(this.data);
    }

    /**
     * Check the config has all been read.
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(order = 150)
    public Boolean isRead()
    {
        return this.config.isRead();
    }

    /**
     * Check that the config can be closed
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(order = 200)
    public Boolean closeConfig()
    {
        try
        {
            this.config.close();
        }
        catch (DataException ex)
        {
            // if you can't close then an exception is thrown
            return false;
        }
        return true;
    }

    /**
     * Check that the config resets
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(order = 300, tearDown = "tearDownTest")
    public Boolean resetConfig()
    {
        this.config.reset();
        return !this.config.isRead();
    }

    /**
     * Check that the config has an exception when closed and not read.
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(order = 400, setUp = "setUpTest", tearDown = "tearDownTest")
    public Boolean closeException()
    {
        try
        {
            this.config.close();
        }
        catch (DataException ex)
        {
            return true;
        }
        return false;
    }

     /**
     * Check that by copying config all of its conent is marked as read.
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(order = 500, setUp = "setUpTest", tearDown = "tearDownTest")
    public Boolean copyToRead()

     /**
     * Check that each config item contains it's correct path.
     * @return {@code true} if successful, otherwise {@code false}
     */
    {
            DataSet copy = new ArrayDataSet(this.config);
            return this.config.isRead();
    }

    /**
     * Check that the config contains the correct paths.
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(order = 550, setUp = "setUpTest", tearDown = "tearDownTest")
    public Boolean pathName()
    {
        return TestConfig.validateConfigPath(null, config);
    }

    /**
     * Check that each item in a config contains the correct path.
     * @param path
     *          the path to the config
     * @param config
     *          the config to check
     * @return {@code true} if successful, otherwise {@code false}
     */
    private static boolean validateConfigPath(String path, ConfigDataSet config)
    {
        for (DataItem item : config)
        {
            String itemPath = path == null ? item.getKey() :
                    path + '.' + item.getKey();
            if (!TestConfig.validateConfigPath(itemPath, (ConfigDataItem)item))
            {
                return false;
            }
        }
        return (path == null ? "[root]" : path).equals(config.getPath());
    }

    /**
     * Check that an item and its value contain the correct path.
     * @param path
     *          the path to the config
     * @param config
     *          the config to check
     * @return {@code true} if successful, otherwise {@code false}
     */
    private static boolean validateConfigPath(String path, ConfigDataItem config)
    {
        return path.equals(config.getPath()) &&
                TestConfig.validateConfigPath(path,
                        (ConfigDataValue)config.getValue());
    }

    /**
     * Check that a value and its children contain the correct path.
     * @param path
     *          the path to the config
     * @param config
     *          the config to check
     * @return {@code true} if successful, otherwise {@code false}
     */
    private static boolean validateConfigPath(String path, ConfigDataValue config)
    {
        switch (config.getType())
        {
            case ARRAY :
            {
                if (!TestConfig.validateConfigPath(path, config.getArray()))
                {
                    return false;
                }
                break;
            }
            case DATA_SET :
            {
                if (!TestConfig.validateConfigPath(path, config.getDataSet()))
                {
                    return false;
                }
                break;
            }
        }
        return path.equals(config.getPath());
    }

    /**
     * Check that each value in an array contains the correct path.
     * @param path
     *          the path to the config
     * @param config
     *          the config to check
     * @return {@code true} if successful, otherwise {@code false}
     */
    private static boolean validateConfigPath(String path, ConfigDataArray config)
    {
        for (int index = 0; index < config.size(); index++)
        {
            String itemPath = path + ':' + Integer.toString(index);
            if (!TestConfig.validateConfigPath(itemPath, config.get(index)))
            {
                return false;
            }
        }
        return path.equals(config.getPath());
    }

    /**
     * Check that empty config is marked as read.
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(order = 600, tearDown = "tearDownTest")
    public Boolean emptyConfig()
    {
            this.config = new ConfigDataSet(null);
            return this.config.isRead();
    }
}
