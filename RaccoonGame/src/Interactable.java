import java.awt.Rectangle;

import com.engine.core.gfx.SpriteSheet;

import javafx.animation.Animation;

import java.awt.Color;
import java.awt.Point;

public abstract class Interactable {
	//attributes
	//Tracks the location of the object on the map
    protected Maps.Room currentRoom;
	
	protected Rectangle destRec;
	
	protected SpriteSheet sprite;
	
	protected Rectangle spriteBox;
	
	protected Vector2 currentVelocity;
	
	protected Vector2 maxVelocity;
	
	protected Point moveDir;
	
	protected Point currentPos;
	
	protected Point accel;
	
	protected Animation spriteAnim;
    protected Color spriteColor = Color.white;

	
	//Tracks the friction of the character and the ground
    protected float groundFriction;
    protected float baseFriction;
	
	protected int pushForce;
	
	protected boolean touchingWall[];
	
	protected boolean isTeleporting;
	
	
	//constructor
	public Interactable(Maps.Room currentRoom, SpriteSheet sprite, Animation spriteAnim)
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
        moveDir = new Point(Math.signum(currentVelocity.getX()), Math.signum(currentVelocity.getY()));

        //Set the array of touching wall booleans
        touchingWall = new boolean[4];

        //Sets the positional data of the entity
        currentPos.x = 0;
        currentPos.y = 0;

        //Sets the visual data
        this.sprite = sprite;
        this.spriteAnim = spriteAnim;

        //Sets the dest rec of the entity based on sprite size
        destRec = new Rectangle((int)currentPos.x, (int)currentPos.y, sprite.Width << 1, sprite.Height << 1);
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
		if(destRec.OUT_TOP + currentVelocity.getY() * Data.deltaTime < Data.roomBoundary.OUT_TOP && !isTeleporting) {
			//The character is stopped right below the top boundary
			currentPos.y = Data.roomBoundary.y;
			currentVelocity.setY(currentVelocity.getY() * -0.5f);
		}
      
		if(destRec.OUT_BOTTOM + currentVelocity.getX() * Data.deltaTime < Data.roomBoundary.OUT_BOTTOM && !isTeleporting) {
			//The character is stopped right below the left boundary
			currentPos.y = Data.roomBoundary.OUT_BOTTOM - destRec.height;
			currentVelocity.setX(currentVelocity.getX() * -0.5f);
		}
		
        if (destRec.OUT_LEFT + currentVelocity.getX() * Data.deltaTime < Data.roomBoundary.OUT_LEFT && !isTeleporting)
        {
            //The character is stopped right below the left boundary
            currentPos.x = Data.roomBoundary.OUT_LEFT;
            currentVelocity.setX(currentVelocity.getX() * -0.5f);

            //The character is touching the left wall
            touchingWall[3] = true;
        }
        if (destRec.OUT_RIGHT + currentVelocity.getX() * Data.deltaTime > Data.roomBoundary.OUT_RIGHT && !isTeleporting)
        {
            //The character is stopped right below the right boundary
            currentPos.x = Data.roomBoundary.OUT_RIGHT - destRec.OUT_RIGHT;
            currentVelocity.setX(currentVelocity.getX() * -0.5f);

            //The character is touching the right wall
            touchingWall[1] = true;
        }

        //Sets the destination rectangle as the current position
        destRec.setLocation((int)currentPos.x, (int)currentPos.y);
	}
	void update() {
		move();
		updatePosition();
	}


	public Maps.Room getCurrentRoom() {
		return currentRoom;
	}


	public void setCurrentRoom(Maps.Room currentRoom) {
		this.currentRoom = currentRoom;
	}


	public Rectangle getDestRec() {
		return destRec;
	}


	public void setDestRec(Rectangle destRec) {
		this.destRec = destRec;
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


	public Point getMoveDir() {
		return moveDir;
	}


	public void setMoveDir(Point moveDir) {
		this.moveDir = moveDir;
	}


	public Point getCurrentPos() {
		return currentPos;
	}


	public void setCurrentPos(Point currentPos) {
		this.currentPos = currentPos;
	}


	public Point getAccel() {
		return accel;
	}


	public void setAccel(Point accel) {
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
	
}
