package org.project.cassino.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Utils {
    public static boolean trueAll(boolean[] validate) {
        int count = 0;
        for(boolean value : validate) {
            if(value == true) {
                count += 1;
            }
        }

        if(count == validate.length) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean trueAny(boolean[] validate) {
        for(boolean value : validate) {
            if(value == true) {
                return true;
            }
        }

        return false;
    }
    
	public static class Associatives<T extends Comparable<? super T>> {
		private List<T> objects = new ArrayList<T>();
		
		@SafeVarargs
		public Associatives(T...objects) {
			for(int i = 0; i < objects.length; i++) {
				this.objects.add(i, objects[i]);
			}
		}
		
		public void add(T object) {
			objects.add(object);
		}
		
		public T get(int index) {
			return objects.get(index);
		}
		
		public boolean validate(T object) {
            for(int i = 0; i < this.objects.size(); i++) {
                if(object.equals(this.objects.get(i))) {
                    return true;
                }
            }

            return false;
		}
		
		public List<T> getObjects() {
			return objects;
		}
		
		public Iterator<T> iterator() {
			return objects.iterator();
		}
		
		public void sort() {
			Collections.sort((List<T>) objects);
		}
		
		public int size() {
			return this.objects.size();
		}
        
        public void clear() {
        	objects.clear();
        }
		
        @Override
		public String toString() {
			return Arrays.toString(objects.toArray());
		}
	}
}
