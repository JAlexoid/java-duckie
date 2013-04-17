package eu.activelogic.duck.test.data;

import java.util.ArrayList;
import java.util.List;

public class Four {

	public void method() {
		System.out.println("This is it!");
	}

	public List<String> getList(){
		List<String> ls = new ArrayList<String>();
		ls.add("Four");
		return ls;
	}
	
}
