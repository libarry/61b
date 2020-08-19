package byog.Core;

import java.io.Serializable;
import byog.TileEngine.TETile;

public class gameInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    public TETile[][] world;
    public gameInfo(TETile[][] w){
        world = w;
    }
}
