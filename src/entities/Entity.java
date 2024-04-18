package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
	
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}

	protected void drawHitBox(Graphics g) {
		g.setColor(Color.orange);
		g.drawRect((int)hitbox.x, (int)hitbox.y,(int) hitbox.width,(int) hitbox.height);
	}
	
	protected void initHitBox(float x, float y, float width, float height) {
		// TODO Auto-generated method stub
		hitbox = new Rectangle2D.Float(x,y,width, height);
	}
	
//	protected void updateHitBox() {
//		hitBox.x = (int) x;
//		hitBox.y = (int) y;
//	}
	
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
}
