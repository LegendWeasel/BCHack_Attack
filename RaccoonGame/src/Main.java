import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;

import com.engine.core.*;
import com.engine.core.gfx.*;

public class Main extends AbstractGame
{
	//Required Basic Game Functional Data
	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private static int screenWidth = device.getDisplayMode().getWidth();
	private static int screenHeight = device.getDisplayMode().getHeight();
	
	//Required Basic Game Visual data used in main below
	private static String gameName = "Raccoon Game";
	private static int windowWidth = 1280;	//For fullscreen mode set these next two to screenWidth and screenHeight
	private static int windowHeight = 720;
	private static int fps = 30;

	private FloorMap floorMap;
	private Player player;
	private Controls controls;
	private Data data = new Data();

	private boolean isWorldPaused = false;

	long time =0;;
	long last_time = 0;

	SoundClip backgroundMsc;

	public static void main(String[] args) 
	{
		GameContainer gameContainer = new GameContainer(new Main(), gameName, windowWidth, windowHeight, fps);
		gameContainer.Start();
	}

	@Override
	public void LoadContent(GameContainer gc)
	{
		//TODO: This subprogram automatically happens only once, just before the actual game loop starts.
		//It should never be manually called, the Engine will call it for you.
		//Load images, sounds and set up any data
		
		
		Sprites.playerSprite = new SpriteSheet();

		//Creates the floor map
		floorMap = new FloorMap(data);

		//Creates a new player
		player = new Player(floorMap.GetCurRoom(),Sprites.playerSprite);
	}
	
	@Override
	public void Update(GameContainer gc, float deltaTime) 
	{
		//TODO: Add your update logic here, including user input, movement, physics, collision, ai, sound, etc.
		System.out.println(Data.deltaTime);
		
		//Updates the delta time
		time = System.nanoTime();
		Data.deltaTime = (int) ((time - last_time) / 1000000);
		last_time = time;

		 //Checks if the player is alive
		 if (player.GetIsAlive())
		 {
			 //Updates the player
			 player.Update();

			 //Updates the map
			 floorMap.Update(player);

			 //Checks if the player can go to the next level
			 if(player.GetCanDecend())
			 {
				 //Increases the zone by 1
				 Data.curZone++;

				 //Regenerates the next floor
				 floorMap.GenerateFloor(player);

				 //The player only decends once
				 player.SetCanDecend(false);
			 
			 }
		 }
	}

	@Override
	public void Draw(GameContainer gc, Graphics2D gfx) 
	{

	}

}
