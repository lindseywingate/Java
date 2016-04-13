import java.util.*;

public class BinaryTrie //implements Iterable<String>, TrieSet
{
	Node root = new Node();
	int size;
	
	public BinaryTrie()
	{
	}

	public boolean add(String a)
	{
		Node curr=root;
		String[] num=a.split("");
		int c;
		for(c=0; c<num.length; c++) {
			if(num[c].equals("*")) {
				System.out.println("found a star");
				curr=addme(1, curr);
				continue;
			}		
			if(num[c].equals("1")) {
				curr=addme(1, curr);
				continue;
			}
			if(num[c].equals("0")) {
				curr=addme(0, curr);
				continue;
			}
		}
		return true;
	}

	public Node addme(int a, Node curr)
	{
	//add nodes as necessary until string added
		if(a==1) {
			if (curr.getOne()==null) {
				curr.setOne(new Node());
				curr.setData(false); 
				curr = curr.getOne();
				return curr;
			}
			else if (curr.getOne()!=null){
				curr=curr.getOne();
				return curr;
			}
		}
		if(a==0){
			if (curr.getZero()==null) {
				curr.setZero(new Node());
				curr.setData(false);
				curr = curr.getZero();
				return curr;
			}

			else if (curr.getZero()!=null) {	
				curr=curr.getZero();
				return curr;
			}
		}	
		return curr;	
	}
	public boolean clear()
	{
		if(root==null)
			return true;
		else {
			root.setZero(null);
			root.setOne(null);
		}
		return true;
	}

	public boolean contains(String a)
	{
		Node temp;
		temp = root;
		String[] num = a.split("");
		int w;

		for(w=0; w<num.length; w++) {
			if(root==null)
				return false;	
			if(num[w].equals("1")){
				if(temp.getOne()!=null) {
					temp=temp.getOne();
					continue;
				}
				else {
					return false;
				}
			}
			if(num[w].equals("0")){
				if(temp.getZero()!=null){
					temp=temp.getZero();
					continue;
				}
				else {
					return false;
				}
			}
		}	
			return true;	
	}	

	public boolean remove(String s)
	{
		Node point;
		point=root;
		String[] num=s.split("");
		
		//if num is not in tree
		if(contains(s)==false)	
			return false;
		
		
		return true;		
	}

	public int size()
	{
		int count=0;
		count=visitNode(root);		
		return count;
	}

	public int visitNode(Node node)
	{
		if(node==null)
			return 0;
		if(node.getZero()==null && node.getOne()==null) 
			return 1;
		return visitNode(node.getZero())+visitNode(node.getOne());
	}
/*	
	public String toString()
	{

	}

	public Iterator<String> iterator()
	{

	}
*/
}
