import java.util.*;

class BinaryTrie //implements Iterable<String>, TrieSet
{
	private Node root=new Node(true, null, null); 
	private int size;
	
	public BinaryTrie()
	{
	}

	public boolean add(int a)
	{
		Node curr = root;
		//convert int back into String so can add easily digit by digit to tree
		String a1 = Integer.toString(a);
		int c;
		int[] num = a1.split();
			
		//add nodes as necessary until string added
		for(c=0; c<num.length; c++) {
			if(num[c]==1)
				if (curr.right==null){
					curr.right = new Node(true, null, null);
					curr.data = false;
					curr = curr.right;
				}
				else
					add_left(curr);
			if(num[c]==0)
				if(curr.left==null) {
					curr.left = new Node(true, null, null);
					curr.data = false;
					curr=curr.left;	
			}	
		}
	}
	public Node add_left(Node curr)
	{
		
	}

	public Node add_right(Node curr)
	{


	}

/*
	public boolean clear()
	{

	}

	public boolean contains()
	{

	}

	public boolean remove()
	{

	}

	public boolean size()
	{

	}
	
	public String toString()
	{

	}

	public Iterator<String> iterator()
	{

	}
*/
}
