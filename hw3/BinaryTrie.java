import java.util.*;

public class BinaryTrie //implements Iterable<String>, TrieSet
{
	 Node root = new Node();
	 int size;
	
	public BinaryTrie()
	{
	}

	public boolean add(int a)
	{
		Node curr=new Node();
		curr=root;
		//convert int back into String so can add easily digit by digit to tree
		String a1 = Integer.toString(a);
		int c, w;
		String[] num = a1.split("");
		
		//add nodes as necessary until string added
		for(c=0; c<num.length; c++) {
			if(num[c].equals("1")) {
				if (curr.getOne()!=null){
					curr=curr.one;
				}
				if (curr.getOne()==null){
					curr.one = new Node();
					curr.data = false;
					curr = curr.one;
				}
			}
			if(num[c].equals("0")){
				if(curr.getZero()==null) {
					curr.zero = new Node();
					curr.data = false;
					curr = curr.zero;	
				}
				if (curr.getZero()!=null) {
					curr=curr.zero;
				}
			}		
		}
		curr=root;
		return true;
	}
//these methods may not be necessary
/*	//add zerp
	public Node add_zero(Node curr)
	{
		curr.zero = new Node(true);
		curr.data = false;
		curr = curr.zero;
		return curr;	
	}

	//add one
	public Node add_one(Node curr)
	{
		curr.one = new Node(true);
		curr.data = false;
		curr = curr.one;
		return curr;

	}
*/
/*
	public boolean clear()
	{

	}
*/
	public boolean contains(String a)
	{
		Node curr = root;
		int c;
		String[] num = a.split("");

		if(root==null)
			return false;	
		for(c=0; c<num.length; c++) {
			if(num[c]=="1"){
				System.out.println("TEST2: 1");
				if(curr.one==null)
					return false;
				else {
					curr = curr.one;			
				}
			}
			if(num[c]=="0"){
				System.out.println("TEST: 2");
				if(curr.zero==null)	
				return false;
			}
				else {
					curr = curr.zero;
				}
		}		
		return true;
	}	
/*
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
