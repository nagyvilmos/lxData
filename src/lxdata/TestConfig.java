/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxdata;

import lexa.core.data.DataSet;
import lexa.core.data.SealedDataSet;
import lexa.core.data.ArrayDataSet;
import lexa.core.data.config.ConfigDataSet;
import lexa.core.data.config.ConfigDataValue;
import lexa.core.data.exception.DataException;
import lexa.test.TestAnnotation;
import lexa.test.TestClass;

/**
 *
 * @author william
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
        this.data = new SealedDataSet(d); // can't change it now
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

    public Boolean setUpTest()
    {
        this.config = new ConfigDataSet(this.data);
        return true;
    }

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
     *
     * @return
     */
    @TestAnnotation(order = 100)
    public Boolean configEqualsData()
    {
        return this.config.equals(this.data);
    }

    /**
     *
     * @return
     */
    @TestAnnotation(order = 150)
    public Boolean isRead()
    {
        return this.config.isRead();
    }

    /**
     *
     * @return
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
     *
     * @return
     * @throws DataException
     */
    @TestAnnotation(order = 300, tearDown = "tearDownTest")
    public Boolean resetConfig() throws DataException
    {
        this.config.reset();
        return !this.config.isRead();
    }

    /**
     *
     * @return
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
    @TestAnnotation(order = 500, setUp = "setUpTest", tearDown = "tearDownTest")
    public Boolean copyToRead()
    {
            DataSet copy = new ArrayDataSet(this.config);
            return this.config.isRead();
    }
    @TestAnnotation(order = 550, setUp = "setUpTest", tearDown = "tearDownTest")
    public Boolean pathName()
    {
            String key = "array:6.key";
            ConfigDataValue item = (ConfigDataValue)this.config.item(key);
            String path = item.getPath();
            return key.equals(path);
    }
    @TestAnnotation(order = 600, tearDown = "tearDownTest")
    public Boolean emptyConfig()
    {
            this.config = new ConfigDataSet(null);
            return this.config.isRead();
    }
}
