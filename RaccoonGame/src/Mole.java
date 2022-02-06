import com.engine.core.gfx.SpriteSheet;

public class Mole extends Character {

	public Mole(Room currentRoom, SpriteSheet sprite) {
		super(currentRoom, sprite);
		hitBox.width = sprite.GetFrameWidth() << 1;
        hitBox.height = sprite.GetFrameHeight() << 1;
        setFriendly(false);
	}
	
	/// Sets the mole to accelerate directly towards the player
    public void CalcMoveDir()
    {
        //Sets the acceleration towards the player
        accel.x = super.getHitBox().getCenterX() - hitBox.getCenterX();
        accel.y = super.getHitBox().getCenterY() - hitBox.getCenterY();

        //Changes the acceleration values to a direction vector
        if(accel.x != 0 && accel.y != 0)
        {
            //Normalizes the vector
            accel.normalize();

            //Scales the direction by the acceleration value
            accel.x *= maxAccel.x;
            accel.y *= maxAccel.y;
        }
        else
        {
            //There is no acceleration
        	accel.x = 0; accel.y = 0;
        }
    }
    
  /// Resets all character data to its base values
    public void SetBaseStats()
    {
        //Sets the character health stats
        maxHP = (int)(2f * Data.difficultyMulti);
        currentHP = maxHP;
        baseHP = maxHP;

        //Sets the ground friction data
        baseFriction = 0;
        groundFriction = baseFriction;

        //Set in air state
        inAir = true;
    }
    

}
