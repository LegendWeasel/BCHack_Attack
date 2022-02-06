class MonsterRoom extends Room {
    //Tracks the current room layout 
    private int roomType;

    //Tracks the differnt kinds of room layouts
    private final int TYPEA = 0;
    private final int TYPEB = 1;
    private final int TYPEC = 2;

    private final int MAXROOMTYPES = 3;

    public MonsterRoom(int roomID)
    {
        super(roomID);

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

        base.Populate(player);
    }
}