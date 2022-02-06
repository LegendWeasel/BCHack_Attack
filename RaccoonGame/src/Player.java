import com.engine.core.gfx.*;
import java.awt.Point;
import java.awt.Rectangle;

public class Player extends RangeCharacter{
    //Tracks which direction the projectile will be traveling
    private float projAngle = 0;

    private float firingTimer = 0;

    //Tracks if the player can move to the next floor
    private boolean canDecend = false;

    public Player(Room currentRoom, SpriteSheet sprite)
    {
        super(currentRoom,sprite);

        //Calls the SetBaseStats method
        SetBaseStats();
    }

    /// <summary>
    /// Retrives the dirty state of the player
    /// </summary>
    /// <returns></returns>
    public boolean GetIsDirty()
    {
        return isDirty;
    }

    /// <summary>
    /// Retrives if the player can decend
    /// </summary>
    /// <returns></returns>
    public boolean GetCanDecend()
    {
        return canDecend;
    }

    /// <summary>
    /// Sets a new room as the current one
    /// </summary>
    /// <param name="newRoom"></param>
    public void SetCurrentRoom(Room newRoom)
    {
        currentRoom = newRoom;
    }

    /// <summary>
    /// Sets the current position
    /// </summary>
    /// <param name="pos"></param>
    public void SetCurrentPos(Point pos)
    {
        this.currentPos = pos;
    }

    /// <summary>
    /// Sets if the player is to move to the next floor
    /// </summary>
    /// <param name="ready"></param>
    public void SetCanDecend(boolean canDecend)
    {
        this.canDecend = canDecend;
    }

     /// <summary>
    /// Updates the player
    /// </summary>
    @Override
    public void Update()
    {
        //Calls ApplyBuffs
        ApplyBuffs();

        updateFireingTimer();

        super.Update();
    }

    /// <summary>
    /// Allows the player to fire projectiles
    /// </summary>
    public void Attack(float projAngle)
    {
        if(firingTimer == 0) 
            currentRoom.AddProj(new Projectile(this, GenProjVel(projAngle), null));
    }

    public void updateFireingTimer() {
        //Updates the firing timer
        firingTimer += Data.deltaTime;

        //Updates the firing timer if the timer is running
        if (firingTimer != 0)
        {
            //Updates the firing timer
            firingTimer += Data.deltaTime;

            //Resets the timer after the fireing delay
            if (firingTimer > projStats[Data.FIRERATE])
            {
                //Resets the firing timer
                firingTimer = 0;
            }
        }
    }

    /// <summary>
    /// Generates the projectiles velocity
    /// </summary>
    /// <param name="dir">A positive int value</param>
    /// <returns>A non-zero vector</returns>
    private Vector2 GenProjVel(float projAngle)
    {
        //Stores a temprary direction vector for output
        Vector2 projDir = new Vector2();

        //Applies inaccuracy based on player stats
        projAngle += Data.getRandomNumber(-(int)projStats[Data.ACCURACY], (int)projStats[Data.ACCURACY]);

        //Converts angles from degrees to radians
        projAngle *= Data.degToRad;

        //Converts the angle to a direction vector
        projDir.x = (float)(Math.cos(projAngle));
        projDir.y = (float)(Math.sin(projAngle));

        projDir.x *= projStats[Data.SPEED];
        projDir.y *= projStats[Data.SPEED];

        projDir.x += currentVelocity.x * 0.5f;
        projDir.y += currentVelocity.y * 0.5f;

        //Returns the firing direction
        return projDir;
    }

    /// <summary>
    /// Reads player input and sets the correspoinding velocities
    /// </summary>
    public void CalcMoveDir(boolean[] moveDir)
    {

        //Moves the player based on the keyboard input
        if (moveDir[Data.UP])
        {
            //The player is accelerating up
            accel.y = -maxAccel.y;
        }
        if (moveDir[Data.DOWN])
        {
            //The player is accelerating down
            accel.y = maxAccel.y;
        }
        if (moveDir[Data.LEFT])
        {
            //The player is accelerating to the left
            accel.x = -maxAccel.x;
        }
        if (moveDir[Data.RIGHT])
        {
            //The player is accelerating to the right
            accel.x = maxAccel.x;
        }


        //Resets the acceleration if the corresponding keys are not down
        if(!moveDir[Data.UP] && !moveDir[Data.DOWN])
        {
            //Sets vertical accel to 0
            accel.y = 0;
        }
        if (!moveDir[Data.LEFT] && !moveDir[Data.RIGHT])
        {
            //Sets horizontal accel to zero
            accel.x = 0;
        }

        //Calls the base movement
        super.move();
    }

    /// <summary>
    /// Resets all character data to its base values
    /// </summary>
    public void SetBaseStats()
    {
        //Sets the dirty bool as false
        isDirty = false;

        //Sets the player as friendly
        isFriendly = true;

        //Sets the character health stats
        maxHP = 6;
        currentHP = maxHP;
        baseHP = maxHP;

        //Sets the base projectile stats
        baseProjStats[Data.SPEED] = 400f; //Moves 400 pixels/sec faster than the player
        baseProjStats[Data.DMG] = 1; //1 points of damage
        baseProjStats[Data.FIRERATE] = 0.4f; //0.5 second between each shot
        baseProjStats[Data.RANGE] = 500; //Range of 500 pixels
        baseProjStats[Data.AMOUNT] = 1; //1 projectile fired at once
        baseProjStats[Data.ACCURACY] = 3; //1 degrees of spread
        baseProjStats[Data.SIZE] = 30; //30 pixels wide

        //Sets the current projectile stats
        for (int i = 0; i < projStats.length; i++)
        {
            //Sets the projectile stat
            projStats[i] = baseProjStats[i];
        }

        //Tracks projectile modifiers
        projMods[Data.HOMING] = false;
        projMods[Data.SCATTER] = false;

        //Sets the player resources
        coinAmount = 0;
        keyAmount = 0;
        //inventory.Empty();

        //Sets player player state
        inAir = false;
        isAlive = true;

        //Sets the player invincability timer
        maxInvulnTime = 1;

        //Sets the player as invuln temporarily as they spawn in
        invulnTimer += Data.deltaTime;

        //Sets the ground friction data
        baseFriction = currentRoom.GetGroundFriction();
        groundFriction = baseFriction;

        //Sets the characters positional stats
        maxVelocity = new Vector2(250, 250);
        currentPos = new Point((int)Data.roomBoundary.getCenterX(), (int)Data.roomBoundary.getCenterY());
        hitBox = new Rectangle((int)currentPos.x, (int)currentPos.y, sprite.GetFrameWidth() << 1, sprite.GetFrameHeight() << 1);

        //Sets starting motion values to zero
        StopMotion();

        //The player starts with 1 key
        keyAmount = 1;
    }
}
