package org.project.cassino.model.util;

import org.project.cassino.model.card.Card;
import org.project.cassino.model.card.Sort;

public class Manipulate {
	public static Card pop(Card[] cards) throws NullPointerException {
		if(cards.length > 0) {
			Card card = Manipulate.extract(cards, 0);
			cards = Manipulate.shift(cards, 1, (cards.length - 1), 1, Direction.LEFT);
			
			return card;
		}
		return Card.NULL_CARD;
	}
	
	public static Card extract(Card[] array, int index) {
		if(index >= 0 && index < array.length) {
			Card extraction;
			extraction = array[index];
			array[index] = Card.NULL_CARD;
			
			return extraction;
		}
		
		return Card.NULL_CARD;
	}
	
	public static Card[] extract(Card[] array, int index, int numberOfElements) {
		if(index >= 0 && index <= (array.length - numberOfElements)) {
			Card[] extraction = new Card[numberOfElements];
			
			for(int i = 0; i < numberOfElements; i++) {
				extraction[i] = array[index];
				array[index] = Card.NULL_CARD;
				index++;
			}
			
			return extraction;
		}
		
		return null;
	}
	
	public static Card[] shift(Card[] array, int index, int numberOfElements, int steps, Direction dir) {
		Card[] extraction = Manipulate.extract(array, index, numberOfElements);
		
		switch(dir) {
			case RIGHT:
				Card[] temp = Manipulate.replace(array, (index + steps), extraction);
				
				return temp;
			case LEFT:
				Card[] temp1 = Manipulate.replace(array, (index - steps), extraction);
				
				return temp1;
			default:
				break;
		}
		
		return null;
	}
	
	public static Card[] replace(Card[] array, int index, Card element) {
		if(index < array.length) {
			array[index] = element;
			
			return array;
		}
		
		return null;
	}
	
	public static Card[] replace(Card[] array, int index, Card[] replacementArray) {
		if(index < array.length && index <= (array.length - replacementArray.length)) {
			for(int i = 0; i < replacementArray.length && index < array.length; i++) {
				array[index] = replacementArray[i];
				index++;
			}
			
			return array;
		}
		
		return null;
	}
	
	public static Card[] sort(Card[] array, Sort mode) {
		Card pivot;
		// Card[] sortedArray = new Card[array.length];
		
		switch(mode) {
			case SUIT:
				// The following sorting algorithm is based off of insertion sort.
				if(array.length < 2) {
					return array;
				} else if(array[1] instanceof Card/* && array[1] != Card.NULL_CARD*/) {
					loop:
						for(int pivotIndex = 1; pivotIndex < array.length; pivotIndex++) {
							pivot = array[pivotIndex];
							
							for(int i = 0; i < array.length; i++) {
								if(pivotIndex != i) {
									if(pivot.isInferiorTo(array[i])) {
										if(pivotIndex == (i - 1)) {
											continue loop;
										} else if(pivotIndex < i && (pivotIndex + 1) == i) {
											Card[] extraction = Manipulate.extract(array, (pivotIndex + 1), (i - 1) - pivotIndex);
											array = Manipulate.replace(array, pivotIndex, extraction);
											array = Manipulate.replace(array, (i - 1), pivot);
											continue loop;
										} else if(pivotIndex > i) {
											array = Manipulate.shift(array, i, (pivotIndex - i), 1, Direction.RIGHT);
											array = Manipulate.replace(array, i, pivot);
											continue loop;
										}
									} 
								} else if ((i == array.length - 1) && pivot.greaterThan(array[i])) {
									array = Manipulate.shift(array, pivotIndex, ((i - 1) - pivotIndex), 1, Direction.LEFT);
									array = Manipulate.replace(array, i, pivot);
								}
							}
						}
					
					return array;
				}
				
				return null;
			case RANK:
				// The following algorithm is based off of insertion sort.
				if(array.length < 2) {
					return array;
				} else if(array[1] instanceof Card /*&& array[1] != Card.NULL_CARD*/) {
					loop:
						for(int pivotIndex = 1; pivotIndex < array.length; pivotIndex++) {
							pivot = array[pivotIndex];
							
							for(int i = 0; i < array.length; i++) {
								if(pivotIndex != i) {
									if(pivot.lessThan(array[i])) {
										if(pivotIndex == (i - 1)) {
											continue loop;
										} else if(pivotIndex < i && (pivotIndex + 1) == i) {
											Card[] extraction = Manipulate.extract(array, (pivotIndex + 1), (i - 1) - pivotIndex);
											array = Manipulate.replace(array, pivotIndex, extraction);
											array = Manipulate.replace(array, (i - 1), pivot);
											continue loop;
										} else if(pivotIndex > i) {
											array = Manipulate.shift(array, i, (pivotIndex - i), 1, Direction.RIGHT);
											array = Manipulate.replace(array, i, pivot);
											continue loop;
										}
									} 
								} else if ((i == array.length - 1) && pivot.greaterThan(array[i])) {
									array = Manipulate.shift(array, pivotIndex, ((i - 1) - pivotIndex), 1, Direction.LEFT);
									array = Manipulate.replace(array, i, pivot);
								}
							}
						}
					
					return array;
				}
				
				return null;
			case SUIT_VS_RANK:
				array = Manipulate.sort(array, Sort.SUIT);
				array = Manipulate.sort(array, Sort.RANK);
				
				return array;
			case RANK_VS_SUIT:
				array = Manipulate.sort(array, Sort.RANK);
				array = Manipulate.sort(array, Sort.SUIT);
				
				return array;
			// case NULL_EXCLUSION:
			//	return null;
			default:
				return null;
		}
	}
	
	public static Card[] flatten(Card[][] array) {
		int size = 0;
		
		for(int i = 0; i < array.length; i++) {
			size += array[i].length;
		}
		
		Card[] flattenedArray = new Card[size];
		
		int k = 0;
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				flattenedArray[k] = array[i][j];
				k += 1;
			}
		}
		
		return flattenedArray;
	}
	
	public static <T> void printMultiArray(T[][] array) {
		System.out.print("array = { ");
		
		for(int i = 0; i < array.length; i++) {
			System.out.print("{ ");
			for(int j = 0; j < array[i].length; j++) {
				if(j == array[i].length - 1) {
					System.out.print("\"" + array[i][j] + "\"");
					break;
				}
				System.out.print("\"" + array[i][j] + "\", ");
			}
			
			if(i == array.length - 1) {
				System.out.print("}");
				break;
			}
			
			System.out.print("}, ");
		}
		
		System.out.println(" }");
	}
	
	public static <T> void printArray(T[] array) {
		System.out.print(array + " = { ");
		
		for(int i = 0; i < array.length; i++) {
			if(i == array.length - 1) {
				System.out.print("\"" + array[i] + "\"");
				break;
			}
			System.out.print("\"" + array[i] + "\", ");
		}
		
		System.out.print("}");
	}
}











