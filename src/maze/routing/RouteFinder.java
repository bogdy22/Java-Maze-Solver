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

public class RouteFinder implements Serializable
{
	private Maze maze;
	private Stack <Tile> route;
	private boolean finished;
	private List<Tile> visitedList = new ArrayList<Tile>();

	public RouteFinder(Maze m) throws NoRouteFoundException
	{
		maze = m;
		route = new Stack<Tile>();
		route.push(maze.getEntrance());
		finished = false;
		while(finished == false)
			{
				finished = step();
				// if(finished == true)
				// 	System.out.println("Maze solved.");
			}
	}

	public Maze getMaze()
	{
		return maze;
	}

	public List<Tile> getRoute()
	{
		List<Tile> routeSaved = new ArrayList<Tile>();
		Stack<Tile> steps = route;

		while(route.size() != 0)
			routeSaved.add(steps.pop());

		Collections.reverse(routeSaved);
		return routeSaved;
	}

	public boolean isFinished()
	{
		return finished;
	}

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

	// System.out.println("Poped:");
	route.pop();
	return false;
	}

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