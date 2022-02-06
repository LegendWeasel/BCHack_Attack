
import java.awt.Rectangle;
import java.util.List;

public class Room {
    //Tracks which element the current room is in
    protected int roomID;

    //Tracks all charcters in the room
    protected CharacterManager characterManager;

    //Tracks all items in the room
    protected RoomInventory inventory;

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

    public Room(int roomID)
    {
        //Sets the room ID
        this.roomID = roomID;

        //Creates the player manager
        characterManager = new Characters.CharacterManager(this);

        //Creates the inventory
        inventory = new Items.RoomInventory(this);

        //Creates the door list
        door = new List<Door>();

        //Sets the baseground friction
        groundFriction = 10f;

        //Sets the node size
        nodeSize = 20;

        //The current room should not be locked
        shouldLock = false;

        //Sets up all the room nodes
        SetUpNodes();            ;
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
    public RoomInventory GetRoomInventory()
    {
        return inventory;
    }
}
