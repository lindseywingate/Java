import java.util.*;
import java.io.*;

class TwoToSqrtN extends OptimizationAlgorithm
{
    long n;
    long result;

    public TwoToSqrtN()
    {
		name = new String("2^sqrt(N)");
    }

    public long getStepCount()
    {
		return n;
    }

    public long getSpace()
    {
		return n;
    }

    public long getLongResult()
    {
		return result;
    }

    public void setInstance(Object instance)
    {
		n = ((Long)instance).longValue();
    }

   public void setConstraint(int i)
   {
      return;
   }
   
   public void setConstraint(int i, long value)
   {
      return;
   }

    public void run()
    {
		result = (long) Math.pow(2, Math.sqrt(n));
    }
}
