/*Code written by Lindsey Wingate*/
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
		while(curr != null) {
			curr = curr.getNext();
			size++;
		}
		return size;
	}

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
		
/*	public void addAll(PairList others)
	{

	}
*/
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

/*	public void deleteAll(PairList others)
	{
	
	}
*/
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

/*	public void setKey(int key)
	{
		int a, b;
		Node curr = front;
		if (order == 0) {
			//use insertion sort
			while(a<length()-1) {
				b = a+1;
				while(b<length()-1) {
					if(curr.getSize()>(curr.getNext().getSize()) {
						
					}
				}	
			}

		}	
		else {
			
		}	

	}
/*
	public void setOrder(int order)
	{
	}

/*
	public Iterator<SVPair> iterator() {
		 Iterator<SVPair> it = new Iterator<SVPair>() {
			
			Node current = front;
			public boolean hasNext(SVPair a) {
				if (a.getNext() !=null)
					return true;
				//return (current != null);
			}
			
			public SVPair next(SVPair current) {
				return current.getNext().getData();
			}		

			public remove() {
				throw new UnsupportedOperationException();	
			}
		}
	}
*/
}
