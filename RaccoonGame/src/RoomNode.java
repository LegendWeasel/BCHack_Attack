

import java.util.ArrayList;
import java.util.List;


public class RoomNode {
	
    //Stores positional data of the tile
    private int row;
    private int col;

    //Tracks the walls of the room
    private boolean[] hasWall;

    //Tracks all the doors of the node
    private byte doors;

    //Tracks if the node has been visited
    private boolean visited;

    //Tracks the neighboring nodes
    private List<RoomNode> neighbor;
    private List<RoomNode> openNeighbor;

    //Tracks the size of the map grid
    private int gridSize;

    //Tracks if the room is a special room
    private boolean isDeadEnd;

    private Data data;
    Vector2 point;
    
    //Constructor
    public RoomNode(Data data, int row, int col, int gridSize)
    {
        this.data = data;

        //Sets the current row and sile
        this.row = row;
        this.col = col;

        //Sets the grid size
        this.gridSize = gridSize;

        //Creates the list of neighbors
        neighbor = new ArrayList<RoomNode>();
        openNeighbor = new ArrayList<RoomNode>();

        //Sets the base stat of the node
        SetBaseStats();
    }
    
  /// <summary>
    /// Retrives the row
    /// </summary>
    /// <returns></returns>
    public int GetRow()
    {
        //Returns row
        return row;
    }

    /// <summary>
    /// Retrives the column
    /// </summary>
    /// <returns></returns>
    public int GetCol()
    {
        //Returns col
        return col;
    }

    /// <summary>
    /// Retrives if each side has a wall
    /// </summary>
    /// <returns></returns>
    public boolean[] GetHasWall()
    {
        //Returns has wall
        return hasWall;
    }

    /// <summary>
    /// Returns if the room is a dead end
    /// </summary>
    /// <returns></returns>
    public boolean GetIsDeadEnd()
    {
        return isDeadEnd;
    }

    /// <summary>
    /// Retrives which doors are open in byte form
    /// </summary>
    /// <returns></returns>
    public byte GetDoors()
    {
        return doors;
    }

    /// Sets a is special state
    public void SetIsDeadEnd(boolean isDeadEnd)
    {
        this.isDeadEnd = isDeadEnd;
    }

    /// <summary>
    /// Sets a new visited state
    /// </summary>
    /// <returns></returns>
    public void SetVisited(boolean visited)
    {
        this.visited = visited;
    }

    /// <summary>
    /// Opens a door given a side
    /// </summary>
    /// <param name="doorSide"></param>
    public void OpenDoors(byte doorSide)
    {
        //Opens the door
        doors |= doorSide;
    }
    
    /// <summary>
        /// Adds all neighboring nodes
        /// </summary>
        /// <param name="grid"></param>
        public void FindNeighbors(List<RoomNode> grid)
        {
            //Adds a neighbor if it exists
            if (Data.CalcIndex(col, row - 1, gridSize, gridSize) != -1)
            {
                //A neighbor is above 
                neighbor.add(grid.get(Data.CalcIndex(col, row - 1, gridSize, gridSize)));
            }

            if (Data.CalcIndex(col + 1, row, gridSize, gridSize) != -1)
            {
                //A neighbor is below 
                neighbor.add(grid.get(Data.CalcIndex(col + 1, row, gridSize, gridSize)));
            }

            if (Data.CalcIndex(col, row + 1, gridSize, gridSize) != -1)
            {
                //A neighbor is to the right 
                neighbor.add(grid.get(Data.CalcIndex(col, row + 1, gridSize, gridSize)));
            }

            if (Data.CalcIndex(col - 1, row, gridSize, gridSize) != -1)
            {
                //A neighbor is to the left 
                neighbor.add(grid.get(Data.CalcIndex(col-1, row, gridSize, gridSize)));
            }
        }

    /// <summary>
    /// Checks which neighbors are visted and adds them to the list
    /// </summary>
    /// <param name="grid"></param>
    /// <returns></returns>
    public RoomNode CheckNeighbors(List<RoomNode> grid)
    {
        //Clears the open neighbors list
        openNeighbor.clear();

        //Loops for all neighbors
        for (int i = 0; i < neighbor.size(); i++)
        {
            //Adds a non visited neighbor to the open neighbors list
            if (!neighbor.get(i).visited)
            {
                //Adds the current node to the open list
                openNeighbor.add(neighbor.get(i));
            }
        }

        //Checks if there are any neighbors available
        if (openNeighbor.size() > 0)
        {
            //Returns a random available neighbor
            //return openNeighbor[Data.rng.Next(0, openNeighbor.Count)];
            return openNeighbor.get(Data.getRandomNumber(0,openNeighbor.size()));
        }

        //There are no more neighbors
        return null;
    }
    
    /// <summary>
    /// Calculates if the node is a dead end
    /// </summary>
    public void CalcIsDeadEnd()
    {
        //Checks for a power of two to determine a single entry room
        if ((doors & (doors - 1)) == 0)
        {
            //The room only has a single entry
            isDeadEnd = true;
        }
        else
        {
            isDeadEnd = false;
        }
    }

    
    /// <summary>
    /// Sets the starting stat of the node
    /// </summary>
    public void SetBaseStats()
    {
        //Sets the node as unvisited
        visited = false;

        //Sets all sides to have a wall
        hasWall = new boolean[] { true, true, true, true };

        //Sets the room as not special
        isDeadEnd = false;

        //Sets the room to have no doors
        doors = 0;
    }
    
}
