Java Duck Typing
================

As described in http://www.panz.in/2008/12/quack-my-class-lil-bit-more-dinamicity.html

License
========

   Copyright 2013 Aleksandr Panzin

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
Blog post
=========

Posted 10th December 2008 by Alexander Panzhin

Java is criticized for being too rigid due to static typing. But what do we know about type dynamicity in Java:
Reflection and bean introspection
In theory you can write statically untraceable code using only refection API, that would constitute dynamicity.

Java 1.4 Proxy API
Need to have a stub for an interface? This is the tool for you. Supply an interface and InvocationHandler and you will get an Object that implements the Interface and will forward the method calls to your InvocationHandler.

ClassLoading is on demand
You may load classes from different locations on demand and invoke them thus adding new functionality on the fly. This leads to ability to generate code, compile it and load without intervention of a developer.

Java 5 introduced the instrumentation API
Java 5 gave us something new. From this version we can redefine classes via a standard interface – java agents.

The upcoming invokedynamic JVM isntruction
invokedynamic is less about Java as a language, but more to introduce more languages to JVM, specially the dynamic ones.

On the scene we have OO “dynamic” languages that follow something called duck typing.

Duck typing - “if it walks like a duck and quacks like a duck, therefore it must be a duck”, the languages that have that scheme only determine if the variable will accept the method call or have a particular property/instance variable with corresponding name. But they will “complain” if the call was made incorrectly.
In essence if you provide any object that has a method walk, to code that expects object of type Human, it will execute without any problems.
Duck typing in Java
True duck typing in Java is not really possible. But, there is always a “but”.
Say there are 2 classes that are identical in their method signature, duck typing would allow those to be “interchangeable”. In Java you would need both of them to implement same interface to do it “correctly”.
We can also do some hacking and create an interface and use that. As for the classes we could create an InvocationHandler and a Proxy, witch would forward the method calls to the classes.[(2) in the list above].
The code would be like the following:
		HashMap anything = new HashMap();

		DuckInvocationHandler dih = new DuckInvocationHandler(anything);
		
		anything.put("test", "Congratulations, it WORKS!");
		
		MyMap duck = (MyMap)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{MyMap.class}, dih);
		
		System.out.println("Out: "+ duck.get("test"));

The interface to represent what the result we want:
		public interface MyMap {
		      Object get(Object i);
		   }





The InvocationHandler implementation:
`java.lang.reflect.InvocationHandler` has only one method to override.

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		   Method mth = nonDuckClass.getMethod(method.getName(), method.getParameterTypes());
		   return mth.invoke(nonDuck, args);
		}



This approach:
Is standards based
Will work only with interfaces

There is another way. A popular library for class manipulation is CGLib. It features the ability to Proxy a class, by creating a subclass of it at runtime.
How will the code look? CGLib also has an InvocationHandler, and it also has only one method invoke. So just replace java.lang.reflect.InvocationHandler with net.sf.cglib.proxy.InvocationHandler, and the conversion process is done for the handler.
The proxy creation process is a bit longer, but does have it's own advantages. The code:
		// Use caching Enhancer object
		Enhancer e = new Enhancer();
		e.setUseCache(true);
		e.setCallback(duckInvocationHandlerInstance);
		e.setClassLoader(o.getClass().getClassLoader());
		e.setInterceptDuringConstruction(false);
		if (asType.isInterface())
		   e.setInterfaces(new Class[] { asType });
		else
		   e.setSuperclass(asType);
		Object output = e.create();

This approach:
Caching : As you can see on line 3 we set the Enhancer to use cache.

Works with both classes and interfaces


What are we missing? Type safety.
For this to work correctly before you make the class quack as you wish, you need to check if the type and return type classes are compatible. For this I have created a small library, ready to use, but still a bit limited.
The limitation is, that return and parameters values are not proxied. Therefore parameters have to of the same type and return type has to be either same type or subtype.


The result
We may or may not want anything to do with duck typing. The reasons against is that Java was built to be strict. It violates the core Java philosophy – more checks during compile time. Although, this way is much better then totally blind duck typing, since you have to specify the interface.

Where might we use it?

The main reason for using it, is to be able to practically alias some APIs and possibly use different versions of one API in the same code.