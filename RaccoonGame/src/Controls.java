import com.engine.core.*;
import com.engine.core.gfx.Vector2F;

import java.awt.event.KeyEvent;

public class Controls {
    // Sets all the input keys at default
	static int Esc = KeyEvent.VK_ESCAPE;
	static int moveRight = KeyEvent.VK_D;
	static int moveLeft = KeyEvent.VK_A;
    static int moveUP = KeyEvent.VK_W;
	static int moveDOWN = KeyEvent.VK_S;

    static Vector2F mousePos;

    //Tracks which direction to move the player
    boolean[] moveDir;

    //Tracks the player and game container
    Player player;
    GameContainer gc;

    Controls(Player player, GameContainer gc) {
        this.player = player;
        this.gc = gc;
    }

    public void update(Player player, GameContainer gc) {
        
        mousePos = Input.GetMousePos();
        MovementCon();
        MouseCon();
    }

    private void MovementCon() {

        if(Input.IsKeyPressed(moveRight)) {
            moveDir[Data.RIGHT] = true;
        }
        if(Input.IsKeyPressed(moveLeft)) {
            moveDir[Data.LEFT] = true;
        }
        if(Input.IsKeyPressed(moveUP)) {
            moveDir[Data.UP] = true;
        }
        if(Input.IsKeyPressed(moveDOWN)) {
            moveDir[Data.DOWN] = true;
        }
        
    }

    private void MouseCon() {
        if(Input.IsMouseButtonDown(Input.MOUSE_LEFT)) {
            player.Attack(-(float)Math.atan2(mousePos.x - player.getHitBox().getCenterX(), mousePos.y - player.getHitBox().getCenterY()));
        }
    }
}
