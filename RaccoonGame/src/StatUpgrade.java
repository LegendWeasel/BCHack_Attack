import com.engine.core.gfx.SpriteSheet;
import java.awt.Point;

public class StatUpgrade extends Item{
    //Tracks what stat to upgrade
    public int upgradeType;

    //Tracks the upgrade multiplier
    public float[] increaseValue;

    private Player owner;

    public StatUpgrade(boolean inWorld, Player owner ,Room currentRoom, Point pos, int upgradeType,SpriteSheet sprite)
    {
        super( inWorld, owner, currentRoom,  pos,  sprite);
        this.upgradeType = upgradeType;

        this.owner = owner;

        //Sets the ID of the current item
        itemID = upgradeType;

        //Sets the base multipliers depending on upgrade type
        SetMultiValues(upgradeType);
    }

    /// <summary>
    /// Retrives the buff multipliers
    /// </summary>
    /// <returns></returns>
    public float[] GetBuffValues()
    {
        return increaseValue;
    }

    /// <summary>
    /// Applies the current effects to the player
    /// </summary>
    /// <param name="player">A type player</param>
    @Override
    public void DeliverPayload()
    {
        //Loops through all stat types
        for(int statType = 0; statType < Data.statAmount; statType++)
        {
            //Adds the current stat buff to the character buff list
            owner.GetProjBuff()[statType].add(increaseValue[statType]);
        }

        super.DeliverPayload();
    }

    /// <summary>
    /// Sets the stat multipliers
    /// </summary>
    /// <param name="statType">An int corresponding to a projectile stat</param>
    public void SetMultiValues(int upgradeType)
    {
        //stores a stat upgrade value based on upgrade type
        switch (upgradeType)
        {
            case Data.SPEEDUP:
                {
                    //Increases by 50% of players projectile speed
                    increaseValue[Data.SPEED] = 1.5f;
                    break;
                }
            case Data.DMGUP:
                {
                    //Increases by 50% of player damage
                    increaseValue[Data.DMG] = 1.5f;
                    break;
                }
            case Data.FIRERATEUP:
                {
                    //Decreases by 30% of player fire delay
                    increaseValue[Data.FIRERATE] = 0.7f;
                    break;
                }
            case Data.RANGEUP:
                {
                    //Increases by 50% of player damage
                    increaseValue[Data.RANGE] = 1.5f;
                    break;
                }
            case Data.AMOUNTUP:
                {
                    //Increases by 100% of player projectile amount
                    increaseValue[Data.AMOUNT] = 2f;
                    break;
                }
            case Data.ACCURACYUP:
                {
                    //Decreases spread by 50% of amount
                    increaseValue[Data.ACCURACYUP] = 0.5f;
                    break;
                }
        }

        //Sets all unmodified stat multiplyer as 1x
        for(int i = 0; i < increaseValue.length; i++)
        {
            //Checks if the multiplier is modified 
            if(increaseValue[i] == 0)
            {
                //Sets the multi to 1
                increaseValue[i] = 1;
            }
        }
    }
}
