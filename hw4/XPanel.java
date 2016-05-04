import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;

class XPanel extends JPanel
{
   XScaleBar bar;
   XLegend legend;
   SeriesPanel seriespanel;
   int width, height, inset, chartwidth;
   int xcount;
   double lowerx, upperx, xspan;
   ChartPanel parent;

   public XPanel(int x, int y, int i, int c, ChartPanel p)
   {
      super();
      setPreferredSize(new Dimension(x, y));
      setBackground(AlgoLab.BACK_COLOR);
      FlowLayout layout = (FlowLayout) getLayout();
      layout.setAlignment(FlowLayout.LEFT);
      layout.setHgap(0);
      layout.setVgap(0);
      parent = p;
      width = x;
      height = y;
      inset = i;
      chartwidth = c;
      JLabel spacer = new JLabel();
      spacer.setPreferredSize(new Dimension(inset,30));
      bar = new XScaleBar(chartwidth, 30);
      add(spacer);
      add(bar);
      legend = new XLegend();
      legend.setPreferredSize(new Dimension(width,20));
      add(legend);
      seriespanel = new SeriesPanel(width,30);
      add(seriespanel);
   }

	public void initX(int count, long start, long end, String desc)
   {
      lowerx = start;
      upperx = end;
      xspan = upperx - lowerx;
      xcount = count;
      long increment = (long) Math.floor(xspan/(double)(xcount-1));
      bar.setScale(increment);
      legend.setText(desc);
      repaint();
   }

   public void reset()
	{
		long increment = (long) Math.floor(xspan/(double)(xcount-1));
      bar.setScale(increment);
	}

   public void initXRange(long x)
   {   // initializes lower and upper x values for custom x values
		double xval = (double) x;
      upperx = lowerx = xval;
		xspan = upperx - lowerx;
   }

   public void updateXRange(long x)
   {   // updates lower and upper x values for new data point
		double xval = (double) x;
		if (xval > upperx)
		{
			upperx = xval;
		}
		if (xval < lowerx)
		{
			lowerx = xval;
		}
		xspan = upperx - lowerx;
   }
/*
	public void setLegend(String s)
	{
      legend.setText(s);
      repaint();
	}

	public void setScale(int tickcount, int increment)
	{
      bar.setScale(tickcount, increment);
	}
*/	
   public void setBoolean()
   {
      seriespanel.clear();
      seriespanel.addSeries("% Yes", 2);
      seriespanel.addSeries("% No", 3);
      seriespanel.validate();
      seriespanel.repaint();
   }

   public void setNumeric(Series [] allsteps, int algcount)
   {
      seriespanel.clear();
      for (int i=0; i<algcount; i++)
         seriespanel.addSeries(allsteps[i].getName(), allsteps[i].getColorIndex());
      seriespanel.validate();
      seriespanel.repaint();
   }	
	
	public double getXSpan()
	{
	   return xspan;
	}
	
	public double getLowerX()
	{
	   return lowerx;
	}
	
	public double getUpperX()
	{
	   return upperx;
	}
//////////////////////////////////// XLegend //////////////////////////////////////////////////////
	private class XLegend extends JComponent
	{
      String text = "Size";

      public void setText(String s)
      {
         text = s;
         repaint();
      }

      public void paintComponent(Graphics g)
      {
         super.paintComponent(g);
         Graphics2D g2 = (Graphics2D) g;
         g2.setPaint(Color.black);
         g2.drawString(text,getWidth()/2,getHeight()-5);
      }
   }
/////////////////////////////////////////////// XScaleBar ///////////////////////////////////////////////////
   private class XScaleBar extends JComponent
   {
      int height, width;
      long increment;

      public XScaleBar(int x, int y)
      {
         super();
         width = x;
         height = y;
         setPreferredSize(new Dimension(x, y));
         setOpaque(true);
      }
	
      public void setScale(long inc)
      {
         increment = inc;
         repaint();
      }

      public void paintComponent(Graphics g)
      {
         super.paintComponent(g);
         String xstring;
         int xwidth;
         double nextx = lowerx;
         double scaledx;
         int ticklength = 5;
         Graphics2D surface = (Graphics2D) g;
         surface.setPaint(AlgoLab.LIGHT_GRAY);
         surface.fillRect(0,0,width,height);
         surface.setPaint(Color.black);
         //System.err.println();
         while (nextx <= upperx)
         {
            scaledx = parent.scaleX(nextx);
            //System.err.println(""+nextx);
            surface.drawLine((int)scaledx, ticklength,(int)scaledx, 0);
            xstring = makeString((int)nextx);
            xwidth = SwingUtilities.computeStringWidth(getFontMetrics(getFont()), xstring);
            surface.drawString(xstring,(int)(scaledx-xwidth/2), ticklength+15);
            nextx += increment;
         }    
      }

	   private String makeString(int val)
	   {
         //System.err.println(""+val);
         String instring = Integer.toString(val);
         int slen = instring.length();
         if (slen < 6)
            return blanks(5-slen)+instring;
         else
            return String.valueOf(instring.charAt(0))+'.'+
                  String.valueOf(instring.charAt(1))+'e'+
                     Integer.toString(slen-1);
      }

		private String blanks(int length)
		{
			String bstring = "";
			for (int i=0; i<length; i++)
				bstring += " ";
			return bstring;
      }
   }
   ////////////////////////////////////////////////  SeriesPanel  //////////////////////////////////////////////////
   private class SeriesPanel extends JPanel
   {
      int width, height;
      public SeriesPanel(int x, int y)
      {
         setPreferredSize(new Dimension(x, y));
         setBackground(AlgoLab.BACK_COLOR);
         width = x;
         height = y;
      }

      public void addSeries(String legend, int colorindex)
      {
         add(new SeriesLegend(legend, colorindex, height));
      }

      public void clear()
      {
         removeAll();
      }
//////////////////////////////////////////////  SeriesLegend  //////////////////////////////////////////////////////
      private class SeriesLegend extends JLabel
      {
         String legend;
         int colorindex;
         int height;

         public SeriesLegend(String s, int c, int h)
         {
            legend = s;
            colorindex = c;
            height = h;
            int swidth = SwingUtilities.computeStringWidth(getFontMetrics(getFont()), legend);
            setPreferredSize(new Dimension(swidth+60, height));
            setText(s);
            setForeground(ColorList.getColor(c));
            setHorizontalAlignment(SwingConstants.RIGHT);
         }

         public void paintComponent(Graphics g)
         {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            double dotsize = parent.avedotsize;
            int center = getHeight()/2;
            g2.setPaint(ColorList.getColor(colorindex));
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(20, center, 50, center);
            Point2D.Double p = new Point2D.Double(35, center);
            ColorList.makeDot(colorindex, p, dotsize, g2);
            //Ellipse2D.Double dot = new Ellipse2D.Double(35-dotsize/2.0,
            //            center-dotsize/2.0, dotsize, dotsize);
            //g2.fill(dot);
  
         }
      }
   }
}