package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;
/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static class pair{
        int x;
        int y;
    }
    private static final int WIDTH = 35;
    private static final int HEIGHT = 35;
    private static final long SEED = System.currentTimeMillis()%100000;
    private static final Random RANDOM = new Random(SEED);

    private static boolean isInHex(int x, int y, int s){
        return x <= y && y + x <= 3 * s - 3 ;
    }
    public static void addHexagon(TETile[][] tiles,TETile tile, int xx,int yy,int s){
        int lowHalfBase = s - 1;
        int highHalfBase = s;
        for(int h = 0;h < s;h++){
            for(int w = 0;w < 3 * s - 2;w++){
                if(isInHex(h,w,s)){
                    int i = w;
                    int j = lowHalfBase - h;
                    tiles[xx + i][yy + j] = tile;
                    j = highHalfBase + h;
                    tiles[xx + i][yy + j] = tile;
                }
            }
        }
    }

    public static void fillNothing(TETile[][] world){
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }
    
    private static pair[] countSurOff(int n, int s){
        if(n==0){
            pair[] out = new pair[1];
            out[0] = new pair();
            out[0].y = 0;
            out[0].x = 0;
            return out;
        }
        pair[] out = new pair[6 * n];
        out[0] = new pair();
        out[0].x = 0;
        out[0].y = 2 * n * s;
        for(int i = 0;i < 6 * n - 1; i++){
            pair lastP = out[i];
            pair p = new pair();
            switch(i / n){
                case 0:
                    p.x = lastP.x - 2*s + 1;
                    p.y = lastP.y - s;
                    break;
                case 1:
                    p.x = lastP.x;
                    p.y = lastP.y - 2*s;
                    break;
                case 2:
                    p.x = lastP.x + 2*s - 1;
                    p.y = lastP.y - s;
                    break;
                case 3:
                    p.x = lastP.x + 2*s - 1;
                    p.y = lastP.y + s;
                    break;
                case 4:
                    p.x = lastP.x;
                    p.y = lastP.y + 2*s;
                    break;
                case 5:
                    p.x = lastP.x - 2*s + 1;
                    p.y = lastP.y + s;
                    break;
            }
            out[i + 1] = p;
        }
        return out;
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(7);
        switch (tileNum) {
            case 0: return Tileset.WATER;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.TREE;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.GRASS;
            case 5: return Tileset.FLOOR;
            case 6: return Tileset.LOCKED_DOOR;
            default: return Tileset.NOTHING;
        }
    }

    public static void drawHexWorld(TETile[][] tiles,int layerNum,int x,int y,int s){
        for(int n = 0;n < layerNum;n++){
            pair[] pairs = countSurOff(n,s);
            for(int k = 0;k < pairs.length;k++){
                TETile tile = randomTile();
                addHexagon(tiles,tile,x + pairs[k].x,y + pairs[k].y,s);
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        fillNothing(world);
        drawHexWorld(world,3,WIDTH/2 - 4,HEIGHT/2 - 4,3);

        ter.renderFrame(world);
    }
}
