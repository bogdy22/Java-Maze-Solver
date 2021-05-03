package maze;


/**
* Class provided for throwing an exception when there is no exit in a maze
* @author Bogdan-Gabriel Rotaru
* @version 29th April 2021
*/
public class NoExitException extends InvalidMazeException
{

	/**
	* Constructor that displays a message when the error is thrown
	* @param message the message to be displayed
	*/
	public NoExitException(String message)
	{
		super(message);
	}
}