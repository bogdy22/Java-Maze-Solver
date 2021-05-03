package maze;

/**
* Class provided for throwing an exception when the maze is invalid
* @author Bogdan-Gabriel Rotaru
* @version 29th April 2021
*/
public class InvalidMazeException extends Exception
{
	public InvalidMazeException(String message)
	{
		super(message);
	}

}