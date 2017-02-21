package com.cooksys.ftd.assignments.control;

import java.util.ArrayList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * The Fibonacci sequence is simply and recursively defined: the first two elements are `1`, and
 * every other element is equal to the sum of its two preceding elements. For example:
 * <p>
 * [1, 1] =>
 * [1, 1, 1 + 1]  => [1, 1, 2] =>
 * [1, 1, 2, 1 + 2] => [1, 1, 2, 3] =>
 * [1, 1, 2, 3, 2 + 3] => [1, 1, 2, 3, 5] =>
 * ...etc
 */
public class Fibonacci {

    /**
     * Calculates the value in the Fibonacci sequence at a given index. For example,
     * `atIndex(0)` and `atIndex(1)` should return `1`, because the first two elements of the
     * sequence are both `1`.
     *
     * @param i the index of the element to calculate
     * @return the calculated element
     * @throws IllegalArgumentException if the given index is less than zero
     */
    public static void atIndex(int index) throws IllegalArgumentException {
    	
    }
 
    /**
     * Calculates a slice of the fibonacci sequence, starting from a given start index (inclusive) and
     * ending at a given end index (exclusive).
     *
     * @param start the starting index of the slice (inclusive)
     * @param end   the ending index of the slice(exclusive)
     * @return the calculated slice as an array of int elements
     * @throws IllegalArgumentException if either the given start or end is negative, or if the
     *                                  given end is less than the given start
     */
    public static int[] slice(int start, int end) throws IllegalArgumentException {
    	int[] mehIntArray = fibonacci(end);
    	ArrayList<Integer> ughList = new ArrayList<Integer>();
    	for (int i = 0; i < mehIntArray.length; i++) {
    		if (i >= start && i <= end) {
    			ughList.add(mehIntArray[i]);
    		}
    	}
    	int[] outIntArray = new int[ughList.size()];
    	int counter = 0;
    	for (Integer fibNum : ughList) {
    		outIntArray[counter] = fibNum;
    		counter ++;
    		System.out.println(fibNum);
    	}
    	return outIntArray;
    }

    /**
     * Calculates the beginning of the fibonacci sequence, up to a given count.
     *
     * @param count the number of elements to calculate
     * @return the beginning of the fibonacci sequence, up to the given count, as an array of int elements
     * @throws IllegalArgumentException if the given count is negative
     */
    public static int[] fibonacci(int count) throws IllegalArgumentException {
		int num1 = 1;
    	int num2 = 1;
    	int num3 = 0;
    	ArrayList<Integer> outlist = new ArrayList<Integer>();
    	if (count == 1) {
    		return new int[]{1};
    	} else {
	    	for (int i = 0; i <= count; i++) {
	    		if (i == 0 || i == 1) {
	    			outlist.add(1);
	    		} else {
		    		num3 = num1 + num2;
		       		num1 = num2;
		    		num2 = num3;
		    		outlist.add(num3);
		    		if (num3 >= count) {
		    			break;
		    		}
	    		}
	    	}
	    	
	    	int[] outIntArray = new int[outlist.size()];
	    	int counter = 0;
	    	for (Integer fibNum : outlist) {
	    		outIntArray[counter] = fibNum;
	    		counter ++;
	    	}
	    	return outIntArray;
    	}
    }
    
    public static void main(String[] args) {
    	int[] hey = slice(21, 34);
    }
}
