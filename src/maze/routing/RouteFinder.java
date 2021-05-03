package maze.routing;

import maze.*;
import maze.Maze.Direction;
import maze.Maze.Coordinate;
import maze.Maze;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;


/**
* Class provided for finding the route out of a maze
* @author Bogdan-Gabriel Rotaru
* @version 29th April 2021
*/
public class RouteFinder implements Serializable
{
	private Maze maze;
	private Stack <Tile> route;
	private boolean finished;
	private List<Tile> visitedList = new ArrayList<Tile>();


	/**
	* Constructor of the class that initialises the route with the maze entrance and starts to make steps through the exit
	* @param m the maze that we want to solve (find a route)
	* @throws java.maze.routing.NoRouteFoundException Indicates that the maze could not be solved, as there is no route out of it
	*/
	public RouteFinder(Maze m) throws NoRouteFoundException
	{
		maze = m;
		route = new Stack<Tile>();
		route.push(maze.getEntrance());
		finished = false;
		while(finished == false)
				finished = step();
	}


	/**
	* Gets the maze 
	* @return Returns the maze to be solved
	*/
	public Maze getMaze()
	{
		return maze;
	}


	/**
	* Gets the route of the maze
	* @return Returns a list of tiles representing the route that has been found 
	*/
	public List<Tile> getRoute()
	{
		List<Tile> routeSaved = new ArrayList<Tile>();
		Stack<Tile> steps = route;

		while(route.size() != 0)
			routeSaved.add(steps.pop());

		Collections.reverse(routeSaved);
		System.out.println(routeSaved);
		return routeSaved;
	}


	/**
	* Check if the maze has been solved
	* @return Returns a boolean value, which is true if the maze has been solved, or false otherwise
	*/
	public boolean isFinished()
	{
		return finished;
	}


	/**
	* Load the route at its last state
	* @param filename the name of the file where the route is saved
	* @return Returns the route from the input file
	* @throws java.io.IOexception Indicates that there is a problem with the file
	* @throws java.io.FileNotFoundException Indicates that the file cannot be found
	*/
	public static RouteFinder load(String filename)
	{
		ObjectInputStream objectStream = null;
        try {
            objectStream = new ObjectInputStream(new FileInputStream(filename));
            return (RouteFinder)objectStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not read " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: problem when reading "+ filename);
        } finally {
            try {
                objectStream.close();
            } catch (IOException e) {
                System.out.println("Error: IOException when closing "+ filename);
            }
        }
        return null;
	}

	/**
	* Save the current route in a file
	* @param filename the name of the file where the route should be saved
	* @throws java.io.IOexception Indicates that there is a problem with the file
	* @throws java.io.FileNotFoundException Indicates that the file cannot be opened for writing
	*/
	public void save(String filename){
		ObjectOutputStream objectStream = null;
        try {
            objectStream = new ObjectOutputStream(new FileOutputStream(filename));
            objectStream.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not open " + filename + " for writing.");
        } catch (IOException e) {
            System.out.println("Error: IOException when writing "+ filename);
        } finally {
            try {
                objectStream.close();
            } catch (IOException e) {
                System.out.println("Error: IOException when closing "+ filename);
            }
        }
    }


    /**
    * Makes the next move in the maze, trying to find the route to the exit
    * @return Returns true if the maze is solved, otherwise returns false
    * @throws java.maze.routing.NoRouteFoundException Indicates that there is no route out of the maze
    */
	public boolean step() throws NoRouteFoundException
	{
		if(isFinished() == true)
			return true;

		if(route.size() == 0)
			throw new NoRouteFoundException("There is no route out of this maze!");

		Tile tileVisited = route.peek();
				
		if(tileVisited.toString() == maze.getExit().toString())
			{
				finished = true;
				return true;
			}

		Tile tileNorth = maze.getAdjacentTile(tileVisited, Direction.NORTH);
		Tile tileSouth = maze.getAdjacentTile(tileVisited, Direction.SOUTH);
		Tile tileEast = maze.getAdjacentTile(tileVisited, Direction.EAST);
		Tile tileWest = maze.getAdjacentTile(tileVisited, Direction.WEST);

		visitedList.add(tileVisited);
		
		
		if(tileNorth != null && tileNorth.isNavigable() && visitedList.contains(tileNorth) == false)
		{
			route.push(tileNorth);
			if(tileNorth == maze.getExit())
			{
				finished = true;
				return true;
			}

			return false;
		}

		
		if(tileSouth != null && tileSouth.isNavigable() && visitedList.contains(tileSouth) == false)
		{
			route.push(tileSouth);
			if(tileSouth == maze.getExit())
			{
				finished = true;
				return true;
			}

			return false;
		}

		if(tileEast != null && tileEast.isNavigable() && visitedList.contains(tileEast) == false)
		{
			route.push(tileEast);
			if(tileEast == maze.getExit())
			{
				finished = true;
				return true;
			}

			return false;
		}

		if(tileWest != null && tileWest.isNavigable() && visitedList.contains(tileWest) == false)
		{
			route.push(tileWest);
			if(tileWest == maze.getExit())
			{
				finished = true;
				return true;
			}

			return false;
		}

	route.pop();
	return false;
	}


	/**
	* Creates a string to visualise the maze with the route ilustrated
	* @return Returns a string to illustrate the solved maze and the route 
    */
	public String toString()
	{
		String mazeString = "";
		int i,j;
		List<Tile> route = getRoute();

		for(i=0; i<getMaze().getTiles().size(); i++)
			{
				for(j=0; j<getMaze().getTiles().get(0).size(); j++)
				{
					if(route.contains(getMaze().getTiles().get(i).get(j)))
						mazeString = mazeString + "*";			
					else 
						mazeString = mazeString + getMaze().getTiles().get(i).get(j).toString();
					mazeString = mazeString + " ";
				}

				mazeString = mazeString + "\n";
			}	
		return mazeString;
	}
}