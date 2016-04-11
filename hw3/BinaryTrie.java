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
		Node curr;
		curr=root;
		//convert int back into String so can add easily digit by digit to tree
		String a1 = Integer.toString(a);
		int c;
		String[] num = a1.split("");
	
		//add nodes as necessary until string added
		for(c=0; c<num.length; c++) {
			if(num[c].equals("1")) {
				if (curr.getOne()!=null){
					curr=curr.getOne();
					continue;
				}
				else {
					curr.setOne(new Node());
					curr.setData(false); 
					curr = curr.getOne();
					continue;
				}
			}
			if(num[c].equals("0")){
				if (curr.getZero()!=null) {
					curr=curr.getZero();
					continue;
				}
				else {
					curr.setZero(new Node());
					curr.setData(false);
					curr = curr.getZero();
					continue;	
				}
			}		
		}
		return true;
	}
	public boolean clear()
	{
		if(root==null)
			return true;
		else
			root.setZero(null);
			root.setOne(null);
		return true;	
	}

	public boolean contains(String a)
	{
		Node curr;
		curr = root;
		int c;
		String[] num = a.split("");

		int w;
		for(w=0; w<num.length; w++)
			System.out.println(num[w]);

		if(root==null)
			return false;	
		for(c=0; c<num.length; c++) {
			if(num[c].equals("1")){
				if(curr==null) {
					return false;
				}
				else {
					curr = curr.getOne();			
				}
			}
			if(num[c].equals("0")){
				if(curr==null){	
					return false;
				}
				else {
					curr = curr.getZero();
				}
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
