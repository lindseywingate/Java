import java.util.*;
import java.io.*;

/**
 * This class is a wrapper class for BufferedReader that provides methods for
 * reading strings, integers, and fixed point numbers from a text file. It is
 * designed for convenient extraction of strings and numbers from a file where
 * white space characters (spaces, tabs, and newline characters) serve as
 * delimiters between data items.
 * @author Thomas E. O'Neil
 * @version 1.2
 */
public class TextReader
{
    /**
     * This field stores a reference to a BufferedReader object.  All reading
     * is implemented by calling the reader's methods.
     * @since 1.2
     */
    BufferedReader reader;

    /**
     * This field stores the last character that was read from the system's
     * input buffer.
     * @since 1.0
     */
    int prevchar;

    boolean juststarting;

    /**
     * This constructor creates a new TextReader for the file that is supplied
     * as a parameter.  
     * @param file a reference to a File object.
     * @throws FileNotFoundException if, for some reason, the file cannot be
     * opened for reading
     * @since 1.0
     */
    public TextReader(File file) throws FileNotFoundException
    {
	reader = new BufferedReader(new FileReader(file));
	prevchar = ' ';
	juststarting = true;
    }

    /**
     * This constructor creates a new TextReader for the InputStream object
     * (such as System.in) that is supplied as a parameter.
     * @param in a reference to an InputStream object.
     * @since 1.0
     */
    public TextReader(InputStream in)
    {
	reader = new BufferedReader(new InputStreamReader(in));
	prevchar = ' ';
	juststarting = true;
    }

    /**
     * This method reads the next character from the input file or stream.
     * @return the next character from the input stream.
     * @throws IOException if an I/O error occurs
     * @throws EOFException if there are no more characters in the stream.
     * @since 1.1
     */
    public char readChar() throws IOException, EOFException
    {
	if (juststarting)
	{
	    juststarting = false;
	    int nextchar = reader.read();
	    if (nextchar == -1)
	    {
		prevchar = -1;
		throw new EOFException();
	    }
	    else
		prevchar = nextchar;
	}
	char returnchar = (char) prevchar;
	prevchar = reader.read();
	return returnchar;
    }

    /**
     * This method returns the next line of characters from the input stream
     * as a string.  Its behavior is consistent with BufferedReader.readLine(),
     * which returns null when there are no more lines to read (rather than
     * throwing EOFException).
     * @see java.io.BufferedReader#readLine
     * @return the next line of characters from the input stream.
     * @throws IOException if an I/O error occurs
     * @since 1.1
     */
    public String readLine() throws IOException
    {
	String s;
	if (juststarting)
	{
	    juststarting = false;
	    s = reader.readLine();
	}
	else
	    s = prevchar + reader.readLine();
	if (s == null)
	    prevchar = -1;
	else
	    prevchar = reader.read();
	return s;
    }

    /**
     * This method reads the next string of non-transparent characters from the
     * input file or stream.  It skips over leading transparent characters
     * (such as spaces, tabs, or newlines),
     * reads a sequence of non-transparent characters, and stops reading when
     * it reaches the next transparent character.
     * @return a string of non-transparent characters.
     * @throws IOException if an I/O error occurs.
     * @throws EOFException if the end of file is reached before a 
     * non-transparent character can be found.
     * @since 1.0
     */
    public String readString() throws IOException, EOFException
    {
	int inchar;
	String instring = "";
	char [] charstring = new char[1];

	skipSpace();
	inchar = prevchar;
	while (!isTransparent(inchar) && inchar != -1)
	{
	    charstring[0] = (char)inchar;
	    instring = instring.concat(new String(charstring));
	    inchar = reader.read();
	}
	prevchar = inchar;
	return instring;
    }

    /**
     * This method reads the next string of characters from the
     * input file or stream that is enclosed in double quote marks.
     * It skips over leading transparent characters
     * (such as spaces, tabs, or newlines) till it finds a double quote,
     * reads a sequence of characters, and stops reading when
     * it reaches the second double quote character.
     * @return a string of characters.
     * @throws IOException if the next non-transparent character is not a 
     * double quote.
     * @throws EOFException if the end of file is reached before the closing
     * double quote character can be found.
     * @since 1.0
     */
    public String readQuotedString() throws IOException, EOFException
    {
	int inchar;
	String instring = "";
	char [] charstring = new char[1];

	skipSpace();
	inchar = prevchar;
	if (inchar == '"')
	    inchar = reader.read();
        else
	    throw new IOException();
    
	while (inchar != '"' && inchar != -1)
	{
	    charstring[0] = (char)inchar;
	    instring = instring.concat(new String(charstring));
	    inchar = reader.read();
	}
	if (inchar == '"')
	    inchar = reader.read();
	prevchar = inchar;
	if (inchar == -1)
	    throw new EOFException();
	return instring;	
    }

    /**
     * This method reads the next signed or unsigned integer from the
     * input file or stream.
     * It skips over leading transparent characters
     * (such as spaces, tabs, or newlines) till it finds a +, -, or digit,
     * reads a sequence of digits, and stops reading when
     * it reaches a character that is not a digit.
     * @return the integer value of a digit string.
     * @throws IOException if the next non-transparent character is not a
     * digit or + or -.
     * @throws EOFException if the end of file is reached before a digit can
     * be found.
     * @since 1.0
     */
    public int readInt() throws IOException, EOFException
    {
	int inchar;
	int polarity = 1;
	int value = 0;

	skipSpace();
	inchar = prevchar;
	if (inchar == '+')
	{
	    polarity = 1;
	    inchar = reader.read();
	}
	else if (inchar == '-')
	{
	    polarity = -1;
	    inchar =  reader.read();
	}
	else polarity = 1;
	if (!Character.isDigit((char) inchar))
	{
	    prevchar = inchar;
	    throw new IOException("Invalid character for integer");
	}
	while (Character.isDigit((char)inchar))
	{
	    value = value * 10 + Character.digit((char)inchar, 10);
	    inchar = reader.read();
	}
	prevchar = inchar;
	return value * polarity;
    }

    /**
     * This method reads the next signed or unsigned long integer from the
     * input file or stream.
     * It skips over leading transparent characters
     * (such as spaces, tabs, or newlines) till it finds a +, -, or digit,
     * reads a sequence of digits, and stops reading when
     * it reaches a character that is not a digit.
     * @return the long integer value of a digit string.
     * @throws IOException if the next non-transparent character is not a
     * digit or + or -.
     * @throws EOFException if the end of file is reached before a digit can
     * be found.
     * @since 1.0
     */
    public long readLong() throws IOException, EOFException
    {
	int inchar;
	int polarity = 1;
	long value = 0;

	skipSpace();
	inchar = prevchar;
	if (inchar == '+')
	{
	    polarity = 1;
	    inchar = reader.read();
	}
	else if (inchar == '-')
	{
	    polarity = -1;
	    inchar =  reader.read();
	}
	else polarity = 1;
	if (!Character.isDigit((char) inchar))
	{
	    prevchar = inchar;
	    throw new IOException("Invalid character for integer");
	}
	while (Character.isDigit((char)inchar))
	{
	    value = value * 10 + Character.digit((char)inchar, 10);
	    inchar = reader.read();
	}
	prevchar = inchar;
	return value * polarity;
    }

    /**
     * This method reads the next signed or unsigned fixed point number
     * from the input file or stream.
     * It skips over leading transparent characters
     * (such as spaces, tabs, or newlines) till it finds a +, -, or digit,
     * reads a sequence of digits optionally followed by a decimal point and
     * another string of digits, and stops reading when
     * it reaches a character that is not a digit.
     * @return the float value of a digit string.
     * @throws IOException if the next non-transparent character is not a
     * digit or + or -.
     * @throws EOFException if the end of file is reached before a digit can
     * be found.
     * @since 1.0
     */
    public float readFloat() throws IOException, EOFException
    {
	int inchar;
	int polarity = 1;
	float value = 0, place = (float) 1.0;

	skipSpace();
	inchar = prevchar;
	if (inchar == '+')
	{
	    polarity = 1;
	    inchar = reader.read();
	}
	else if (inchar == '-')
	{
	    polarity = -1;
	    inchar = reader.read();
	}
	else polarity = 1;
	if (!Character.isDigit((char) inchar))
	{
	    prevchar = inchar;
	    throw new IOException("Invalid character for integer");
	}
	while (Character.isDigit((char)inchar))
	{
	    value = value * 10 + Character.digit((char)inchar, 10);
	    inchar = reader.read();
	}
	if (inchar == '.')
	{
	    inchar = reader.read();
	    while (Character.isDigit((char)inchar))
	    {
		place = place * (float) 0.1;
		value = value + place * Character.digit((char)inchar, 10);
		inchar = reader.read();
	    }
	}
	prevchar = inchar;
	return value * polarity;
    }

    /**
     * This method reads skips over non-transparent characters
     * till it finds a transparent character, effectively skipping the current
     * data item in the input file.
     * @throws IOException if an I/O error occurs.
     * @since 1.0
     */
    public void skipItem() throws IOException
    {// skip to next white space
	juststarting = false;
	int inchar = prevchar;
	while (!isTransparent(inchar))
	    inchar = reader.read();
	prevchar = inchar;
    }

    /**
     * This method reads and discards characters until it finds a
     * new-line character.  It has no effect if the current character is
     * already a new-line character.  It effectively skips the remainder of 
     * the current line of input.
     * @throws IOException if an I/O error occurs.
     * @since 1.1
     */
    public void skipLine() throws IOException
    {
	juststarting = false;
	int inchar = prevchar;
	while (inchar != '\n')
	    inchar = reader.read();
	prevchar = reader.read();
    }

    /**
     * This method reads over transparent characters
     * till it finds a non-transparent character, or until it reaches the end
     * of the file.
     * @throws IOException if an I/O error occurs.
     * @throws EOFException if the end of the file is encountered.
     * @since 1.0
     */
    public void skipSpace() throws IOException, EOFException
    {   // skip over white space
	juststarting = false;
	int inchar = prevchar;
	while (isTransparent(inchar))
	    inchar = reader.read();
	prevchar = inchar;
	if (inchar == -1)
	    throw new EOFException();
    }

    /**
     * This method determines whether its integer parameter represents a
     * transparent character.  The transparent characters are space, newline
     * (\n), tab (\t), carriage return (\r), and formfeed (\f).
     * @param c a character (passed in as type int).
     * @return true if the input parameter is a transparent character,
     * false otherwise.
     * @since 1.0
     */
    protected boolean isTransparent(int c)
    {
	return (c==' ' || c=='\n' || c=='\t' || c=='\r'|| c=='\f');
    }

    /**
     * This method tells whether the file contains any more non-white
     * characters.  White space is skipped as the cursor is advanced to
     * the next non-white character.
     * @return true if the file contains more non-white characters.
     * @since 1.2
     */
    public boolean moreData()
    {
	if (prevchar == -1)
	    return false;
	if (!isTransparent(prevchar))
	    return true;
	int inchar = prevchar;
	try {
	    juststarting = false;
	    while (isTransparent(inchar))
		inchar = reader.read();
	    prevchar = inchar;
	}
	catch (IOException e)
	{
	    return false;
	}
	return (prevchar != -1);
    }

    /**
     * This method tells whether the end of the file has been detected.
     * @return true if a previous read operation hit the end of the file.
     * @since 1.0
     */
    public boolean endOfFile()
    {
	return (prevchar == -1);
    }
}

