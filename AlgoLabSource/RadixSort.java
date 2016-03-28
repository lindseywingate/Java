/*This code was written by Lindsey Wingate*/
class RadixSort extends DecisionAlgorithm
{
    int [] intset;
    long stepcount = 0;
    int numcount = 0;
    long setsum = 0;
 
    public RadixSort()
    {
       name = "Radix Sort";
    }
 
    public void setInstance(Object instance)
    {
       int [] iset = (int []) instance;
       numcount = iset.length;
       intset = new int [numcount];
       setsum = 0;
       for (int p=0; p<numcount; p++)
       {
          intset[p] = iset[p];
          setsum = setsum + intset[p];
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
		int x;
		int m =intset[0];
		int i=1;
		int n=intset.length;
		int[] b = new int[10];
		//finds biggest number in set
		for(x=1; x<n; x++)
			if(intset[x] > m)
				m=intset[x];
		//while biggest #/1 still has leftovers
		while(m/i>0)
		{
			int[] bucket = new int[10];
			//adds how many numbers are in each bucket
			for (x=0; x<n; x++)
				bucket[(intset[x]/i)%10] = bucket[(intset[x]/i)%10]+1;
				stepcount++;
			for (x=1; x < 10; x++)
                bucket[x] += bucket[x - 1];
				stepcount++;
            for (x=n-1; x>= 0; x--)
                b[--bucket[(intset[x] / i) % 10]] = intset[x];
				stepcount++;
            //refills array with correct order
			for (x = 0; x < n; x++)
                intset[x] = b[x];
				stepcount++;
            i *= 10;        
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
/*This code was written by Lindsey Wingate*/ 