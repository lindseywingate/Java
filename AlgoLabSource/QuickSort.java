import java.util.*;
import java.lang.*;

class QuickSort extends DecisionAlgorithm
{
	int [] intset;
	long stepcount = 0;
	int numcount = 0;
	long setsum = 0;
	List top = new ArrayList();	
	List bottom = new ArrayList();
	List answer = new ArrayList();

	public QuickSort()
	{
		name = "Quick Sort";
	}

	public void setInstance(Object instance)
	{
		int [] iset = (int []) instance;
		numcount = iset.length;
		intset = new int [numcount];
		setsum = 0;
		for(int p=0; p<numcount; p++)
		{
			intset[p] = iset[p];
			setsum = setsum + intset[p];
		}
	}
	
	public void setConstraint(int i, long value)
	{
		
	}
	
	public void setConstraint(int i)
	{

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
		sortSet();
	}

	public void sortSet()
	{
		quicksortme(intset);
	}
	
	public int[] quicksortme(int[] sortme)
	{
		if (sortme.length == 0)
			return null;

		int pivot = sortme[0];
		int [] top = new int[numcount-1];
		int [] bottom = new int[numcount-1];	
	
		for (int b=1; b<sortme.length; b++)
		{
			stepcount++;
			if (sortme[b]>pivot)
				top[b] = sortme[b];
			else
				bottom[b]= sortme[b];
		}
		
		int[] answer = new int[numcount];
	
		bottom = quicksortme(bottom);

		int bottom_size = bottom.length;
		int top_size = top.length;

		if(bottom!=null)
		{
			for(int c=0; c<bottom_size; c++)
				answer[c] = bottom[c];
				stepcount++;
		}	
		answer[numcount-1] = pivot;
	
		int size = bottom.length;	
	
		top = quicksortme(top);
		if(top!=null) 
		{
			for(int d=0; d<top_size; d++) 
				answer[d+size] = top[d];
				stepcount++;
		}
		return answer;
	}
	
	private boolean orderCheck()
	{
		long sortedsum = 0;
		boolean result = true;
		for (int i=0; i<numcount-1; i++)
		{
			//increment stepcount
			stepcount++;
			sortedsum = sortedsum + intset[i];
			if (intset[i] > intset[i+1]);
			{
				result = false;
				System.out.println("*** out of order");
				break;
			}
		}
		if (setsum != sortedsum + intset[numcount-1])
			result=false;
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
 
