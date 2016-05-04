public class ExecutionRecord
{
	long seed = 0;
	Object result;
	long space = 0;
	long stepcount = 0;
	long xvalue;
	long time = 0;

	public ExecutionRecord (long sd, Object r, long sp, long st, long xv, long ti)
	{
		seed = sd;
		result = r;
		space = sp;
		stepcount = st;
		xvalue = xv;
		time = ti;
	}
	
	public String toString()
	{
      return new String("["+result.toString()+","+space+","+stepcount+","+xvalue+","+time+"]");	   
	}
}
