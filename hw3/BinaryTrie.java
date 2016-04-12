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
				curr=addme(0, curr);
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
				System.out.println("Node added for 1");
				return curr;
			}
			else if (curr.getOne()!=null){
				System.out.println("Node already exists for 1");
				curr=curr.getOne();
				return curr;
			}
		}
		if(a==0){
			if (curr.getZero()==null) {
				curr.setZero(new Node());
				curr.setData(false);
				curr = curr.getZero();
				System.out.println("Node added for 0");
				return curr;
			}

			else if (curr.getZero()!=null) {	
				curr=curr.getZero();
				System.out.println("Node already exists for 0");
				return curr;
			}
		}	
		return curr;	
	}
/*	public boolean clear()
	{

	}
*/
	public boolean contains(String a)
	{
		Node temp;
		temp = root;
		String[] num = a.split("");

		int w;
		for(w=0; w<num.length; w++) {
			System.out.println(num[w]);
			if(root==null)
				return false;	
			if(num[w].equals("1")){
				if(temp.getOne()!=null) {
					System.out.println("found");
					temp=temp.getOne();
					continue;
				}
				else {
					return false;
				}
			}
			if(num[w].equals("0")){
				if(temp.getZero()!=null){
					System.out.println("found");	
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
