import java.util.ArrayList;
import java.util.List;

import com.engine.core.gfx.SpriteSheet;

public class CharacterManager {
    //Tracks all characters
    private List<Character>[] character;

    //Tracks the current room
    private Room currentRoom;

    //Tracks all the monster sprites
    private SpriteSheet[] monsterSprite;
    private SpriteSheet[] bossSprites;

    //Sets the maximum about of characters allowed in a room
    private int characterLimit = 15;

    public CharacterManager(Room currentRoom)
    {
        //Sets the current room
        this.currentRoom = currentRoom;

        //Sets the array of lists of characters
        character = new ArrayList[2];

        //Sets the lists of monsters and bosses 
        character[Data.MONSTER] = new ArrayList<Character>();
        character[Data.BOSS] = new ArrayList<Character>();

        //Sets all monster sprites
        monsterSprite = new SpriteSheet[4];
        monsterSprite[Data.MOLE] = Sprites.monsterSprite[Data.MOLE];
        monsterSprite[Data.BEE] = Sprites.monsterSprite[Data.BEE];

        //Sets all boss sprites
        bossSprites = new Texture2D[2];
        bossSprites[Data.DOG] = Sprites.bossSprite[Data.DOG];
        bossSprites[Data.CHEF] = Sprites.bossSprite[Data.CHEF];
    }

    /// <summary>
    /// Retrives the characters
    /// </summary>
    /// <returns>A type character</returns>
    public List<Character>[] GetCharacter()
    {
        //Returns all the characters
        return this.character;
    }


    /// <summary>
    /// Updates all characters
    /// </summary>
    public void UpdateManager()
    {
        //Calls Update for all characters
        for (int i = 0; i < character.length ; i++)
        {
            for(int j = 0; j < character[i].size(); j++ )
            {
                //Updates the current monster
                character[i].get(j).Update();

                //Removes the monster if dead
                if (!character[i].get(j).GetIsAlive())
                {
                    //Removes the monster
                    character[i].remove(j);
                }
            }
        }
    }

    /// <summary>
    /// Spawns a given mosnter to the current room
    /// </summary>
    public void AddMonster(int monsterType, Vector2 currentPos,Player player)
    {
        if (!(character[Data.MONSTER].size() >= characterLimit))
        {
            //Spawns the given monster type
            switch (monsterType)
            {
                case Data.MOLE:
                    {
                        //Spawns a dot fly
                        character[Data.MONSTER].add(new Mole(player, currentRoom, currentPos, monsterSprite[Data.MOLE], null));
                        break;
                    }
                case Data.BEE:
                    {
                        //Spawns a dung drop
                        character[Data.MONSTER].add(new Bee(player, currentRoom, currentPos, monsterSprite[Data.BEE], null));
                        break;
                    }
            }
        }
    }

    /// <summary>
    /// Creates the boss of the floor
    /// </summary>
    public void AddBoss(int bossType,Vector2 currentPos, Player player)
    {
        //Spawns the given boss type
        switch(bossType)
        {
            case Data.DOG:
                {
                    //Adds a Lord Fly
                    character[Data.BOSS].add(new Dog(player, currentRoom, currentPos, bossSprites[Data.DOG], null));
                    break;
                }
            case Data.CHEF:
                {
                    //Adds a Lord Dung
                    character[Data.BOSS].Add(new Chef(player, currentRoom, currentPos, bossSprites[Data.DOG], null));
                    break;
                }
        }
    }

    /// <summary>
    /// Draws all characters
    /// </summary>
    /// <param name="spritebatch">The graphics object to draw to</param>
    public void DrawCharacter(SpriteBatch spritebatch)
    {
        //Draws all enemies
        for (int i = 0; i < character.length; i++)
        {
            for(int j = 0; j < character[i].size(); j++)
            {
                //Draws the current character
                character[i].get(j).Draw(spritebatch);
            }
        }
    }
}
