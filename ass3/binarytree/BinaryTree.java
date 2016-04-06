import java.util.*;
import java.io.*;

class BinaryTree //implements Iterable<String>
{
	public void add(String s, Node addme)
	{
		//error checking for s
		String[] num = s.split("");
		int count, c;
		for(count=0; count<num.length; count++) {
			if((num[count].equals("0") || num[count].equals("1") || num[count].equals("*")))
				continue;
			else
				error_message("The number you have entered is not valid. Please enter a binary number.");
				break;
			}

			if(addme == null)
				return new Node(null);
			if(x<addme.data)
				addme.left=add(addme_num, addme.left);
			else
				addme.right=add(addme_num, addme.right);
			return addme;
				
	}	

	public void delete_number(String s)
	{
		String[]num = s.split("");
		int count;
		for(count=0; count<num.length; count++) {
			if(num[count]!="*") {
				if(!(num[count].equals("0")|| num[count].equals("1")))
					error_message("The number you have entered is not valid. Please enter a binary number.");
					break;
			}
		}

	}
	
	public void error_message(String error)
	{
		System.out.println("ERROR: "+error+"\n");
	}
		
	public static void main(String[] args)
	{
		System.out.println("test");
	}
}
