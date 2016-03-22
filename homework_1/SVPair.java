//The SVPair list allows the users to store data within a Node in a PairList.

import java.util.*;
import java.io.*;

class SVPair
{
  	public static final int SIZE = 0;
  	public static final int VALUE = 1;
  	public static final int UNORDERED = 2;
  	public static final int UP = 3;
	public static final int DOWN = 4;
	
	long size;
	long value;
//constructor: creates an SVPair with long variables	
	public SVPair(long s, long v)
	{
	   size = s;
	   value = v;
	}
//constructor: creates an SVPair with integer variables
	public SVPair(int s, int v)
	{
	   size = s;
	   value = v;
	}  
//returns the size variable
	public long getSize()
	{
	   return size;
	}
//returns the value variable
	public long getValue()
	{
	   return value;
	}
//prints the size and value variables
	public String toString()
	{
	   return "(" + size + "," + value + ")";
	}
//allows the comparison of two SVPairs
	public boolean lessThan(SVPair other, int key)
	{
		if (key == VALUE)
		{
			if (value < other.value) return true;
			else if (value == other.value && size < other.size)
				return true;
			else return false;
		}
		else  // key == SIZE
		{
			if (size < other.size) return true;
			else if (size == other.size && value < other.value)
				return true;
			else return false;
		}
	}                
//verifies of two SVPair contents are the same
	public boolean equals(SVPair other)
	{
		return (size == other.size) && (value == other.value);
	}
}
