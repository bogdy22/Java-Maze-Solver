import maze.InvalidMazeException;
import java.io.FileNotFoundException;
import java.io.IOException;
import maze.Maze;
import maze.routing.RouteFinder;
import maze.routing.NoRouteFoundException;
import maze.Maze.Direction;

public class MazeDriver 
{

    public static void main(String args[]) throws InvalidMazeException, FileNotFoundException, IOException, NoRouteFoundException 
    {
    	//mazeInstance.fromTxt("/home/csimage/comp16412-coursework-2_w09524br/resources/mazes/maze1.txt");
    	Maze maze = Maze.fromTxt("/home/csimage/comp16412-coursework-2_w09524br/resources/mazes/maze1.txt");
    	//System.out.println(maze.toString());

    	RouteFinder route = new RouteFinder(maze);
    	System.out.println(route.toString());
    }
}
