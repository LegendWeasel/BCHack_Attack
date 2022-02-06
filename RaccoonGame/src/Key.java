import java.awt.Point;

import com.engine.core.gfx.SpriteSheet;

public class Key extends Resource{
    public Key(boolean inWorld, Character owner, Room currentRoom, Point pos, SpriteSheet sprite)
    {
        super(inWorld, owner, currentRoom, pos, Sprites.resourceSprite[Data.DOORKEY]);
        //Sets the item ID as the key ID
        itemID = Data.DOORKEY;
    }

    /// <summary>
    /// Gives the player one key
    /// </summary>
    @Override
    public void DeliverPayload()
    {
        //Gives the character one key
        owner.SetKeyAmount(owner.GetKeys() + 1);

        super.DeliverPayload();
    }
}
