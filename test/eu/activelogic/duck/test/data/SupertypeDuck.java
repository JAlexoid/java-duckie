package eu.activelogic.duck.test.data;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SupertypeDuck {
	public Collection<?> getList(){
		return new LinkedList<Object>();
	}
}
