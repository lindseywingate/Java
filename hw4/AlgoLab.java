import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.imageio.stream.*;

class AlgoLab extends JFrame
{
    static public final int MAX_ALG_COUNT = 8;
    static public final int MAX_PARAMETER_COUNT = 6;
    static public final int MAX_CONSTRAINT_COUNT = 6;
    static public final Color BACK_COLOR = new Color(255,235,175);
    static public final Color LIGHT_GRAY = new Color(245,240,230);
    static public final Color GREEN = new Color(80,190,0);
    static public final Color BROWN = new Color(140, 8, 0);
    static public final Color ORANGE = new Color(255, 150, 0);
    static public final Color PURPLE = new Color(160, 10, 255);
    static public final Color DARKBLUE = new Color(70, 70, 130);
    ButtonWatch bwatch;
    ControlPanel controlpanel;
    ChartPanel displaypanel;
    AlgoPanel algopanel;
    RunPanel runpanel;
    JButton clearbutton, createbutton, gobutton, morebutton, capturebutton;
    int parametercount = 0, constraintcount = 0;
    JTabbedPane parameterpane;
    JTabbedPane constraintpane;
    XVarPanel xbox;
    ParameterPanel curvarpanel;
    int curparamindex;
    //ParameterPanel [] paramlist;
    //ParameterPanel [] conslist;
    
    //ParameterPanel param1, param2, target;
    //Parameter p1, p2, p3;
    JTextField trialfield;
    DefaultListModel algolist;
    JLabel totlabel, clashlabel;
    int totaltrials = 0;
    int totalclash = 0;
    int totalruns = 0;
    Random seedgenerator;
    InstanceGenerator generator;
    ImageWriter framewriter;
    BufferedImage snapshot;
    Graphics2D snapshotgraphics;
    String projectname;
    String prjfilename = "AlgoLab.prj";
    ProjectReader preader;


    public AlgoLab()
    {
	super("Algorithms Laboratory");
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	bwatch = new ButtonWatch();
	algolist = new DefaultListModel();
	initialize();
	displaypanel = new ChartPanel(1250,630);
	controlpanel = new ControlPanel(1250,150, this);
	seedgenerator = new Random();


	JPanel statuspanel = new JPanel();
	statuspanel.setBackground(BACK_COLOR);
	statuspanel.setPreferredSize(new Dimension(1250, 30));
	FlowLayout layout = (FlowLayout) statuspanel.getLayout();
	layout.setAlignment(FlowLayout.RIGHT);
	capturebutton = new JButton("Capture");
	capturebutton.addActionListener(bwatch);
	capturebutton.setPreferredSize(new Dimension(100,20));
	clashlabel = new JLabel("Consistency: 100%");
	clashlabel.setPreferredSize(new Dimension(140,20));
	totlabel = new JLabel("Total trials: 0");
	totlabel.setPreferredSize(new Dimension(140,20));
	statuspanel.add(totlabel);
	statuspanel.add(clashlabel);
	statuspanel.add(capturebutton);

	Iterator itor = ImageIO.getImageWritersByFormatName("jpg");
	framewriter = (ImageWriter) itor.next();

	getContentPane().add(controlpanel, BorderLayout.NORTH);
        getContentPane().add(displaypanel, BorderLayout.CENTER);
	getContentPane().add(statuspanel, BorderLayout.SOUTH);

	initAlgorithms();
	setVarParam(0); // first parameter is default for X-axis variable
	resetTrials();
	//param1.clear();
	//param2.clear();
	//displaypanel.clear();
    }

   public void capture()
   {
      snapshot = new BufferedImage(getWidth(), getHeight(),
         BufferedImage.TYPE_INT_RGB);
      snapshotgraphics = snapshot.createGraphics();
      paint(snapshotgraphics);
      paintComponents(snapshotgraphics);
      File ifile = new File("jframe.jpg");
      FileImageOutputStream istream = null;

      try
      {
         istream = new FileImageOutputStream(ifile);
         framewriter.setOutput(istream);
	    //framewriter.setOutput(ImageIO.createImageOutputStream(ifile));
      }
      catch (IOException e)
      {
         System.err.println("Can't open image file");
      }

      try {
         framewriter.write(snapshot);
         istream.close();
      }
      catch (IOException iox)
      {
         System.err.println("Can't write to image file");
      }
   }

   private void updateTrials(int increment, int runs, int clash)
   {
      totaltrials = totaltrials + increment;
      totalclash = totalclash + clash;
      totalruns = totalruns + runs;
      int consistency = (int)(100.0 * (float)(totalruns - totalclash)
                              / (float) totalruns);
      totlabel.setText("Total trials: " + totaltrials);
      clashlabel.setText("Consistency: "+ consistency + "%");
   }

   private void resetTrials()
   {
      totaltrials = 0;
      totalruns = 0;
      totalclash = 0;
      totlabel.setText("Total trials: 0");
      clashlabel.setText("Consistency: 100%");
   }

	private void initialize()
	{  // read the .prj file
		try {
			preader = new ProjectReader(new File(prjfilename));
			projectname = preader.getProjectName();
			//projecttype = preader.getProjectType();
			setTitle(getTitle()+" -- "+ projectname);
			generator = preader.getGenerator();
			parametercount = preader.getParameterCount();
			constraintcount = preader.getConstraintCount();
			//paramlist = new ParameterPanel [parametercount];
			//conslist = new ParameterPanel [constraintcount];
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Can't find project file: "+prjfilename);
		}
	}

	private void initAlgorithms()
	{
	   int atype;
	   Algorithm nextalg;
		for (int i=0; i<preader.getAlgCount(); i++)
		{
		   nextalg = preader.getAlgorithm(i);
		   atype = getAlgorithmType(nextalg);
			algolist.addElement(nextalg);
         if (i == 0)
            algopanel.setAlgType(atype);           
         else if (atype != algopanel.getAlgType())
         {
            System.err.println("Algorithm type clash: "+nextalg.getName());
            return;
         }
		}
		algopanel.resetActive();
	}
	
	private int getAlgorithmType(Algorithm alg)
	{	// algorithm type is BOOLEAN=2 or NUMERIC=3
		if (alg instanceof DecisionAlgorithm)
			return ChartPanel.BOOLEAN;
		else if (alg instanceof OptimizationAlgorithm)
			return ChartPanel.NUMERIC;
		else
		{
			System.err.println("Unknown algorithm type for "+alg.getName());
			return ChartPanel.UNKNOWN;
		}
	}
/*
	private void addAlgorithm(Algorithm newalg)
	{
		algolist.addElement(newalg);
		algopanel.resetActive();
	}
*/
	private void clear()
	{
		displaypanel.clear();
		resetTrials();
	}

   public void setVarParam(int index)
   {
      //System.err.println("Selected index: "+index);
      enable(curparamindex);
      curparamindex = index;
      if (index >= parametercount)
      {
         curvarpanel = (ParameterPanel) constraintpane.getComponentAt(index-parametercount);
         constraintpane.setEnabledAt(index-parametercount, false);
      }
      else
      {
         curvarpanel = (ParameterPanel) parameterpane.getComponentAt(index);
         parameterpane.setEnabledAt(index, false);
      }
      curvarpanel.suspend();
      xbox.setParameter(curvarpanel.getParameter());
   }

   private void enable(int index)
   {
      ParameterPanel panel;
      if (index >= parametercount)
      {
         constraintpane.setEnabledAt(index-parametercount, true);
         panel = (ParameterPanel) constraintpane.getComponentAt(index-parametercount);
      }
      else
      {
         parameterpane.setEnabledAt(index, true);
         panel = (ParameterPanel) parameterpane.getComponentAt(index);
      }
      panel.restore();
   }

   private void run(boolean startflag)
   {
      long xstart, xend, xinc;
      long seed;
      String xdesc;
      int reps = Integer.parseInt(trialfield.getText());
      int clashcount = 0;
      int runcount = 0;

      xstart = xbox.getLowValue();
      xend = xbox.getHighValue();
      xinc = xbox.getIncrement();
      xdesc = xbox.getParameter().getDescription();
      if (startflag)
      {
         displaypanel.clear();
         algopanel.setUpAlgs();
         displaypanel.setXAxis(xstart, xend, xinc, xdesc);
         displaypanel.initY();
         resetTrials();
      }
	   for (int i=0; i<reps; i++)
      {
         int xindex = 0;
         for (long x=xstart; x<=xend; x+=xinc)
         {
            seed = seedgenerator.nextLong();
            if (!runAlgs(xindex, x, seed))
               clashcount++;
            runcount++;
            xindex++;
         }
      }
      updateTrials(reps, runcount, clashcount);
      if (startflag)
         displaypanel.drawSeries();
      else
         displaypanel.redrawSeries();
   }

	private long [] getParamValues(long xval)
	{	// the instance is generated using the parameters (not the constraints)
      long [] returnvals = new long [parametercount];
      for (int i=0; i<parametercount; i++)
      {
         ParameterPanel nextpanel = (ParameterPanel) parameterpane.getComponentAt(i);
         //System.out.println("Retrieving value for " + nextpanel.getParameter().getName());
         if (!parameterpane.isEnabledAt(i))
            returnvals[i] = xval;
         else
            returnvals[i] = nextpanel.getValue(xbox.getParameter().getName(), xval);
      }
		return returnvals;
	}

	private void setConstraintValue(int index, long xval, Algorithm alg)
	{	
      ParameterPanel conspanel = (ParameterPanel) constraintpane.getComponentAt(index);
      //System.out.println("Retrieving value for " + conspanel.getParameter().getName());
      if (!constraintpane.isEnabledAt(index))
         alg.setConstraint(index, xval);
      else if (conspanel.hasSelection())
         alg.setConstraint(index, conspanel.getValue(xbox.getParameter().getName(), xval));
      else // set default value
         alg.setConstraint(index);
	}

    private boolean runAlgs(int xindex, long x, long seed)
    {
    	Long starttime, seconds;
		ExecutionRecord [] results = new ExecutionRecord[algopanel.getActiveCount()];
		Object prevresult = null;
		Algorithm nextalg = null;
		int activecount = 0;
		boolean consistent = true;
		Object instance;

		for (int i=0; i<algopanel.getAlgCount(); i++)
		{
			if (algopanel.isActive(i))
			{
				nextalg = (Algorithm)algolist.get(i);
				long [] params = getParamValues(x);
				nextalg.setInstance(generator.makeInstance(seed, params));
				for (int j=0; j<constraintcount; j++)
				   setConstraintValue(j, x, nextalg);
				starttime = System.currentTimeMillis();
				nextalg.run();
				seconds = (System.currentTimeMillis()-starttime);

				results[activecount] = new ExecutionRecord(seed,
						    nextalg.getResult(),
						    nextalg.getSpace(),
						    nextalg.getStepCount(),
						    x,
						    seconds);
				if (activecount > 0 && 
							!(results[activecount].result).equals(prevresult))
				{
					System.err.println("***ALGORITHM CLASH***  instance parameters: " + seed + " "
					                   + params[0] + " " + params[1]);
					consistent = false;
				}
				prevresult = results[activecount].result;
				//System.err.println(results[activecount].toString());
				activecount++;
			}
		}
		displaypanel.addPoints(xindex, results);
		return consistent;
    }

//  ControlPanel  //
    private class ControlPanel extends JPanel
    {
      AlgoLab parent;
		public ControlPanel(int x, int y, AlgoLab p)
		{
			super();
			parent = p;
			setPreferredSize(new Dimension(x, y));
			setBackground(BACK_COLOR);
			xbox = new XVarPanel(parent, preader);
			parameterpane = new JTabbedPane();
         constraintpane = new JTabbedPane();
			Parameter next;
			for (int i=0; i<parametercount; i++)
			{
			   next = preader.getParameter(i);
			   parameterpane.add(next.name, new ParameterPanel("Parameter", next));
			   //System.out.println("Adding parameter "+next.name);
			}
			for (int i=0; i<constraintcount; i++)
			{
			   next = preader.getConstraint(i);
			   constraintpane.add(next.name, new ParameterPanel("Constraint", next));
			   //System.out.println("Adding constraint "+next.name);
			}
         add(xbox);
			add(parameterpane);
			if (constraintcount > 0)
			{
				add(constraintpane);
			}
			algopanel = new AlgoPanel(160,y-8);
			add(algopanel);
			runpanel = new RunPanel(150, y-8);
			add(runpanel);
		}
    }
//////////////////////////////// AlgoPanel  ///////////////////////////////////////////
    private class AlgoPanel extends JPanel
    {
	int width, height;
	JList algobox;
	ListWatch lwatch;
	boolean listening;
	boolean [] active;
	int algtype;
	int activecount = 0;

	public AlgoPanel(int w, int h)
	{
	    super();
	    width = w;
	    height = h;
	    setPreferredSize(new Dimension(w, h));
	    setBackground(Color.orange);
	    setBorder(new LineBorder(Color.black));
	    lwatch = new ListWatch();
	    JLabel header = new JLabel("Algorithms");
	    header.setHorizontalAlignment(SwingConstants.CENTER);
	    header.setPreferredSize(new Dimension(width-5, 20));
	    add(header);

	    algobox = new JList(algolist);
	    algobox.setCellRenderer(new AlgoLabel());
	    algobox.setBackground(Color.white);
	    algobox.setSelectionMode(
	    		   ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    algobox.addListSelectionListener(lwatch);
	    JScrollPane algopane = new JScrollPane(algobox);
	    algopane.setPreferredSize(new Dimension(width-5, height-35));
	    add(algopane);
	    listening = true;
	}

	public void resetActive()
	{
	    active = new boolean[algolist.size()];
	    for (int i=0; i<active.length; i++)
			active[i] = false;
	}

   public void setAlgType(int code)
   {
      algtype = code;
   }

   public int getAlgType()
   {
      return algtype;
   }

	public void setUpAlgs()
	{
	    int i;
	    displaypanel.clear();
	    for (i=0; i<algolist.size(); i++)
			if (active[i])
					displaypanel.addAlgorithm((Algorithm)algolist.get(i), algtype);
	}

	public int getActiveCount()
	{
	    return activecount;
	}

	public int getAlgCount()
	{
	    return algolist.size();
	}

	public boolean isActive(int index)
	{
	    return active[index];
	}

	private class ListWatch implements ListSelectionListener
	{
	    public void valueChanged(ListSelectionEvent event)
	    {
		if (algobox.getValueIsAdjusting()||algobox.isSelectionEmpty())
		    return;

		int current = algobox.getSelectedIndex();
		if (active[current])
		{
		    active[current] = false;
		    activecount--;
		}
		else
		{
		    active[current] = true;
		    activecount++;
		}
		algobox.clearSelection();
	    }
	}

	private class AlgoLabel implements ListCellRenderer
	{
	    public Component getListCellRendererComponent(JList list,
		    Object value, int index, boolean isSelected,
		    boolean hasCellFocus)
	    {
		JLabel label = new JLabel();
		Algorithm alg = (Algorithm)algolist.get(index);
		label.setOpaque(true);
		label.setFont(label.getFont().deriveFont((float) 10.0));
		if (active[index])
		{
		    label.setText("+"+alg.getName());
		    if (algobox.getSelectedIndex() == index)
			label.setBackground(BACK_COLOR);
		    else
		    label.setBackground(Color.white);
		}
		else
		{
		    label.setText("-"+alg.getName());
		    if (algobox.getSelectedIndex() == index)
			label.setBackground(BACK_COLOR);
		    else
		    label.setBackground(Color.lightGray);
		}
		return label;
	    }			  
	}
    }
///////////////////////////////////////   RunPanel  ///////////////////////////////////////////
	private class RunPanel extends JPanel
	{
	int width, height;
	public RunPanel(int w, int h)
	{
	    super();
	    width = w;
	    height = h;
	    setPreferredSize(new Dimension(w, h));
	    setBackground(Color.orange);
	    setBorder(new LineBorder(Color.black));
	    JLabel label = new JLabel("trials: ");
	    add(label);    
	    trialfield = new JTextField();
	    trialfield.setPreferredSize(new Dimension(80,20));
	    add(trialfield);
	    gobutton = new JButton("Start");
	    gobutton.addActionListener(bwatch);
	    gobutton.setPreferredSize(new Dimension(100,20));
	    add(gobutton);
	    morebutton = new JButton("Continue");
	    morebutton.addActionListener(bwatch);
	    morebutton.setPreferredSize(new Dimension(100,20));
	    add(morebutton);
	    clearbutton = new JButton("Clear");
	    clearbutton.addActionListener(bwatch);
	    clearbutton.setPreferredSize(new Dimension(100,20));
	    add(clearbutton);
	}
	}

    private class ButtonWatch implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    Object source = event.getSource();

	    if (source == clearbutton)
		clear();
	    else if (source == gobutton)
		run(true);
	    else if (source == morebutton)
		run(false);
	    else if (source == capturebutton)
		capture();
	}
    }

    public static void main(String[] args)
    {
	    AlgoLab labbox = new AlgoLab();
	    labbox.pack();
	    labbox.setVisible(true);
    }
}












