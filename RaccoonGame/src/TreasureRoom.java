import java.awt.Point;

public class TreasureRoom extends Room{
    public TreasureRoom(Data data,int roomID)
    {
        super(data, roomID);
        //The current room should be locked
        shouldLock = true;
    }

    /// <summary>
    /// Populates the treasure room with a random item
    /// </summary>
    /// <param name="player"></param>
    @Override
    public void Populate(Player player)
    {
        //Adds a random item
        inventory.AddItem(true, new Point((int)Data.roomBoundary.getCenterX(), (int)Data.roomBoundary.getCenterY()));

        super.Populate(player);
    }
}
