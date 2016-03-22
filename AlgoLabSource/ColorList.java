import java.awt.*;
import java.awt.geom.*;
///////////////////////////////////////////  ColorList  ///////////////////////////////////////////////////////   
public class ColorList
{
   static int colorcount = AlgoLab.MAX_ALG_COUNT;
   static Color [] clist = {
      Color.black,
      Color.blue,
      AlgoLab.GREEN,
      Color.red,
      AlgoLab.BROWN,
      AlgoLab.ORANGE,
      AlgoLab.PURPLE,
      AlgoLab.DARKBLUE
         //add(Color.magenta);
   };
   
   public static Color getColor (int index)
   {
      if (index < colorcount)
         return clist[index];
      else
         return null;
   }
   
   public static void makeDot (int index, Point2D.Double p, double dotsize, Graphics2D surface)
   {
      surface.setPaint(ColorList.getColor(index));
      if (index == 0)
      {
         RectangularShape dot = new Ellipse2D.Double(p.x-dotsize/2.0, p.y-dotsize/2.0,
				 dotsize, dotsize);
         surface.draw(dot);
      }
      else if (index == 1)
      {
         RectangularShape dot = new Ellipse2D.Double(p.x-dotsize/2.0, p.y-dotsize/2.0,
				 dotsize, dotsize);
         surface.fill(dot);
      }
      else if (index == 2)
      {
         RectangularShape dot = new Rectangle2D.Double(p.x-dotsize/2.0, p.y-dotsize/2.0,
				 dotsize, dotsize);
         surface.draw(dot);
      }
      else if (index == 3)
      {
         RectangularShape dot = new Rectangle2D.Double(p.x-dotsize/2.0, p.y-dotsize/2.0,
				 dotsize, dotsize);
         surface.fill(dot);
      }
      else if (index == 4)
      {
         surface.draw(new Line2D.Double(p.x-dotsize/2.0, p.y-dotsize/2.0,
                                          p.x+dotsize/2.0, p.y+dotsize/2.0));
         surface.draw(new Line2D.Double(p.x+dotsize/2.0, p.y-dotsize/2.0,
                                          p.x-dotsize/2.0, p.y+dotsize/2.0));
      }
      else if (index == 5)
      {
         surface.draw(new Line2D.Double(p.x, p.y-dotsize/2.0,
                                          p.x-dotsize/2.0, p.y+dotsize/2.0));
         surface.draw(new Line2D.Double(p.x, p.y-dotsize/2.0,
                                          p.x+dotsize/2.0, p.y+dotsize/2.0));
         surface.draw(new Line2D.Double(p.x-dotsize/2.0, p.y+dotsize/2.0,
                                          p.x+dotsize/2.0, p.y+dotsize/2.0));
      }
      else if (index == 6)
      {
         surface.draw(new Line2D.Double(p.x, p.y+dotsize/2.0,
                                          p.x-dotsize/2.0, p.y-dotsize/2.0));
         surface.draw(new Line2D.Double(p.x, p.y+dotsize/2.0,
                                          p.x+dotsize/2.0, p.y-dotsize/2.0));
         surface.draw(new Line2D.Double(p.x-dotsize/2.0, p.y-dotsize/2.0,
                                          p.x+dotsize/2.0, p.y-dotsize/2.0));
      }
      else if (index == 7)
      {
         surface.draw(new Line2D.Double(p.x, p.y-dotsize/2.0,
                                          p.x-dotsize/2.0-1, p.y));
         surface.draw(new Line2D.Double(p.x, p.y-dotsize/2.0,
                                          p.x+dotsize/2.0+1, p.y));
         surface.draw(new Line2D.Double(p.x, p.y+dotsize/2.0,
                                          p.x-dotsize/2.0-1, p.y));
         surface.draw(new Line2D.Double(p.x, p.y+dotsize/2.0,
                                          p.x+dotsize/2.0+1, p.y));
      }
      else
      {
         RectangularShape dot = new Ellipse2D.Double(p.x-dotsize/2.0, p.y-dotsize/2.0,
				 dotsize, dotsize);
         surface.draw(dot);
      }
	}
}
