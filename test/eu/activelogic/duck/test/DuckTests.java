package eu.activelogic.duck.test;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import eu.activelogic.duck.Duck;
import eu.activelogic.duck.DuckException;
import eu.activelogic.duck.test.data.Four;
import eu.activelogic.duck.test.data.MyDuck;
import eu.activelogic.duck.test.data.MyDuck2;
import eu.activelogic.duck.test.data.MyDuck4;
import eu.activelogic.duck.test.data.MyDuck5;
import eu.activelogic.duck.test.data.SupertypeDuckInverted;
import eu.activelogic.duck.test.data.One;
import eu.activelogic.duck.test.data.SubtypeDuck;
import eu.activelogic.duck.test.data.SupertypeDuck;
import eu.activelogic.duck.test.data.Three;
import eu.activelogic.duck.test.data.Two;

/**
 * 
 * Test suite to cover fully the library.(May not be complete)
 * 
 * 
 * 
 * @author alex
 *
 */
public class DuckTests {

	@Test
	public void timingTest() throws Exception {
		Two two = new Two();
		long start = System.currentTimeMillis();
		MyDuck asDuck = Duck.quack(two, MyDuck.class);
		asDuck.method();
		System.out.println("Createon of first took: " + (System.currentTimeMillis() - start));

		System.out.println("Memory usage: " + Runtime.getRuntime().totalMemory() + " - " + Runtime.getRuntime().freeMemory());

		start = System.currentTimeMillis();
		asDuck = Duck.quack(two, MyDuck.class);
		asDuck.method();
		System.out.println("Createon of second took: " + (System.currentTimeMillis() - start));

		System.out.println("Memory usage: " + Runtime.getRuntime().totalMemory() + " - " + Runtime.getRuntime().freeMemory());

		start = System.currentTimeMillis();
		asDuck = Duck.quack(two, MyDuck.class);
		asDuck.method();
		System.out.println("Createon of third took: " + (System.currentTimeMillis() - start));

		System.out.println("Memory usage: " + Runtime.getRuntime().totalMemory() + " - " + Runtime.getRuntime().freeMemory());

		start = System.currentTimeMillis();
		asDuck = Duck.quack(two, MyDuck.class);
		asDuck.method();
		System.out.println("Createon of fourth took: " + (System.currentTimeMillis() - start));

		System.out.println("Memory usage: " + Runtime.getRuntime().totalMemory() + " - " + Runtime.getRuntime().freeMemory());
	}

	@Test
	public void interfaceTest() throws Exception {
		Two two = new Two();
		MyDuck asDuck = Duck.quack(two, MyDuck.class);
		asDuck.method();
	}

	@Test
	public void classTest() throws Exception {
		Two two = new Two();
		One asDuck = Duck.quack(two, One.class);
		asDuck.method();
	}

	/**
	 * Test representation  
	 */
	@Test(expected = DuckException.class)
	public void classTestFail() throws Exception {
		One one = new One();
		Two asDuck = Duck.quack(one, Two.class);
		asDuck.method();
	}

	@Test(expected = DuckException.class)
	public void interfaceTestFail() throws Exception {
		Two two = new Two();
		MyDuck2 asDuck = Duck.quack(two, MyDuck2.class);
		asDuck.method();
	}

	@Test
	public void subtypeTest() throws Exception {
		SubtypeDuck suDu = new SubtypeDuck();
		SupertypeDuck asDuck = Duck.quack(suDu, SupertypeDuck.class);
	}

	@Test(expected = DuckException.class)
	public void subtypeTestInverted() throws Exception {
		SubtypeDuck suDu = new SubtypeDuck();
		SupertypeDuckInverted asDuck = Duck.quack(suDu, SupertypeDuckInverted.class);
	}

	/**
	 * Test when parameters match in types 
	 */
	@Test
	public void parameterTest() throws Exception {
		Two two = new Two();
		MyDuck asDuck = Duck.quack(two, MyDuck.class);
		List<String> lx = new LinkedList<String>();
		Assert.assertSame("Expected to have the same list ass passed in", lx, asDuck.getList(lx));
	}

	/**
	 * Test when parameters do not match in types  
	 */
	@Test(expected = DuckException.class)
	public void parameterTestInverted() throws Exception {
		Three three = new Three();
		MyDuck asDuck = Duck.quack(three, MyDuck.class);
		asDuck.method();
	}

	/**
	 * Test when the duck in question has a method with a return type that is a supertype of the class' return type
	 */
	@Test
	public void returnTest() throws Exception {
		Four four = new Four();
		MyDuck4 asDuck = Duck.quack(four, MyDuck4.class);
		Assert.assertTrue("Expected to have the list with certain value", asDuck.getList().contains("Four"));
	}

	/**
	 * Test when the duck in question has a method with a return type that is a subtype of the class'es return method
	 */
	@Test(expected = DuckException.class)
	public void returnTestInverted() throws Exception {
		Four four = new Four();
		MyDuck5 asDuck = Duck.quack(four, MyDuck5.class);
		asDuck.getList();
	}
	
}
