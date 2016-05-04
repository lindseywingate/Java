import java.util.*;
import java.io.*;

class NSquared extends OptimizationAlgorithm
{
    long n;
    long result;

    public NSquared()
    {
		name = new String("N Squared");
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
		result = n*n;
    }
}
