import java.awt.Color;
import java.util.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;

public class Series implements Iterable<PointSet>
{   // a list of point sets
   String name;
   //boolean active = true;
   int colorindex;
   
   PointSet [] pointsets;

   public Series (String n, int index)
   {
      super();
      name = n;
      colorindex = index;
   }

   public int getColorIndex()
   {
      return colorindex;
   }

   public String getName()
   {
      return name;
   }

   public void clear()
   {
      pointsets = null;
   }

   public void initialize(int xcount, int type)
   {
      //System.err.println("Creating series type "+type+" with "+xcount+" x points");
      pointsets = new PointSet[xcount];
      for (int i=0; i<xcount; i++)
      {
         if (type == ChartPanel.NUMERIC)
            pointsets[i] = new NumericPointSet();
         else
            pointsets[i] = new BooleanPointSet();
      }
   }

   public boolean addPoint(int xindex, DataPoint p)
   {
      PointSet pset = pointsets[xindex];
      if (pset != null)
      {
         pset.add(p);
         return true;
      }
      else
         return false;
	}

	public Iterator<PointSet>  iterator()
	{
		return new Cursor();
	}	
	
   protected class Cursor implements Iterator<PointSet>
   {
      int currentindex = 0;

      public boolean hasNext()
      {
         return currentindex < pointsets.length;
      }

      public PointSet next()
      {
         if (hasNext())
         {
            return pointsets[currentindex++];
         }
         else
            return null;
      }

		public void remove()
		{	// not implemented
         System.err.println("Iterator-based removal is not implemented");
		}
   }
   
   public static void main(String [] args)
   {
      Series test = new Series("Test", 0);
      test.initialize(3, 3);
      test.addPoint(0, new DataPoint(10, new Long(10)));
      test.addPoint(0, new DataPoint(20, new Long(20)));
      test.addPoint(1, new DataPoint(30, new Long(30)));
      test.addPoint(1, new DataPoint(40, new Long(40)));      
      test.addPoint(2, new DataPoint(50, new Long(50)));
      test.addPoint(2, new DataPoint(60, new Long(60)));
      for (PointSet pset:test.pointsets)
      {
         System.out.println("Next set: ");
         for (int i=0; i<pset.size(); i++)
            System.out.print(" " + ((NumericPointSet)pset).get(i).toString());
         System.out.println(" Ave point: " + ((NumericPointSet)pset).getAve());
      }
   }
}



