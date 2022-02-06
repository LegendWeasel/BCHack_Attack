import com.engine.core.gfx.SpriteSheet;

public class BossRoom extends Room{
    //Tracks the trap door to move to the next floor
    protected ZoneDoor zoneDoor;

    public BossRoom(Data data,int roomID)
    {
        super(data, roomID);
    }

    /// <summary>
    /// Updates the boss room
    /// </summary>
    /// <param name="player"></param>
    /// <param name="map"></param>
    @Override
    public void Update(Player player, Room[] map)
    {
        //Adds a trap door to the room if the boss is dead
        if(characterManager.GetCharacter()[Data.BOSS].Count() == 0)
        {
            zoneDoor = new ZoneDoor(this, Sprites.trapdoor, null);
        }

        //Checks for collisions if the trap door exists
        if(trapdoor != null)
        {
            //Checks of the trap door has collided with the player
            trapdoor.PlayerCollison(player);
        }

        super.Update(player, map);
    }

    /// <summary>
    /// Spawns the boss of the room
    /// </summary>
    /// <param name="player"></param>
    @Override
    public void Populate(Player player)
    {
        //Adds a random boss enemy
        characterManager.AddBoss((Data.curZone), 
                                 new Vector2(Data.roomBoundary.getCenterX(), Data.roomBoundary.getCenterY()), player);
        super.Populate(player);
    }

    /// <summary>
    /// Draws the room
    /// </summary>
    /// <param name="spriteBatch"></param>
    @Override
    public void Draw(SpriteSheet sprite)
    {
        base.Draw(spriteBatch);

        //Draws the trapdoor if it exists
        if (trapdoor != null)
        {
            trapdoor.Draw(spriteBatch);
        }
        
    }
}
