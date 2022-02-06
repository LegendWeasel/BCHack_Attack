import java.util.ArrayList;
import java.util.List;

import com.engine.core.gfx.SpriteSheet;


public abstract class Character extends Interactable {
	//attributes
	int health;
	int attackDamage;
	int movementSpeed;
	boolean isEnemy;
	
	//Tracks if the player stats have changed
    protected boolean isDirty;

    //Tracks the current and bases stats of projectiles
    protected float[] projStats = new float[Data.statAmount];
    protected float[] baseProjStats = new float[Data.statAmount];
    protected float firingTimer = 0;

    //Tracks projectile modifiers
    protected boolean[] projMods;

    //Tracks the buffs on each projectile
    protected ArrayList<Float>[] projBuff;

    //Tracks all the enemies of the current character
    protected List<Character> enemy = new ArrayList<Character>();

    //Tracks base character stats
    protected int maxHP;
    protected int baseHP;
    protected int currentHP;

    //Tracks if the character is affected by knock back
    protected boolean knockbackImumne;

    //Tracks the characters basic state
    protected boolean isAlive;
    protected boolean inAir;

    //Tracks the relation with other characters
    protected boolean isFriendly;

    //Tracks the players invincible data
    protected boolean isInvuln = false;
    protected float maxInvulnTime;
    protected float invulnTimer;

    //Tracks the character inventory
//    protected CharacterInventory inventory;

    //Tracks the visual oppacity data of the character
    protected byte invulnAlpha = (byte) 150;
    protected byte baseAlpha = (byte) 255;

    //Tracks the amount of character held resources
    protected int coinAmount;
    protected int keyAmount;

    public Character(Room currentRoom, SpriteSheet sprite)
    {
    	super(currentRoom, sprite);
    	
        //Sets the current room
        this.currentRoom = currentRoom;

        //Sets the movement data of the character
        this.currentVelocity.setX(0);
        this.currentVelocity.setY(0);
        accel.x= 0;
        accel.y= 0;
        maxAccel = new Vector2(100,100);

        //Set the array of touching wall booleans
        setTouchingWall(new boolean[4]);

        //Sets the array of lists of projectile buffs
        projBuff = new ArrayList[Data.statAmount];

        //Sets the array of projectile modifiers
        projMods = new boolean[Data.projModAmount];

        //Loops through all the projectile buffs
        for (int i = 0; i < Data.statAmount; i++)
        {
            //Sets the current projBuff list
            projBuff[i] = new ArrayList<Float>();
        }

        //Sets the pushing speed
        pushForce = 75;

        //Sets the base character to be hostile
        isFriendly = false;

        //Sets the base invunerability data
        maxInvulnTime = 1;
        invulnTimer = 0;

        //Sets the base immunity false
        knockbackImumne = false;

        //Create an inventory for the charcter
//        inventory = new CharacterInventory(currentRoom, this);

        //Sets the visual data of the character
        this.sprite = sprite;
        this.spriteAnim = spriteAnim;
    }

    /// <summary>
    /// Retrives the current coin amount of the character
    /// </summary>
    /// <returns>The coin amount as a int</returns>
    public int GetCoins()
    {
        return coinAmount;
    }

    /// <summary>
    /// Retrives the current key amount of the character
    /// </summary>
    /// <returns>The key amount as a int</returns>
    public int GetKeys()
    {
        return keyAmount;
    }

    /// <summary>
    /// Retrives the current hit points of the character
    /// </summary>
    /// <returns>The hit points as a int</returns>
    public int GetHP()
    {
        //Returns the current HP
        return currentHP;
    }

    /// <summary>
    /// Retrives the maximum hit points of the character
    /// </summary>
    /// <returns></returns>
    public int GetMaxHP()
    {
        //Returns the max HP
        return maxHP;
    }

    /// <summary>
    /// Retrives the living state of the character
    /// </summary>
    /// <returns>A bool representing if alive</returns>
    public boolean GetIsAlive()
    {
        //Returns is alive
        return isAlive;
    }
    
    /// <summary>
    /// Retrives the relationships of the character
    /// </summary>
    /// <returns></returns>
    public boolean GetIsFriendly()
    {
        return isFriendly;
    }

    /// <summary>
    /// Retrives the projectile stats
    /// </summary>
    /// <returns>Returns an array of floats</returns>
    public float[] GetProjStats()
    {
        return projStats;
    }

    /// <summary>
    /// Retrives what projectile mods the character has
    /// </summary>
    /// <returns></returns>
    public boolean[] GetProjMods()
    {
        return projMods;
    }

    /// <summary>
    /// Retrives the current inventory of the character
    /// </summary>
    /// <returns></returns>
//    public CharacterInventory GetInventory()
//    {
//        return inventory;
//    }

    /// <summary>
    /// Retrives the projectile buffs
    /// </summary>
    /// <returns></returns>
    public List<Float>[] GetProjBuff()
    {
        return projBuff;
    }

    /// <summary>
    /// Sets the dirty state of the character
    /// </summary>
    /// <param name="isDirty"></param>
    public void SetIsDirty(boolean isDirty)
    {
        this.isDirty = isDirty;
    }

    /// <summary>
    /// Retrives if the character is invuln
    /// </summary>
    /// <returns></returns>
    public void SetIsInvuln(boolean isInvuln)
    {
        //Sets the invuln state as the given data
        this.isInvuln = isInvuln;
    }

    /// <summary>
    /// Sets the projectile stats 
    /// </summary>
    /// <param name="projStats"></param>
    public void SetProjStats(float[] projStats)
    {
        //Sets the new projectile stat
        this.projStats = projStats;
    }

    /// <summary>
    /// Sets the projectile stats 
    /// </summary>
    /// <param name="statType">A int representing stat type</param>
    /// <param name="newStat">A float representing the new stat</param>
    public void SetProjStats(int statType, float newStat)
    {
        //Sets the current stat
        this.projStats[statType] = newStat;
    }

    /// <summary>
    /// Sets a new key amount
    /// </summary>
    /// <param name="keyAmount"></param>
    public void SetKeyAmount(int keyAmount)
    {
        this.keyAmount = keyAmount;
    }

    /// <summary>
    /// Sets a new coin amount
    /// </summary>
    /// <param name="coinAmount"></param>
    public void SetCoinAmount(int coinAmount)
    {
        this.coinAmount = coinAmount;
    }

    /// <summary>
    /// Sets a new given hit point value
    /// </summary>
    /// <param name="hp"></param>
    public void SetHP(int hp)
    {
        this.currentHP = hp;
    }

    /// <summary>
    /// Updates the character
    /// </summary>
    public void Update()
    {
        //Sets the character as clean
        isDirty = false;

        //Upadates the inventory
//        inventory.UpdateInventory(this);

        //Calls Attack
        Attack();

        //Calls Invulnerability
        Invulnerability();

        //Calls CalcMoveDir
        CalcMoveDir();

        Update();
    }

    /// <summary>
    /// Applies all items and buffs
    /// </summary>
    public void ApplyBuffs()
    {
        //Applies buffs if the character has changed
        if(isDirty)
        {
            //Applies all of the passive and resource items effect
//            inventory.ApplyPassiveItem();
//            inventory.ApplyResource();

            //Applies all proj buffs
            for (int i = 0; i < Data.statAmount;i++)
            {
                //Tracks the total combined buff value 
                float totalBuff = 1;

                //Loops through and combines all buffs of the current stat type
                for (int j = 0; j < projBuff[i].size(); j++)
                {
                    //Products the current buff
                    totalBuff *= projBuff[i].get(j);
                }

                //Applies the stats of the projectile
                projStats[i] = baseProjStats[i] * totalBuff;
            }
        }
    }

    /// <summary>
    /// Allows the character to attack
    /// </summary>
    public void Attack()
    {
        //Base
    }

    /// <summary>
    /// Hits the player to deal damage
    /// </summary>
    /// <param name="dmg"></param>
    /// <param name="velocity"></param>
    /// <param name="size"></param>
    public void Hit(float dmg, Vector2 projVelocity,float size)
    {
        //Damages the character if not invunerable
        if(!isInvuln)
        {
            //Increases the invunderability timer
            invulnTimer += Data.deltaTime;

            //Subtracts the current hit points by the set damage rounded up
            currentHP -= (int)Math.ceil(dmg);

            //Kills the character if HP drops to or below 0
            if(currentHP <= 0)
            {
                //Calls Death
                Death();
            }

            //Applies knowckback if not immune
            if(!knockbackImumne)
            {
                //Sets the velocity of the character relative to the projectile velocity as knockback
                currentVelocity.y = projVelocity.y * 0.5f;
                currentVelocity.x = projVelocity.x * 0.5f;
            }     
        }
    }

    public void CalcMoveDir()
    {
        //Base
    }

    /// <summary>
    /// Makes the character invincable temporarily
    /// </summary>
    public void Invulnerability()
    {
        //Decays the invuln period 
        if(invulnTimer != 0)
        {
            //Sets the character as invunerable
            isInvuln = true;

            //Updates the timer
            invulnTimer += Data.deltaTime;

            //Makes the player vunerable after a set time
            if (invulnTimer > maxInvulnTime)
            {
                //Makes the player vulnerable and reset the invuln timer
                isInvuln = false;
                invulnTimer = 0;
            }
        }

//        //Changes the player alpha if invunerable
//        if(isInvuln)
//        {
//            //Sets the sprite alpha to the invuln alpha
//            spriteColor.A = invulnAlpha;
//        }
//        else
//        {
//            //Restores the sprite opacity
//            spriteColor.A = baseAlpha;
//        }

    }

    /// <summary>
    /// Stops all character movement
    /// </summary>
    public void StopMotion()
    {
        //Stops all velocity
        currentVelocity = new Vector2(0, 0);

        //Stops all acceleration
        accel = new Vector2(0, 0);
    }

    /// <summary>
    /// Kills the character
    /// </summary>
    public void Death()
    {
        //Tracks the item to be dropped
//        Item droppedItem;

//        //Loops for all items and dropps them
//        for (int i = 0; i < inventory.GetItems().Length; i++)
//        {
//            //Loops for all of a item type
//            for(int j =0; j < inventory.GetItems()[i].Count; j++)
//            {
//                //Sets the current dropped item
//                droppedItem = inventory.GetItems()[i][j];
//                currentRoom.GetRoomInventory().AddItem(true, droppedItem.GetItemType(), droppedItem.GetItemID(), currentPos);
//            }
//        }

        //Set alive to false
        isAlive = false;
    }
}
