import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 *	Prompt.java - Uses BufferedReader.
 *	Provides utilities for user input.  This enhances the BufferedReader
 *	class so our programs can recover from "bad" input, and also provides
 *	a way to limit numerical input to a range of values.
 *
 *	The advantages of BufferedReader are speed, synchronization, and piping
 *	data in Linux.
 *
 *	@author	Amogh Upadhyaya
 *	@since	September 10, 2020
 */

public class Prompt
{
	// BufferedReader variables
	private static InputStreamReader streamReader = new InputStreamReader(System.in);
	private static BufferedReader bufReader = new BufferedReader(streamReader);

	/**
	 *	Prompts user for string of characters and returns the string.
	 *	@param ask  The prompt line
	 *	@return  	The string input
	 */
	public static String getString (String ask)
	{
		System.out.print(ask + " -> ");
		String input = "";
		try{
			input = bufReader.readLine();
		}
		catch(IOException e)
		{
			System.err.println("ERROR: BufferedReader could not read line");
		}
		return input;
	}
	
	/**
	 *	Prompts the user for a character and returns the character.
	 *	@param ask  The prompt line
	 *	@return  	The character input
	 */
	public static char getChar (String ask)
	{
		System.out.print(ask + " -> ");
		char input = ' ';
		try
		{ 
			input = (char)bufReader.read();
		}
		catch(IOException e)
		{
			System.err.println("ERROR: BufferedReader could not read character");
		}
		return input;
	}
	
	/**
	 *	Prompts the user for an integer and returns the integer.
	 *	@param ask  The prompt line
	 *	@return  	The integer input
	 */
	public static int getInt (String ask)
	{
		boolean badInput = false;
		String input = new String("");
		int value = 0;
		do{
			badInput = false;
			input = getString(ask);
			try
			{
				value = Integer.parseInt(input);
			}
			catch(NumberFormatException e)
			{
				badInput = true;
			}
		} while(badInput);
		return value;
	}
	
	/**
	 *	Prompts the user for an integer using a range of min to max,
	 *	and returns the integer.
	 *	@param ask  The prompt line
	 *	@param min  The minimum integer accepted
	 *	@param max  The maximum integer accepted
	 *	@return  	The integer input
	 */
	public static int getInt (String ask, int min, int max)
	{
		int value = 0;
		do
		{
			value = getInt(ask + " (" + min + " - " + max + ") ");
		} while(value < min || value > max);
		return value;
	}
	
	/**
	 *	Prompts the user for a double and returns the double.
	 *	@param ask  The prompt line
	 *	@return  The double input
	 */
	public static double getDouble (String ask)
	{
		boolean badInput = false;
		String input = "";
		double value = 0;
		do
		{
			badInput = false;
			input = getString(ask);
			try
			{
				value = Double.parseDouble(input);
			}
			catch(NumberFormatException e)
			{
				badInput = true;
			}
		}while(badInput);
		return value;
	}
	
	/**
	 *	Prompts the user for a double and returns the double.
	 *	@param ask  The prompt line
	 *	@param min  The minimum double accepted
	 *	@param max  The maximum double accepted
	 *	@return  The double input
	 */
	public static double getDouble (String ask, double min, double max)
	{
		double value = 0;
		do
		{
			value = getDouble(ask + " (" + min + " - " + max + ") ");
		} while(value < min || value > max);
		return value;
	}
}
