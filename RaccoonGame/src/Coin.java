import com.engine.core.gfx.SpriteSheet;
import java.awt.Point;

public class Coin extends Resource{
    public Coin(boolean inWorld, Character owner, Room currentRoom, Point pos, SpriteSheet sprite) 
    {
        super(inWorld, owner, currentRoom, pos, Sprites.resourceSprite[Data.COIN]);
        //Sets the item ID as the coin ID
        itemID = Data.COIN;
    }

    /// <summary>
    /// Gives the player one coin
    /// </summary>
    @Override
    public void DeliverPayload()
    {
        //Gives the character one key
        owner.SetCoinAmount(owner.GetCoins() + 1);

        super.DeliverPayload();
    }
}
