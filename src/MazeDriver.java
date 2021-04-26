import maze.InvalidMazeException;
import java.io.FileNotFoundException;
import java.io.IOException;
import maze.Maze;

public class MazeDriver {

	static Maze mazeInstance;

    public static void main(String args[]) throws InvalidMazeException, FileNotFoundException, IOException {
    	
    	mazeInstance.fromTxt("/home/csimage/comp16412-coursework-2_w09524br/resources/mazes/maze1.txt");
    	
    }
}
