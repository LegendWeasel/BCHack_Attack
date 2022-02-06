import com.engine.core.gfx.SpriteSheet;
import java.awt.Point;

public class ShopItem extends Item{
    //Tracks the price of the current item
    private int price;
    private int basePrice;
    private int maxPrice;

    //Tracks if the item should be deleted
    private boolean isAlive = true;

    public ShopItem(int itemType, int itemID, boolean inWorld, Character owner, Room currentRoom, Point pos, SpriteSheet sprite) 
    {
        super(inWorld,owner,currentRoom,pos,sprite);
        this.itemType = itemType;

        this.itemID = itemID;

        //Sets the base price
        basePrice = 5;

        //Sets the max price depending on difficulty
        maxPrice = (int)(basePrice * Data.curZone);

        //Sets the price as a random value between base and max prices
        price = Data.getRandomNumber(basePrice, maxPrice);
    }

    /// <summary>
    /// Retrives the price of the item
    /// </summary>
    /// <returns></returns>
    public int GetPrice()
    {
        return price;
    }

    /// <summary>
    /// Retrives if the item is alive
    /// </summary>
    /// <returns></returns>
    public boolean GetIsAlive()
    {
        return isAlive;
    }

    /// <summary>
    /// Lets the player buy the item if it has enough money
    /// </summary>
    /// <param name="player">A type Player</param>
    public void BuyItem(Player player)
    {
        //Tracks how much money the player has
        int playerCoins = player.GetCoins();

        //Checks if the player can afford the item
        if(playerCoins >= price)
        {
            //Reduces the amount of money the player has by the cost
            player.SetCoinAmount(playerCoins - price);

            //Spawns in the current item
            currentRoom.getInventory().AddItem(true,itemType,itemID,currentPos);

            //Marks the item to be deleted
            isAlive = false;
        }
    }
}
