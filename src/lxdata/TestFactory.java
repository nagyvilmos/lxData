/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * TestFactory.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: May 2017
 *==============================================================================
 */
package lxdata;

import lexa.core.data.ArrayFactory;
import lexa.core.data.DataArray;
import lexa.core.data.DataFactory;
import lexa.core.data.DataItem;
import lexa.core.data.DataSet;
import lexa.core.data.DataValue;
import lexa.core.data.HashFactory;
import lexa.core.data.config.ConfigFactory;
import lexa.test.TestAnnotation;

/**
 * Test handler for the {@link lexa.core.data.DataFactory}
 * @author william
 * @since 2017-05
 */
@TestAnnotation(
        arguments = "factoryTypes",
        setUp = "setUpFactory",
        tearDown = "tearDownFactory")
public class TestFactory
        extends lexa.test.TestClass
{
    /**
     * Get a factory by it's name.
     * Supports array, hash and config.
     * @param type the type of factory
     * @return an instance of a factory
     */
    private static DataFactory factory(String type)
    {
        switch (type)
        {
            case "array" :
            {
                return ArrayFactory.factory;
            }
            case "hash" :
            {
                return HashFactory.factory;
            }
            case "config" :
            {
                return ConfigFactory.factory;
            }
        }
        throw new IllegalArgumentException("Invalid factory type");
    }

    /** The current factory being tested. */
    private DataFactory factory;

    /**
     * Get the types of dataset to be tested
     * @return an array of names.
     */
    public Object[] factoryTypes()
    {
        return new Object[]{
            "array",
            "hash",
            "config"
        };
    }

    /**
     * Get the types of dataset to test the copy functions
     * @param arg the destination factory
     * @return an array of names.
     */
    public Object[] copyFactoryTypes(Object arg)
    {
        return this.factoryTypes();
    }

    /**
     * Set up test class
     * @param arg the type of factory being tested
     * @return {@code true} if successful, otherwise {@code false}
     */
    public Boolean setUpFactory(Object arg)
    {
        this.factory = TestFactory.factory((String)arg);
        return true;
    }

    /**
     * Tear down the test class
     * @param arg the type of factory being tested
     * @return {@code true} if successful, otherwise {@code false}
     */
    public Boolean tearDownFactory(Object arg)
    {
        this.factory = null;
        return true;
    }

    /**
     * Check that a data set can be created
     * @param arg the type of factory being tested
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation()
    public Boolean getDataSet(Object arg)
    {
        DataSet test = this.factory.getDataSet();
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    /**
     * Check that a data item can be created
     * @param arg the type of factory being tested
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation()
    public Boolean getDataItem(Object arg)
    {
        DataItem test = this.factory.getDataItem("k", arg);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    /**
     * Check that a data array can be created
     * @param arg the type of factory being tested
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation()
    public Boolean getDataArray(Object arg)
    {
        DataArray test = this.factory.getDataArray();
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    /**
     * Check that a data value can be created
     * @param arg the type of factory being tested
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation()
    public Boolean getDataValue(Object arg)
    {
        DataValue test = this.factory.getDataValue(arg);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    /**
     * Check that a data set can be cloned
     * @param arg the type of factory being cloned
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public Boolean cloneDataSet(Object arg)
    {
        DataSet test = TestFactory.factory((String)arg).getDataSet();
        test = this.factory.clone(test);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    /**
     * Check that a data item can be cloned
     * @param arg the type of factory being cloned
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public Boolean cloneDataItem(Object arg)
    {
        DataItem test = TestFactory.factory((String)arg).getDataItem("k", arg);
        test = this.factory.clone(test);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    /**
     * Check that a data value can be cloned
     * @param arg the type of factory being cloned
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public Boolean cloneDataValue(Object arg)
    {
        DataValue test = TestFactory.factory((String)arg).getDataValue(arg);
        test = this.factory.clone(test);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    /**
     * Check that a data array can be cloned
     * @param arg the type of factory being cloned
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public Boolean cloneDataArray(Object arg)
    {
        DataArray test = TestFactory.factory((String)arg).getDataArray();
        test = this.factory.clone(test);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    /**
     * Check that a data set can be converted
     * @param arg the type of factory being converted
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public Boolean convertDataSet(Object arg)
    {
        DataSet test = TestFactory.factory((String)arg).getDataSet();
        test = this.factory.convert(test);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    /**
     * Check that a data item can be converted
     * @param arg the type of factory being converted
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public Boolean convertDataItem(Object arg)
    {
        DataItem test = TestFactory.factory((String)arg).getDataItem("k", arg);
        test = this.factory.convert(test);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    /**
     * Check that a data value can be converted
     * @param arg the type of factory being converted
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public Boolean convertDataValue(Object arg)
    {
        DataValue test = TestFactory.factory((String)arg).getDataValue(arg);
        test = this.factory.convert(test);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    /**
     * Check that a data array can be converted
     * @param arg the type of factory being converted
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public Boolean convertDataArray(Object arg)
    {
        DataArray test = TestFactory.factory((String)arg).getDataArray();
        test = this.factory.convert(test);
        return (test != null && this.factory.checkFactory(test.factory()));
    }
}
