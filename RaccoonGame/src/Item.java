import java.awt.Point;

import com.engine.core.gfx.SpriteSheet;

public class Item extends Interactable{
    //Tracks if the item has been used
    protected boolean isUsed;

    //Tracks what type of item it is
    protected int itemType;
    protected int itemID;

    //Tracks the owner of the item
    protected Character owner;

    public Item(boolean inWorld, Character owner ,Room currentRoom, Point pos, SpriteSheet sprite)
    {
        super(currentRoom,sprite);
        //Sets the current position
        currentPos = pos;

        //Sets the hit box to be based on position
        hitBox.x = (int)currentPos.x;
        hitBox.y = (int)currentPos.y;

        //Sets the base max accel
        maxAccel = new Vector2(100, 100);

        //Sets the bse ground friction
        groundFriction = 10;
    }

    /// <summary>
    /// Retrives if the item has been used
    /// </summary>
    /// <returns></returns>
    public boolean GetIsUsed()
    {
        //Returns is used
        return isUsed;
    }

    /// <summary>
    /// Retrives the current item type
    /// </summary>
    /// <returns>A int type number</returns>
    public int GetItemType()
    {
        //Return itemType
        return itemType;
    }

    /// <summary>
    /// Retreives the item ID
    /// </summary>
    /// <returns>A int ID number</returns>
    public int GetItemID()
    {
        //Returns the ID
        return itemID;
    }

    /// <summary>
    /// Sets the owner as the given character
    /// </summary>
    /// <returns></returns>
    public void SetOwner(Character owner)
    {
        //Sets the owner
        this.owner = owner;
    }

    /// <summary>
    /// Applies the current items effect
    /// </summary>
    /// <param name="player">A type Player</param>
    public void DeliverPayload()
    {
        //Sets the item as used
        isUsed = true;
    }

    /// <summary>
    /// Makes items move apart when collided
    /// </summary>
    public void ItemCollision()
    {
        //Tracks the current item that is being checked
        Item currentItem;

        //Loops for all items in the current room
        for (int i = 0; i < Data.itemTypeAmount; i++)
        {
            //Loops through all items of the current item types
            for (int j = 0; j < currentRoom.getInventory().GetItems()[i].size(); j++)
            {
                //Sets the current item
                currentItem = currentRoom.getInventory().GetItems()[i].get(j);

                //Checks if the entity and the current character is colliding
                if (Data.IsCollided(currentItem.getHitBox(), hitBox) &&
                    currentItem != this)
                {
                    //Tracks the pushing direction
                    Vector2 pushDir = new Vector2();

                    //Pushes back the current item away from the other item
                    pushDir = new Vector2(hitBox.getCenterX() - currentItem.getHitBox().getCenterX(),
                                          hitBox.getCenterY() - currentItem.getHitBox().getCenterY());

                    //Randomly generates a direction if the direction vectors are zero
                    if (pushDir.x == 0f && pushDir.y == 0f)
                    {
                        //Generates and normalizes a push direction
                        pushDir = Data.GenRandDir();
                    }

                    //Normalizes the push direction and scale it based on pushing force
                    pushDir.normalize();

                    //Applies the pushing direction and pushing force to velocity
                    currentVelocity.x += pushDir.x * pushForce;
                    currentVelocity.y += pushDir.y * pushForce;
                }
            }
        }
    }
}
