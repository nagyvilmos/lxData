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
        }
        throw new IllegalArgumentException("Invalid factory type");
    }

    private DataFactory factory;
    /**
     * Get the types of dataset to be tested
     * @return an array of names.
     */
    public Object[] factoryTypes()
    {
        return new Object[]{"array","hash"};
    }
    public Object[] copyFactoryTypes(Object arg)
    {
        return this.factoryTypes();
    }
    /**
     * Set up test class
     * @param arg the type of data set,
     * @return {@code true} if successful, otherwise {@code false}
     */
    public Boolean setUpFactory(Object arg)
    {
        this.factory = TestFactory.factory((String)arg);
        return true;
    }
    /**
     *
     * @param arg
     * @return
     */
    public Boolean tearDownFactory(Object arg)
    {
        this.factory = null;
        return true;
    }

    @TestAnnotation()
    public Boolean getDataSet(Object arg)
    {
        DataSet test = this.factory.getDataSet();
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    @TestAnnotation()
    public Boolean getDataItem(Object arg)
    {
        DataItem test = this.factory.getDataItem("k", arg);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    @TestAnnotation()
    public Boolean getDataArray(Object arg)
    {
        DataArray test = this.factory.getDataArray();
        return (test != null && this.factory.checkFactory(test.factory()));
    }
    @TestAnnotation()
    public Boolean getDataValue(Object arg)
    {
        DataValue test = this.factory.getDataValue(arg);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    @TestAnnotation(arguments = "copyFactoryTypes")
    public Boolean cloneDataSet(Object arg)
    {
        DataSet test = TestFactory.factory((String)arg).getDataSet();
        test = this.factory.clone(test);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    @TestAnnotation(arguments = "copyFactoryTypes")
    public Boolean cloneDataItem(Object arg)
    {
        DataItem test = TestFactory.factory((String)arg).getDataItem("k", arg);
        test = this.factory.clone(test);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    @TestAnnotation(arguments = "copyFactoryTypes")
    public Boolean cloneDataValue(Object arg)
    {
        DataValue test = TestFactory.factory((String)arg).getDataValue(arg);
        test = this.factory.clone(test);
        return (test != null && this.factory.checkFactory(test.factory()));
    }

    @TestAnnotation(arguments = "copyFactoryTypes")
    public Boolean cloneDataArray(Object arg)
    {
        DataArray test = TestFactory.factory((String)arg).getDataArray();
        test = this.factory.clone(test);
        return (test != null && this.factory.checkFactory(test.factory()));
    }
}
