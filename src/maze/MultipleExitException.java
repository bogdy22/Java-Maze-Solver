package maze;

/**
* Class provided for throwing an exception when there are multiple exits in a maze
* @author Bogdan-Gabriel Rotaru
* @version 29th April 2021
*/
public class MultipleExitException extends InvalidMazeException
{
	/**
	* Constructor that displays a message when the exception is thrown
	* @param message the message to be displayed
	*/
	public MultipleExitException(String message)
	{
		super(message);
	}
}