import java.awt.Point;

public class CharacterInventory extends Inventory{
    //Tracks the player
    private Character owner;

    //Tracks the current active item
    private Item activeItem;

    public CharacterInventory(Room currentRoom, Character character)
    {
        super(currentRoom);

        //Sets the player
        this.owner = character;
    }

    /// <summary>
    /// Updates all the items in the inventory
    /// </summary>
    /// <param name="character"></param>
    @Override
    public void UpdateInventory(Character character)
    {
        //Updates the active item if it exists
        if(activeItem != null)
        {
            //Updates the item
            activeItem.update();
        }

        super.UpdateInventory(character);
    }

    /// <summary>
    /// Transfers an item between inventories to the inventory
    /// </summary>
    /// <param name="newItem"></param>
    public void TransferItem(Item newItem)
    {
        //The inventory has been modified
        isDirty = true;

        //Sets the owner of the item as the inventories owner
        newItem.SetOwner(owner);

        //Tracks the new item type
        int itemType = newItem.GetItemType();

        //Transfers the item based on item type
        switch (itemType)
        {
            case Data.ITEMACTIVE:
                {
                    //Adds the current active item into the world if it exists
                    if(activeItem != null)
                    {
                        currentRoom.getInventory().AddItem(true, activeItem.GetItemType(), activeItem.GetItemID(), new Point(owner.getCurrentPos().x, owner.getCurrentPos().y + 30));
                    }
                   
                    //Sets the new item as the current active item
                    activeItem = newItem;
                    break;
                }
            case Data.ITEMPASSIVE:
                {
                    //Transfers the passive item
                    item[Data.ITEMPASSIVE].add(newItem);
                    break;
                }
            case Data.RESOURCE:
                {
                    //Transfers the resource
                    item[Data.RESOURCE].add(newItem);
                    break;
                }
        }
    }

    /// <summary>
    /// Applies all passive items effects on the player
    /// </summary>
    /// <param name="character">A type character</param>
    public void ApplyPassiveItem()
    {
        //Loops through all passive items
        for (int j = 0; j < item[Data.ITEMPASSIVE].size(); j++)
        {
            //Applies the current item
            if (!item[Data.ITEMPASSIVE].get(j).GetIsUsed())
            {
                //Updates the current item
                item[Data.ITEMPASSIVE].get(j).DeliverPayload();
            }
        }
    }

    /// <summary>
    /// Applies all active items on the character
    /// </summary>
    public void ApplyActiveItem()
    {
        //Activate the item if the player has one
        if(activeItem != null)
        {
            //Applies the items effect if it has not been used
            if (!activeItem.GetIsUsed())
            {
                //Applies the items effect
                activeItem.DeliverPayload();
            }
        }

    }

    /// <summary>
    /// Applies all resources
    /// </summary>
    public void ApplyResource()
    {
        //Loops through all passive items
        for (int j = 0; j < item[Data.RESOURCE].size(); j++)
        {
            //Checks if the item has been used
            if (!item[Data.RESOURCE].get(j).GetIsUsed())
            {
                //Applies the current item
                item[Data.RESOURCE].get(j).DeliverPayload();

                //Removes the resource
                item[Data.RESOURCE].remove(j);
            }
        }
    }
}
