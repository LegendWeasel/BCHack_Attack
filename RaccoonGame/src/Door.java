import java.awt.Rectangle;
import com.engine.core.gfx.*;

import javafx.geometry.Point2D;

import java.awt.Point;
public class Door {
    //Tracks the adjacent room
    //private Room adjRoom;
    private int adjRoomID;

    //Tracks the sprite and dest rec of the door
    private Rectangle destRec;
    private SpriteSheet sprite;

    //Sets the dimentions of the door
    private int doorWidth;
    private int doorDepth;

    //Tracks the 4 possible positions of the door
    private Point[] position;

    //Tracks which wall the door is on
    private int wallSide;

    //Tracks if the door is useable
    private boolean isOpen = false;

    //Tracks if the door is locked
    private boolean isLocked = false;
    
    //Tracks the player
    private Player player;

    //Tracks the world map
    private Room[] map;

    public Door(Player player,Room adjRoom, Room[] map,int wallSide, bool isLocked)
    {
        this.player = player;

        this.map = map;

        //this.adjRoom = adjRoom;
        adjRoomID = adjRoom.GetRoomID();

        this.wallSide = wallSide;

        this.isLocked = isLocked;

        //Sets the sprite depending on locked state
        switch(isLocked)
        {
            case true:
                {
                    //The door is lock
                    sprite = Sprites.lockedDoorSprite[wallSide];
                    break;
                }
            case false:
                {
                    //The door is not lock
                    sprite = Sprites.closedDoorSprite[wallSide];
                    break;
                }
        }
            

        //Stores 4 possible positions based on cardinal directions
        position = new Point[4];

        //Sets the door dimentions based on position
        if(wallSide == Data.UP || wallSide == Data.DOWN)
        {
            //The door is on the top or bottom
            doorDepth = Data.wallHeight - Data.wallToEdgeGapY;
        }
        else
        {
            //The door is on the left or right
            doorDepth = Data.wallWidth - Data.wallToEdgeGapX;
        }

        //Sets the width of the door
        doorWidth = 100;

        //Sets the position of the door
        SetPosition();
    }
    

    /// <summary>
    /// Retreives the doors dest rec
    /// </summary>
    /// <returns></returns>
    public Rectangle GetDoorDestRec()
    {
        return destRec;
    }

    /// <summary>
    /// Retrives the adjacent room
    /// </summary>
    /// <returns></returns>
    public Room GetAdjRoom()
    {
        return map[adjRoomID];
    }

    /// <summary>
    /// Retrives the wall side
    /// </summary>
    /// <returns></returns>
    public int GetWallSide()
    {
        return wallSide;
    }
    
    /// <summary>
    /// Sets the doors open state
    /// </summary>
    /// <param name="isOpen"></param>
    public void SetIsOpen(boolean isOpen)
    {
        //Change the door state if the door isnt locked
        if(!isLocked)
        {
            this.isOpen = isOpen;

            //Changes the door sprite to open state
            //sprite = Sprites.doorSprite[wallSide];

        }
    }

    /// <summary>
    /// Locks the door
    /// </summary>
    public void LockDoor()
    {
        //Set is locked to true
        isLocked = true;

        //Sets the sprite to the locked sprite
        sprite = Sprites.lockedDoorSprite[wallSide];
    }

    /// <summary>
    /// Updates the door
    /// </summary>
    public void Update(bool isClear, Room[] map)
    {
        //Checks for charcter collision
        CheckCollision(isClear);

        this.map = map;
    }

    /// <summary>
    /// Checks for collision between door and player
    /// </summary>
    public void CheckCollision(boolean isClear)
    {
        //Checks for collision if the door is open or locked and the room is cleared
        if (isClear && (isOpen || isLocked))
        {
            //Transports the player if it has collided with the door
            if (Data.IsCollided(player.getHitBox(), destRec))
            {
                if(isOpen)
                {
                    //Wipes all projectiles from the players current room
                    player.getCurrentRoom().WipeProjectiles();

                    //Transports the player
                    TransportPlayer();
                }
                else
                {
                    //The door is locked
                    //Unlocked the door if the player has a key
                    if(player.GetKeys() > 0)
                    {
                        //Removes one key from the player
                        player.SetKeyAmount(player.GetKeys() - 1);

                        //Unlocked the door
                        isLocked = false;

                        //Opens the door
                        SetIsOpen(true);
                    }
                }
            }
        }
    }

    /// <summary>
    /// Transports the player to the adjacent room
    /// </summary>
    public void TransportPlayer()
    {
        //Sets the player as currently teleporting
        player.setTeleporting(true);;

        //Stops player movement
        player.StopMotion();

        //Decreases the cooldown of an active item
        //player.GetInventory().DecreaseCooldown(1);

        //Teleports the player to the next door depending on current door
        switch (wallSide)
        {
            case Data.UP:
                {
                    //The next door is on the bottom
                    player.setCurrentPos(new Point(position[Data.DOWN].x,
                                                    position[Data.DOWN].y - player.getHitBox().Height - 1));
                    break;
                }
            case Data.RIGHT:
                {
                    //The next door is on the left
                    player.setCurrentPos(new Point(position[Data.LEFT].x + doorDepth + 1,
                                                        position[Data.LEFT].y));
                    break;
                }
            case Data.DOWN:
                {
                    //The next door is on the top
                    player.setCurrentPos(new Vector2(position[Data.UP].x,
                                                        position[Data.UP].y + doorDepth + 1));
                    break;
                }
            case Data.LEFT:
                {
                    //The next door is on the right
                    player.setCurrentPos(new Vector2(position[Data.RIGHT].x - player.getHitBox().Width -1,
                                                        position[Data.RIGHT].y));
                    break;
                }
        }

        //Sets the players current room as the adjacent one
        player.SetCurrentRoom(map[adjRoomID]);
    }

    /// <summary>
    /// Sets the position of the door
    /// </summary>
    public void SetPosition()
    {
        //Sets all the possible possition of the door
        position[Data.UP] = new Point((int)Data.roomBoundary.getCenterX() - (doorWidth >> 1), (int)Data.roomBoundary.getMinY() - doorDepth);
        position[Data.RIGHT] = new Point((int)Data.roomBoundary.getMaxX(), (int)Data.roomBoundary.getCenterY() - (doorWidth >> 1));
        position[Data.DOWN] = new Point((int)Data.roomBoundary.getCenterX() - (doorWidth >> 1), (int)Data.roomBoundary.getMaxY());
        position[Data.LEFT] = new Point((int)Data.roomBoundary.getMinX() - doorDepth, (int)Data.roomBoundary.getCenterY() - (doorWidth >> 1));

        //Sets the dest rec based on which side the door is on
        switch (wallSide)
        {
            case Data.UP:
                {
                    //The door is on the top of the room
                    destRec = new Rectangle(position[Data.UP].x, position[Data.UP].y, doorWidth,doorDepth);
                    break;
                }
            case Data.RIGHT:
                {
                    //The door is on the right of the room
                    destRec = new Rectangle(position[Data.RIGHT].X, position[Data.RIGHT].Y, doorDepth, doorWidth);
                    break;
                }
            case Data.DOWN:
                {
                    //The door is on the bottom of the room
                    destRec = new Rectangle(position[Data.DOWN].X, position[Data.DOWN].Y, doorWidth, doorDepth);
                    break;
                }
            case Data.LEFT:
                {
                    //The door is on the left of the room
                    destRec = new Rectangle(position[Data.LEFT].X, position[Data.LEFT].Y, doorDepth, doorWidth);
                    break;
                }
        }
    }
}
