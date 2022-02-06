

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.engine.core.gfx.SpriteSheet;

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
            this.data = data;

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
        for (int i = 0; i < grid.size(); i++)
        {
            //Adds a new room
            room.add(new MonsterRoom(data, i));
        }

        //Sets the starting room as a random room
        int startRoom = Data.getRandomNumber(0, room.size());
        room.set(startRoom, new StartRoom(startRoom));

        //Sets the player and maps current room as the starter room
        currentRoom = startRoom;
        player.SetCurrentRoom(room.get(startRoom));

        //Tracks the IDs of dead end rooms
        Stack<Integer> deadEnds = new Stack<Integer>();

        //Loops through all rooms and tracks which rooms are dead ends
        for (int i = 0; i < room.size(); i++)
        {
            //Checks if the room is a dead end and is not the starting room
            if(grid.get(i).GetIsDeadEnd() && i != startRoom)
            {
                deadEnds.push(room.get(i).GetRoomID());
            }
        }

        //Tracks the room ID of a special room
        int specialRoomID;

        //Adds the boss room if there is a dead end node
        if (deadEnds.size() > 0)
        {
            //Sets the boss room at a dead end
            specialRoomID = deadEnds.pop();
            room.set(specialRoomID, new BossRoom(specialRoomID));
        }

        //Adds the treausure room if there is a dead end node
        if (deadEnds.size() > 0)
        {
            //Sets the treausure room at a dead end
            specialRoomID = deadEnds.pop();
            room.set(specialRoomID, new TreasureRoom(specialRoomID));
        }

        //Adds the shop room if there is a dead end node
        if (deadEnds.size() > 0)
        {
            //Sets the shop room at a dead end
            specialRoomID = deadEnds.pop();
            room.set(specialRoomID, new ShopRoom(specialRoomID));
        }

        //Loops through all nodes and adds in a coresponding door
        for (int i = 0; i < grid.size(); i++)
        {
            //Adds the doors
            AddDoors(i, player);
        }

        //Loops through all nodes and locks certain doors
        for (int i = 0; i < grid.size(); i++)
        {
            //Loops through all the doors in the room
            for(int j = 0; j < room.get(i).GetDoors().size(); j++)
            {
                if(room.get(i).GetDoors().get(j).GetAdjRoom().GetShouldLock())
                {
                    //Lock the door to the room
                    room.get(i).GetDoors().get[j].LockDoor();
                }
            }
        }

            //Loops through all nodes and populates it
            for (int i = 0; i < grid.size(); i++)
        {
            //Populates the current room
            room.get(i).Populate(player);
        }

        //Once the map is generated, enter the game
        Data.currentScreen = Data.GAMESCREEN;
    }

    /// <summary>
    /// Generates the nodes of the maze
    /// </summary>
    public void GenerateNodemap()
    {
        //Resets the entire grid to be regenerated
        for (int i = 0; i < grid.size(); i++)
        {
            //Resets the current grid stats
            grid.get(i).SetBaseStats();
        }

        //Clears the node stack
        tileStack.clear();

        //Sets the current node as a random node
        currentTile = grid.get( Data.getRandomNumber(0, grid.size()));

        //Loops until the map is done. The tile stack is empty
        do
        {
            //Sets the current tile as visited
            currentTile.SetVisited(true);

            //Sets the next tile as a random neighboring tile
            nextTile = currentTile.CheckNeighbors(grid);

            //Checks if a next tile exists
            if (nextTile != null)
            {
                //Set the next node as visited
                nextTile.SetVisited(true);

                //Adds the current tile to the stack
                tileStack.push(currentTile);

                //Remove walls between the current and next node
                RemoveWalls(currentTile, nextTile);

                //Set the current tile as the next tile
                currentTile = nextTile;
            }
            else if (tileStack.size() > 0)
            {
                //Sets the current tile as the previous one from the stack
                currentTile = (RoomNode)tileStack.pop();
            }
        } while (tileStack.size() != 0);
    }

    /// <summary>
    /// Adds all doors to a room
    /// </summary>
    /// <param name="roomID"></param>
    /// <param name="player"></param>
    public void AddDoors(int roomID , Player player)
    {
        //Adds a door if there are no walls on that side of the room
        if (!grid.get(roomID).GetHasWall()[Data.UP])
        {
            //Adds a door to the top of the room
            room.get(roomID).AddDoors(player, room.get(roomID - Data.mapNodeSize), room.toArray(), Data.UP, false);
        }
        if (!grid.get(roomID).GetHasWall()[Data.RIGHT])
        {
            //Adds a door to the right of the room
            room.get(roomID).AddDoors(player, room.get(roomID + 1), room.toArray(), Data.RIGHT, false);
        }
        if (!grid.get(roomID).GetHasWall()[Data.DOWN])
        {
            //Adds a locked door to the bottom of the room
            room.get(roomID).AddDoors(player, room.get(roomID + Data.mapNodeSize), room.toArray(),Data.DOWN, false);
        }
        if (!grid.get(roomID).GetHasWall()[Data.LEFT])
        {
            //Adds a door to the left of the room
            room.get(roomID).AddDoors(player, room.get(roomID - 1), room.toArray(), Data.LEFT, false);
        }
    }

        /// <summary>
    /// Removes the adjecent room's wall based on position
    /// </summary>
    /// <param name="tileA"></param>
    /// <param name="tileB"></param>
    private void RemoveWalls(RoomNode tileA, RoomNode tileB)
    {
        //Sets the difference is rows and colums
        int x = tileA.GetCol() - tileB.GetCol();
        int y = tileA.GetRow() - tileB.GetRow();

        //The removes walls based on node position
        if (x == 1)
        {
            //The right and left wall of tileA and tileB are removed
            tileA.GetHasWall()[Data.LEFT] = false;
            tileB.GetHasWall()[Data.RIGHT] = false;

            tileA.OpenDoors(Data.WEST);
            tileB.OpenDoors(Data.EAST);
        }
        else if (x == -1)
        {
            //The right and left wall of tileA and tileB are removed
            tileA.GetHasWall()[Data.RIGHT] = false;
            tileB.GetHasWall()[Data.LEFT] = false;

            tileA.OpenDoors(Data.EAST);
            tileB.OpenDoors(Data.WEST);
        }
        if (y == 1)
        {
            //The top and bottom wall of tileA and tileB are removed
            tileA.GetHasWall()[Data.UP] = false;
            tileB.GetHasWall()[Data.DOWN] = false;

            tileA.OpenDoors(Data.NORTH);
            tileB.OpenDoors(Data.SOUTH);
        }
        else if (y == -1)
        {
            //The top and bottom wall of tileA and tileB are removed
            tileA.GetHasWall()[Data.DOWN] = false;
            tileB.GetHasWall()[Data.UP] = false;

            tileA.OpenDoors(Data.SOUTH);
            tileB.OpenDoors(Data.NORTH);
        }

        //Checks for a power of two to determine a single entry room
        tileA.CalcIsDeadEnd();
        tileB.CalcIsDeadEnd();
    }

    /// <summary>
    /// Regenerates and shuffles the possible item spawns
    /// </summary>
    public void SetItemStack()
    {
        //Empties the item stack
        Data.possiblePassive.clear();
        Data.possibleActive.clear();

        for(int i = 0; i < Data.pItemAmount-1;i++)
        {
            Data.possiblePassive.push(i);
        }
        for (int i = 0; i < Data.aItemAmount - 1; i++)
        {
            Data.possibleActive.push(i);
        }

        //Randomly suffles the item orders
        Data.possibleActive = new Stack<Integer>();
        Data.possibleActive.addAll(Data.Shuffle(new ArrayList<>(Data.possibleActive)));

        Data.possiblePassive = new Stack<Integer>();
        Data.possiblePassive.addAll(Data.Shuffle(new ArrayList<>(Data.possiblePassive)));
    }

    /// <summary>
    /// Draws the current room
    /// </summary>
    /// <param name="spriteBatch"></param>
    public void Draw(SpriteSheet sprite)
    {
        //Draws the current room
        room.get(currentRoom).Draw(sprite);
    }
}
