//The PairList class is an interface for the SVPairList class. It provides the structure for any linked list using SVPairs.

import java.util.*;
import java.io.*;

interface PairList extends Iterable<SVPair>
//requires methods of Iterable
{
	//returns n, the number of pairs in the list: O(1)
	public int length();
	
//returns the pair at the specified index (0...n-1): O(n)
	public SVPair getElementAt(int index);

	//adds a new pair to the list and returns true if there is a change: O(1) for an unordered list, O(n) otherwise
	public boolean add(SVPair p);

	//merges others into the list O(m) where m = others.length() if this list is unordered; O(n+m) if both lists have the same order and key, O(nm) otherwise
	public void addAll(PairList others);
	
	//deletes the specified pair and returns true if there is a change: O(n)
	public boolean delete(SVPair target);

	//deletes all pairs in others from this list: O(n+m) if both lists have the same order and key, O(nm) otherwise, where m=others.length()	
	public void deleteAll(PairList others);

	//searches for the target: O(n)
	public boolean contains(SVPair target);

	//resets the list to empty: O(1)
	public void clear();

	//sets the sort key to SVPair.SIZE or SVPair.VALUE. If the list is ordered when the key is changed, it must be resorted: O(1) for empty or unordered list, O(n lg n) for ordered list
	public void setKey(int key);
	
	//sets the order of the pairs in the list to SVPair.UP, SVPair.DOWN, or SVPair.UNORDERED. The list is resorted if necessary: O(1) or O(n lg n)
	public void setOrder(int order);

	//returns an Iterator object that meets the specifications in the JFC: O(1)
	public Iterator<SVPair> iterator();
	
}
