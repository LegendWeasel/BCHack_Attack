class MonsterRoom extends Room {
    //Tracks the current room layout 
    private int roomType;

    //Tracks the differnt kinds of room layouts
    private final int TYPEA = 0;
    private final int TYPEB = 1;
    private final int TYPEC = 2;

    private final int MAXROOMTYPES = 3;

    public MonsterRoom(Data data, int roomID)
    {
        super(data, roomID);

        //Sets the room type as a random room type
        roomType = Data.getRandomNumber(0, MAXROOMTYPES);

        //Sets up all spawn nodes
        SetUpSpawnNodes();
    }

    /// <summary>
    /// Populates the room with monsters
    /// </summary>
    /// <param name="player"></param>
    @Override
    public void Populate(Player player)
    {
        //Adds the monsters
        SpawnMonsters(player);

        super.Populate(player);
    }

    /// <summary>
    /// Spawns all monsters on the nodes
    /// </summary>
    /// <param name="player"></param>
    @Override
    public void SpawnMonsters(Player player)
    {
        //Tracks the current monster ID
        int monsterType;

        //Sets up the spawn nodes
        SetUpSpawnNodes();

        //Spawns monsters based on the room type
        switch (roomType)
        {
            case TYPEA:
                {
                    //Loops through and and spawns a random monster per node
                    for (int i = 0; i < spawnNode.size(); i++)
                    {
                        //Tracks the current monster ID
                        monsterType = Data.getRandomNumber(0, Data.enemyTypeAmount);

                        //Adds the current monster at the node
                        characterManager.AddMonster(monsterType, new Vector2(spawnNode.get(i).x, spawnNode.get(i).y), player);
                    }
                    break;
                }
            case TYPEB:
                {
                    //Tracks the monster to spawn
                    monsterType = Data.getRandomNumber(0, Data.enemyTypeAmount);

                    //Loops through and and spawns one type of enemy per spawn node
                    for (int i = 0; i < spawnNode.size(); i++)
                    {
                        //Adds the current monster at the node
                        characterManager.AddMonster(monsterType, new Vector2(spawnNode.get(i).x, spawnNode.get(i).y), player);
                    }
                        break;
                }
            case TYPEC:
                {
                    //Sets the mosnter stype as fly
                    monsterType = Data.DUNGDROP;

                    //Tracks how many enemies to spawn
                    int spawnAmount = 5;

                    //Loops through and and spawns one type of enemy per spawn node
                    for (int i = 0; i < spawnAmount; i++)
                    {
                        //Adds the current monster at the node
                        characterManager.AddMonster(monsterType, new Vector2(spawnNode.get(0).x, spawnNode.get(0).y), player);
                    }
                    break;
                }
        }
    }
}