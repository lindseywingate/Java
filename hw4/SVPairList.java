//The SVPairList class implements a doubly linked list of SVPair's within nodes. 
/*Code written by Lindsey Wingate*/
import java.util.*;
import java.io.*;
import java.lang.*;

public class SVPairList implements Iterable<SVPair>, PairList
{
	private SVPairList data;
	private Node front = null;
	private int size;
	
	public SVPairList()
	{
		data = new SVPairList();
	}
/*	public SVPairList makeInstance(long seed, long[] params)
	{
		SVPairList different = new SVPairList();
		Random randomGen = new Random();
		int length = params.length;
		int x;
		int random;
		for(x=0; x<length; x++) {
			random = randomGen.nextInt(100);
			SVPair blah = new SVPair(random, params[x]);
			different.add(blah);	
		}		
		different.show();
		return different;
	}
*/
//returns the number of nodes in the list
	public int length()
	{
		int size = 0; 
		Node curr = front;
		if (curr == null) {
			return 0;
		}
		while(curr != null) {
			curr = curr.getNext();
			size++;
		}
		return size;
	}
//allows the user to search with an index and returns the corresponding SVPair. Requires the length() method
	public SVPair getElementAt(int index)
	{
		Node target = front;
		int n = 0;
		if (target == null)
			return null;
		if (index > length()-1)
			return null;
		else {
			if(index == 0)
				return target.getData();
			while(n<index && target.getNext() != null) {
				target = target.getNext();
				n++;
			}
		}
		return target.getData(); 
	}
//adds an SVPair (and as a result a node) to the doubly linked list
	public boolean add(SVPair p)
	{
		Node newstuff = new Node(p);
		if (p == null)
			return false;
		if (front == null) {
			front = newstuff;
			return true;
		}
		else {
			Node curr = front;
			while (curr.getNext() !=null) {
				curr = curr.getNext();
			}			
			curr.setNext(newstuff);
			newstuff.setLast(curr);
		}
		return true;
	}
	
	public void addAll(PairList others)
	{
			
	}
	
//deletes the target SVpair (and as a result node) from the SVPairList
	public boolean delete(SVPair target)
	{
		//if list is empty
		if (front == null)
			return false;

		//if front is to be deleted
		if (front.getData().equals(target)) {
			Node curr = front;
			front = front.getNext();
			//disconnects from list
			curr.setNext(null);
			return true;
		}
		//it is somewhere in the middle or end
		else
		{
			Node curr = front;
			while(curr.getNext() !=null && target != curr.getNext().getData()) {
				curr = curr.getNext();
			}
			if (curr.getNext() == null)
				return false;
			else {
				System.out.println("FOUND! WILL DELETE");
				Node one = curr;
				Node two = curr.getNext();
				Node three = curr.getNext().getNext();	
				one.setNext(three);
				three.setLast(one);
				two.setNext(null);
				two.setLast(null);
				return true;	
			}
		}
	}

	public void deleteAll(PairList others)
	{
	}

//returns true of the SVPair is in the list and false if it is not
	public boolean contains(SVPair target)
	{
		Node curr = front;
		if (target == null) {
			System.out.println("Target is null.\n");
			return false;
		}
		while (curr != null) {
			if(curr.getData()==target)
				return true;
			else
				curr = curr.getNext();
		}
		return false;	
	}
	public void clear()
	{
		while(length() != 0) {
			delete(front.getData());		
		}		
	}

	public void setKey(int key)
	{

	}
	
	public void setOrder(int order)
	{

	}

	public boolean show()
	{
		Node curr = front;
		if (curr == null) {
			return false; 
		}
		while(curr != null) {
			System.out.println(curr.getData().toString());
			curr = curr.getNext();
		}
		return true;
	}

	public Iterator<SVPair> iterator() { 
		return new Iterator();
	}
	public class Iterator {
	//	SVPair pair;
		int index;
		public Iterator() {
			index = 0;
		}
		public boolean hasNext() {	
			//as long as index is smaller than the amount in list, done			
			return index < data.length();	
		}
		public SVPair next() {
			return data.getElementAt(index);
		}
		public boolean remove() {
		}
	}
	
	
}
