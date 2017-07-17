/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataTest.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: April 2009
 *================================================================================
 */
package lxdata;

import lexa.test.TestClass;
import lexa.test.TestRun;

/**
 * Test app for the {@link lexa.core.data} stack
 * @author william
 * @since 2009-04
 */
public class DataTest
{

	/**
     * Run the tests for the {@link lexa.core.data} stack
	 * @param args the command line arguments; not used
	 */
	public static void main(String[] args)
	{
        TestClass[] tests = new TestClass[]{
            new TestFactory(),
            new TestDataSet(),
            new TestDataIO(),
            new TestConfig(),
            new TestDataObject(),
            new TestCompare()
        };
        System.out.println(
                new TestRun(tests)
                        .execute()
                        .getReport(false, true)
        );
	}
}
