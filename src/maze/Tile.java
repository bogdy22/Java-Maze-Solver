package maze;


/**
* Class provided for creation of tiles of the maze
* @author Bogdan-Gabriel Rotaru
* @version 29th April 2021
*/
public class Tile
{
	private Type type;


	/**
	* Constructor of Tile class, which initialise the type of the tile
	* @param typeIn the type of the tile
	*/
	private Tile(Type typeIn)
	{
		type = typeIn;
	}


	/**
	* Gets the type of the tile from an input character
	* @param c the character that represents the type of the tile
	* @return Returns the tile created
	*/
	protected static Tile fromChar(char c)
	{
		Type type = Type.CORRIDOR;
		if(c == '.')
			type = Type.CORRIDOR;
		else if(c == 'e')
			type = Type.ENTRANCE;
		else if(c == 'x')
			type = Type.EXIT;
		else if(c == '#')
			type = Type.WALL;

		Tile newTile = new Tile(type);
		return newTile;
	}


	/**
	* Gets the type of the tile
	* @return Returns the type
	*/
	public Type getType()
	{
		return type;
	}


	/**
	* Check if a tile is navigable
	* @return Returns true if the tile is navigable (if it is anything than Wall), or false otherwise
	*/
	public boolean isNavigable()
	{
		if(type.name().equals("WALL"))
			return false;
		else
			return true;
	}


	/**
	* Creates a string to illustrate the tile
	* @return Returns a string to visualise each tile with their respective symbols
	*/
	public String toString()
	{
		String str = "";
		if(type.name().equals("CORRIDOR"))
			str = ".";
		else if(type.name().equals("ENTRANCE"))
			str = "e";
		else if(type.name().equals("EXIT"))
			str = "x";
		else if(type.name().equals("WALL"))
			str = "#";
		return str;
	}


	/**
	* Enumeration for the types of a tile
	*/
	public enum Type
	{
		CORRIDOR, ENTRANCE, EXIT, WALL
	}

}