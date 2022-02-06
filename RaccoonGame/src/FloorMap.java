

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

    //Points to the data class
    Data data;

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
            room.add(new StartRoom(0));
        }

    /// <summary>
    /// Returns the current room
    /// </summary>
    /// <returns>The current room in tupe Room</returns>
    public Room GetCurRoom()
    {
        return room.get(currentRoom);
    }

    /// <summary>
    /// Retrives all the rooms in the map
    /// </summary>
    /// <returns></returns>
    public List<Room> GetRooms()
    {
        return room;
    }

    /// <summary>
    /// Updates the current room
    /// </summary>
    public void Update(Player player)
    {
        //Updates the current room
        room.get(currentRoom).Update(player, room.toArray());

        //Sets the current room as the players current room
        currentRoom = player.GetCurrentRoom().GetRoomID();
    }
    
    /// <summary>
    /// Generates the floor
    /// </summary>
    /// <param name="player"></param>
    public void GenerateFloor(Player player)
    {
        //Generates a new map 
        GenerateNodemap();

        //Clears all rooms
        room.clear();

        //Loops through all nodes and sets it as a monster room except the starter room
        for (int i = 0; i < grid.Count; i++)
        {
            //Adds a new room
            room.add(new MonsterRoom(data, i));
        }

        //Sets the starting room as a random room
        int startRoom = Data.rng.Next(room.Count);
        room[startRoom] = new StartRoom(startRoom);

        //Sets the player and maps current room as the starter room
        currentRoom = startRoom;
        player.SetCurrentRoom(room[startRoom]);

        //Tracks the IDs of dead end rooms
        Stack<int> deadEnds = new Stack<int>();

        //Loops through all rooms and tracks which rooms are dead ends
        for (int i = 0; i < room.Count; i++)
        {
            //Checks if the room is a dead end and is not the starting room
            if(grid[i].GetIsDeadEnd() && i != startRoom)
            {
                deadEnds.Push(room[i].GetRoomID());
            }
        }

        //Tracks the room ID of a special room
        int specialRoomID;

        //Adds the boss room if there is a dead end node
        if (deadEnds.Count > 0)
        {
            //Sets the boss room at a dead end
            specialRoomID = deadEnds.Pop();
            room[specialRoomID] = new BossRoom(specialRoomID);
        }

        //Adds the treausure room if there is a dead end node
        if (deadEnds.Count > 0)
        {
            //Sets the treausure room at a dead end
            specialRoomID = deadEnds.Pop();
            room[specialRoomID] = new TreasureRoom(specialRoomID);
        }

        //Adds the shop room if there is a dead end node
        if (deadEnds.Count > 0)
        {
            //Sets the shop room at a dead end
            specialRoomID = deadEnds.Pop();
            room[specialRoomID] = new ShopRoom(specialRoomID);
        }

        //Loops through all nodes and adds in a coresponding door
        for (int i = 0; i < grid.Count; i++)
        {
            //Adds the doors
            AddDoors(i, player);
        }

        //Loops through all nodes and locks certain doors
        for (int i = 0; i < grid.Count; i++)
        {
            //Loops through all the doors in the room
            for(int j = 0; j < room[i].GetDoors().Count; j++)
            {
                if(room[i].GetDoors()[j].GetAdjRoom().GetShouldLock())
                {
                    //Lock the door to the room
                    room[i].GetDoors()[j].LockDoor();
                }
            }
        }

            //Loops through all nodes and populates it
            for (int i = 0; i < grid.Count; i++)
        {
            //Populates the current room
            room[i].Populate(player);
        }

        //Once the map is generated, enter the game
        Data.currentScreen = Data.GAMESCREEN;
    }
}
