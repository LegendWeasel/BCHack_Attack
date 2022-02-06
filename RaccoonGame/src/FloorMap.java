

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.awt.Rectangle;

public class FloorMap {
    //Tracks the outline grid of the map
    private List<RoomNode> grid = new ArrayList<RoomNode>();
    private RoomNode currentTile;
    private RoomNode nextTile;

    //Tracks the tile stack
    Stack tileStack = new Stack();

    //Tracks all the rooms of a map
    private List<Room> room = new ArrayList<Room>();

    //Tracks the index of the current room
    private int currentRoom = 0;

    //Tracks how many times a room tried to spawn
    private int retryCounter = 0;

    int wallHeight;
    int wallWidth;

    public FloorMap(Data data)
        {
            //Sets the wall width and room boundries
            wallHeight = 130;
            wallWidth = 150;

            Data.roomBoundary = new Rectangle(Data.wallWidth, Data.wallHeight, Data.screenWidth - 2 * Data.wallWidth, Data.screenHeight - 2 * Data.wallHeight);

            //Populates the grid with all the nodes
            //Loops through all rows
            for (int row = 0; row < Data.mapNodeSize; row++)
            {
                //Loops through all columns
                for (int col = 0; col < Data.mapNodeSize; col++)
                {
                    //Adds the node
                    grid.add(new RoomNode(data, row, col, Data.mapNodeSize));
                }
            }

            //Loops through all nodes and finds its neighbor
            for (int i = 0; i < grid.size(); i++)
            {
                //Finds the current nodes neighbor
                grid.get(i).FindNeighbors(grid);
            }

            //Sets the current tile as a random tile
            currentTile = grid.get(Data.getRandomNumber(0,grid.size()));

            //Creates a start room
            room.Add(new StartRoom(0));
        }

        
}
