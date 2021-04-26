import javafx.application.Application;
import java.io.BufferedReader;
import java.io.FileNotFoundException; 
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze extends InvalidMazeException{
	
	private Tile entrance;
	private Tile exit;
	private List<List<Tile>> tiles = new ArrayList<List<Tile>>();

	private Maze()
	{
		super("No args exception");
	}

	public static Maze fromTxt(String input) throws InvalidMazeException, RaggedMazeException, MultipleEntranceException, MultipleExitException, NoEntranceException, NoExitException
	{
		Maze maze = new Maze();
	
		String tempReg;
		String tempMake;
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
			int i=0;

			while(lineLength != 0)
			{
				ArrayList<Tile> row = new ArrayList<Tile>();
				if(lineLength != currentLine.length())
					throw new RaggedMazeException("\nThe maze is ragged.");
				else
					while(i <= currentLine.length())
					{
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

						if(validChars.indexOf(currentLine.charAt(i))==-1)
							throw new InvalidMazeException("The maze is invalid.");
						row.add(Tile.fromChar(currentLine.charAt(i)));
						i++;
					}

				currentLine = mazeStream.readLine();
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
	// public Tile getAdjacentTile(Tile t, Direction d){

	//}

	public Tile getEntrance()
	{
		return entrance;
	}

	public Tile getExit()
	{
		return exit;
	}

	// public Tile getTileAtLocation(Coordinate c){

	// }

	// public Coordinate getTileLocation(Tile t){

	// }

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

	//  public String toString()
	// {
	// 	int i;
	// 	for(i = 0; i<=)

	// }

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
			str = "(" + String.valueOf(x) + "," + String.valueOf(y) + ")";
			return str;
		}
	}


	public enum Direction
	{
		NORTH, SOUTH, EAST, WEST
	}


	
}