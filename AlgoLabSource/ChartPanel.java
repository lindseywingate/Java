import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
//import java.awt.geom.Point2D.Double;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;

class ChartPanel extends JPanel
{
    static final public int STEPCOUNT = 0;
    static final public int SPACE = 1;
    static final public int BOOLEAN = 2;
    static final public int NUMERIC = 3;
    static final public int TIME = 4;
    static final public int UNKNOWN = 5;

    static final public int WORST = 0;
    static final public int AVERAGE = 1;
    static final public int BEST = 2;

   final public double dotsize = 2.0;
   final public double avedotsize = 8.0;

    XPanel xpanel;
    YPanel ypanel;
    ScalePanel scalepanel;
    Chart chart;

    int width, chartwidth;
    int height, chartheight;
    int algcount;
    int algtype;
    Series allsteps [], allspace [], allresults [], alltime [];
    double margin = 20.0;
    double pixelwidth, pixelheight;
    boolean datapresent = false;
    String customxlegend;

	public ChartPanel(int w, int h)
	{
		super();
		width = w;
		height = h;
		chartwidth = width - 150;
		pixelwidth = ((double)chartwidth) - 2.0 * margin;
		chartheight = height - 80;
		pixelheight = chartheight - margin;
		algcount = 0;
		algtype = UNKNOWN;

		setLayout(new BorderLayout());
		chart = new Chart(chartwidth, chartheight);
		allsteps = new Series [AlgoLab.MAX_ALG_COUNT];
		allspace = new Series [AlgoLab.MAX_ALG_COUNT];
		allresults = new Series [AlgoLab.MAX_ALG_COUNT];
		alltime = new Series [AlgoLab.MAX_ALG_COUNT];

		setPreferredSize(new Dimension(width, height));

		xpanel = new XPanel(width,80,100,chartwidth, this);
		ypanel = new YPanel(100,chartheight, this, AlgoLab.BACK_COLOR);
		scalepanel = new ScalePanel(50, chartheight);

		add(xpanel, BorderLayout.SOUTH);
		add(ypanel, BorderLayout.WEST);
		add(chart, BorderLayout.CENTER);
		add(scalepanel, BorderLayout.EAST);
		clear();
	}

    public void clear()
    {
		chart.clear();
		xpanel.initX(10, 1, 10, "Size");
		initY();
		for (int i=0; i<algcount; i++)
		{
			allsteps[i].clear();
			allspace[i].clear();
			allresults[i].clear();
			alltime[i].clear();
		}
		algcount = 0;
		xpanel.setNumeric(allsteps, algcount);
		datapresent = false;
		customxlegend = null;
    }

    public void addAlgorithm(Algorithm alg, int atype)
    {
		//int atype = getAlgorithmType(alg);
		if (atype == UNKNOWN)
			return;
		else if (algcount == 0)
		{
			algtype = atype;
		}
//		else if (atype != algtype)
//		{
//			System.err.println("Algorithm type clash: "+alg.getName());
//			return;
//		}
		allsteps[algcount] = new Series(alg.getName(), algcount);
		allspace[algcount] = new Series(alg.getName(), algcount);
		allresults[algcount] = new Series(alg.getName(), algcount);
		alltime[algcount] = new Series(alg.getName(), algcount);
		algcount++;
    }
/*
	private int getAlgorithmType(Algorithm alg)
	{	// algorithm type is BOOLEAN=2 or NUMERIC=3
		int atype;
		if (alg instanceof DecisionAlgorithm)
			return BOOLEAN;
		else if (alg instanceof OptimizationAlgorithm)
			return NUMERIC;
		else
		{
			System.err.println("Unknown algorithm type for "+alg.getName());
			return UNKNOWN;
		}
	}
*/
    public int getResultType()
    {
		return algtype;
    }

   public void addPoints(int xindex, ExecutionRecord [] results)
   {
      DataPoint point;
      // the results from each algorithm should have the same x value
      long xvalue = results[0].xvalue;
      if (customxlegend != null)
      {
         if (datapresent)
            xpanel.updateXRange(xvalue);
         else
            xpanel.initXRange(xvalue);     
      }
		for (int i=0; i<algcount; i++)
		{
			point = new DataPoint(xvalue, new Long(results[i].stepcount));
			allsteps[i].addPoint(xindex, point);
			ypanel.updateYScale(point, STEPCOUNT);
			point = new DataPoint(xvalue, new Long(results[i].space));
			ypanel.updateYScale(point, SPACE);
			allspace[i].addPoint(xindex, point);
			point = new DataPoint(xvalue, results[i].result);
			allresults[i].addPoint(xindex, point);
			ypanel.updateYScale(point, algtype);
			point = new DataPoint(xvalue, new Long(results[i].time));
			//System.out.println("Adding time point: ("+x+","+results[i].time+")");
			alltime[i].addPoint(xindex, point);
			ypanel.updateYScale(point, TIME);
		}
		datapresent = true;
    }

    public void initY()
    {
		ypanel.setYCode(STEPCOUNT);
		ypanel.setCaseCode(AVERAGE);
		ypanel.reset();
		datapresent = false;
    }

   public void setXAxis(long start, long end, long increment, String desc)
   {
      int xcount = 0;
      for (long nextx=start; nextx<=end; nextx+=increment) xcount++;
      if (customxlegend != null)
         desc = customxlegend;
      xpanel.initX(xcount, start, end, desc);
      for (int i=0; i<algcount; i++)
      {
         allsteps[i].initialize(xcount, NUMERIC);
         allspace[i].initialize(xcount, NUMERIC);
         alltime[i].initialize(xcount, NUMERIC);
         allresults[i].initialize(xcount, algtype);
      }
   }

   public void drawSeries()
   {   // match y scale to data and draw the series
      if (!datapresent)
         return;
      ypanel.setYBounds(algtype);
      ypanel.setYBounds(STEPCOUNT);
      ypanel.setYBounds(SPACE);
      ypanel.setYBounds(TIME);
      xpanel.reset();
      redrawSeries();
   }

   public void redrawSeries()
   {   // draw the series without changing the y scale
      if (!datapresent)
         return;
      int code = ypanel.getYCode();
      if (code == BOOLEAN)
      {
         xpanel.setBoolean();
         drawBooleanSeries();
      }
      else
      {
         xpanel.setNumeric(allsteps, algcount);
         drawNumericSeries(code);
      }
   }

    public void drawBooleanSeries()
    {
	Point2D.Double prevpoint = null, avepoint;
	chart.clear();
	Iterator<PointSet> iterator = allresults[0].iterator();
	chart.surface.setPaint(AlgoLab.GREEN);
	while (iterator.hasNext())
	{
	    BooleanPointSet nextset = (BooleanPointSet) iterator.next();
	    avepoint = scalePoint(nextset.getPercentYes());
	    drawScaledPoint(2, avepoint);  // color index 2 is green
	    if (prevpoint != null)
		chart.surface.draw(new Line2D.Double(prevpoint, avepoint));
	    prevpoint = avepoint;
	    //System.err.println("line to "+avepoint.toString());
	}
	chart.surface.setPaint(Color.red);
	iterator = allresults[0].iterator();
	prevpoint = null;
	while (iterator.hasNext())
	{
	    BooleanPointSet nextset = (BooleanPointSet) iterator.next();
	    avepoint = scalePoint(nextset.getPercentNo());
	    drawScaledPoint(3, avepoint);  // color index 3 is red
	    if (prevpoint != null)
		chart.surface.draw(new Line2D.Double(prevpoint, avepoint));
	    prevpoint = avepoint;
	}
	chart.repaint();
    }

	public void drawNumericSeries(int code)
	{
		chart.clear();
		for (int i=0; i<algcount; i++)
			if (code == NUMERIC)
				drawNumericSeries(allresults[i]);
			else if (code == STEPCOUNT)
				drawNumericSeries(allsteps[i]);
			else if (code == SPACE)
				drawNumericSeries(allspace[i]);
			else if (code == TIME)
				drawNumericSeries(alltime[i]);
		chart.repaint();
	}

	private void drawNumericSeries(Series series)
	{
	   int casecode = ypanel.getCaseCode();
		Point2D.Double prevpoint = null, nextpoint;
		Iterator<PointSet> iterator = series.iterator();
		while (iterator.hasNext())
		{
			NumericPointSet nextset = (NumericPointSet) iterator.next();
			drawSet(nextset, series.getColorIndex());
			if (casecode == WORST)
			   nextpoint = scalePoint(nextset.getMax());
			else if (casecode == BEST)
			   nextpoint = scalePoint(nextset.getMin());
			else  //defaults to AVERAGE
			   nextpoint = scalePoint(nextset.getAve());
			//System.err.println("Next point "+avepoint.toString());
			if (prevpoint != null)
				chart.surface.draw(new Line2D.Double(prevpoint, nextpoint));
			prevpoint = nextpoint;
		}
    }

   private void drawScaledPoint(int colorindex, Point2D.Double p)
   {
      chart.surface.setPaint(ColorList.getColor(colorindex));
      ColorList.makeDot(colorindex, p, avedotsize, chart.surface);
      //RectangularShape dot = ColorList.getDot(colorindex, p, avedotsize);
      //chart.surface.fill(dot);
   }

   private void drawSet(NumericPointSet pset, int colorindex)
   {
      if (scalepanel.scatbox.isSelected())
         for (int i=0; i<pset.size(); i++)
         {
            DataPoint nextpoint = pset.get(i);
            render(ColorList.getColor(colorindex), nextpoint);
         }
      int casecode = ypanel.getCaseCode();
      Point2D.Double dpoint;
      if (casecode == WORST)
         dpoint = pset.getMax();
		else if (casecode == BEST)
			dpoint = pset.getMin();
		else  //defaults to AVERAGE
			dpoint = pset.getAve();
      //System.err.println(dpoint.toString());
      //drawScaledPoint(colorindex, scalePoint(dpoint));
      ColorList.makeDot(colorindex, scalePoint(dpoint), avedotsize, chart.surface);
   }

   private void toggleX()
   {
      xpanel.reset();
      drawSeries();                    
   }

   private void toggleY()
   {
      drawSeries();          
   }

    private Point2D.Double scalePoint(DataPoint p)
    {   // data point to screen point
		double x = scaleX((double)p.x);
		double y = scaleY(((Long)p.y).doubleValue());
		return new Point2D.Double(x,y);
    }

    private Point2D.Double scalePoint(Point2D.Double p)
    {   // data point to screen point
	double x = scaleX(p.x);
	double y = scaleY(p.y);
	return new Point2D.Double(x,y);
    }

   public double scaleX(double x)
   {
	   //return margin + xmultiplier * (x - xpanel.getLowerX());
	   if (x == 0)
         return margin;
      else if (scalepanel.xlog.isSelected())
         return margin + pixelwidth/Math.log(xpanel.getXSpan()) * Math.log(x-xpanel.getLowerX());
      else
         return margin + pixelwidth/xpanel.getXSpan() * (x - xpanel.getLowerX());
   }

   public double scaleY(double y)
   {
      double yspan = ypanel.getYSpan();
      double lowery = ypanel.getLowerY();
      if (y == 0)
         return pixelheight;
      else if (scalepanel.ylog.isSelected())
         return pixelheight-
               (pixelheight/Math.log(yspan))*Math.log(y-lowery);
      else
         return pixelheight - (pixelheight/yspan)*(y - lowery);
    }

   private Point2D.Double unscale(Point2D.Double p)
   {   // screen point to data point
      double x = unscaleX(p.getX());
      double y = unscaleY(p.getY());
      return new Point2D.Double(x, y);
   }

   private double unscaleX(double x)
   {
      if (scalepanel.xlog.isSelected())
         return Math.exp((x-margin)*(Math.log(xpanel.getXSpan())/pixelwidth))+xpanel.getLowerX();
      else
         return (x - margin)/(pixelwidth/xpanel.getXSpan()) + xpanel.getLowerX();
   }

   public double unscaleY(double y)
   {
      double yspan = ypanel.getYSpan();
      double lowery = ypanel.getLowerY();
      if (scalepanel.ylog.isSelected())
         return Math.exp((pixelheight-y)*(Math.log(yspan)/pixelheight))+lowery;
      else
         return (pixelheight - y)/(pixelheight/yspan) + lowery;
   }

   private void render(Color color, DataPoint p)
   {
      Point2D.Double sp = scalePoint(p);
      chart.surface.setPaint(color);
      Rectangle2D.Double dot = new Rectangle2D.Double(sp.x-dotsize/2.0, sp.y-dotsize/2.0,
				   dotsize, dotsize);
      chart.surface.fill(dot);
   }

   ///////////////////////////////////////////////// ScalePanel ///////////////////////////////////////////
   private class ScalePanel extends JPanel
   {
      JCheckBox xlog, ylog, scatbox;
      ButtonWatch bwatch;
      int width, height;
      public ScalePanel(int x, int y)
      {
         super();
         setPreferredSize(new Dimension(x, y));
         width = x;
         height = y;
         setBackground(AlgoLab.BACK_COLOR);
         bwatch = new ButtonWatch();
         xlog = new JCheckBox();
         xlog.setBackground(AlgoLab.BACK_COLOR);
         xlog.addActionListener(bwatch);
         ylog = new JCheckBox();
         ylog.setBackground(AlgoLab.BACK_COLOR);
         ylog.addActionListener(bwatch);
         add(new JLabel("     "));
         add(new JLabel("     "));
         add(new JLabel("Log Y"));
         add(ylog);
         add(new JLabel("     "));
         add(new JLabel("     "));
         add(new JLabel("     "));
         add(new JLabel("Log X"));
         add(xlog);

         JPanel spacer = new JPanel();
         spacer.setBackground(AlgoLab.BACK_COLOR);
         spacer.setPreferredSize(new Dimension(x-2,y/2));
         add(spacer);
         scatbox = new JCheckBox();
         scatbox.setBackground(AlgoLab.BACK_COLOR);
         scatbox.addActionListener(bwatch);
         JLabel scatlabel = new JLabel("Scatter");
         //scatlabel.setPreferredSize(new Dimension(x-2, 10));
         scatlabel.setFont(scatlabel.getFont().deriveFont((float)10.0));
         add(scatlabel);
         add(scatbox);   
         scatbox.setSelected(true);
      }

      private class ButtonWatch implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
            Object source = event.getSource();

            if (source == xlog)
               toggleX();
            else if (source == ylog)
               toggleY();
            else if (source == scatbox)
               drawSeries();
         }
      }
   }

   //////////////////////////////////// Chart ///////////////////////////////////////////////////
    private class Chart extends JComponent
    {
	int width;
	int height;
	BufferedImage display;
	Graphics2D surface;
	ImageWriter iwriter;
	Point2D.Double curpoint;

	public Chart(int w, int h)
	{
	    width = w;
	    height = h;
	    display = new BufferedImage(width, height, 
				    BufferedImage.TYPE_INT_RGB);
	    surface = display.createGraphics();
	    surface.setBackground(Color.white);
	    surface.setStroke(new BasicStroke(2));
	    curpoint = null;

	    addMouseListener(new MouseWatch());
	}

	public void paintComponent(Graphics g)
	{
	    super.paintComponent(g);	    
	    ((Graphics2D) g).drawImage(display, 0, 0, this);
	    if (curpoint != null)
			showPoint(g);
	}
	
	public void clear()
	{
	    surface.clearRect(0,0,width,height);
	    repaint();
	}

	private void showPoint(Graphics g)
	{
	    int x, dx, rx;
	    int y, dy, ry;
	    x = (int)curpoint.getX();
	    y = (int)curpoint.getY();
	    Point2D.Double datapoint = unscale(curpoint);
	    dx = (int)Math.round(datapoint.getX());
	    dy = (int)Math.round(datapoint.getY());
	    String pstring = "("+dx+","+dy+")";
	    int swidth = SwingUtilities.computeStringWidth(
				 getFontMetrics(getFont()), pstring);
	    if (y > height-10)
		ry = y;
	    else
		ry = y + 10;
	    if (x > width-swidth-10)
		rx = x - swidth;
	    else
		rx = x + 10;
	    g.drawString(pstring, rx, ry);
	}

	private void setPoint(int x, int y)
	{
	    curpoint = new Point2D.Double(x, y);
	    repaint();
	}

	private void erasePoint()
	{
	    curpoint = null;
	    repaint();
	}

	private class MouseWatch extends MouseInputAdapter
	{
	    public void mousePressed(MouseEvent e)
	    {
		if (e.getButton() > 1)
		    setPoint(e.getX(), e.getY());
	    }
	    public void mouseReleased(MouseEvent e)
	    {
		if (e.getButton() > 1)
		    erasePoint();
	    }
	}
    }

}
