import java.util.*;
 
 class IntSetGenerator implements InstanceGenerator
 {
     public SVPairList makeInstance(long seed, long [] params)
     {
         return makeSVPairList(seed, params);
     }
 
     public SVPairList makeSVPairList(long seed, long[] setsize)
     {
         int count = 0;
		 int setsize2 = setsize.length; 
	     int arraysize = Long.valueOf(setsize2).intValue();
		 SVPairList iset  = new SVPairList();
		 int seed2 = Long.valueOf(seed).intValue();
         Random randgen = new Random(seed2);
 
         while (count < setsize2)
         {
             SVPair me = new SVPair(count, 100);
			 iset.add(me);	
             count++;
         }
         return iset;
     }
    public static void main(String [] args)
    {	
     //SVPairList intset = new SVPairList();
    // IntSetGenerator setmaker = new IntSetGenerator();
     //intset = setmaker.makeSVPairList(Long.parseLong(args[0]), Long.parseLong(args[1]));
     //for (SVPair i: intset)
	//	System.out.print(" "+i);
	//	System.out.println();
    }
 }
