package Entity;

import java.awt.Rectangle;

import com.engine.core.gfx.SpriteSheet;

import java.awt.Point;

public abstract class Interactable {
	//attributes
	private Rectangle hitbox;
	
	private SpriteSheet sprite;
	
	private Rectangle spriteBox;
	
	private Point speed;
	
	private Point pos;
	
	
	//methods
	void move() {
		pos.x += speed.x;
		pos.y += speed.y;
	}
	void update() {
		move();
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
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
		return speed;
	}
	public void setSpeed(Point speed) {
		this.speed = speed;
	}
	public Point getPos() {
		return pos;
	}
	public void setPos(Point pos) {
		this.pos = pos;
	}
	
	
}
