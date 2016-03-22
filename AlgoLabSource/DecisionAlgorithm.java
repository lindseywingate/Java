abstract class DecisionAlgorithm implements Algorithm
{
   String name;

   public String getName()
   {
      return name;
   }

   public Object getResult()
   {
      return new Boolean(getBooleanResult());
   }

   abstract public void setInstance(Object instance);
   abstract public void run();
   abstract public long getStepCount();
   abstract public long getSpace();

   abstract public boolean getBooleanResult();
   abstract public void setConstraint(int i);	// set a default value for constraint at index i
   abstract public void setConstraint(int i, long value);  //set value of constraint i
}
