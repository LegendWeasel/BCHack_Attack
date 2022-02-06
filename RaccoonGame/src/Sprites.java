import com.engine.core.gfx.*;

public class Sprites {
        //Tracks the main games background
        public static SpriteSheet backGround = new SpriteSheet();

        //Stores the sprites of all the items
        public static SpriteSheet[] passiveSprite = new SpriteSheet[Data.pItemAmount];
        public static SpriteSheet[] resourceSprite = new SpriteSheet[Data.rItemAmount];

        //Stores the sprites of monsters and boss
        public static SpriteSheet[] monsterSprite = new SpriteSheet[Data.enemyTypeAmount];
        public static SpriteSheet[] bossSprite = new SpriteSheet[Data.bossTypeAmount];

        //Stores the player sprite
        public static SpriteSheet playerSprite;

        //Stores the projectile sprite
        public static SpriteSheet projSprite;

        //Stores the trapdoor sprite
        public static SpriteSheet zoneDoor;

        //Stores the door sprites for 
        public static SpriteSheet[] doorSprite = new SpriteSheet[Data.numberOfDir];
        public static SpriteSheet[] closedDoorSprite = new SpriteSheet[Data.numberOfDir];
        public static SpriteSheet[] lockedDoorSprite = new SpriteSheet[Data.numberOfDir];
}
