import java.util.*;
import java.io.*;

public class BinaryTreeTester
{
	public static void main(String[] args)
	{
		Node newnode = new Node(8);
		BinaryTree testTree = new BinaryTree();
		testTree.add(8, newnode);	

		Scanner reader=new Scanner(System.in);
		int a, d, s;
		int n=0;	
		while(n!=7) {
			System.out.println("\nPlease choose from the following selections: \n1. Add number\n2. Delete number\n3. Show size of tree\n4. Search for target string\n5. Clear the tree\n6. Return the structure of the tree\n7.Exit\n");
			n=reader.nextInt();
			
			switch(n) {
				case 1:
					System.out.println("Please enter a number to add: ");
					a=reader.nextInt();
					//add(a);
					break;	
				case 2:
					System.out.println("Please enter a number to delete: ");
					d=reader.nextInt();
					//delete(d);
					break;
				case 3:
					//size();
					break;
				case 4:
					System.out.println("Please enter a search number: ");
					s=reader.nextInt();
					//search();
					System.out.println(s);
					break;
				case 5:
					//clear();
					break;
				case 6:
					//toString();
					break;	
			}
				
		}

	}


}
