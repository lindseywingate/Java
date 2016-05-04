//The Node class creates a node for data to be stored in. In this case, the Node stores an SVPair object in a doubly linked list.
public class Node
{
	private Node next = null;
	private Node last = null;
	private SVPair data;
	
//Assigns data to Node
	public Node(SVPair input)
	{
			setData(input);
	}
//allows reassignment of SVpair object
	public void setData(SVPair input)
	{
			data = input;
	}
//returns SVPair object
	public SVPair getData()
	{
			return data;
	}
//creates pointer to last node 
	public void setLast(Node l)
	{
			last = l;
	}
//creates pointer to next node
	public void setNext(Node n)
	{
			next = n;
	}
//returns the node before the current node
	public Node getLast()
	{
		return last;
	}
//returns the node after the current node
	public Node getNext()
	{
		return next;
	}
}
