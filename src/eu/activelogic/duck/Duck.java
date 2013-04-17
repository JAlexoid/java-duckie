package eu.activelogic.duck;

import java.lang.reflect.Method;
import java.util.WeakHashMap;

import net.sf.cglib.proxy.Enhancer;

/**
 * The main operation point for "ducking"
 *
 * @author jalexoid@gmail.com alex@activelogic.eu (Aleksandr Panzin)
 * @version 0.5
 */
public class Duck {

	/**
	 * A cache of type eqalities, to speedup lookup
	 */
	private static WeakHashMap<Class<?>, Class<?>> typcastCache = new WeakHashMap<Class<?>, Class<?>>();

	/**
	 * Check if class oClazz can have class asType be a facade for it, by intercepting method invocations
	 *
	 * @param oClazz object's class that needs to be checked if it can receive method call redirects
	 * @param asType type witch needs to be check if it's compatible with object o
	 * @return if object o can be of type clazz
	 */
	public static boolean isQuackingClass(Class<?> oClazz, Class<?> asType) {
		if (typcastCache.containsKey(asType) && typcastCache.get(asType).equals(oClazz))
			return true;

		Method[] mths = asType.getMethods();
		// Go though the methods
		for (Method method : mths) {
			try {
				Method mt = oClazz.getMethod(method.getName(), method.getParameterTypes());
				if (!method.getReturnType().isAssignableFrom(mt.getReturnType()))
					return false;
			} catch (NoSuchMethodException e) {
				return false;
			}
		}
		// Place the equality into cache
		typcastCache.put(asType, oClazz);
		return true;
	}
	
	
	/**
	 * Check if object o can have class(asType) be as a facade for it
	 *
	 * @param o object that needs to be checked if it can receive method call redirects
	 * @param clazz type witch needs to be check if it's compatible with object o
	 * @return if object o can be of type clazz
	 */
	public static boolean isQuacking(Object o, Class<?> asType) {
		return isQuackingClass(o.getClass(), asType);
	}

	/**
	 * Create a representation of an object o as type <T>.
	 *
	 * @param <T> the resulting type of an object
	 * @param o object that will receive method calls redirected from <T> type
	 * @param asType class of type that will be the facade for object o
	 * @return proxy of type <T>
	 * @throws DuckException if the type of o is incompatible with clazz
	 */
	@SuppressWarnings("unchecked")
	public static <T> T quack(Object o, Class<T> asType) throws DuckException {
		if (!isQuacking(o, asType))
			throw new DuckException("Not duckable");
		DuckHandler dp = new DuckHandler(o);

		// Use caching Enhancer object
		Enhancer e = new Enhancer();
		e.setUseCache(true);
		e.setCallback(dp);
		e.setClassLoader(o.getClass().getClassLoader());
		e.setInterceptDuringConstruction(false);
		if (asType.isInterface())
			e.setInterfaces(new Class[] { asType });
		else
			e.setSuperclass(asType);
		Object output = e.create();
		return (T) output;
	}
}
