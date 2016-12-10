/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lexa.core.data.DataSet;
import lexa.core.data.SimpleDataSet;
import lexa.core.data.config.ConfigDataSet;
import lexa.core.data.io.DataReader;
import lexa.core.data.io.DataWriter;
import lexa.test.TestClass;
import lexa.test.TestException;
import lexa.test.TestMethod;

/**
 *
 * @author william
 */
public class TestConfig
        extends TestClass
{
    private File file;
    private DataSet data;
    private ConfigDataSet config;

    @Override
    public Boolean setUpClass() throws TestException
    {
        DataSet d = new SimpleDataSet();
        if (!TestDataSet.populate(d))
            return false;
        try
        {
            this.file = new File("test.lexa");
            new DataWriter(this.file).write(d);
        } catch (IOException ex)
        {
            throw new TestException("Cannot create test file", ex);
        }
        return true;
    }

    @Override
    public Boolean tearDownClass() throws TestException
    {
        if (!this.file.exists())
        {
            return true;
        }
        return this.file.delete();
    }
    
    @TestMethod
    public Boolean createConfig() throws TestException
    {
        try
        {
            this.config = new ConfigDataSet(
                    new DataReader(this.file).read()
            );
        } catch (IOException ex)
        {
            throw new TestException("Cannot load test file", ex);
        }
        return true;
    }
}
