import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;

class ParameterPanel extends JPanel
{
    Parameter param;
    JLabel header;
    JTextField valuefield, formulafield;
    ButtonGroup radiogroup;
    JPanel radiopanel;
    JRadioButton fixed, dependent;
    JPanel fixedpanel, dependentpanel, currentpanel;
    ButtonWatch bwatch;
    Calculator evaluator;

    public ParameterPanel(String ptype, Parameter p)
    {
		param = p;
		bwatch = new ButtonWatch();
		setPreferredSize(new Dimension(260,115));
		setBackground(AlgoLab.BACK_COLOR);
		header = new JLabel(ptype + " " + param.getName() + "=" + param.getDescription());
		header.setPreferredSize(new Dimension(250,20));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		add(header);

		radiopanel = new JPanel();
		radiopanel.setBorder(new LineBorder(Color.black));
		radiogroup = new ButtonGroup();
		radiopanel.setPreferredSize(new Dimension(250,30));
		radiopanel.setBackground(Color.orange);
		fixed = new JRadioButton("Fixed");
		fixed.addActionListener(bwatch);
		fixed.setBackground(Color.orange);
		fixed.setFont(fixed.getFont().deriveFont((float)10.0));
		radiogroup.add(fixed);
		radiopanel.add(fixed);

		dependent = new JRadioButton("Dependent");	
		dependent.addActionListener(bwatch);
		dependent.setBackground(Color.orange);
		dependent.setFont(dependent.getFont().deriveFont((float)10.0));
		radiogroup.add(dependent);
		radiopanel.add(dependent);
		add(radiopanel);
	
		fixedpanel = new JPanel();
		fixedpanel.setPreferredSize(new Dimension(255,48));
		fixedpanel.setBackground(Color.orange);
		fixedpanel.setBorder(new LineBorder(Color.black));
		JLabel label = new JLabel("value");
		label.setPreferredSize(new Dimension(250,15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		valuefield = new JTextField();
		valuefield.setPreferredSize(new Dimension(80,20));
		fixedpanel.add(label);
		fixedpanel.add(valuefield);
	
		dependentpanel = new JPanel();
		dependentpanel.setPreferredSize(new Dimension(255,48));
		dependentpanel.setBackground(Color.orange);
		dependentpanel.setBorder(new LineBorder(Color.black));
		label = new JLabel("formula");
		label.setPreferredSize(new Dimension(250,15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		formulafield = new JTextField();
		formulafield.setPreferredSize(new Dimension(80,20));
		dependentpanel.add(label);
		dependentpanel.add(formulafield);
		evaluator = new Calculator();

    }

    public long getValue()
    {
		return Long.parseLong(valuefield.getText());
    }

    public long getValue(String var, long value)
    {
		if (isFixed())
			return getValue();
		else  // dependent
		{
			evaluator.setExpression(formulafield.getText());
			evaluator.setVariable(var, value);
			return (long) evaluator.evalExp();
		}
    }

   public Parameter getParameter()
   {
      return param;
   }

    public void clear()
    {
		valuefield.setText("0");
		formulafield.setText("");
    }

    public boolean isFixed()
    {
		return fixed.isSelected();
    }

    public void setFixed()
    {
		if (currentpanel != null)
	    remove(currentpanel);
		currentpanel = fixedpanel;
		add(currentpanel);
		validate();
		repaint();
    }

    public boolean isDependent()
    {
		return dependent.isSelected();
    }

    public void setDependent()
    {
		if (currentpanel != null)
			remove(currentpanel);
		currentpanel = dependentpanel;
		add(currentpanel);
		validate();
		repaint();
    }

   public void suspend()
   {
		if (currentpanel != null)
			remove(currentpanel);
		remove(radiopanel);
		radiogroup.clearSelection();
		validate();
		repaint();
   }

   public void restore()
   {
      add(radiopanel);
      validate();
		repaint();
   }

	public boolean hasSelection()
	{
		return isDependent() || isFixed();
	}

    private class ButtonWatch implements ActionListener
    {
		public void actionPerformed(ActionEvent event)
		{
			Object source = event.getSource();
			if (source == fixed)
					setFixed();
			else if (source == dependent)
					setDependent();
		}
    }
}

