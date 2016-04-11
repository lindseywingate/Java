import java.util.*;
import java.io.*;

public class TrieSetTester
{
	public static void main(String[] args)
	{
		BinaryTrie testTree = new BinaryTrie();
		Scanner reader=new Scanner(System.in);
		String a, d, s;
		boolean flag;
		int n=0;	
		while(n!=7) {
			System.out.println("\nPlease choose from the following selections: \n1. Add number\n2. Delete number\n3. Show size of tree\n4. Search for target string\n5. Clear the tree\n6. Return the structure of the tree\n7.Exit\n");
			n=reader.nextInt();
	
			switch(n) {
				case 1:
					System.out.println("Please enter a number to add: ");
					a=reader.next();
					test_num(a);
					int foo = Integer.parseInt(a);
					testTree.add(foo);
					break;	
				case 2:
					System.out.println("Please enter a number to delete: ");
					d=reader.next();
					test_num(d);
					//testTree.delete_number(d);
					break;
				case 3:
					//size();
					break;
				case 4:
					System.out.println("Please enter a search number: ");
					s=reader.next();
					test_num(s);
					flag = testTree.contains(s);
					if(flag==true)
						System.out.println("Your number is included in the tree.");
					else
						System.out.println("Your number is not included in the tree.");
					break;
				case 5:
					testTree.clear();
					break;
				case 6:
					//toString();
					break;
				case 7:
					break;		
				default: 
					System.out.println("Please enter a valid selection from the menu.");
					break;	
			}
		}
	}
	public static void error_message(String err)
	{
		System.out.println("ERROR: "+err+"\n");
	}
	public static void test_num(String a)
	{
		String[] num = a.split("");
		int c;
		for (c=0; c<num.length; c++) {
			if(num[c].equals("0")||num[c].equals("1")||num[c].equals("*"))
				continue;
			else
				error_message("The number you have entered is not valid. Please enter a binary number.");
			break;
		}				
	}
}
