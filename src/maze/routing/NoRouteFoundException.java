package maze.routing;


/**
* An Exception subclass to be thrown when there is no route out of a maze
*/
public class NoRouteFoundException extends Exception
{

	/**
	* Constructor that displays a message when the error is thrown
	* @param message the message to be displayed
	*/
	public NoRouteFoundException(String message)
	{
		super(message);
	}

}