package org.project.cassino.model.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;

public class Search<T> {
	private List<T> list;
	
	@SafeVarargs
	public Search(T...elements) {
		super();
		
		list = new LinkedList<T>();
		
		for(T element : elements) {
			list.add(element);
		}
	}
	
	public List<Combination<Integer, List<T>>> search() {
		List<Combination<Integer, List<T>>> combinations = new ArrayList<Combination<Integer, List<T>>>();

		for(int pivot = 0; pivot < list.size(); pivot++) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("PIVOT: " + pivot);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			for(int depth = 1; (list.size() - depth - pivot) > 0; depth++) {
				System.out.println("------------------------------------------------------------");
				System.out.println("DEPTH: " + depth);
				System.out.println("--------");
				
				Combination<Integer, List<T>> combination = new Combination<Integer, List<T>>();
				List<T> subList = Search.subList(list, pivot + 1, (list.size() - 1));
				int shift = pivot + 1;
				
				for(int start = 0; start < subList.size(); start++) {
//					if(shift) {
//					}
					
//					System.out.println("PERMA: " + pivot + " " + subList.get(start));
				}
				
//				for(int perma = pivot - depth; perma < (list.size() - pivot); perma++) {
//					System.out.println("Perma: " + perma);
//				}
			}
		}
		
		return combinations;
	}
	
	public List<Combination<Integer, List<T>>> search2() {
		List<Combination<Integer, List<T>>> combinations = new ArrayList<Combination<Integer, List<T>>>();

		for(int pivot = 0; pivot < list.size(); pivot++) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("PIVOT: " + pivot);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			for(int depth = 1; (list.size() - depth - pivot) > 0; depth++) {
				System.out.println("------------------------------------------------------------");
				System.out.println("DEPTH: " + depth);
				System.out.println("--------");
				
				Combination<Integer, List<T>> combination = new Combination<Integer, List<T>>();
				List<T> subList = Search.subList(list, pivot + 1, (list.size() - 1));
				int shift = pivot + 1;
				
				for(int start = 0; start < subList.size(); start++) {
					if(subList.contains(shift)) {
					}
					
//					System.out.println("PERMA: " + pivot + " " + subList.get(start));
				}
				
//				for(int perma = pivot - depth; perma < (list.size() - pivot); perma++) {
//					System.out.println("Perma: " + perma);
//				}
			}
		}
		
		return combinations;
	}
	
	public static <T> List<T> subList(List<T> list, int start, int end) {
		if((start >= 0 && start < list.size()) &&
		    (end  >= 0 && end   < list.size())) {
			LinkedList<T> sub = new LinkedList<T>();
			Iterator<T> iterator = list.iterator();
			int counter = 0;
			
			while(iterator.hasNext()) {
				if(counter >= start && counter <= end) {
					sub.add(iterator.next());
				} else {
					iterator.next();
				}
				
				counter += 1;
			}
			
			return sub;
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		Search<Integer> searcher = new Search<Integer>(0, 1, 2, 3, 4);
		searcher.search();
		
//		List<Integer> list = new LinkedList<Integer>();
//		list.add(1);
//		list.add(2);
//		list.add(3);
//		list.add(4);
//		list.add(5);
//		list.add(6);
//		list.add(6);
//		list.add(7);
//		
//		System.out.println("   List: " + list);
//		System.out.println("SubList: " + Search.subList(list, 0, 6));
	}
	
	public class Combination<K, V> {
		private HashMap<K, List<V>> elements;

		public Combination() {
			super();
			
			elements = new HashMap<K, List<V>>();
		}
		
		public boolean add(K key, V value) {
			return elements.get(key).add(value);
		}
		
		public void set(K key) {
			elements.put(key, new ArrayList<V>());
		}
		
		public List<V> get(K key) {
			return elements.get(key);
		}
	}
}
