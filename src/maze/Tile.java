package maze;

public class Tile
{
	private Type type;

	private Tile(Type typeIn)
	{
		type = typeIn;
	}

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

	public Type getType()
	{
		return type;
	}

	public boolean isNavigable()
	{
		if(type.name().equals("WALL"))
			return false;
		else
			return true;
	}

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

	public enum Type
	{
		CORRIDOR, ENTRANCE, EXIT, WALL
	}

}