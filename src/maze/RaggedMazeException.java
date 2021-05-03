package maze;

/**
* Class provided for throwing an exception when the maze is ragged
* @author Bogdan-Gabriel Rotaru
* @version 29th April 2021
*/
public class RaggedMazeException extends InvalidMazeException
{

	/**
	* Constructor that displays a message when the error is thrown
	* @param message the message to be displayed
	*/
	public RaggedMazeException(String message)
	{
		super(message);
	}
}