package eu.activelogic.duck.test.data;

import java.util.Collection;
import java.util.List;

public class Two {

	public void method() {
		System.out.println("This is it!");
	}

	public void method2(List<?> list) {

	}

	public List<String> getList(List<String> l){
		return (List<String>)l;
	}
	
}
