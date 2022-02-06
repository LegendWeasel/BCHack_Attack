<<<<<<< HEAD

=======
>>>>>>> 7407b9f6fedc2869923760051a03a1348e7d25ce

import java.awt.Rectangle;

import com.engine.core.gfx.SpriteSheet;

import java.awt.Point;

public abstract class Interactable {
	//attributes
	private Rectangle destRec;
	
	private SpriteSheet sprite;
	
	private Rectangle spriteBox;
	
	private Point currentVelocity;
	
	private Point maxVelocity;
	
	private Point moveDir;
	
	private Point currentPos;
	
	private Point accel;
	
	private float groundFriction;
	
	private boolean touchingWall[];
	
	private boolean isTeleporting;
	
	
	//methods
	void move() {
		//Changes the current velocity based on character acceleration
		currentVelocity.x += accel.x - Math.signum(moveDir.x) * groundFriction;
		currentVelocity.y += accel.y - Math.signum(moveDir.y) * groundFriction;
//
//      //Sets the move directions based on current velocity
		moveDir.x += Math.signum(currentVelocity.x);
		moveDir.y += Math.signum(currentVelocity.y);
		
		//Sets the character's velocity to the maximum if current velocity is over
		if(Math.abs(currentVelocity.y) > maxVelocity.y) {
			//Sets vertical velocity as the limit
			currentVelocity.y = (int) (Math.signum(currentVelocity.y) * maxVelocity.y);
		}
		if (Math.abs(currentVelocity.x) > maxVelocity.x) {
			//Sets horizontal velocity as the limit
			currentVelocity.x = (int) (Math.signum(currentVelocity.x) * maxVelocity.x);
		}
	}
	void updatePosition() {
//		
		//Resets the touching wall bool
		for (int i = 0; i < touchingWall.length; i++) {
			touchingWall[i] = false;
		}
//
        //Moves the player based on the players velocity
		currentPos.x += currentVelocity.x * Data.deltaTime;
		currentPos.y += currentVelocity.y * Data.deltaTime;
		
//
//        //Keeps the character from moving out the boundary
		if(destRec.OUT_TOP + currentVelocity.y * Data.deltaTime < Data.roomBoundary.OUT_TOP && !isTeleporting) {
			//The character is stopped right below the top boundary
			currentPos.y = Data.roomBoundary.y;
			currentVelocity.y *= -0.5f;
		}
//        
//        }
		if(destRec.OUT_BOTTOM + currentVelocity.x * Data.deltaTime < Data.roomBoundary.OUT_BOTTOM && !isTeleporting) {
			//The character is stopped right below the left boundary
			currentPos.y = Data.roomBoundary.OUT_BOTTOM - destRec.height;
			currentVelocity.x *= -0.5f;
		}
		
        if (destRec.OUT_LEFT + currentVelocity.x * Data.deltaTime < Data.roomBoundary.OUT_LEFT && !isTeleporting)
        {
            //The character is stopped right below the left boundary
            currentPos.x = Data.roomBoundary.OUT_LEFT;
            currentVelocity.x *= -0.5f;

            //The character is touching the left wall
            touchingWall[3] = true;
        }
        if (destRec.OUT_RIGHT + currentVelocity.x * Data.deltaTime > Data.roomBoundary.OUT_RIGHT && !isTeleporting)
        {
            //The character is stopped right below the right boundary
            currentPos.x = Data.roomBoundary.OUT_RIGHT - destRec.OUT_RIGHT;
            currentVelocity.x *= -0.5f;

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
	public Rectangle getHitbox() {
		return destRec;
	}
	public void setHitbox(Rectangle hitbox) {
		this.destRec = hitbox;
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
	public Point getSpeed() {
		return currentVelocity;
	}
	public void setSpeed(Point speed) {
		this.currentVelocity = speed;
	}
	public Point getPos() {
		return moveDir;
	}
	public void setPos(Point pos) {
		this.moveDir = pos;
	}
	
	
}
