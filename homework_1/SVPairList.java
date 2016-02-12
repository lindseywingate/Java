import java.util.*;
import java.io.*;

class SVPairList implements Iterable<SVPair>, PairList
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
		while(curr.getNext() != null) {
			curr = curr.getNext();
			size++;
		}
		return size;
	}

	public SVPair getElementAt(int index)
	{
		int n = 0;
		Node target = front;
		while (n<index) {
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
			curr.setNext(new newstuff(p));
			front = newstuff;
		}
	}
		
	public void addAll(PairList others)
	{

	}

	public boolean delete(SVPair target)
	{

	}

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

}
