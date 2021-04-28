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

	public static Maze fromTxt(String input) throws InvalidMazeException, RaggedMazeException, MultipleEntranceException, MultipleExitException, NoEntranceException, NoExitException
	{
		Maze maze = new Maze();
		maze.entrance = Tile.fromChar('.');
		maze.exit = Tile.fromChar('.');
		
		try (
		FileReader mazeFile = new FileReader(input);
		BufferedReader mazeStream = new BufferedReader(mazeFile);
		)

		{
			String validChars = ".ex#";
			String currentLine = mazeStream.readLine();
			int lineLength = currentLine.length();

			while(currentLine != null)
			{
				ArrayList<Tile> row = new ArrayList<Tile>();
				int i=0;
				if(lineLength != currentLine.length())
					throw new RaggedMazeException("\nThe maze is ragged.");
				else
					while(i <= currentLine.length() - 1)
					{
						if(validChars.indexOf(currentLine.charAt(i))==-1)
							throw new InvalidMazeException("The maze is invalid.");
						row.add(Tile.fromChar(currentLine.charAt(i)));

						if(currentLine.charAt(i) =='e')
						{
							if(maze.entrance.toString() == "e")
								throw new MultipleEntranceException("The maze has more than one entrance.");
							maze.setEntrance(Tile.fromChar('e'));
						}

						if(currentLine.charAt(i) == 'x')
						{
							if(maze.entrance.toString() == "x")
								throw new MultipleExitException("The maze has more than one exit.");
							maze.setExit(Tile.fromChar('x'));
						}

	
						i++;
					}

				currentLine = mazeStream.readLine();
				maze.tiles.add(row);
			}

			if(maze.entrance.toString() == "")
				throw new NoEntranceException("The maze has no entrance.");
			if(maze.exit.toString() == "")
				throw new NoExitException("The maze has no exit.");
			
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
		Tile adjTile = null;

		if(d.name().equals("NORTH") && y <= tiles.size()-1)
			adjTile = tiles.get(tiles.size()-y-2).get(x);

		if(d.name().equals("SOUTH") && y <= tiles.size()-1)
			adjTile = tiles.get(tiles.size()-y).get(x);

		if(d.name().equals("EAST") && x <= tiles.get(0).size()-1)
			adjTile = tiles.get(tiles.size()-y-1).get(x+1);

		if(d.name().equals("WEST") && x > 0)
			adjTile = tiles.get(tiles.size()-y-1).get(x-1);

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
		Coordinate c = null;
		int i,j;
		for(i=0; i<= tiles.size()-1; i++)
			for(j=0; j<=tiles.get(0).size()-1; j++)
				if(tiles.get(i).get(j) == t)
					c = new Coordinate(j, (tiles.size()-i-1));

		return c;
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
		System.out.println(tiles.get(0).size());
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