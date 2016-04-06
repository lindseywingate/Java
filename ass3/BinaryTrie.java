import java.util.*;

public class BinaryTrie //implements Iterable<String>, TrieSet
{
	public Node root=null; 
	public int size;
	
	public BinaryTrie()
	{
	}

	public boolean add(int a)
	{
		Node curr = root;
		//convert int back into String so can add easily digit by digit to tree
		String a1 = Integer.toString(a);
		int c;
		String[] num = a1.split("");
			
		//add nodes as necessary until string added
		for(c=0; c<num.length; c++) {
			if(root==null)
				root = new Node(true);
				curr = root;
			if(num[c]=="1"){
				if (curr.right==null){
					curr.right = new Node(true);
					curr.data = false;
					curr = curr.right;
				}
				else
					curr = add_left(curr);
			}
			if(num[c]=="0"){
				if(curr.left==null) {
					curr.left = new Node(true);
					curr.data = false;
					curr=curr.left;	
				}
				else
					curr = add_right(curr);
			}		
		}
		return true;
	}
	//add zerp
	public Node add_left(Node curr)
	{
		curr.left = new Node(true);
		curr.data = false;
		curr = curr.left;
		return curr;	
	}

	//add one
	public Node add_right(Node curr)
	{
		curr.right = new Node(true);
		curr.data = false;
		curr = curr.right;
		return curr;

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
