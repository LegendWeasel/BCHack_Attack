
import java.awt.Rectangle;
import java.util.List;

import com.engine.core.gfx.*;
import java.util.ArrayList;
import java.awt.Graphics2D;

public class Room {
    //Tracks which element the current room is in
    protected int roomID;

    //Tracks all charcters in the room
    protected CharacterManager characterManager;

    //Tracks all items in the room
    protected Inventory inventory;

    //Tracks the size of a node
    protected int nodeSize;

    //Tracks the amount of nodes in a room
    protected int nodesWide;
    protected int nodesHigh;

    //Track all the nodes in a room
    protected Rectangle[] node;
    protected List<Rectangle> spawnNode;

    //Tracks the amount and max amount of spawn nodes
    protected int spawnNodeAmount;
    protected int maxSpawnNode;

    //Tracks all doors in the room
    protected List<Door> door;

    //Tracks if the current room is cleared
    protected boolean isClear;

    //Tracks all fired projectiles in the room
    protected List<Projectile> proj = new ArrayList<Projectile>();

    //Tracks the ground friction of the room
    protected float groundFriction;

    //Tracks if the current room should be locked
    protected boolean shouldLock;

    protected Data data;

    public Room(Data data, int roomID)
    {
        //Sets the room ID
        this.roomID = roomID;

        //Creates the player manager
        characterManager = new CharacterManager(this);

        //Creates the inventory
        inventory = new Inventory(this);

        //Creates the door list
        door = new ArrayList<Door>();

        //Sets the baseground friction
        groundFriction = 10f;

        //Sets the node size
        nodeSize = 20;

        //The current room should not be locked
        shouldLock = false;

        //Sets up all the room nodes
        SetUpNodes();
    }

    /// <summary>
    /// Retrives the room ID
    /// </summary>
    /// <returns></returns>
    public int GetRoomID()
    {
        //Return room ID
        return roomID;
    }

    /// <summary>
    /// Retrives all the doors of the curren room
    /// </summary>
    /// <returns></returns>
    public List<Door> GetDoors()
    {
        return door;
    }

    /// <summary>
    /// Retrives if the room is to be locked
    /// </summary>
    /// <returns></returns>
    public boolean GetShouldLock()
    {
        return shouldLock;
    }

    /// <summary>
    /// Retrives if the room is cleared of enemies
    /// </summary>
    /// <returns></returns>
    public boolean GetIsClear()
    {
        return isClear;
    }

    /// <summary>
    /// Retrives the ground friction of the room
    /// </summary>
    /// <returns>A float value</returns>
    public float GetGroundFriction()
    {
        return groundFriction;
    }

    /// <summary>
    /// Retrives the rooms character manager
    /// </summary>
    /// <returns>A type CharacterManager</returns>
    public CharacterManager GetCharacterManager()
    {
        return characterManager;
    }

    /// <summary>
    /// Retrives the rooms inventory
    /// </summary>
    /// <returns></returns>
    public Inventory getInventory()
    {
        return inventory;
    }

     /// <summary>
    /// Adds the connecting doors to the room
    /// </summary>
    /// <param name="player"></param>
    /// <param name="adjRoom"></param>s
    /// <param name="wallSide"></param>
    /// <param name="sprite"></param>
    public void AddDoors(Player player ,Room adjRoom,Room[] map,int wallSide, boolean isLocked)
    {
        //Adds a door
        door.add(new Door(player, adjRoom, map, wallSide, isLocked));
    }

    /// <summary>
    /// Spawns a projectile
    /// </summary>
    /// <param name="newProj">A non null type Projectile </param>
    public void AddProj(Projectile newProj)
    {
        //Adds the new projetile to the list
        proj.add(newProj);
    }

    /// <summary>
    /// Locks a door
    /// </summary>
    /// <param name="side"></param>
    public void LockDoor(int side)
    {
        //Locks the current door
        door.get(side).LockDoor();
    }

    //Updates the room
    public void Update(Player player, Room[] map)
    {
        //Loops through all projectiles
        for (int i = 0; i < proj.size(); i++)
        { 
            //Updates the projectiles
            if(proj.get(i).GetIsFriendly())
            {
                //Updates the current freindly projectile
                proj.get(i).Update(characterManager.GetCharacter());
            }
            else
            {
                //Update the current monster projectile
                proj.get(i).Update(player);
            }

            //Deletes the projectile if its is not alive
            if(!proj.get(i).GetIsAlive())
            {
                //Deletes the projectile
                proj.remove(i);
            }
        }

        //Loops through and updates all doors
        for (int i = 0; i < door.size(); i++)
        {
            //Updates the current door
            door.get(i).Update(isClear, map);
        }

        //Updates the charcter manager
        characterManager.UpdateManager();

        //Updates the inventory
        inventory.UpdateInventory(player);

        //Checks if the current room has any monsters
        if(characterManager.GetCharacter()[Data.MONSTER].size() == 0 &&
           characterManager.GetCharacter()[Data.BOSS].size() == 0)
        {
            //The room is cleared if monsters
            //Opens all doors
            for(int i = 0; i < door.size(); i++)
            {
                //Opens the current door
                door.get(i).SetIsOpen(true);

                //Sets the current room to be cleared of enemies
                isClear = true;
            }
        }
    }

    /// <summary>
    /// Spawns a set of enemies
    /// </summary>
    public void Populate(Player player)
    {

    }

    /// <summary>
    /// Sets up all base data for the nodes
    /// </summary>
    public void SetUpNodes()
    {
        //Sets the number of nodes wide and high
        nodesHigh = Data.roomBoundary.height / nodeSize;
        nodesWide = Data.roomBoundary.width / nodeSize;

        //Creates all the nodes
        node = new Rectangle[nodesHigh * nodesWide];

        //Loops through ands sets up all node rectangles
        for(int i = 0; i < node.length; i++)
        {
            //Sets the location of the tile
            node[i] = new Rectangle((i % nodesWide) * nodeSize + Data.roomBoundary.x, (int)(i / nodesWide) * nodeSize + Data.roomBoundary.y,
                                    nodeSize, nodeSize);
        }

        //Sets the maximum and current spawn nodes based on difficulty
        maxSpawnNode = 10;
        spawnNodeAmount = Data.getRandomNumber(0, maxSpawnNode);
    }

    /// <summary>
    /// Spawns monsters in the room
    /// </summary>
    /// <param name="player"></param>
    public void SpawnMonsters(Player player)
    {
        
    }

    /// <summary>
    /// Sets up all spawning nodes
    /// </summary>
    public void SetUpSpawnNodes()
    {
        //Sets up all spawn nodes
        spawnNode = new ArrayList<Rectangle>();
    }

    /// <summary>
    /// Deletes all projectiles in the room
    /// </summary>
    public void WipeProjectiles()
    {
        //Clears all projectiles
        proj.clear();
    }

    /// <summary>
    /// Draws the room
    /// </summary>
    /// <param name="spriteBatch"></param>
    public void Draw(Graphics2D gfx)
    {
        Sprites.backGround.Draw(gfx);

        //Draws all projectiles
        for (int i = 0; i < proj.size(); i++)
        {
            //Draws the current projectile
            proj.get(i).getSprite().Draw(gfx);
        }

        //Draws all characters in the room
        characterManager.DrawCharacter(gfx);

        //Draws all in world items
        inventory.Draw(gfx);

        //Draws all doors
        for(int i = 0; i < door.size(); i++)
        {
            //Draws the current door
            door.get(i).Draw(gfx);
        }
    }
}
