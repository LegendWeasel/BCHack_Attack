import com.engine.core.gfx.SpriteSheet;
import java.awt.Point;

class Resource extends Item{
    public Resource(boolean inWorld,Character owner , Room currentRoom, Point pos, SpriteSheet sprite)
    {
        super(inWorld,owner,currentRoom,pos, sprite);

        //Sets the item type to be RESOURCE
        itemType = Data.RESOURCE;
    }
}