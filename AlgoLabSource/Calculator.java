import java.util.*;
public class Calculator
{
   String expstring;
   int cursor;
   int backpos;
   HashMap<String,Double> memory;
	
   public Calculator()
   {
      memory = new HashMap<String,Double>();
   }
	
	public Calculator(HashMap<String,Double> mem)
	{
		memory = mem;
	}	
	
	public void setVariable(String key, double value)
	{
		memory.put(key, new Double(value));
	}

   public void setExpression(String estring)
   {
      expstring = estring;
      cursor = 0;
   }

	private char look()
	{
		if (cursor >= expstring.length())
			return '\0';
		else
			return expstring.charAt(cursor);
	}

	private char next()
	{
		if (cursor >= expstring.length())
			return '\0';
		else
		{
			char temp = expstring.charAt(cursor);
			cursor = cursor + 1;
			return temp;
		}
	}

   public double evalExp()
   {
   	//System.out.println("Evaluating "+expstring);
   	double result;
      if (expstring == null)
      {
         System.err.println("There is no expression to evaluate.");
         System.err.println("Call setExpression() before calling getResult().");
         return 0;
      }
      result = evalTerm();
      while (look() == '+' || look() == '-')
      {
         char operator = next();
         double term = evalTerm();
         if (operator == '+')
            result = result + term;
         else if (operator == '-')
         	result = result - term;
      }
      return result;
   }
	
   private double evalTerm()
   {
      double result = evalFactor();
      while (look() == '*' || look() == '/')
      {
         char operator = next();
         double factor = evalFactor();
         if (operator == '*')
            result = result * factor;
         else if (operator == '/')
         	result = result / factor;
      }
      //System.out.println("Got term "+result);
      return result;
   }	
	
   private double evalFactor()
   {
		double result = getOperand();
		if (look() == '^')
		{
			next();
			result = Math.pow(result, evalFactor());
		}
      return result;
   }
	
   private double getOperand()
   {
		if (look() == '(')
      {
      	next();
      	double result = evalExp();
      	if (next() != ')')
      		System.err.println("Expecting right paren");
      	return result;
      }
   	else if (look() == '-')
   	{
   		next();
   		return -evalExp();
   	}
		String opstring = "";
      while (look() != '\0' && !isOperator(look()))
         opstring = opstring + next();
      //System.out.println("Got operand "+opstring);
      try
      {
      	return Double.parseDouble(opstring);
      }
      catch (NumberFormatException e)
      {
         if (opstring.equals("sqrt"))
            return Math.sqrt(evalExp());
         else if (opstring.equals("lg"))
         {
         	double x = evalExp();
         	return Math.log10(x)/Math.log10(2.0);
         }
         else
      	   return memory.get(opstring);
      }
   }
   
   private boolean isOperator(char c)
   {
      String opset = "+-*/^()";
      return (opset.indexOf(c) >= 0);
   }
   
   public static void main(String [] args)
   {
   	Scanner scan = new Scanner(System.in);
   	Calculator calc = new Calculator();
   	String exp;
   	calc.setVariable("x", 5.0);
   	if (args.length == 1)
   	{
   		calc.setExpression(args[0]);
   		System.out.println(calc.evalExp());
   	}
   	else
   	{
   		System.out.print("? ");
   		exp = scan.nextLine();
   		while (!exp.equals("quit"))
   		{
   			calc.setExpression(exp);
   			System.out.print("=> " + calc.evalExp() + "\n? ");
   			exp = scan.nextLine();
   		}
   	}
   }
}