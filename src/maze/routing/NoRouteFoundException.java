package maze.routing;

/**
* An Exception subclass to be thrown when there is no route out of a maze
* @author Bogdan-Gabriel Rotaru
* @version 30th April 2021
*/
public class NoRouteFoundException extends Exception
{

	/**
	* Constructor that displays a message when the exception is thrown
	* @param message the message to be displayed
	*/
	public NoRouteFoundException(String message)
	{
		super(message);
	}

}