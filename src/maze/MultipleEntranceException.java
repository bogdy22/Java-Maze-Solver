package maze;

/**
* Class provided for throwing an exception when there are multiple entrances in the maze
* @author Bogdan-Gabriel Rotaru
* @version 29th April 2021
*/
public class MultipleEntranceException extends InvalidMazeException
{
	/**
	* Constructor that displays a message when the exception is thrown
	* @param message the message to be displayed
	*/
	public MultipleEntranceException(String message)
	{
		super(message);
	}
}