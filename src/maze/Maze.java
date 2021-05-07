package maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException; 
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


/**
* Class provided for generating the maze
* @author Bogdan-Gabriel Rotaru
* @version 29th April 2021
*/
public class Maze implements Serializable
{	
	private Tile entrance;
	private Tile exit;
	private List<List<Tile>> tiles = new ArrayList<List<Tile>>();


	/**
	* Constructor of the Maze class, which is empty
	*/
	private Maze(){}

	/**
	* Creates a Maze object from reading a txt file
	* @param input the input text file that will be read and used to create the Maze object
	* @return Returns the Maze object created
	* @throws java.io.IOException Indicates a problem with the input file
	* @throws java.io.FileNotFound Indicates that the input file does not exist
	* @throws java.maze.InvalidMazeException Indicates a problem with the structure of the maze constructed from the file input 
	* @throws java.maze.RaggedMazeException Indicates that the maze is ragged
	* @throws java.maze.NoExitException Indicates that the maze has no exit
	* @throws java.maze.NoEntranceException Indicates that the maze has no entrance
	* @throws java.maze.MultipleExitException Indicates that the maze has more than one exit
	* @throws java.maze.MultipleEntranceExcepion Indicates that the maze has more than one entrance
	*/
	public static Maze fromTxt (String input) throws FileNotFoundException, IOException, InvalidMazeException
	{
		Maze maze = new Maze();
		int row = 0;
		boolean exitMaze = false;
		int exits = 0;
		int entrances = 0;
		boolean entranceMaze = false; 
		String validChars = "ex.#";

		try(
		FileReader mazeFile = new FileReader(input);
		BufferedReader mazeStream = new BufferedReader(mazeFile);
		)

		{
			String currentLine = mazeStream.readLine();
			int lineLength = currentLine.length();

			while (currentLine != null)
			{
				int i = 0;
				maze.tiles.add(new ArrayList<Tile>());
				
				if(lineLength != currentLine.length())
	    			throw new RaggedMazeException("The maze is ragged.");
	    	
	    		else 
					while (i < currentLine.length())
					{

						if (validChars.indexOf(currentLine.charAt(i)) == -1)
							throw new InvalidMazeException("An invalid character was found in the maze");

						maze.tiles.get(row).add(Tile.fromChar(currentLine.charAt(i)));
						if (currentLine.charAt(i) == 'e') 
						{
							
							maze.setEntrance(maze.tiles.get(row).get(maze.tiles.get(row).size() - 1));
							entranceMaze = true;
							entrances++;
							
						}

						else 
							if (currentLine.charAt(i) == 'x')
							{
								maze.setExit(maze.tiles.get(row).get(maze.tiles.get(row).size() - 1));
								exitMaze = true;
								exits++;
							}

						i++;
					}
				row++;
				currentLine = mazeStream.readLine();
			}

			if (exitMaze == false){
				throw new NoExitException("No exit found");
			}

			if (entranceMaze == false){
				throw new NoEntranceException("No entrance found");
			}

			if (exits > 1 && exits !=0)
				throw new MultipleExitException("There are more than one exit.");
			if (entrances > 1 && entrances != 0)
				throw new MultipleEntranceException("There are more than one entrance.");
		}

		catch(FileNotFoundException e) 
		{
			System.out.println("No file was read.");
		}

		catch(IOException e) 
		{
			System.out.println("There was a problem reading the file.");
		}

		return maze;
	}


	/**
	* Gets the adjacent tile of the input tile, in the given direction
	* @param t represents the tile whose adjacent tile we want
	* @param d represents the direction of the desired adjacent tile
	* @return Returns the adjacent tile of the given tile in the given direction, or null if the adjacent tile does not exist
	*/
	public Tile getAdjacentTile(Tile t, Direction d)
	{
		Coordinate c = getTileLocation(t);
		int x = c.getX();
		int y = c.getY();
		Tile adjTile = null;

		if(d.name().equals("NORTH") && y < tiles.size()-1)
			adjTile = tiles.get(tiles.size()-y-2).get(x);

		if(d.name().equals("SOUTH") && y > 0)
			adjTile = tiles.get(tiles.size()-y).get(x);

		if(d.name().equals("EAST") && x < tiles.get(0).size()-1)
			adjTile = tiles.get(tiles.size()-y-1).get(x+1);

		if(d.name().equals("WEST") && x > 0)
			adjTile = tiles.get(tiles.size()-y-1).get(x-1);

		return adjTile;
	}


	/**
	* Gets the entrance tile of the maze
	* @return Returns the entrance of the maze
	*/
	public Tile getEntrance()
	{
		return entrance;
	}


	/**
	* Gets the exit tile of the maze
	* @return Returns the exit of the maze
	*/
	public Tile getExit()
	{
		return exit;
	}


	/**
	* Gets the tile at a specified coordinate
	* @param c the coordinate where we want the tile to be located
	* @return Returns the new location of the tile at coordinate c, or null if the coordinate does not exist
	*/
	public Tile getTileAtLocation(Coordinate c)
	{
		int x = c.getX();
		int y = c.getY();
		Tile newLocation = null;

		if( x <= tiles.get(0).size()-1 && y <= tiles.size()-1)
			newLocation = tiles.get(tiles.size()-y-1).get(x);

		return newLocation;		
	}


	/**
	* Gets the location of the input tile
	* @param t the tile whose coordinate we want
	* @return Returns the location of the input tile, or null if the tile does not exist
	*/
	public Coordinate getTileLocation(Tile t)
	{
		int i,j;
		for (i=0;i<=tiles.size()-1; i++)
			for(j=0;j<=tiles.get(0).size()-1;j++)	
					if(tiles.get(i).get(j) == t)
						return new Coordinate(j, (tiles.size()-i-1));
		return null;
	}


	/**
	* Gets the tiles of the maze
	* @return Returns a two-dimensional list of the tiles that are present in the maze
	*/
	public List<List<Tile>> getTiles()
	{
		return tiles;
	}


	/**
	* Sets the entrance of the maze
	* @param t the tile that is set as the entrance of the maze
	* @throws java.maze.InvalizMazeException Indicates that the maze has more than one entrance or that the tile is not part of the maze
	*/
	private void setEntrance(Tile t) throws InvalidMazeException
	{
		if(entrance != null)
			throw new MultipleEntranceException("There are more than one entrance in this maze.");
		if(getTileLocation(t) != null)
			entrance = t;

	}


	/**
	* Sets the exit of the maze
	* @param t the tile that is set as the exit of the maze
	* @throws java.maze.InvalizMazeException Indicates that the maze has more than one exit or that the tile is not part of the maze
	*/
	private void setExit(Tile t) throws InvalidMazeException
	{
		if(exit != null)
			throw new MultipleExitException("There are more than one exit in this maze.");
		if(getTileLocation(t) != null)
			exit = t;
	}


	/**
	* Create a string that illustrates the maze
	* @return Returns a string that visualises the entire maze
	*/
	public String toString()
	{
		String mazeString = "";
		int i,j,k;
		
		for(i=0; i<=tiles.size()-1;i++)
			{
				for(j=0;j<=tiles.get(0).size()-1;j++)
				{
					mazeString =  mazeString + tiles.get(i).get(j).toString() ;
				}
				mazeString = mazeString + "\n";
			}
		
		return mazeString;
	}


	/**
	* Class provided for the coordinate of the tiles
	* @author Bogdan-Gabriel Rotaru
	* @version 29th April 2021
	*/
	public class Coordinate
	{
		private int x;
		private int y;


		/**
		* Constructor of the class that sets the coordinates
		* @param xIn the coordinate on x axis 
		* @param yIn the coordinate on y axis
		*/
		public Coordinate(int xIn,int yIn)
		{
			x = xIn;
			y = yIn;
		}


		/**
		* Gets the x coordinate of the tile
		* @return Returns an integer that represent the coordinate of the x axis
		*/
		public int getX()
		{
			return x;
		}


		/**
		* Gets the y coordinate of the tile
		* @return Returns an integer that represent the coordinate of the y axis
		*/
		public int getY()
		{
			return y;
		}


		/**
		* Create a string that illustrates the coordinates
		* @return Returns a string that visualise the coordinates of a tile
		*/
		public String toString()
		{
			String str = "";
			str = "(" + String.valueOf(x) + ", " + String.valueOf(y) + ")";
			return str;
		}
	}


	/**
	* Enumeration for the cardinal directions
	*/
	public enum Direction
	{
		NORTH, SOUTH, EAST, WEST
	}	
}