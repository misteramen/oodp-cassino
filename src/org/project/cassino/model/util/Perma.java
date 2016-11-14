package org.project.cassino.model.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Perma<T extends Object> {
	private LinkedList<T> list;
	
	public Perma() {
		list = new LinkedList<T>();
	}
	
	public boolean push(T object) {
		return list.offerLast(object);
	}
	
	public T pop() {
		return list.pollFirst();
	}
	
	public void search() {
		
	}
	
	public class Combination<V extends Object> {
		private List<V> combinationList;
		
		public Combination() {
			combinationList = new ArrayList<V>();
		}
		
		@SafeVarargs
		public Combination(V...elements) {
			this();
			
			for(V element : elements) {
				combinationList.add(element);
			}
		}
		
		public boolean add(V element) {
			return combinationList.add(element);
		}
	}
	
	
}
