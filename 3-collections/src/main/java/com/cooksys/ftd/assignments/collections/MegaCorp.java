package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.stream.Collectors;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {

	private HashSet<Capitalist> mIsInHierarchy = new HashSet<>();
	
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
    	if (capitalist == null || this.has(capitalist) || capitalist.hasParent() == false && capitalist instanceof WageSlave) {
    		return false;
    	} else if (capitalist.hasParent() != true && capitalist instanceof FatCat) {
    		mIsInHierarchy.add(capitalist); 
    		return true;
    	} else {
    		mIsInHierarchy.add(capitalist);
    		this.add(capitalist.getParent());
    		return true;
    	}
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
    	boolean doesHave = false;
    	if (mIsInHierarchy.contains(capitalist)) {
    		doesHave = true;
    	}
    	return doesHave;
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() {
    	Set<Capitalist> capitalists = new HashSet<>();
    	capitalists.addAll(mIsInHierarchy);
        return capitalists;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
        Set<FatCat> parentals = (Set<FatCat>)(Set<?>) mIsInHierarchy.stream().filter(capitalist -> capitalist instanceof FatCat).collect(Collectors.toSet());
        return parentals;
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
    	Set<Capitalist> testSet = mIsInHierarchy.stream().filter(capitalist -> capitalist.getParent() == fatCat).collect(Collectors.toSet());
        return testSet;
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
    	Map<FatCat, Set<Capitalist>> mapOut = new HashMap<>();
    	if (mIsInHierarchy.isEmpty()) {
    		mapOut.isEmpty();
    	} else {
	    	for (Capitalist capitalist : mIsInHierarchy) {
	    		if (capitalist instanceof FatCat) {
	    			mapOut.put((FatCat) capitalist, new HashSet<Capitalist>());
	    		}
	    	}
	    	for (Capitalist capitalist : mIsInHierarchy) {
	    		if (capitalist.hasParent()) {
	    			mapOut.get(capitalist.getParent()).add(capitalist);
	    		}
	    	}
    	}
        return mapOut;
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {
    	List<FatCat> parents = new ArrayList<>();
    	
    	if (capitalist == null || capitalist.hasParent() == false || !(mIsInHierarchy.contains(capitalist.getParent()))) {
    		return parents;
    	}
    	
    	FatCat parent = capitalist.getParent();
        while (parent != null) {
            parents.add(parent);
            parent = parent.getParent();
        }
        return parents;
    }
}
