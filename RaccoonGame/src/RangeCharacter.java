import java.util.ArrayList;
import java.util.List;

import com.engine.core.gfx.SpriteSheet;

public class RangeCharacter extends Character {
	
	//Tracks the current and bases stats of projectiles
    protected float[] projStats = new float[Data.statAmount];
    protected float[] baseProjStats = new float[Data.statAmount];
    protected float firingTimer = 0;

    //Tracks projectile modifiers
    protected boolean[] projMods;

    //Tracks the buffs on each projectile
    protected ArrayList<Float>[] projBuff;
    
    //constructor
	public RangeCharacter(Room currentRoom, SpriteSheet sprite) {
		super(currentRoom, sprite);
		//Sets the array of lists of projectile buffs
        projBuff = new ArrayList[Data.statAmount];

        //Sets the array of projectile modifiers
        projMods = new boolean[Data.projModAmount];

        //Loops through all the projectile buffs
        for (int i = 0; i < Data.statAmount; i++)
        {
            //Sets the current projBuff list
            projBuff[i] = new ArrayList<Float>();
        }
	}
	
	
	//methods
	/// <summary>
    /// Retrives the projectile stats
    /// </summary>
    /// <returns>Returns an array of floats</returns>
    public float[] GetProjStats()
    {
        return projStats;
    }

    /// <summary>
    /// Retrives what projectile mods the character has
    /// </summary>
    /// <returns></returns>
    public boolean[] GetProjMods()
    {
        return projMods;
    }
    
  /// <summary>
    /// Retrives the projectile buffs
    /// </summary>
    /// <returns></returns>
    public List<Float>[] GetProjBuff()
    {
        return projBuff;
    }
    
  /// <summary>
    /// Sets the projectile stats 
    /// </summary>
    /// <param name="projStats"></param>
    public void SetProjStats(float[] projStats)
    {
        //Sets the new projectile stat
        this.projStats = projStats;
    }

    /// <summary>
    /// Sets the projectile stats 
    /// </summary>
    /// <param name="statType">A int representing stat type</param>
    /// <param name="newStat">A float representing the new stat</param>
    public void SetProjStats(int statType, float newStat)
    {
        //Sets the current stat
        this.projStats[statType] = newStat;
    }
    
  /// <summary>
    /// Applies all items and buffs
    /// </summary>
    public void ApplyBuffs()
    {
        //Applies buffs if the character has changed
        if(isDirty)
        {
            //Applies all of the passive and resource items effect
//            inventory.ApplyPassiveItem();
//            inventory.ApplyResource();

            //Applies all proj buffs
            for (int i = 0; i < Data.statAmount;i++)
            {
                //Tracks the total combined buff value 
                float totalBuff = 1;

                //Loops through and combines all buffs of the current stat type
                for (int j = 0; j < projBuff[i].size(); j++)
                {
                    //Products the current buff
                    totalBuff *= projBuff[i].get(j);
                }

                //Applies the stats of the projectile
                projStats[i] = baseProjStats[i] * totalBuff;
            }
        }
    }

}
