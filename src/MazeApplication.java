import java.io.File;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import maze.InvalidMazeException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import maze.Maze;
import maze.Tile;
import maze.routing.NoRouteFoundException;
import maze.routing.RouteFinder;
import java.io.FileNotFoundException;
import java.io.IOException;
import maze.visualisation.CreateGrid;
import javafx.geometry.Pos;


/**
* Class provided for creating the interface of the Maze Solver Application
* @author Bogdan-Gabriel Rotaru 
* @version 3rd May 2021
*/
public class MazeApplication extends Application 
{
    private RouteFinder route;
    private GridPane mazeGrid;
    private GridPane buttons;
    private Button save;
    private Button step;
    private Button loadMap;
    private Button loadRoute;


    /**
    * Main method that launches the JavaFX application
    * @param args arguments to launch and run the application
    */
    public static void main(String args[])
    {
        launch(args);
    }


    /**
    * Creates all necessary components of the applications and put them together to be displayed on the screen. Features such as save, load route, load map and solving a maze step by step are implemented
    * @param stage the stage of the Maze Solver application
    */
    @Override
    public void start(Stage stage)
    {
        FileChooser mazeFile = new FileChooser();
        mazeGrid = CreateGrid.getGrid();
        mazeGrid.setAlignment(Pos.CENTER);
        buttons = CreateGrid.getGrid();
        buttons.setAlignment(Pos.CENTER);

        loadMap = new Button("Load Map");
        loadMap.setMinSize(100,50);
        loadMap.setOnMouseClicked(e ->
        {
            mazeFile.setTitle("Choose a text file");
            mazeFile.setInitialDirectory(new File("resources/mazes"));
            mazeFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TEXT", "*.txt"));
            File chosenFile = mazeFile.showOpenDialog(stage);
            if(chosenFile != null)
            {
                try
                {
                    route = new RouteFinder(Maze.fromTxt(chosenFile.getAbsolutePath()));
                    createMaze();
                    stage.sizeToScene();
                }
            
                catch(NoRouteFoundException ex)
                {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("No Route Found");
                    alert.setContentText(ex.getMessage());
                    alert.show();
                }
                catch(IOException ex)
                {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("IOException");
                    alert.setContentText(ex.getMessage());
                    alert.show();
                }
                catch(InvalidMazeException ex)
                {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Invalid Maze");
                    alert.setContentText(ex.getMessage());
                    alert.show();
                }
            }

        });


        loadRoute = new Button("Load Route");
        loadRoute.setMinSize(100,50);
        loadRoute.setOnMouseClicked(e ->
        {
            mazeFile.setTitle("Choose a file with a route");
            mazeFile.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("OBJECT", "*.obj"));
            File chosenFile = mazeFile.showOpenDialog(stage);
            if(chosenFile != null)
            {
                route = route.load(chosenFile.getAbsolutePath());
                createMaze();
                stage.sizeToScene();
            }
        });
        

        save = new Button("Save");
        save.setMinSize(100,50);
        save.setOnMouseClicked(e ->
        {
            mazeFile.setTitle("Choose the location to save the file");
            mazeFile.setInitialDirectory(new File("resources/routes"));
            mazeFile.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("OBJECT", "*.obj"));
            File chosenFile = mazeFile.showOpenDialog(stage);
            if(chosenFile != null)
            {             
                route.save(chosenFile.getAbsolutePath());
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Maze saved");
                alert.show();
            }
        });


        step = new Button("Step");
        step.setMinSize(100,50);
        step.setOnMouseClicked(e ->
        {
            try
            {
                if(route.isFinished() == false)
                {
                    route.step();
                    createMaze();
                    stage.sizeToScene();
                }

                else
                {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("Maze solved");
                    alert.show();
                }
            }
            catch(NoRouteFoundException ex){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("No Route Found");
                alert.setContentText(ex.getMessage());
                alert.show();
            }
            catch(NullPointerException ex)
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("NullPointerException");
                alert.setContentText("No maze loaded");
                alert.show();
            }

        });
       
        buttons.add(loadMap, 0, 0);
        buttons.add(loadRoute, 0, 1);
        buttons.add(save, 0,2);
        buttons.add(step, 0,4);

        stage.setTitle("Maze Solver");
        Scene scene = new Scene(buttons, 600, 600);
        stage.setScene(scene);
        stage.show();
    } 


    /**
    * Creates the maze and update it every time the method is called. At first, the maze is in initial state, then it is updated as steps are done by the player
    */
    private void createMaze()
    {
        List<List<Tile>> tiles = route.getMaze().getTiles();
        buttons.getChildren().remove(mazeGrid);
        mazeGrid.getChildren().clear();

        int i,j;
        for(i=0; i<=tiles.size()-1; i++)
            for(j=0; j<=tiles.get(0).size()-1; j++)
                if(tiles.get(i).get(j).isNavigable() == true)
                {
                    StackPane tilesStack = new StackPane();
                    Text tile;

                    if(tiles.get(i).get(j).getType() == Tile.Type.ENTRANCE)
                    {
                        tile = new Text("◈");
                        tilesStack.getChildren().addAll(new Rectangle(20, 30, Color.LIGHTBLUE), tile);
                        mazeGrid.add(tilesStack, j, i);
                    }

                    else if(tiles.get(i).get(j).getType() == Tile.Type.EXIT)
                    {
                        tile = new Text(" ");
                        tilesStack.getChildren().addAll(new Rectangle(20, 30, Color.GREEN), tile);
                        mazeGrid.add(tilesStack, j, i);
                    }

                    else if(tiles.get(i).get(j).getType() == Tile.Type.CORRIDOR)
                    {
                        if(route.visited().contains(tiles.get(i).get(j)))
                        {
                            tile = new Text("◈");
                            tilesStack.getChildren().addAll(new Rectangle(20, 30, Color.WHITE), tile);
                            mazeGrid.add(tilesStack, j, i);
                        }
                    
                       else
                        {
                            tile = new Text(" ");
                            tilesStack.getChildren().addAll(new Rectangle(20, 30, Color.WHITE), tile);
                            mazeGrid.add(tilesStack, j, i);
                        }
                    }
                }

                else
                    mazeGrid.add(new Rectangle(20, 30, Color.BLACK), j, i);
        
        buttons.add(mazeGrid, 0, 3);
    }
}