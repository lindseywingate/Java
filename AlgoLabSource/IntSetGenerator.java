import java.util.*;
 
 class IntSetGenerator implements InstanceGenerator
 {
     public Object makeInstance(long seed, long [] params)
     {
         return makeIntSet(seed, params[0], params[1], params[2]);
     }
 
     public int[] makeIntSet(long seed, long setsize, long max, long percentordered)
     {
         int count = 0;
         int arraysize = Long.valueOf(setsize).intValue();
         int maxvalue = Long.valueOf(max).intValue();
         int [] iset = new int[arraysize];
         Random randgen = new Random(seed);
 
         while (count < setsize)
         {
             iset[count] = randgen.nextInt(maxvalue)+1;
             count++;
         }
         return iset;
     }
 
     public int[] makeIntSet(long setsize, long max, long percentordered)
     {
         int count = 0;
         int arraysize = Long.valueOf(setsize).intValue();
         int maxvalue = Long.valueOf(max).intValue();
         int [] iset = new int[arraysize];
         Random randgen = new Random();
 
         while (count < setsize)
        {
             iset[count] = randgen.nextInt(maxvalue)+1;
             count++;
         }
         return iset;
     }
 
    public static void main(String [] args)
    {
     int [] intset;
     IntSetGenerator setmaker = new IntSetGenerator();
     if (args.length == 2) // set size and max value
         intset = setmaker.makeIntSet(Long.parseLong(args[0]), Long.parseLong(args[1]), 0);
     else // seed, set size, and max value
         intset = setmaker.makeIntSet(Long.parseLong(args[0]), Long.parseLong(args[1]), Long.parseLong(args[2]), 0);
     for (int i: intset)
          System.out.print(" "+i);
       System.out.println();
    }
 }
