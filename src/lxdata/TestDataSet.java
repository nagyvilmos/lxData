/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxdata;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import lexa.core.data.DataSet;
import lexa.core.data.HashDataSet;
import lexa.core.data.SimpleDataSet;
import lexa.core.data.SimpleValueArray;
import lexa.core.data.ValueType;
import lexa.core.data.io.DataReader;
import lexa.core.data.io.DataWriter;
import lexa.test.TestClassMethod;
import lexa.test.TestMethod;

/**
 *
 * @author william
 */
@TestClassMethod(
        arguments = "dataSetTypes", 
        setUp = "setUpDataSet", 
        tearDown = "tearDownClass")
public class TestDataSet
        extends lexa.test.TestClass
{

    static Boolean populate(DataSet data)
    {
        data
                .put("boolean", true)
                .put("integer", 1)
                .put("long", 2L)
                .put("double",(Double)3.14)
                .put("date",new Date(8945094))
                .put("array", new SimpleValueArray()
                        .add("One")
                        .add(2)
                        .add(9876543210L)
                        .add(3.4)
                        .add(false)
                        .add(new SimpleValueArray(1,2))
                        .add(new SimpleDataSet().put("key","value"))
                        .add(new Date(6984564))
                )
                .put("dataset", new SimpleDataSet()
                        .put("null", null)
                        .put("extendedString", "1\\2#3-4\"5{6}7?8$9@a%b\nc")
                        .put("farewell", "So-long, farewell Adure!")
                )
                .put("string","test string");
        return true;
    }
    private DataSet data;
    private File file;

    /**
     *
     * @param arg
     * @return
     */
    public Boolean tearDownClass(Object arg)
    {
        this.data = null;
        if (this.file == null || !this.file.exists())
            return true;
        return this.file.delete();
    }

    /**
     *
     * @return
     */
    public Object[] dataSetTypes()
    {
        return new Object[]{"simple","hash"};
    }
    
    /**
     *
     * @param arg
     * @return
     */
    public Boolean setUpDataSet(Object arg)
    {
        String type = (String)arg;
        switch (type)
        {
        case "simple" :
        {
            this.data = new SimpleDataSet();
            break;
        }
        case "hash" :
        {
            this.data = new HashDataSet();
            break;
        }
        default :
        {
            return false;
        }
        }
        this.file = new File(type + ".test.lexa");
        return TestDataSet.populate(this.data);
    }
    
    /**
     *
     * @param arg
     * @return
     * @throws IOException
     */
    @TestMethod(order = 0)
    public Boolean populated(Object arg) throws IOException
    {
        return (this.data.size() == 8 &&
                this.data.get("boolean").getType().equals(ValueType.BOOLEAN) &&
                this.data.get("integer").getType().equals(ValueType.INTEGER) &&
                this.data.get("long").getType().equals(ValueType.LONG) &&
                this.data.get("double").getType().equals(ValueType.DOUBLE) &&
                this.data.get("date").getType().equals(ValueType.DATE) &&
                this.data.get("array").getType().equals(ValueType.ARRAY) &&
                this.data.get("dataset").getType().equals(ValueType.DATA_SET) &&
                this.data.get("string").getType().equals(ValueType.STRING));
    }

    /**
     *
     * @param arg
     * @return
     */
    @TestMethod(order = 1)
    public Boolean printFormatted(Object arg)
    {
        this.data.printFormatted(System.out);
        return true;
    }

    /**
     *
     * @param arg
     * @return
     * @throws IOException
     */
    @TestMethod(order = 2)
    public Boolean writeToFile(Object arg)
            throws IOException
    {
        DataWriter dw = new DataWriter(this.file);
        dw.write(this.data);
        dw.close();
        return true;
    }

    /**
     *
     * @param arg
     * @return
     * @throws IOException
     */
    @TestMethod(order = 3)
    public Boolean readFromFile(Object arg)
            throws IOException
    {
        DataReader dr = new DataReader(this.file);
        DataSet read = dr.read();
        dr.close();
        return this.data.equals(read);
    }
//        DataSet data = test(new SimpleDataSet());
//        ConfigDataSet config = new ConfigDataSet(data);
//        config.printFormatted(System.out);
//        try
//        {
//            config.close(); // should be okay
//            config.reset();
//        }
//        catch (DataException ex)
//        {
//            System.out.println("failed to read config");
//            ex.printStackTrace(System.out);
//        }
//        new SimpleDataSet(config); // reads everything
//        try
//        {
//            System.out.println("Exception due");
//            config.get("MISSING KEY"); // should cause an error too
//            System.out.println("missing exception");
//            config.close(); // should be okay
//        }
//        catch (DataException ex)
//        {
//            System.out.println("Exception occured");
//        }
//    }
    
}
