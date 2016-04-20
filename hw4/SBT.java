import java.util.*;
class SBT extends DecisionAlgorithm
{  // a slow backtracking algorithm
   PairList pairlist;
   long stepcount = 0;
   int paircount = 0;
   int maxdepth = 0;
   long maxsize = 0;
   long minvalue = 0;
   long sizesum = 0;
   long valuesum = 0;
   boolean decision = false;
   boolean [] dfault = {true, true};

   public SBT()
   {
      name = this.getClass().getName();
   }

   public SBT(PairList ilist)
   {
      name = this.getClass().getName();
		setInstance(ilist);
   }

   public void setInstance(Object instance)
   {
      pairlist = (PairList) instance;
		paircount = pairlist.length();
		sizesum = 0;
		valuesum = 0;
      for (SVPair pair: pairlist)
      {
         sizesum += pair.getSize();
         valuesum += pair.getValue();
      }
		updateDefaults();
   }

   public void setConstraint(int i, long value)
   {
      if (i == 0)
         maxsize = value;
      else if (i == 1)
         minvalue = value;
      dfault[i] = false;
      updateDefaults();
   }

   public void setConstraint(int i)
   {
      dfault[i] = true;
      updateDefaults();
   }

   private void updateDefaults()
   {
   	double cfactor = 0.33;
   	double vfactor = 0.66;
      if (dfault[0] && dfault[1])
      {
      	maxsize = (long) (cfactor * (double) sizesum);
         minvalue = (long) (vfactor * (double) valuesum);	
      }
      else if (dfault[1]) //update minvalue relative to maxsize
      {
      	cfactor = (double) maxsize / (double) sizesum;
      	vfactor = Math.min(0.9, cfactor + 0.33);
         minvalue = (long) (vfactor * (double) valuesum);
      }
      else if (dfault[0]) // update maxsize relative to minvalue
      {
         vfactor = (double) minvalue / (double) valuesum;
      	cfactor = Math.min(0.1, vfactor - 0.33);
         maxsize = (long) (cfactor * (double) sizesum);
      }
   }

   public long getStepCount()
   {
      return stepcount;
   }

   public long getSpace()
   {
      return maxdepth;
   }

   public boolean getBooleanResult()
   {
      return decision;
   }

   public void run()
   {
      stepcount = 0;
      maxdepth = 0;
      decision = findSub(0, 0, 0, 0);
   }

   public boolean findSub(int index, long setsize, long setvalue, int depth)
   {
      stepcount = stepcount + 1;
      depth = depth + 1;
      if (depth > maxdepth)
         maxdepth = depth;
      if (setsize <= maxsize && setvalue >= minvalue)
         return true;
      else if (setsize > maxsize || index == paircount)
         return false;
   	SVPair curpair = pairlist.getElementAt(index);
      if (findSub(index+1, setsize+curpair.size, setvalue+curpair.value, depth))
         return true;
      else
         return findSub(index+1, setsize, setvalue, depth);
   }

	public void report()
	{
		System.out.println(getName()+" Pair count: "+paircount+" Total size: "+sizesum+" Total value: "+valuesum);
		System.out.println("Target size: "+maxsize+" Target value: "+minvalue);	
	}

	private void showInstance()
	{
		for (SVPair pair: pairlist)
         System.out.println(pair.toString());
      report();
	}
}
