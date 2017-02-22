package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	private HashSet<Capitalist> mIsInHierarchy = new HashSet<>();
	private HashMap<FatCat, Set<Capitalist>> mHierarchy = new HashMap<>();
	
    /**
     * Adds a given element to the hierarchy.
     * <p>
     * If the given element is already present in the hierarchy,
     * do not add it and return false
     * <p>
     * If the given element has a parent and the parent is not part of the hierarchy,
     * add the parent and then add the given element
     * <p>
     * If the given element has no parent but is a Parent itself,
     * add it to the hierarchy
     * <p>
     * If the given element has no parent and is not a Parent itself,
     * do not add it and return false
     *
     * @param capitalist the element to add to the hierarchy
     * @return true if the element was added successfully, false otherwise
     */
    @Override
    public boolean add(Capitalist capitalist) {
    	boolean isAdded = false;
    	if (capitalist == null) {
    		isAdded = false;
    	} else if (capitalist.hasParent()) {
    		mIsInHierarchy.add(capitalist);
    		mIsInHierarchy.add(capitalist.getParent()); 
    		isAdded = true;
    	} else if (!(capitalist.hasParent()) && capitalist instanceof FatCat) {
    		mIsInHierarchy.add(capitalist);
    		isAdded = true;
    	}
    	return isAdded;
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
    	boolean out = false;
    	if (mIsInHierarchy.isEmpty()) {
    		 
    	} else if (mIsInHierarchy.contains(capitalist)) {
    		out = true;
    	}
    	return out;
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() {
        return mIsInHierarchy;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	Set<FatCat> parents = new HashSet<>();
        for (Capitalist piggy : mIsInHierarchy) {
        	if (piggy.hasParent()) {
        		parents.add(piggy.getParent());
        	}
        }
        return parents;
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
    	Set<Capitalist> cattySet = new HashSet<>();
    	for (Capitalist capitalist : mIsInHierarchy) {
    		if (capitalist.getParent().equals(fatCat)) {
    			cattySet.add(capitalist);
    		}
    	}
        return cattySet;
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
    	Map<FatCat, Set<Capitalist>> lMappy = new HashMap<>();
    	if (mIsInHierarchy.isEmpty()) {
    		lMappy.isEmpty();
    	} else {
	    	for (Capitalist cat : mIsInHierarchy) {
	    		if (cat instanceof FatCat) {
	    			lMappy.put((FatCat) cat, new HashSet<Capitalist>());
	    		}
	    	}
	    	for (Capitalist cat : mIsInHierarchy) {
	    		if (cat.hasParent()) {
	    			lMappy.get(cat.getParent()).add(cat);
	    		}
	    	}
    	}
        return lMappy;
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {
    	LinkedList<FatCat> lParentList = new LinkedList<>();
    	if (!(mIsInHierarchy.contains(capitalist))) {
    		// do nothing
    	} else {
    		lParentList = nextCapitalistPig(capitalist);
    	}
    	return lParentList;
    }
    
    private static LinkedList<FatCat> nextCapitalistPig(Capitalist pig) {
    	LinkedList<FatCat> piggies = new LinkedList<>();
    	if (pig.hasParent()) {
    		piggies.add(pig.getParent());
    		nextCapitalistPig(pig.getParent());
    	}
    	return piggies;
    }
}
