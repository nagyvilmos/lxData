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
import lexa.core.data.DataFactoryItem;
import lexa.core.data.DataItem;
import lexa.core.data.DataSet;
import lexa.core.data.DataValue;
import lexa.core.data.HashFactory;
import lexa.core.data.config.ConfigFactory;
import lexa.test.TestAnnotation;
import lexa.test.TestResult;

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
     * @return a {@link TestResult} with the results
     */
    public TestResult setUpFactory(Object arg)
    {
        this.factory = TestFactory.factory((String)arg);
        return TestResult.notNull(this.factory);
    }

    /**
     * Tear down the test class
     * @param arg the type of factory being tested
     * @return a {@link TestResult} with the results
     */
    public TestResult tearDownFactory(Object arg)
    {
        this.factory = null;
        return TestResult.isNull(this.factory);
    }

    /**
     * Check that a data set can be created
     * @param arg the type of factory being tested
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation()
    public TestResult getDataSet(Object arg)
    {
        return this.checkCreated(
                this.factory.getDataSet());
    }

    /**
     * Check that a data item can be created
     * @param arg the type of factory being tested
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation()
    public TestResult getDataItem(Object arg)
    {
        return this.checkCreated(
                this.factory.getDataItem("k", arg));
    }

    /**
     * Check that a data array can be created
     * @param arg the type of factory being tested
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation()
    public TestResult getDataArray(Object arg)
    {
        return this.checkCreated(
                this.factory.getDataArray());
    }

    /**
     * Check that a data value can be created
     * @param arg the type of factory being tested
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation()
    public TestResult getDataValue(Object arg)
    {
        return this.checkCreated(
                this.factory.getDataValue(arg));
    }

    /**
     * Check that a data set can be cloned
     * @param arg the type of factory being cloned
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public TestResult cloneDataSet(Object arg)
    {
        DataSet test = this.factory.getDataSet();
        DataSet clone = this.factory.clone(test);
        return TestResult.all(
                TestResult.result(test, clone),
                TestResult.result(false, test == clone),
                this.checkCreated(clone)
        );
    }

    /**
     * Check that a data item can be cloned
     * @param arg the type of factory being cloned
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public TestResult cloneDataItem(Object arg)
    {
        DataItem test = this.factory.getDataItem("k", arg);
        DataItem clone = this.factory.clone(test);
        return TestResult.all(
                TestResult.result(test, clone),
                TestResult.result(false, test == clone),
                this.checkCreated(clone)
        );
    }

    /**
     * Check that a data value can be cloned
     * @param arg the type of factory being cloned
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public TestResult cloneDataValue(Object arg)
    {
        DataValue test = this.factory.getDataValue(arg);
        DataValue clone = this.factory.clone(test);
        return TestResult.all(
                TestResult.result(test, clone),
                TestResult.result(false, test == clone),
                this.checkCreated(clone)
        );
    }

    /**
     * Check that a data array can be cloned
     * @param arg the type of factory being cloned
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public TestResult cloneDataArray(Object arg)
    {
        DataArray test = this.factory.getDataArray();
        DataArray clone = this.factory.clone(test);
        return TestResult.all(
                TestResult.result(test, clone),
                TestResult.result(false, test == clone),
                this.checkCreated(clone)
        );
    }

    /**
     * Check that a data set can be converted
     * @param arg the type of factory being converted
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public TestResult convertDataSet(Object arg)
    {
        DataSet test = TestFactory.factory((String)arg).getDataSet();
        DataSet convert = this.factory.convert(test);
        return TestResult.all(
                TestResult.result(test, convert),
                this.checkCreated(convert)
        );
    }

    /**
     * Check that a data item can be converted
     * @param arg the type of factory being converted
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public TestResult convertDataItem(Object arg)
    {
        DataItem test = TestFactory.factory((String)arg).getDataItem("k", arg);
        DataItem convert = this.factory.convert(test);
        return TestResult.all(
                TestResult.result(test, convert),
                this.checkCreated(convert)
        );
    }

    /**
     * Check that a data value can be converted
     * @param arg the type of factory being converted
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public TestResult convertDataValue(Object arg)
    {
        DataValue test = TestFactory.factory((String)arg).getDataValue(arg);
        DataValue convert = this.factory.convert(test);
        return TestResult.all(
                TestResult.result(test, convert),
                this.checkCreated(convert)
        );
    }

    /**
     * Check that a data array can be converted
     * @param arg the type of factory being converted
     * @return a {@link TestResult} with the results
     */
    @TestAnnotation(arguments = "copyFactoryTypes")
    public TestResult convertDataArray(Object arg)
    {
        DataArray test = TestFactory.factory((String)arg).getDataArray();
        DataArray convert = this.factory.convert(test);
        return TestResult.all(
                TestResult.result(test, convert),
                this.checkCreated(convert)
        );
    }

    private TestResult checkCreated(DataFactoryItem test)
    {
        return TestResult.all(
                TestResult.notNull(test),
                TestResult.result(
                    this.factory.checkFactory(test.factory()))
        );
    }
}
