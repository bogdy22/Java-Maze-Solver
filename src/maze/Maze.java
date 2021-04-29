package maze;
import java.io.BufferedReader;
import java.io.FileNotFoundException; 
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze{
	
	private Tile entrance;
	private Tile exit;
	private List<List<Tile>> tiles = new ArrayList<List<Tile>>();

	private Maze()
	{
		// super("No args exception");
	}

	public static Maze fromTxt (String input) throws FileNotFoundException, IOException, InvalidMazeException,IOException 
	{
		Maze maze = new Maze();
		int row = 0;
		boolean exitMaze = false;
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
						}

						else 
							if (currentLine.charAt(i) == 'x')
							{
								maze.setExit(maze.tiles.get(row).get(maze.tiles.get(row).size() - 1));
								exitMaze = true;
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
		}

		catch(FileNotFoundException e) 
		{
			System.out.println("\nNo file was read.");
		}

		catch(IOException e) 
		{
			System.out.println("\nThere was a problem reading the file.");
		}

		return maze;
	}

	public Tile getAdjacentTile(Tile t, Direction d)
	{
		Coordinate c = getTileLocation(t);
		int x = c.getX();
		int y = c.getY();
		//System.out.println(c);
		Tile adjTile = null;

		if(d.name().equals("NORTH") && y < tiles.size()-1)
			adjTile = tiles.get(tiles.size()-y-2).get(x);

		if(d.name().equals("SOUTH") && y > 0)
			adjTile = tiles.get(tiles.size()-y).get(x);

		if(d.name().equals("EAST") && x < tiles.get(0).size()-1)
			adjTile = tiles.get(tiles.size()-y-1).get(x+1);

		if(d.name().equals("WEST") && x > 0)
			adjTile = tiles.get(tiles.size()-y-1).get(x-1);

		// System.out.print("adjTIle : ");
		// System.out.println(adjTile);
		return adjTile;
	}

	public Tile getEntrance()
	{
		return entrance;
	}

	public Tile getExit()
	{
		return exit;
	}

	public Tile getTileAtLocation(Coordinate c)
	{
		int x = c.getX();
		int y = c.getY();
		Tile newLocation = null;

		if( x <= tiles.get(0).size()-1 && y <= tiles.size()-1)
			newLocation = tiles.get(tiles.size()-y-1).get(x);

		return newLocation;		

	}

	public Coordinate getTileLocation(Tile t)
	{
		int i,j;
		for (i=0;i<=tiles.size()-1; i++)
			for(j=0;j<=tiles.get(0).size()-1;j++)
				{	
					if(tiles.get(i).get(j) == t)
						return new Coordinate(j, (tiles.size()-i-1));
			}
		return null;
	}

	public List<List<Tile>> getTiles()
	{
		return tiles;
	}

	private void setEntrance(Tile t)
	{
		entrance = t;
	}

	private void setExit(Tile t)
	{
		exit = t;
	}

	 public String toString()
	{
		String mazeString = "";
		int i,j,k;
		// System.out.println(tiles.get(0).size());
		// System.out.println(tiles.size());
		for(i=0; i<=tiles.size()-1;i++)
			{
				for(j=0;j<=tiles.get(0).size()-1;j++)
				{
					mazeString =  mazeString + tiles.get(i).get(j).toString() ;
				}
				mazeString = mazeString + "\n";
			}
		
		// System.out.println();
		// System.out.print("     ");
		// for(k=0;k<=tiles.size()-1;k++)
		// 	System.out.println(String.valueOf(k) + " ");

		return mazeString;

	}

	public class Coordinate
	{
		private int x;
		private int y;

		public Coordinate(int xIn,int yIn)
		{
			x = xIn;
			y = yIn;
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}

		public String toString()
		{
			String str = "";
			str = "(" + String.valueOf(x) + ", " + String.valueOf(y) + ")";
			return str;
		}
	}


	public enum Direction
	{
		NORTH, SOUTH, EAST, WEST
	}


	
}