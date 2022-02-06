import java.util.ArrayList;
import java.util.List;

import com.engine.core.gfx.SpriteSheet;

public class ShopRoom extends Room {
    //Tracks the items for sale
    private List<ShopItem> shopItem;

    //Tracks the spawn positions of the items
    private List<Vector2> itemPos;

    //Tracks how many items per shop
    private int itemAmount = 3;

    public ShopRoom(Data data,int roomID)
    {
        super(data,roomID);
        //Sets 3 shop items randomly
        shopItem = new ArrayList<ShopItem>();
        itemPos = new ArrayList<Vector2>();

        //Sets the position of the items to be evenly spaced in the middle of the room
        for (int i = 0; i < itemAmount; i++)
        {
            //Sets the current item position
            itemPos.add(new Vector2(Data.roomBoundary.getCenterX() + (Data.roomBoundary.width / itemAmount * (i - 1)),
                                     Data.roomBoundary.getCenterY()));
        }

        //Tracks the ID of the item to spawn
        int itemID;

        //Creates all the shop item
        for (int j = 0; j < itemAmount; j++)
        {
            //Creates the item if there are unused ones
            if(Data.possiblePassive.size() > 0)
            {
                //Sets the ID as a random valid value
                itemID = Data.possiblePassive.pop();

                //Creates the shop item
                shopItem.Add(new ShopItem(Data.ITEMPASSIVE, itemID, true, null, this, itemPos[j], Sprites.passiveSprite[itemID], null));
            }
        }

        //The current room should be locked
        shouldLock = true;
    }

    /// <summary>
    /// Updates the shop
    /// </summary>
    /// <param name="player"></param>
    @Override
    public void Update(Player player, Room[] map)
    {
        //Loops for all items and buys them if possible
        for(int i = 0; i < shopItem.size(); i++)
        {
            //Checks if the player has collided with the item
            if (Data.IsCollided(player.getHitBox(),shopItem[i].GetDestRec()))
            {
                //Lets the player buy the item if it has enough money
                shopItem[i].BuyItem(player);
            }

            //Deletes any bought items
            if(!shopItem[i].GetIsAlive())
            {
                //Removes the current item
                shopItem.RemoveAt(i);
            }
        }
        
        super.Update(player, map);
    }

    /// <summary>
    /// Draws all the items in the room
    /// </summary>
    /// <param name="spriteBatch"></param>
    @Override
    public void Draw(SpriteSheet sprite)
    {
        super.Draw(sprite);

        //Loops through and draws all shop items
        for (int i =0; i< shopItem.Count; i++)
        {
            //Draws all shop items
            shopItem[i].Draw(spriteBatch);

            //Draws the prices for all the items
            spriteBatch.DrawString(Sprites.HUDFont, shopItem[i].GetPrice().ToString(), 
                                   new Vector2(shopItem[i].GetDestRec().X, shopItem[i].GetDestRec().Bottom), Color.White);
        }
    }
}
