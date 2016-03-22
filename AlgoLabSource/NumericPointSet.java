import java.util.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;

public class NumericPointSet extends PointSet
{   // a set of points all sharing the same x value
   ArrayList <DataPoint> pointlist;
   double avex = 0;
   double avey = 0;
   double maxy = 0;
   double miny = java.lang.Double.MAX_VALUE;

   public NumericPointSet()
	{
      count = 0;
      pointlist = new ArrayList<DataPoint>();
   }

   public void add(DataPoint p)
   {
      pointlist.add(p);
      count++;
      avex = (avex * (double)(count-1) + ((Long)p.x).doubleValue())/(double)(count);
      avey = (avey * (double)(count-1) + ((Long)p.y).doubleValue())/(double)(count);
      maxy = Math.max(maxy, ((Long)p.y).doubleValue());
      miny = Math.min(miny, ((Long)p.y).doubleValue());
	}

   public DataPoint get(int i)
   {
      return pointlist.get(i);
   }

   public Point2D.Double getAve()
   {
      return new Point2D.Double(avex, avey);
   }

   public Point2D.Double getMax()
   {
      return new Point2D.Double(avex, maxy);
   }

   public Point2D.Double getMin()
   {
      return new Point2D.Double(avex, miny);
   }
   
   public static void main(String [] args)
   {
      NumericPointSet pset = new NumericPointSet();
      pset.add(new DataPoint(10, new Long(10)));
      pset.add(new DataPoint(15, new Long(15)));
      pset.add(new DataPoint(20, new Long(20)));
      pset.add(new DataPoint(25, new Long(25)));
      for (int i=0; i<pset.pointlist.size(); i++)
         System.out.println(pset.get(i).toString()); 
   }
}

