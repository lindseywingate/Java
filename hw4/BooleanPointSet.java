public class BooleanPointSet extends PointSet
{
   int yescount = 0;
   int nocount = 0;
   double avex = 0;
    

   public BooleanPointSet()
	{
      count = 0;
	}

   public void add(DataPoint p)
	{
      count++;
      avex = (avex * (double)(count-1) + ((Long)p.x).doubleValue())/(double)(count);
	    //System.err.println("Adding "+p.decision+" at "+x);
      if (((Boolean)p.y).booleanValue())
         yescount++;
      else
         nocount++;
   }
	
   public DataPoint getPercentYes()
	{
      return new DataPoint((long) Math.floor(avex), new Long(100 * yescount/count));
	}

   public DataPoint getPercentNo()
	{
	    //System.err.println("No count: "+nocount);
      return new DataPoint((long) Math.floor(avex), new Long(100 * nocount/count));
	}
}
