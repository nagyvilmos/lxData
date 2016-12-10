/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxdata;

import java.util.Objects;
import lexa.core.data.DataSet;
import lexa.core.data.SimpleDataSet;
import lexa.core.data.object.DataObject;
import lexa.test.TestException;
import lexa.test.TestMethod;

/**
 *
 * @author william
 */
public class TestDataObject
        extends lexa.test.TestClass
{

    private TestImpl testImpl;
    private DataSet data;
    @Override
    public Boolean setUpClass() throws TestException
    {
        return true;
    }

    @Override
    public Boolean tearDownClass() throws TestException
    {
        return true;
    }
    
    @TestMethod
    public Boolean create()
    {
        this.testImpl = new TestImpl();
        this.testImpl.str = "first";
        this.testImpl.lng = 435L;
        
        return true;
    }   

    @TestMethod
    public Boolean toData()
    {
        this.data = testImpl.toData();
        
        return true;
    }   

    @TestMethod
    public Boolean fromData()
    {
        TestImpl reload = new TestImpl();
        reload.fromData(this.data);
        return this.testImpl.equals(reload);
    }   

    class TestImpl
            implements DataObject
    {
        private String str;
        private Long lng;
        
        @Override
        public DataSet toData()
        {
            return new SimpleDataSet()
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
