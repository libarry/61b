package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] finalWorldFrame = null;
        String seed = "";
        char[] orderList =input.toCharArray();
        int i = 0;
        while(i < orderList.length){
            char order = orderList[i];
            if(order == 'N' || order == 'n'){
                order = orderList[i];
                i+=1;
                order = orderList[i];
                while(order != 'S' && order != 's'){
                    if(order <= '9'&&order >= '0'){
                        seed += order;
                    }else{
                        throw new IllegalArgumentException("Seed must be a integer number!");
                    }
                    i+=1;
                    order = orderList[i];
                }
                if(seed.length()==0){
                    throw new RuntimeException("you must input a seed!");
                }else{
                    finalWorldFrame = new TETile[WIDTH][HEIGHT];
                    int num = Integer.parseInt(seed);
                    RandomWorldGenerator worldGen = new RandomWorldGenerator(num,WIDTH,HEIGHT);
                    worldGen.init();
                    worldGen.generateWorld();
                    worldGen.draw(finalWorldFrame);
                }
            }
            if(order == 'w' ||order == 'W'){}
            if(order == 'a' ||order == 'A'){}
            if(order == 's' ||order == 'S'){}
            if(order == 'd' ||order == 'D'){}
            if(order == 'l' ||order == 'L'){
                saveGame s = new saveGame("./a.save");
                gameInfo info = s.getObjFromFile();
                finalWorldFrame = info.world;
            }
            if(order == 'q' ||order == 'Q'){
                gameInfo info = new gameInfo(finalWorldFrame);
                saveGame s = new saveGame("./a.save");
                s.saveObjToFile(info);
            }
            i+=1;
        }
        return finalWorldFrame;
    }
    public static void main(String args[]){
        TETile[][] world = null;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Game g = new Game();
        world = g.playWithInputString("l");
        ter.renderFrame(world);
    }

}
