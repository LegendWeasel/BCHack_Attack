import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Data {

    //Tracks how many cardinal directions exist
    public static int numberOfDir = 4;

    //Tracks the cardinal directions with bytes
    public static byte NORTH = 8;
    public static byte EAST = 4;
    public static byte SOUTH = 2;
    public static byte WEST = 1;

    //Tracks the current screen
    public static int currentScreen = 0;

    //Tracks the current sub screen
    public static int currentSubScreen = 0;

    //Sets the types of screens as ints
    //Sets the start screen
    public static int STARTSCREEN = 0;

    //Sets the game screen
    public static int GAMESCREEN = 1;

    //Sets the menu screens
    public static int MENUSCREEN = 2;

    //Sets the sub screen ID
    public static int NEWRUN = 0;
    public static int OPTIONS = 1;
    public static int EXIT = 2;

    //Tracks the amount of subscreens for the menu
    public static int SUBSCREENNUMMENU = 3;

    //Sets the options screens
    public static int OPTIONSCREEN = 3;

    //Sets the sub screen ID
    public static int SCREENTIMER = 0;
    public static int NOSCREENTIMER = 1;

    //Sets the difficulty selection screen
    public static int DIFFSCREEN = 4;

    //Sets the sub screen ID
    public static int EASY = 0;
    public static int MEDIUM = 1;
    public static int HARD = 2;

    //Tracks the amount of subscreens for the difficulty selection
    public static int SUBSCREENNUMDIFF = 3;

    //Sets the loading screen
    public static int LOADINGSCREEN = 5;

    //Tracks the game window dimentions
    public static int screenWidth = 0;
    public static int screenHeight = 0;

    //Tracks how much space the walls will take up
    public static int wallWidth;
    public static int wallHeight;

    //Tracks how far between the walls and the edge of the screen
    public static int wallToEdgeGapY = 50;
    public static int wallToEdgeGapX = 60;

    //Tracks how wide and high the map is
    public static int mapNodeSize = 3;

    //Sets the conversion factor from degrees to radians
    public static float degToRad = (float)(Math.PI / 180);
    public static int toPercent = 100;

    //Track the diffuculty multiplier
    public static float difficultyMulti = 1f;

    //Tracks the size of a room tile
    public static int tileSize = 40;

    //Tracks the accesable area of the room
    public static Rectangle roomBoundary;

    //Tracks the rectangle of the entire screen
    public static Rectangle screenRectangle;

    //Directions represented by ints
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;

    //Direction angles represented by degrees
    public static float UPANGLE = 270f;
    public static float RIGHTANGLE = 0f;
    public static float DOWNANGLE = 90f;
    public static float LEFTANGLE = 180f;
    public static float[] faceDir;

    //Stores the projectile stat types as ints
    public static int SPEED = 0;
    public static int DMG = 1;
    public static int FIRERATE = 2;
    public static int RANGE = 3;
    public static int AMOUNT = 4;
    public static int ACCURACY = 5;
    public static int SIZE = 6;

    //Stores the amount of projectile statistics
    public static int statAmount = 7;

    //Stores charcter types as ints
    public static int MONSTER = 0;
    public static int BOSS = 1;

    //Stores the enemy types as ints
    public static int DOTFLY = 0;
    public static int DUNGDROP = 1;
    public static int TUMORMAN = 2;
    public static int BUTTBLOB = 3;

    //Stores the amount of enemy types
    public static int enemyTypeAmount = 4;

    //Stores the boss types as ints
    public static int LORDFLY = 0;
    public static int LORDDUNG = 1;

    //Stores the amount of boss types
    public static int bossTypeAmount = 2;

    //Tracks the id of all possible items that can spawn
    public static Stack<Integer> possiblePassive = new Stack<Integer>();
    public static Stack<Integer> possibleActive = new Stack<Integer>();

    //Stores item types as ints
    public static int RESOURCE = 0;
    public static int ITEMPASSIVE = 1;
    public static int ITEMACTIVE = 2;

    //Stores the total amount of item types
    public static int itemTypeAmount = 3;

    //Stores resource types as int
    public static int DOORKEY = 0;
    public static int COIN = 1;
    public static int HEART = 2;

    //Stores the amount of resources
    public static int rItemAmount = 3;

    //Stores active item types as ints
    public static int DOUBLEPROJ = 0;
    public static int INVINCIBILITY = 1;

    //Stores the amount of active items
    public static int aItemAmount = 2;

    //Stores passive items as ints
    //Stores the projectile modifiers
    public static int HOMING = 0;
    public static int SCATTER = 1;

    //Stores the stat upgrades
    public static int SPEEDUP = 2;
    public static int DMGUP = 3;
    public static int FIRERATEUP = 4;
    public static int RANGEUP = 5;
    public static int AMOUNTUP = 6;
    public static int ACCURACYUP = 7;

    //Stores the amount of passive items
    public static int pItemAmount = 8;

    //Tracks the amount of projectile modifiers
    public static int projModAmount = 2;

    //Stores the boss behavior phases as ints
    public static int NORMALMODE = 0;
    public static int RAGEMODE = 1;
    public static int FURYMODE = 2;

    //Tracks the time between update
    public static float deltaTime = 0;

    //Tracks an ingame timer
    public static float inGameTimer = 0;
    public static boolean isTimerActive = true;


        /// <summary>
        /// Checks if the given 2 boxes collides
        /// </summary>
        /// <param name="box1"></param>
        /// <param name="box2"></param>
        /// <returns></returns>
        public static boolean IsCollided(Rectangle box1, Rectangle box2)
        {
            //Checks for impossible collisions
            if (box1.y + box1.height < box2.y || box1.y > box2.y + box2.height ||
                box1.x + box1.width < box2.x  || box1.x > box2.x + box2.width)
            {
                //There is no collisions
                return false;
            }

            //There is a collisions
            return true;
        }

        /// <summary>
        /// Generates a random direction vector. The direction has no limits.
        /// </summary>
        /// <returns>A direction vect</returns>
        public static Vector2 GenRandDir()
        {
            //Randomizes the direction and normalizes it
            Vector2 dir = new Vector2(getRandomNumber(0, 100) - 50.0, getRandomNumber(0, 100) - 50.0);
            dir.normalize();

            return dir;
        }

        /// <summary>
        /// Calaculates the distance squared between 2 points
        /// </summary>
        /// <param name="point1">A Point type</param>
        /// <param name="point2">A Point type</param>
        /// <returns>The squared distance as an int</returns>
        public static int GetDistSqred(Point point1, Point point2)
        {
            //Returns and calculates the distance using the distance formula
            return ((point1.x -point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y));
        }

        /// <summary>
        /// Calaculates an index value based on row and colums
        /// </summary>
        /// <param name="col"></param>
        /// <param name="row"></param>
        /// <returns></returns>
        public static int CalcIndex(int col, int row, int gridWidth, int gridHeight)
        {
            //Checks if the row and colums are valid
            if (col < 0 || row < 0 || col > gridWidth - 1 || row > gridHeight - 1)
            {
                //Returns invalid index
                return -1;
            }

            //Returns the index
            return col + row * gridWidth;
        }

                /// <summary>
        /// Shuffles the values of a int list
        /// </summary>
        /// <param name="list"></param>
        /// <returns></returns>
        public static List<Integer> Shuffle(List<Integer> list)
        {
            //Tracks a copy of the list
            List<Integer> listCopy = new ArrayList<Integer>();

            //Tracks the new shuffled list
            List<Integer> newList = new ArrayList<Integer>();

            //Copies the data from the list to the copy
            for(int i = 0; i < list.size(); i++)
            {
                //Copies the current element
                listCopy.add(list.get(i));
            }

            //Tracks the randomized index of the list
            int index;

            //Repeat for all values of the list copy
            while(listCopy.size()> 0)
            {
                //Randomly generates a index value
                index = getRandomNumber(0, listCopy.size());

                //Adds value at the generated index to the new list
                newList.add(listCopy.get(index));

                //Removes the value at that index
                listCopy.remove(index);
            }

            //Returns the shuffled list
            return newList;
        }

    //Pre: A maximum and minimum integer value
    //Post: A integer within the given limit
    //Description: Generates a number between the given range inclusive
    public static int getRandomNumber(int minValue, int maxValue)
    {
        //Generates then returns the number
        int number = (int)((Math.random() * (maxValue - minValue + 1)) + minValue);
        return number;
    }
}
