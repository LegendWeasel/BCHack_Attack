import java.awt.Rectangle;

import com.engine.core.gfx.SpriteSheet;

import javafx.animation.Animation;

import java.awt.Color;
import java.awt.Point;

public abstract class Interactable {
	//attributes
	//Tracks the location of the object on the map
    protected Room currentRoom;
	
	protected Rectangle hitBox;
	
	protected SpriteSheet sprite;
	
	protected Rectangle spriteBox;
	
	 //Tracks characters movement data
	protected Vector2 currentVelocity;
	protected Vector2 maxVelocity;
	protected Vector2 accel;
	protected Vector2 maxAccel;
	
	protected Vector2 moveDir;
	
	protected Point currentPos;
	
	protected Animation spriteAnim;
    protected Color spriteColor = Color.white;

	
	//Tracks the friction of the character and the ground
    protected float groundFriction;
    protected float baseFriction;
	
    //Tracks base character stats
	protected int pushForce;
	
	//Tracks if the character is currently in contact with a wall
	protected boolean touchingWall[];
	
	//Tracks if the entity is currently teleporting
	protected boolean isTeleporting;
	
	protected boolean isFriendly;
	
	
	//constructor
	public Interactable(Room currentRoom, SpriteSheet sprite)
    {
        this.currentRoom = currentRoom;

        //Sets the ground friction data
        baseFriction = currentRoom.GetGroundFriction();
        groundFriction = baseFriction;

        //Sets the pushing speed
        pushForce = 75;

      //Sets the movement data of the entity
        currentVelocity = new Vector2(0, 0);
        maxVelocity = new Vector2(100, 100);

        //Sets the move directions based on current velocity
        
        moveDir = new Vector2(Math.signum(currentVelocity.getX()), Math.signum(currentVelocity.getY()));

        //Set the array of touching wall booleans
        touchingWall = new boolean[4];

        //Sets the positional data of the entity
		currentPos = new Point();
        currentPos.x = 0;
        currentPos.y = 0;

        //Sets the visual data
        this.sprite = sprite;
        this.spriteAnim = spriteAnim;

		//Initializes accel
		this.accel = new Vector2(0,0);

        //Sets the dest rec of the entity based on sprite size
        hitBox = new Rectangle((int)currentPos.x, (int)currentPos.y, sprite.GetFrameWidth() << 1, sprite.GetFrameHeight() << 1);
    }
	
	
	//methods
	void move() {
		//Changes the current velocity based on character acceleration
		currentVelocity.x += accel.x - Math.signum(moveDir.x) * groundFriction;
		currentVelocity.y += accel.y - Math.signum(moveDir.y) * groundFriction;

      	//Sets the move directions based on current velocity
		moveDir.x += Math.signum(currentVelocity.getX());
		moveDir.y += Math.signum(currentVelocity.getY());
		
		//Sets the character's velocity to the maximum if current velocity is over
		if(Math.abs(currentVelocity.getY()) > maxVelocity.getY()) {
			//Sets vertical velocity as the limit
			currentVelocity.setY((int) (Math.signum(currentVelocity.getX()) * maxVelocity.getY()));
		}
		if (Math.abs(currentVelocity.getX()) > maxVelocity.getX()) {
			//Sets horizontal velocity as the limit
			currentVelocity.setX((int) (Math.signum(currentVelocity.getX()) * maxVelocity.getX()));
		}
	}
	void updatePosition() {

		//Resets the touching wall bool
		for (int i = 0; i < touchingWall.length; i++) {
			touchingWall[i] = false;
		}

        //Moves the player based on the players velocity
		currentPos.x += currentVelocity.getX() * Data.deltaTime;
		currentPos.y += currentVelocity.getY() * Data.deltaTime;
		

        //Keeps the character from moving out the boundary
		if(hitBox.OUT_TOP + currentVelocity.getY() * Data.deltaTime < Data.roomBoundary.OUT_TOP && !isTeleporting) {
			//The character is stopped right below the top boundary
			currentPos.y = Data.roomBoundary.y;
			currentVelocity.setY(currentVelocity.getY() * -0.5f);
		}
      
		if(hitBox.OUT_BOTTOM + currentVelocity.getX() * Data.deltaTime < Data.roomBoundary.OUT_BOTTOM && !isTeleporting) {
			//The character is stopped right below the left boundary
			currentPos.y = Data.roomBoundary.OUT_BOTTOM - hitBox.height;
			currentVelocity.setX(currentVelocity.getX() * -0.5f);
		}
		
        if (hitBox.OUT_LEFT + currentVelocity.getX() * Data.deltaTime < Data.roomBoundary.OUT_LEFT && !isTeleporting)
        {
            //The character is stopped right below the left boundary
            currentPos.x = Data.roomBoundary.OUT_LEFT;
            currentVelocity.setX(currentVelocity.getX() * -0.5f);

            //The character is touching the left wall
            touchingWall[3] = true;
        }
        if (hitBox.OUT_RIGHT + currentVelocity.getX() * Data.deltaTime > Data.roomBoundary.OUT_RIGHT && !isTeleporting)
        {
            //The character is stopped right below the right boundary
            currentPos.x = Data.roomBoundary.OUT_RIGHT - hitBox.OUT_RIGHT;
            currentVelocity.setX(currentVelocity.getX() * -0.5f);

            //The character is touching the right wall
            touchingWall[1] = true;
        }

        //Sets the destination rectangle as the current position
        hitBox.setLocation((int)currentPos.x, (int)currentPos.y);
	}
	void update() {
		move();
		updatePosition();
	}


	public Room getCurrentRoom() {
		return currentRoom;
	}


	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}


	public Rectangle getHitBox() {
		return hitBox;
	}


	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}


	public SpriteSheet getSprite() {
		return sprite;
	}


	public void setSprite(SpriteSheet sprite) {
		this.sprite = sprite;
	}


	public Rectangle getSpriteBox() {
		return spriteBox;
	}


	public void setSpriteBox(Rectangle spriteBox) {
		this.spriteBox = spriteBox;
	}


	public Vector2 getCurrentVelocity() {
		return currentVelocity;
	}


	public void setCurrentVelocity(Vector2 currentVelocity) {
		this.currentVelocity = currentVelocity;
	}


	public Vector2 getMaxVelocity() {
		return maxVelocity;
	}


	public void setMaxVelocity(Vector2 maxVelocity) {
		this.maxVelocity = maxVelocity;
	}


	public Vector2 getMoveDir() {
		return moveDir;
	}


	public void setMoveDir(Vector2 moveDir) {
		this.moveDir = moveDir;
	}


	public Point getCurrentPos() {
		return currentPos;
	}


	public void setCurrentPos(Point currentPos) {
		this.currentPos = currentPos;
	}


	public Vector2 getAccel() {
		return accel;
	}


	public void setAccel(Vector2 accel) {
		this.accel = accel;
	}


	public Animation getSpriteAnim() {
		return spriteAnim;
	}


	public void setSpriteAnim(Animation spriteAnim) {
		this.spriteAnim = spriteAnim;
	}


	public Color getSpriteColor() {
		return spriteColor;
	}


	public void setSpriteColor(Color spriteColor) {
		this.spriteColor = spriteColor;
	}


	public float getGroundFriction() {
		return groundFriction;
	}


	public void setGroundFriction(float groundFriction) {
		this.groundFriction = groundFriction;
	}


	public float getBaseFriction() {
		return baseFriction;
	}


	public void setBaseFriction(float baseFriction) {
		this.baseFriction = baseFriction;
	}


	public int getPushForce() {
		return pushForce;
	}


	public void setPushForce(int pushForce) {
		this.pushForce = pushForce;
	}


	public boolean[] getTouchingWall() {
		return touchingWall;
	}


	public void setTouchingWall(boolean[] touchingWall) {
		this.touchingWall = touchingWall;
	}


	public boolean isTeleporting() {
		return isTeleporting;
	}


	public void setTeleporting(boolean isTeleporting) {
		this.isTeleporting = isTeleporting;
	}
	
	public boolean isFriendly() {
		return isFriendly;
	}
	
	public boolean setFriendly(boolean isFriendly) {
		return this.isFriendly = isFriendly;
	}
	
}
