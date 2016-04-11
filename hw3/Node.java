public class Node
{
	boolean data=true;
	Node zero=null;
	Node one=null;

	public Node()
	{
	}

	public Node(boolean d)
	{
	setData(d);
	}
	
	public void setData(boolean c) 
	{
	data=c;
	}
	
	public void setZero(Node newNext)
	{
	zero=newNext;
	}
	
	public void setOne(Node newNext)
	{
	one=newNext;
	}
	
	public Node getZero()
	{	
	return zero;
	}

	public Node getOne()
	{
	return one;
	}
}

