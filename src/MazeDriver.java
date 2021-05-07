import maze.InvalidMazeException;
import java.io.FileNotFoundException;
import java.io.IOException;
import maze.Maze;
import maze.routing.RouteFinder;
import maze.routing.NoRouteFoundException;
import maze.Maze.Direction;


/**
* Class provided to choose the file of the maze to be solved
* @author Bogdan-Gabriel Rotaru 
* @version 29th April 2021
*/
public class MazeDriver 
{

	/**
	* Main method to generate the maze and become solved
	* @param args the parameter of the main method
	* @throws java.io.IOException Indicates a problem with the input file
	* @throws java.io.FileNotFound Indicates that the input file does not exist
	* @throws java.maze.InvalidMazeException Indicates a problem with the structure of the maze constructed from the file input 
	* @throws java.maze.routing.NoRouteFoundException Indicates that the maze could not be solved, as there is no route out of it
    */
    public static void main(String args[]) throws InvalidMazeException, FileNotFoundException, IOException, NoRouteFoundException 
    {  
    	Maze maze = Maze.fromTxt("/home/csimage/comp16412-coursework-2_w09524br/resources/mazes/maze1.txt");
    
    	RouteFinder route = new RouteFinder(maze);
        System.out.println(route.toString());
    }
}
