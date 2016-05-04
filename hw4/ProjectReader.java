import java.io.*;
import java.util.*;

class ProjectReader
{
    TextReader reader;
    String pname;
    String ptype;
    InstanceGenerator generator;
    Parameter [] paramlist = new Parameter [AlgoLab.MAX_PARAMETER_COUNT];
    Parameter [] conslist = new Parameter [AlgoLab.MAX_CONSTRAINT_COUNT];
    int algcount = 0, paramcount = 0, conscount = 0;
    Algorithm [] alglist;

   public ProjectReader(File prjfile) throws FileNotFoundException
   {
      reader = new TextReader(prjfile);
      alglist = new Algorithm [AlgoLab.MAX_ALG_COUNT];
      processFile();
   }

    private void processFile()
    {
	String keyword;
   while (reader.moreData())
   {
      keyword = getString();
      if (keyword.equals("project"))
         pname = getQuotedString();
      else if (keyword.equals("parameter"))
      {
         Parameter p = new Parameter();
         p.name = getString();
         p.description = getQuotedString();
         addParameter(p);
      }
      else if (keyword.equals("algorithm"))
      {
         alglist[algcount] = loadAlgorithm(getString());
         algcount++;
      }
      else if (keyword.equals("constraint"))
      {
         Parameter target = new Parameter();
         target.name = getString();
         target.description = getQuotedString();
         addConstraint(target);
      }
	    else if (keyword.equals("generator"))
		loadGenerator(getString());

	    else
	    {
		System.err.println("Unknown keyword "+keyword);
		skipLine();
	    }
	}
    }

    private String getString()
    {
	String word = "";
	try
	{
	    word = reader.readString();
	}
	catch (IOException e)
	{
	    System.out.println("I/O Error!");
	}
	return word;
    }

    private String getQuotedString()
    {
	String word = "";
	try
	{
	    word = reader.readQuotedString();
	}
	catch (IOException e)
	{
	    System.out.println("I/O Error!");
	}
	return word;
    }

    private void skipLine()
    {
	try
	{
	    reader.skipLine();
	}
	catch (IOException e)
	{
	    return;
	}
    }

    private void loadGenerator(String gname)
    {
	try
	{
	    generator = (InstanceGenerator)Class.forName(gname).newInstance();
	    System.err.println("Loaded class name: " + gname);
	}
	catch (Exception e)
	{
	    System.err.println("Error loading class. "+e.toString());
	}
    }

   private Algorithm loadAlgorithm(String algname)
   {
      Algorithm alg;
      try
      {
         alg = (Algorithm) Class.forName(algname).newInstance();
         System.err.println("Loaded class name: " + algname);
      }
      catch (Exception e)
      {
         System.err.println("Error loading class. "+e.toString());
         return null;
      }
      return alg;
   }

   private void addParameter(Parameter p)
   {
      if (paramcount == paramlist.length)
         System.err.println("Too many parameters!");
      else
         paramlist[paramcount++] = p;
   }

   private void addConstraint(Parameter p)
   {
      if (conscount == conslist.length)
         System.err.println("Too many constraints!");
      else
         conslist[conscount++] = p;
   }

    public String getProjectName()
    {
	return pname;
    }
    
    public String getProjectType()
    {
	return ptype;
    }

    public InstanceGenerator getGenerator()
    {
	return generator;
    }
	
    public Algorithm getAlgorithm(int i)
    {
	return alglist[i];
    }

    public int getAlgCount()
    {
	return algcount;
    }

   public int getParameterCount()
   {
      return paramcount;
   }
   
   public int getConstraintCount()
   {
      return conscount;
   }

   public Parameter getParameter(int i)
   {
      if (i < 0 || i >= paramcount)
      {
         System.out.println("Invalid parameter index");
         return null;
      }
      else
         return paramlist[i];
    }

   public Parameter getConstraint(int i)
   {
      if (i < 0 || i >= conscount)
      {
         System.out.println("Invalid constraint index");
         return null;
      }
      else
         return conslist[i];
    }
}
