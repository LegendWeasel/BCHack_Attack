import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

import com.engine.core.gfx.SpriteSheet;
import java.awt.Graphics2D;

public class Inventory {
    //Tracks if the class has its data changed
    protected boolean isDirty;

    //Tracks all the items in the inventory
    protected List<Item>[] item;

    //Tracks the current room
    protected Room currentRoom;

    public Inventory(Room currentRoom)
    {
        //Sets the current room
        this.currentRoom = currentRoom;

        //Sets the array of lists of items
        item = new ArrayList[Data.itemTypeAmount];

        //Sets the lists of item types
        item[Data.RESOURCE] = new ArrayList<Item>();
        item[Data.ITEMPASSIVE] = new ArrayList<Item>();
        item[Data.ITEMACTIVE] = new ArrayList<Item>();
    }

    /// <summary>
    /// Retrives the dirty bool
    /// </summary>
    /// <returns>A boolean</returns>
    public boolean GetIsDirty()
    {
        return isDirty;
    }

    /// <summary>
    /// Retrives all the items
    /// </summary>
    /// <returns></returns>
    public List<Item>[] GetItems()
    {
        return item;
    }

    /// <summary>
    /// Updates all the items in the inventory
    /// </summary>
    /// <param name="character"></param>
    public void UpdateInventory(Character character)
    {
        //Resets the dirty bool
        isDirty = false;

        //Loops through all items
        for (int i = 0; i < item.length; i++)
        {
            for (int j = 0; j < item[i].size(); j++)
            {
                //Checks for item item collisions
                item[i].get(j).ItemCollision();

                //Updates the current item
                item[i].get(j).update();

                //Checks if the player has collided with an item
                if (Data.IsCollided(character.getHitBox(), item[i].get(j).getHitBox()))
                {
                    //The current and players inventory has been modified
                    isDirty = true;
                    character.SetIsDirty(true);

                    //Gives the player the item
                    character.GetInventory().TransferItem(item[i].get(j));

                    //Removes the item from the current inventory
                    item[i].remove(j);
                }
            }
        }
    }
    /// <summary>
    /// Adds a spesified item to the inventory
    /// </summary>
    /// <param name="inworld">Bool stating in in world</param>
    /// <param name="itemType">Int representing data item type</param>
    /// <param name="itemID">int represnting item ID</param>
    /// <param name="pos">Vector2 representing current position</param>
    public void AddItem(boolean inWorld, int itemType, int itemID, Point pos)
    {
        //The inventory has been modified
        isDirty = true;

        //Adds a spesified item to the inventory
        switch(itemType)
        {
            case Data.ITEMPASSIVE:
            {
                //Adds the item based on ID
                item[itemType].add(new StatUpgrade(inWorld, null, currentRoom, pos, itemID, Sprites.passiveSprite[itemID]));
            }
            case Data.RESOURCE:
            {
                //Adds the item based on ID
                switch (itemID)
                {
                    case Data.COIN:
                        {
                            //Adds the resource
                            item[itemType].add(new Coin(inWorld, null, currentRoom, pos, null));
                            break;
                        }
                    case Data.KEY:
                        {
                            //Adds the resource
                            item[itemType].add(new Key(inWorld, null, currentRoom, pos, null));
                            break;
                        }
                    case Data.HEART:
                        {
                            //Adds the resource
                            item[itemType].add(new HeartPickup(inWorld, null, currentRoom, pos, null));
                            break;
                        }
                }
                break;
            }
                        
        }
    }

    /// <summary>
    /// Adds a random item to the inventory
    /// </summary>
    /// <param name="inWorld"></param>
    /// <param name="pos"></param>
    public void AddItem(boolean inWorld, Point pos)
    {
        //Tracks the random items type
        int itemType = Data.getRandomNumber(1, Data.itemTypeAmount-1);

        //Tracks the ID of the item to spawn
        int itemID;

        //Checks if there is a spawnable active item
        if (!Data.possiblePassive.isEmpty())
        {
            //Sets a random passive item id 
            itemID = Data.possiblePassive.pop();

            //Adds in the randomized item
            AddItem(inWorld, itemType, itemID, pos);
        }
        
    }

    /// <summary>
    /// Clears all items in the inventory
    /// </summary>
    public void Empty()
    {
        //Loops through all items
        for (int i = 0; i < item.length; i++)
        {
            //Clears all of the current item type
            item[i].clear();;
        }
    }

    /// <summary>
    /// Draws all in world items
    /// </summary>
    /// <param name="spriteBatch"></param>
    public void Draw(Graphics2D gfx)
    {
        //Loops through all items
        for(int i = 0; i < item.length; i++)
        {
            for(int j = 0; j < item[i].size(); j++)
            {
                //Draws the current item
                item[i].get(j).getSprite().Draw(gfx);
            }
        }
    }

}
