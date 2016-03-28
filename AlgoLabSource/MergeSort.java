class MergeSort extends DecisionAlgorithm
 {
    int [] intset;
    long stepcount = 0;
    int numcount = 0;
    long setsum = 0;
 
    public MergeSort()
    {
       name = "Merge Sort";
    }
 
    public void setInstance(Object instance)
    {
       int [] iset = (int []) instance;
       numcount = iset.length;
       intset = new int [numcount];
       setsum = 0;
       for (int pos=0; pos<numcount; pos++)
       {
          intset[pos] = iset[pos];
          setsum = setsum + intset[pos];
       }
    }
 
    public void setConstraint(int i, long value)
    {
       // not needed
    }
 
    public void setConstraint(int i)
    {
       // not needed
    }
 
    public long getStepCount()
    {
       return stepcount;
    }
 
    public long getSpace()
    {
       return numcount;
    }
 
    public boolean getBooleanResult()
    {
       return orderCheck();
    }
 
    public void run()
    {
       stepcount = 0;
       sortSet();
	}
	
	public void sortSet()
	{
		intset = mergesortme(intset);			
	}

	public int[] mergesortme(int[] listo)
	{
		if (listo.length==0)
			return null;
	//split into two lists to further split if necessary	
		int[] left = new int[listo.length/2];
		//fill array with contents
		int size1 = listo.length/2;
		for (int x=0; x<size1; x++)
			left[x] = listo[x];
		
		int size2 = listo.length-left.length;
	
		int[] right = new int[size2];
		//fill array with second half of main array
		for (int y=0; y<size2; y++)
			right[y] = listo[y+size1];

		//call recursively until done splitting 
		mergesortme(left);
		mergesortme(right);
	
		//merge individual splits
		merge(left, right, listo);	
		return listo;
	}

	public void merge(int[] left, int[] right, int[] answer)
	{
		int l = 0;
		int r = 0;
		int x = 0;
		
		//compare items in split arrays to merge into one big, fat array
		for(int i=0; i<answer.length; i++)
		{
			stepcount++;
			if(r>=right.length || (l < left.length && left[l] <= right[r]))
			{	
				answer[x] = left[l];
				l++;
			}
			else
			{
				answer[i] = right[r];
				r++;	
			}  
		} 
	}

	private boolean orderCheck()
    {
       long sortedsum = 0;
       boolean result = true;
       for (int i=0; i<numcount-1; i++)
       {
          sortedsum = sortedsum + intset[i];
          if (intset[i] > intset[i+1])
          {
             result = false;
             System.out.println("*** out of order");
             break;
          }
       }
       if (setsum != sortedsum + intset[numcount-1])
          result = false;
       if (result == false)
       {
          System.out.println("*** sort failure");
          showInstance();
       }
       return result;
    }
 
    public void showInstance()
    {
       for (int i=0; i<intset.length; i++)
          System.out.print(" "+intset[i]);
       System.out.println();
    }
 
    public static void main (String [] args)
    {
       InsertSort sort = new InsertSort();
		  IntSetGenerator setgen = new IntSetGenerator();
		  int [] set = setgen.makeIntSet(10, 100, 0);
		  sort.setInstance(set);
		  sort.showInstance();
		  sort.run();
		  sort.showInstance();
		  System.out.println("Steps: "+sort.getStepCount()+" Space: "+sort.getSpace()+" Result: "+sort.getBooleanResult());
    }
}
 
