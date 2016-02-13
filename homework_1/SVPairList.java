import java.util.*;
import java.io.*;
import java.lang.*;

class SVPairList //implements Iterable<SVPair>, PairList
{
	private Node front = null;
	private int size;
	
	public SVPairList() 
	//set default key and default order
	{

	}

	public int length()
	{
		int size = 0;
		Node curr = front;
		if (curr == null) {
			return 0;
		}
		while(curr.getNext() != null) {
			curr = curr.getNext();
			size++;
		}
		return size;
	}

	public SVPair getElementAt(int index)
	{
		Node target = front;
		int n = 1;
		if (target == null)
			return null;
		while (n!=index) {
			if (target == null)
				return null;
			target = target.getNext();
			n++;
		}
		return target.getData();
	}

	public boolean add(SVPair p)
	{
		Node newstuff = new Node(p);
		if (p == null)
			return false;
		if (front == null) {
			front = newstuff;
			newstuff.setLast(front);
			return true;
		}
		else {
			Node curr = front;
			while (curr.getNext() !=null) {
				curr = curr.getNext();
			}			
			curr.setNext(newstuff);
			front = newstuff;
		}
		return true;
	}
		
/*	public void addAll(PairList others)
	{

	}
*/
	public boolean delete(SVPair target)
	{
		//if list is empty
		if (front == null)
			return false;

		//not in the list
		//

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
/*
	public void deleteAll(PairList others)
	{

	}

	public boolean contains(SVPair target)
	{
	
	}

	public void clear()
	{
	}

	public void setKey(int key)
	{
	
	}

	public void setOrder(int order)
	{

	}

	public Iterator<SVPair> iterator()
	{

	}
*/
}
