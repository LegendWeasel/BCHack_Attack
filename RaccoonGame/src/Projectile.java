import java.awt.Rectangle;
import com.engine.core.gfx.*;
import java.awt.Color;

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
}
