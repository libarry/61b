package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Point {
    public int x;
    public int y;
    private TETile tile = Tileset.FLOOR;
    public Point(int x,int y){ this.x = x;this.y = y;}
    public void move(int x, int y){
        this.x += x;
        this.y += y;
    }
    public void setTile(TETile tile){
        this.tile = tile;
    }
    public void draw(TETile[][] world){
        world[x][y]= tile;
    }
}
