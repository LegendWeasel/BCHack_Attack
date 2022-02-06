import java.awt.Point;

import com.engine.core.gfx.SpriteSheet;


public class HeartPickup extends Resource{

    public HeartPickup(boolean inWorld, Character owner, Room currentRoom, Point pos, SpriteSheet sprite)
    {
        super(inWorld, owner, currentRoom, pos, Sprites.resourceSprite[Data.HEART]);
        //Sets the item ID as the heart ID
        itemID = Data.HEART;
    }

    /// <summary>
    /// Gives the player one hp
    /// </summary>
    @Override
    public void DeliverPayload()
    {
        //Restores the players hit points by one up to a max
        if(owner.GetHP() != owner.GetMaxHP())
        {
            //Gives the character one hit point
            owner.SetHP(owner.GetHP() + 1);
        }
        
        super.DeliverPayload();
    }
}
