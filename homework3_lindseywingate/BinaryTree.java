import java.util.*;
import java.io.*;

public class BinaryTree
{
	public Node add(int x, Node addme)
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
