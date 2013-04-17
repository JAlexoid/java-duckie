package eu.activelogic.duck;


import java.lang.reflect.Method;

import net.sf.cglib.proxy.InvocationHandler;

/**
 * An invocation handler for redirecting method calls to an object
 *
 * @author alex
 * @version 0.5
 */
@SuppressWarnings("unchecked")
public class DuckHandler implements InvocationHandler {

	/**
	 * The method call receiver object
	 */
	private final Object quackingObject;

	/**
	 * The method call receiver object's class
	 */
	private final Class<?> quackingClass;


	/**
	 * Constructor with the target object
	 * @param nonDuck method call receiver object
	 */
	public DuckHandler(Object nonDuck) {
		this.quackingObject = nonDuck;
		this.quackingClass = nonDuck.getClass();
	}

	/**
	 * Method implemented from net.sf.cglib.proxy.InvocationHandler
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Method mth = quackingClass.getMethod(method.getName(), method.getParameterTypes());
		return mth.invoke(quackingObject, args);
	}
}
