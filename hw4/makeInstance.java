import java.lang.*;
import java.io.*;
import java.util.*;

public class makeInstance //implements InstanceGenerator 
{
	public SVPairList makeInstance(long seed, long[] params)
	{
		SVPairList different = new SVPairList();
		Random randomGen = new Random();
		int length = params.length;
		int x;
		int random;
		for(x=0; x<length; x++) {
			random = randomGen.nextInt(10000);
			SVPair blah = new SVPair(random, params[x]);
			different.add(blah);	
		}		
		different.show();
		return different;
	}
}	
