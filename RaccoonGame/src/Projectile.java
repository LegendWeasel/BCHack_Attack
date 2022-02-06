import java.awt.Rectangle;
import com.engine.core.gfx.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Projectile extends Interactable{
    //Tracks who owns the projectile. Enemy or player
    private boolean isFriendly;
    
    //Tracks if the projectile should be deleted
    private boolean isAlive;

    //Tracks the projectile properties
    private float[] stat;
    private float distTraveled = 0;

    //Tracks the behaviors of the projectile
    private boolean isHoming;
    //Tracks the target of the projectile
    private Character target;
    
    public Projectile(Character owner, Vector2 velocity, SpriteSheet sprite)
    {
        super(owner.getCurrentRoom(), sprite);
        //Sets the velocity as the given value
        currentVelocity = velocity;
        maxVelocity = new Vector2(200,200);

        //Sets the maximum acceleration value
        maxAccel = new Vector2(10, 10);

        //Sets the ownership of the projectile
        this.isFriendly = owner.GetIsFriendly();

        //Sets the given behavior
        this.isHoming = owner.GetProjMods()[Data.HOMING];

        //Sets the projectile to not be deleted
        isAlive = true;

        //Sets the ground friction
        groundFriction = 0;

        //Sets the projectile stats as the player projectile stats
        stat = owner.GetProjStats();

        //Sets the positional data of the projectile
        currentPos = owner.getCurrentPos();
        hitBox = new Rectangle((int)currentPos.x, (int)currentPos.y, (int)stat[Data.SIZE], (int)stat[Data.SIZE]);

        //Sets the color of the projectile based on ownership
        if (this.isFriendly)
        {
            //The projectile is friendly
            spriteColor = Color.white;
        }
        else
        {
            //The projectile is hostile
            spriteColor = Color.red;
        }
    }

    /// <summary>
    /// Returns if the projectile is alive
    /// </summary>
    /// <returns>A boolean</returns>
    public boolean GetIsAlive()
    {
        //Returns if the projectile is alive
        return isAlive;
    }

    /// <summary>
    /// Returns if the projectile is friendly to the player
    /// </summary>
    /// <returns>A boolean</returns>
    public boolean GetIsFriendly()
    {
        return isFriendly;
    }

    /// <summary>
    /// Updates the projectile object for player
    /// </summary>
    public void Update(List<Character>[] enemy)
    {
        //Calls CheckCollisions
        CheckCollisions(enemy);

        //Moves in on enemies if homing
        if (isHoming)
        {
            //Sets a target if one did not exist
            if(target == null)
            {
                //Calls CalcTarget
                CalcTarget(enemy);
            }

            //Calls CalcMoveDir
            CalcMoveDir();

            //Calls movement
            move();
        }

        //Calls UpdatePosition
        UpdatePosition();
    }

    /// <summary>
    /// Updates the projectile object for enemies
    /// </summary>
    public void Update(Character enemy)
    {
        //Calls CheckCollisions
        CheckCollisions(enemy);

        //Moves in on enemies if homing
        if (isHoming)
        {
            //Sets the enemy as the target
            target = enemy;

            //Calls CalcMoveDir
            CalcMoveDir();
        }

        //Calls UpdatePosition
        updatePosition();
    }

    /// <summary>
    /// Updates the position of the projectile
    /// </summary>
    @Override
    public void updatePosition()
    {
        //Updates the distance traveled
        distTraveled += currentVelocity.Length() * Data.deltaTime;

        //Removes the projectile once it has reached its maximum range
        if(distTraveled > stat[Data.RANGE])
        {
            //Sets the projectile to be deleted
            isAlive = false;
        }

        super.updatePosition();
    }

    /// <summary>
    /// Calaculates the nearest enemy and homes in on it
    /// </summary>
    /// <param name="enemy">A list of arrays of type character</param>
    public void CalcMoveDir()
    {
        ///Changes the velocity if there are targets
        if(target != null)
        {
            //Sets the acceleration towards the target
            accel.x = target.getDestRec().Center.X - destRec.Center.X;
            accel.y = target.getDestRec().Center.Y - destRec.Center.Y;

            //Changes the accceleration values to a direction vector
            if (accel.X != 0 && accel.Y != 0)
            {
                //Normalizes the vector
                accel.normalize();

                //Scales the direction by the acceleration value
                accel *= maxAccel;
            }
            else
            {
                //There is no acceleration
                accel *= 0;
            }
        }
    }

    /// <summary>
    /// Calculates the closes enemy and sets it as the target
    /// </summary>
    /// <param name="enemy"></param>
    public void CalcTarget(List<Character>[] enemy)
    {
        //Tracks the distance to target
        int distToTargetSqred = 1000000;

        //Loops for all enemies types
        for (int i = 0; i < enemy.Length; i++)
        {
            //Loops for all spesific enemy
            for (int j = 0; j < enemy[i].Count; j++)
            {
                //Compares the old closest distance to the distance to a new character
                if (Data.GetDistSqred(enemy[i][j].GetDestRec().Center, destRec.Center) < distToTargetSqred)
                {
                    //Sets the new distance as the current enemies distance
                    distToTargetSqred = Data.GetDistSqred(enemy[i][j].GetDestRec().Center, destRec.Center);

                    //Sets the target to the current enemy
                    target = enemy[i][j];
                }
            }
        }
    }

     /// <summary>
    /// Checks collisions to determine if the projectile should be deleted
    /// </summary>
    public void CheckCollisions(Character enemy)
    {

        //Checks if the projectile has collided with the current enemy
        if (Data.IsCollided(enemy.GetDestRec(), this.destRec))
        {
            //Sets the projectile to be deleted
            isAlive = false;

            //Hits the enemy by the projectiles damage
            enemy.Hit(stat[Data.DMG], currentVelocity, destRec.Width);
            enemy.SetIsDirty(true);
        }

        //Calls CheckCollBoundry
        CheckCollBoundry();
    }

    /// <summary>
    /// Checks collisions to determine if the projectile should be deleted
    /// </summary>
    public void CheckCollisions(List<Character>[] enemy)
    {
        //Loops for all enemies types
        for (int i = 0; i < enemy.Length; i++)
        {
            //Loops for all spesific enemy
            for(int j = 0; j < enemy[i].Count; j++)
            {
                //Checks if the projectile has collided with the current enemy
                if (Data.IsCollided(enemy[i][j].GetDestRec(), this.destRec))
                {
                    //Sets the projectile to be deleted
                    isAlive = false;

                    //Hits the enemy by the projectiles damage
                    enemy[i][j].Hit(stat[Data.DMG], currentVelocity, destRec.Width);
                    enemy[i][j].SetIsDirty(true);
                }
            }
        }

        //Calls CheckCollBoundry
        CheckCollBoundry();
    }

    /// <summary>
    /// Checks for collions againts the room boundry
    /// </summary>
    public void CheckCollBoundry()
    {
        //Checks if the projetile is beyond the room boundries
        if (destRec.Top < Data.roomBoundary.Top || destRec.Right > Data.roomBoundary.Right ||
            destRec.Bottom > Data.roomBoundary.Bottom || destRec.Left < Data.roomBoundary.Left)
        {
            isAlive = false;
        }
    }

    /// <summary>
    /// Draws the projectile
    /// </summary>
    /// <param name="spriteBatch"></param>
    public override void Draw(SpriteBatch spriteBatch)
    {
        base.Draw(spriteBatch);
    }

}
