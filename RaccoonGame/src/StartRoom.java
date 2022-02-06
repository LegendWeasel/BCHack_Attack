public class StartRoom extends Room
{
        public StartRoom(int roomID)
        {
            super(roomID);
        }

        /// <summary>
        /// Populates the room
        /// </summary>
        /// <param name="player"></param>
        @Override
        public void Populate(Player player)
        {
            base.Populate(player);
        }
}