/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxdata;

import lexa.core.data.DataSet;
import lexa.core.data.SealedDataSet;
import lexa.core.data.SimpleDataSet;
import lexa.core.data.config.ConfigDataSet;
import lexa.core.data.exception.DataException;
import lexa.test.TestClass;
import lexa.test.TestClassMethod;
import lexa.test.TestMethod;

/**
 *
 * @author william
 */
@TestClassMethod(setUp = "setUpClass",tearDown = "tearDownClass")
public class TestConfig
        extends TestClass
{
    private DataSet data;
    private ConfigDataSet config;

    public Boolean setUpClass()
    {
        DataSet d = new SimpleDataSet();
        if (!TestDataSet.populate(d))
        {
            return false;
        }
        this.data = new SealedDataSet(d); // can't change it now
        return true;
    }

    public Boolean tearDownClass()
    {
        this.data = null;
        this.config = null;
        return true;
    }
    
    @TestMethod(order = 0)
    public Boolean createConfig() 
    {
        this.config = new ConfigDataSet(this.data);
        return true;
    }
    @TestMethod(order = 100)
    public Boolean configEqualsData() 
    {
        return this.config.equals(this.data);
    }
    @TestMethod(order = 150)
    public Boolean isRead() 
    {
        return this.config.isRead();
    }

    @TestMethod(order = 200)
    public Boolean closeConfig() throws DataException
    {
        this.config.close();
        return true;
    }
    @TestMethod(order = 300)
    public Boolean resetConfig() throws DataException
    {
        this.config.reset();
        return !this.config.isRead();
    }
    @TestMethod(order = 400)
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
}
