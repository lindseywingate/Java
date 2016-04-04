import java.util.*;
import java.io.*;

public class BinaryTree
{
	private class Node
	{
		int data;
		Node left, right;
	
		Node(int val)
		{
			data=val;
			left=null;
			right=null;
		}
		
		Node(int val, Node left_child, Node right_child)
		{
			data=val;
			left=left_child;
			right=right_child;
		}
	}

	private Node add(int x, Node addme)
	{
		if(addme == null)
			return new Node(x);
		if(x<addme.data)
			addme.left=add(x, addme.left);
		else
			addme.right=add(x, addme.right);
		return addme;
	}	
}
