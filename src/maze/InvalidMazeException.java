package maze;

/**
* Class provided for throwing an exception when the maze is invalid
* @author Bogdan-Gabriel Rotaru
* @version 29th April 2021
*/
public class InvalidMazeException extends Exception
{
	/**
	* Constructor that shows a message when the exception is thrown
	* @param message the message to be displayed
	*/
	public InvalidMazeException(String message)
	{
		super(message);
	}

}