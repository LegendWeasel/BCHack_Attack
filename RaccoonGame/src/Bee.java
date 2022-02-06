import com.engine.core.gfx.SpriteSheet;

public class Bee extends RangeCharacter {
	
	protected double distToPlayer;

	public Bee(Room currentRoom, SpriteSheet sprite) {
		super(currentRoom, sprite);
	}
	
	/// Sets the bee to accelerate directly towards the player
    public void CalcMoveDir(Player player)
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
        
        double xDist= player.getHitBox().getCenterX() -this.getHitBox().getCenterY();
        double yDist= player.getHitBox().getCenterX() -this.getHitBox().getCenterY();
        
        distToPlayer = Math.sqrt(Math.pow(player.getHitBox().getCenterX() -this.getHitBox().getCenterY(),2) +
                Math.pow(player.getHitBox().getCenterY()- this.getHitBox().getCenterY(),2));
        
        if(distToPlayer < 100) {
        	accel.x *= -1;
        	accel.y *= -1;
        }
        
    }
    
    // Allows the bee to fire projectiles
    public void Attack(Player player){

        double xDist = player.getHitBox().getCenterX()-this.hitBox.getCenterX();
        double yDist = player.getHitBox().getCenterY()-this.hitBox.getCenterY();

        currentRoom.AddProj(new Projectile(this, new Vector2(xDist,yDist), null));
    }
	
}
