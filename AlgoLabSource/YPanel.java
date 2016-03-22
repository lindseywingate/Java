import java.awt.*;
import java.awt.event.*;
//import java.awt.image.*;
import java.awt.geom.*;
//import java.awt.geom.Point2D.Double;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;

class YPanel extends JPanel
{
   YScaleBar bar;
   int height;
   int width;
   YLegend legend;
   ChartPanel parent;
   LabelPanel lpanel;
   int ycode, casecode;
   Color backcolor;

   double [] miny;
   double [] maxy;
   double [] lowery;
   double [] uppery;

   Color lightgray = new Color(245,240,230);

   public YPanel(int x, int y, ChartPanel p, Color c)
   {
      super();
      parent = p;
      backcolor = c;
      setPreferredSize(new Dimension(x, y));
      setLayout(new BorderLayout());
      width = x;
      height = y;
      ycode = parent.STEPCOUNT;
      miny = new double[5];
      maxy = new double[5];
      lowery = new double[5];
      uppery = new double[5];

      lpanel = new LabelPanel("Step Count", width-50, height);
      add(lpanel, BorderLayout.WEST);
      bar = new YScaleBar(50, height, parent.margin);
      add(bar, BorderLayout.EAST);
   }


    public void reset()
    {
		maxy[parent.STEPCOUNT] = maxy[parent.SPACE] = maxy[parent.NUMERIC] = maxy[parent.TIME]
			= Double.MIN_VALUE;
		maxy[parent.BOOLEAN] = 102.0;
		miny[parent.STEPCOUNT] = miny[parent.SPACE] = miny[parent.NUMERIC] = miny[parent.TIME]
			= Double.MAX_VALUE;
		miny[parent.BOOLEAN] = 0.0;
		for (int i=0; i<5; i++)
		{
			uppery[i] = 100.0;
			lowery[i] = 0.0;
		}
    }

   public void setCaseCode(int code)
   {
		casecode = code;
		lpanel.setCaseButton(code);
		repaint();
   }
   
   public int getCaseCode()
   {
      return casecode;
   }

    public void setYCode(int code)
    {
		if (code == parent.UNKNOWN)
			return;
		ycode = code;
		if (ycode == parent.STEPCOUNT)
			legend.setText("Step Count");
		else if (ycode == parent.BOOLEAN)
			legend.setText("%Yes/No");
		else if (ycode == parent.SPACE)
			legend.setText("Space Units");
		else if (ycode == parent.NUMERIC)
			legend.setText("Object Size");
		else if (ycode == parent.TIME)
			legend.setText("Milliseconds");
		lpanel.setButton(code);
		repaint();
    }

    public int getYCode()
    {
		return ycode;
    }

   public double getUpperY()
   {
      return uppery[ycode];
   }

   public double getLowerY()
   {
      return lowery[ycode];
   }

   public double getYSpan()
   {
      return uppery[ycode] - lowery[ycode];
   }

    public void updateYScale(DataPoint p, int code)
    {   // updates min and max y values for new data point
		if (code == parent.UNKNOWN || code == parent.BOOLEAN)
			return;
		double yval = ((Long) p.y).doubleValue();
		if (yval > maxy[code])
		{
			maxy[code] = yval;
		}
		if (yval < miny[code])
		{
			miny[code] = yval;
		}
    }
    
    private void setUpperY(int maxval)
    {   // change upper y and redraw both scale and series
    	//System.out.println("Setting upper Y to "+maxval);
		uppery[ycode] = (double) Math.max(maxval, lowery[ycode]+10);
		bar.repaint();
		parent.redrawSeries();
    }
  
    private void setLowerY(int minval)
    {   // change upper y and redraw both scale and series
    	//System.out.println("Setting lower Y to "+minval);
		lowery[ycode] = (double) Math.max(0, Math.min(minval, uppery[ycode]-10));
		bar.repaint();
		parent.redrawSeries();
    }
    
    public void setYBounds(int low, int high, int code)
    {   // set y scale and redraw scale, but not series
		if (code == parent.UNKNOWN)
			return;
		uppery[code] = (double) high;
		lowery[code] = (double) low;
		bar.repaint();
    }

    public void setYBounds(int code)
    {   // sets uppery and lowery to match data, redraw scale, but not series
		if (code == parent.UNKNOWN)
			return;
		uppery[code] = Math.max(maxy[code], 10);
		lowery[code] = Math.min(miny[code], 0);
		bar.repaint();
    }
    
    ////////////////////////////// LabelPanel ////////////////////////////////////////////
   private class LabelPanel extends JPanel implements ActionListener
   {
      JRadioButton stepbtn, spacebtn, resultbtn, timebtn, avebtn, worstbtn, bestbtn;
      public LabelPanel(String label, int width, int height)
      {
	    super();
	    setPreferredSize(new Dimension(width, height));
	    setBackground(backcolor);

	    stepbtn = new JRadioButton("Steps");
	    stepbtn.addActionListener(this);
	    stepbtn.setBackground(backcolor);
	    stepbtn.setVerticalTextPosition(SwingConstants.TOP);
	    stepbtn.setHorizontalTextPosition(SwingConstants.CENTER);
	    stepbtn.setFont(stepbtn.getFont().deriveFont((float)10.0));
	    spacebtn = new JRadioButton("Space");
	    spacebtn.addActionListener(this);
	    spacebtn.setBackground(backcolor);
	    spacebtn.setVerticalTextPosition(SwingConstants.TOP);
	    spacebtn.setHorizontalTextPosition(SwingConstants.CENTER);
	    spacebtn.setFont(spacebtn.getFont().deriveFont((float)10.0));
	    resultbtn = new JRadioButton("Results");
	    resultbtn.addActionListener(this);
	    resultbtn.setBackground(backcolor);
	    resultbtn.setVerticalTextPosition(SwingConstants.TOP);
	    resultbtn.setHorizontalTextPosition(SwingConstants.CENTER);
	    resultbtn.setFont(resultbtn.getFont().deriveFont((float)10.0));
	    timebtn = new JRadioButton("Time");
	    timebtn.addActionListener(this);
	    timebtn.setBackground(backcolor);
	    timebtn.setVerticalTextPosition(SwingConstants.TOP);
	    timebtn.setHorizontalTextPosition(SwingConstants.CENTER);
	    timebtn.setFont(timebtn.getFont().deriveFont((float)10.0));

	    ButtonGroup bgroup = new ButtonGroup();
	    bgroup.add(spacebtn);
	    bgroup.add(stepbtn);
	    bgroup.add(resultbtn);
	    bgroup.add(timebtn);

	    add(stepbtn);
	    add(timebtn);
	    add(spacebtn);
	    add(resultbtn);
	    
	    JPanel spacer = new JPanel();
	    spacer.setBackground(backcolor);
	    spacer.setPreferredSize(new Dimension(width-2,50));
	    add(spacer);
	    legend = new YLegend(label, width-2, 10*label.length());
	    add(legend);
	    
	    worstbtn = new JRadioButton("Worst");
	    worstbtn.addActionListener(this);
	    worstbtn.setBackground(backcolor);
	    worstbtn.setVerticalTextPosition(SwingConstants.TOP);
	    worstbtn.setHorizontalTextPosition(SwingConstants.CENTER);
	    worstbtn.setFont(timebtn.getFont().deriveFont((float)10.0));
	    
	    avebtn = new JRadioButton("Ave");
	    avebtn.addActionListener(this);
	    avebtn.setBackground(backcolor);
	    avebtn.setVerticalTextPosition(SwingConstants.TOP);
	    avebtn.setHorizontalTextPosition(SwingConstants.CENTER);
	    avebtn.setFont(timebtn.getFont().deriveFont((float)10.0));
	    
	    bestbtn = new JRadioButton("Best");
	    bestbtn.addActionListener(this);
	    bestbtn.setBackground(backcolor);
	    bestbtn.setVerticalTextPosition(SwingConstants.TOP);
	    bestbtn.setHorizontalTextPosition(SwingConstants.CENTER);
	    bestbtn.setFont(timebtn.getFont().deriveFont((float)10.0));
	    
	    ButtonGroup agroup = new ButtonGroup();
	    agroup.add(worstbtn);
	    agroup.add(avebtn);
	    agroup.add(bestbtn);

         spacer = new JPanel();
         spacer.setBackground(backcolor);
         spacer.setPreferredSize(new Dimension(width-2,50));
         add(spacer);
         add(worstbtn);
         add(avebtn);
         add(bestbtn);
      }

	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		if (source == stepbtn)
			setYCode(parent.STEPCOUNT);
		else if (source == spacebtn)
			setYCode(parent.SPACE);
		else if (source == resultbtn)
			setYCode(parent.getResultType());
		else if (source == timebtn)
			setYCode(parent.TIME);
      else if (source == worstbtn)
			casecode = parent.WORST;
		else if (source == avebtn)
			casecode = parent.AVERAGE;
		else if (source == bestbtn)
         casecode = parent.BEST;
		parent.redrawSeries();
	}

	public void setButton(int code)
	{
		if (code == parent.STEPCOUNT)
			stepbtn.setSelected(true);
		else if (code == parent.SPACE)
			spacebtn.setSelected(true);
		else if (code == parent.TIME)
			timebtn.setSelected(true);
		else
			resultbtn.setSelected(true);
	}
	
      public void setCaseButton(int code)
      {
         if (code == parent.WORST)
            worstbtn.setSelected(true);
         else if (code == parent.AVERAGE)
            avebtn.setSelected(true);
         else if (code == parent.BEST)
            bestbtn.setSelected(true);
         else
            avebtn.setSelected(true);
      }
   }	/////////////////// end LabelPanel ///////////////////////////////////////////////////////////
    
    ////////////////////////////////// YLegend ///////////////////////////////////////////////////
    private class YLegend extends JComponent
    {
	String text = null;
	public YLegend(String s, int width, int height)
	{
	    super();
	    text = s;
	    setPreferredSize(new Dimension(width, height));
	}

	public void setText(String t)
	{
	    text = t;
	    //repaint();
	}

	public void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;
	    g2.translate(0,getHeight()-1);
		
	    g2.setPaint(Color.black);
	    g2.rotate(3*Math.PI/2);
	    g2.drawString(text,10,30);
	}
    }    /////////////////////// end YLegend ////////////////////////////////////////////////////////////
    
    ////////////////////////////////////// YScaleBar ///////////////////////////////////////////////////////
   private class YScaleBar extends JComponent
   {
		static final int UP = 1;
		static final int DOWN = -1;
		static final int TOP = 1;
		static final int BOTTOM = -1;
      int height, width, interval;
      double margin;

      public YScaleBar(int x, int y, double m)
      {
         super();
         width = x;
         height = y;
         margin = m;
         interval = (height - (int)margin - 10)/10;
         setPreferredSize(new Dimension(x, y));
         setOpaque(true);
         MouseWatch mwatch = new MouseWatch();
         addMouseListener(mwatch);
         addMouseMotionListener(mwatch);
      }
	
      public void paintComponent(Graphics g)
      {
         super.paintComponent(g);
         Graphics2D surface = (Graphics2D) g;
         surface.setPaint(lightgray);
         surface.fillRect(0,0,width,height);
         //double interval = oneTenth();
         surface.setPaint(Color.black);
         //double nexty = lowery;
         int nexty = height - (int) margin;
         double datay = parent.unscaleY((double)nexty);
         int tickwidth = 10;
         //System.err.println("Painting Y scale");
         surface.drawString(makeString((int)datay),0,5+nexty);
         surface.drawLine(width-tickwidth, nexty, width, nexty);
         //checkScaling(nexty);
         for (int i=1; i<=10; i++)
         {
            if (i%5 == 0)
               tickwidth = 10;
            else
               tickwidth = 5;
            nexty -= interval;
            datay = parent.unscaleY((double)nexty);
            surface.drawLine(width-tickwidth, nexty, width, nexty);
            surface.drawString(makeString((int)datay),0,5+nexty);
            //checkScaling(nexty);
         }
      }

		private void checkScaling(int pixely)
		{
			double datay = parent.unscaleY((double)pixely);
			int echo = (int) Math.round(parent.scaleY(datay));
			System.out.println(""+pixely+" => "+datay+" => "+echo);
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

      private void rescale (int side, int direction, int extent)
      {
      	//System.out.println("Rescaling--direction: "+direction+"  extent: "+extent);
         if (extent < 5)
            return;
         double change = (double)extent / (double)height;
         if (side == TOP)
         {
         	if (direction == UP)
            	setUpperY(Math.max(10,(int)(uppery[ycode] - uppery[ycode]*change)));
            else
            	setUpperY(Math.max(10,(int)(uppery[ycode] + uppery[ycode]*change)));
         }
         else
         {
            if (direction == UP)
            	setLowerY(Math.max(0, (int)(lowery[ycode] - extent)));
            else
            	setLowerY(Math.max(0, (int)(lowery[ycode] + extent)));
         }
      }

      private class MouseWatch extends MouseInputAdapter
      {

         private int cury, prevy, direction, side;

         public void mousePressed(MouseEvent e)
         {
            prevy = e.getY();
            if (prevy > height / 2)
            	side = BOTTOM;
            else
            	side = TOP;
         }

         public void mouseDragged(MouseEvent e)
         {
				cury = e.getY();
				if (cury > prevy)
				{
					direction = DOWN;
					rescale(side, direction, cury - prevy);
				}
				else
				{
					direction = UP;
            	rescale(side, direction, prevy - cury);
            }
         }

         public void mouseReleased(MouseEvent e)
         {
         	if (direction == DOWN)
					rescale(side, direction, Math.max(0, e.getY()-prevy));
				else
					rescale(side, direction, Math.max(0, prevy - e.getY()));
         }
      }
   }    ///////////////////////////////End YScaleBar//////////////////////////////////////////
}	///////////////////////////////End YPanel/////////////////////////////////////////
