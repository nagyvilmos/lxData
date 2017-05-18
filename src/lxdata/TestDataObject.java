/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * TestDataObject.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: May 2017
 *================================================================================
 */
package lxdata;

import java.util.Objects;
import lexa.core.data.DataSet;
import lexa.core.data.ArrayDataSet;
import lexa.core.data.object.DataObject;
import lexa.test.TestAnnotation;

/**
 * Test handler for the {@link lexa.core.data.object} stack
 * @author william
 * @since 2017-05
 */
public class TestDataObject
        extends lexa.test.TestClass
{
    private TestImpl testImpl;
    private DataSet data;

    /**
     * Check that object can be created
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(order = 0)
    public Boolean create()
    {
        this.testImpl = new TestImpl();
        this.testImpl.str = "first";
        this.testImpl.lng = 435L;

        return true;
    }

    /**
     * Check that an object can be converted to a data set
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(order = 100)
    public Boolean toData()
    {
        this.data = testImpl.toData();

        return true;
    }

    /**
     * Check that an object can be created from a data set
     * @return {@code true} if successful, otherwise {@code false}
     */
    @TestAnnotation(order = 200)
    public Boolean fromData()
    {
        TestImpl reload = new TestImpl();
        reload.fromData(this.data);
        return this.testImpl.equals(reload);
    }

    /**
     * Test class to use for testing {@link DataObject}
     */
    class TestImpl
            implements DataObject
    {
        private String str;
        private Long lng;

        @Override
        public DataSet toData()
        {
            return new ArrayDataSet()
                    .put("string", this.str)
                    .put("long", this.lng);
        }

        @Override
        public void fromData(DataSet data)
        {
            this.str=data.getString("string");
            this.lng=data.getLong("long");
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }
            if (obj == null)
            {
                return false;
            }
            if (getClass() != obj.getClass())
            {
                return false;
            }
            final TestImpl other = (TestImpl) obj;
            if (!Objects.equals(this.str, other.str))
            {
                return false;
            }
            if (!Objects.equals(this.lng, other.lng))
            {
                return false;
            }
            return true;
        }


    }
}
