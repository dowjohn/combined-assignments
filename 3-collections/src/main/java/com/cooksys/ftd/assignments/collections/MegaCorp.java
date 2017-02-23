package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	private Map<FatCat, Set<Capitalist>> baseMap;

	public MegaCorp() {
		this.baseMap = new HashMap<>();
	}

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
    	if (capitalist == null || this.has(capitalist) || capitalist.getParent() == null && capitalist instanceof WageSlave) {
    		isAdded = false;
    	} else {
    		this.addemAll(capitalist);
    	}
//    	if (capitalist == null || capitalist.getParent() == null && capitalist instanceof WageSlave) {
//    		isAdded = false;
//    	} else if (!(this.has(capitalist))) {
//    		if (!(this.has(capitalist.getParent())) && capitalist.getParent() != null) {
//    			baseMap.put(capitalist.getParent(), new HashSet<Capitalist>());
//    			baseMap.get(capitalist.getParent()).add(capitalist);
//    			isAdded = true;
//    		} else if (capitalist instanceof FatCat && capitalist.getParent() == null) {
//    			// need to add condition for childless fatcat
//    			baseMap.put((FatCat) capitalist, new HashSet<Capitalist>()); 
//    			isAdded = true;
//    		} else if (capitalist instanceof WageSlave && capitalist.hasParent()) {
//    			baseMap.get(capitalist.getParent()).add(capitalist);
//    			isAdded = true;
//    		}
//    	}
    	
    	return isAdded;
    }
    
    private void addemAll(Capitalist cappy) {
    	FatCat lFatty = (FatCat) cappy;
    	if (!(cappy.hasParent())) {
    		baseMap.put(lFatty, new HashSet<>());
    	} else {
    		this.addemAll(cappy.getParent());	
    	}
    }

	/**
	 * @param capitalist
	 *            the element to search for
	 * @return true if the element has been added to the hierarchy, false
	 *         otherwise
	 */
	@Override
	public boolean has(Capitalist capitalist) {
		boolean out = false;
		if (this.getElements().contains(capitalist)) {
			out = true;
		}
		return out;
	}

	/**
	 * @return all elements in the hierarchy, or an empty set if no elements
	 *         have been added to the hierarchy
	 */
	@Override
	public Set<Capitalist> getElements() {
		Set<Capitalist> capitalists = new HashSet<>();
		Collection<Set<Capitalist>> cSet = baseMap.values();
		for (Set<Capitalist> capitalist : cSet) {
			capitalists.addAll(capitalist);
		}
		capitalists.addAll(this.getParents());
		return capitalists;
	}

	/**
	 * @return all parent elements in the hierarchy, or an empty set if no
	 *         parents have been added to the hierarchy
	 */
	@Override
	public Set<FatCat> getParents() {
		return baseMap.keySet();
	}

	/**
	 * @param fatCat
	 *            the parent whose children need to be returned
	 * @return all elements in the hierarchy that have the given parent as a
	 *         direct parent, or an empty set if the parent is not present in
	 *         the hierarchy or if there are no children for the given parent
	 */
	@Override
	public Set<Capitalist> getChildren(FatCat fatCat) {
		return baseMap.get(fatCat);
	}

	/**
	 * @return a map in which the keys represent the parent elements in the
	 *         hierarchy, and the each value is a set of the direct children of
	 *         the associate parent, or an empty map if the hierarchy is empty.
	 */
	@Override
	public Map<FatCat, Set<Capitalist>> getHierarchy() {
		return baseMap;
	}

	/**
	 * @param capitalist
	 * @return the parent chain of the given element, starting with its direct
	 *         parent, then its parent's parent, etc, or an empty list if the
	 *         given element has no parent or if its parent is not in the
	 *         hierarchy
	 */
	@Override
	public List<FatCat> getParentChain(Capitalist capitalist) {
		List<FatCat> lParentList = new LinkedList<>();
		if (!(this.getElements().contains(capitalist))) {
			// do nothing
		} else {
			lParentList = nextCapitalistPig(capitalist);
		}
//		FatCat parent = capitalist.getParent();
//        while (parent != null) {
//            lParentList.add(parent);
//            parent = parent.getParent();
//        }
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
