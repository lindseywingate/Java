class SVPair
{
  	public static final int SIZE = 0;
  	public static final int VALUE = 1;
  	public static final int UNORDERED = 2;
  	public static final int UP = 3;
	public static final int DOWN = 4;
	
	long size;
	long value;
	
	public SVPair(long s, long v)
	{
	   size = s;
	   value = v;
	}
	
	public SVPair(int s, int v)
	{
	   size = s;
	   value = v;
	}  
	
	public long getSize()
	{
	   return size;
	}
	
	public long getValue()
	{
	   return value;
	}

	public String toString()
	{
	   return "(" + size + "," + value + ")";
	}

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

	public boolean equals(SVPair other)
	{
		return (size == other.size) && (value == other.value);
	}
}
