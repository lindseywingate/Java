import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;

class XVarPanel extends JPanel
{
   AlgoLab parent;
   Parameter param;
    JLabel header;
    JTextField lowfield, highfield, incrementfield;
    JPanel combopanel;
    JPanel variablepanel;
    JComboBox parbox;
    ItemWatch iwatch;
    Calculator evaluator;
    boolean initializing;

    public XVarPanel(AlgoLab p, ProjectReader reader)
    {
      parent = p;
		int parametercount = reader.getParameterCount();
		int constraintcount = reader.getConstraintCount();
		iwatch = new ItemWatch();
		setPreferredSize(new Dimension(260,115));
		setBackground(AlgoLab.BACK_COLOR);
		header = new JLabel("X-Axis Value");
		header.setPreferredSize(new Dimension(250,20));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		add(header);

		combopanel = new JPanel();
		combopanel.setBorder(new LineBorder(Color.black));
		combopanel.setPreferredSize(new Dimension(250,30));
		combopanel.setBackground(Color.orange);
      parbox = new JComboBox();
      parbox.addItemListener(iwatch);
      initializing = true;
      for (int i=0; i<parametercount; i++)
         parbox.addItem(reader.getParameter(i).getName());
      for (int i=0; i<constraintcount; i++)
         parbox.addItem(reader.getConstraint(i).getName());
      initializing = false;
      combopanel.add(parbox);
      add(combopanel);

		variablepanel = new JPanel();
		variablepanel.setPreferredSize(new Dimension(255,48));
		variablepanel.setBackground(Color.orange);
		variablepanel.setBorder(new LineBorder(Color.black));
		JLabel label = new JLabel("low");
		label.setPreferredSize(new Dimension(78,15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		variablepanel.add(label);
		label = new JLabel("high");
		label.setPreferredSize(new Dimension(78,15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		variablepanel.add(label);
		label = new JLabel("increment");
		label.setPreferredSize(new Dimension(78,15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		variablepanel.add(label);
		lowfield = new JTextField();
		lowfield.setPreferredSize(new Dimension(78,20));
		variablepanel.add(lowfield);
		highfield = new JTextField();
		highfield.setPreferredSize(new Dimension(78,20));
		variablepanel.add(highfield);
		incrementfield = new JTextField();
		incrementfield.setPreferredSize(new Dimension(78,20));
		variablepanel.add(incrementfield);
		add(variablepanel);
	    }

    public long getLowValue()
    {
        return Long.parseLong(lowfield.getText());
    }

    public long getHighValue()
    {
		return Long.parseLong(highfield.getText());
    }

    public long getIncrement()
    {
		return Long.parseLong(incrementfield.getText());
    }

    public Parameter getParameter()
    {
		return param;
    }
    
    public void setParameter(Parameter p)
    {
      param = p;
    }

    public void clear()
    {
		lowfield.setText("0");
		highfield.setText("0");
		incrementfield.setText("0");
    }

    private class ItemWatch implements ItemListener
    {
		public void itemStateChanged(ItemEvent event)
		{
			Object source = event.getSource();
			if (source == parbox && !initializing)
			{
			   //System.err.println("State change: "+event.getStateChange());
			   if (event.getStateChange() == ItemEvent.SELECTED)
				   parent.setVarParam(parbox.getSelectedIndex());
			}
		}
    }
}

