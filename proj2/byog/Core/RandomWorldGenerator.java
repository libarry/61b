package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.util.*;

public class RandomWorldGenerator {
    private final Random  rand;
    private Room[] rooms;
    private int[] roomFlags;
    private boolean randomRoomNum = true;
    private int maxRoomNum = 60;
    public final int width;
    public final int height;
    private double roomRatio = 0.7;
    private double verticalWayRatio = 0.5;
    public RandomWorldGenerator(long seed,int width,int height){
        rand = new Random(seed);
        this.width = width;
        this.height = height;
        maxRoomNum = width*height/50;
    }

    public void init(){
        int roomNum;
        if(randomRoomNum)
            roomNum = RandomUtils.uniform(rand,maxRoomNum/2,maxRoomNum);
        else
            roomNum = maxRoomNum;
        rooms = new Room[roomNum];
        roomFlags = new int[roomNum];

        Set<Integer> randSet = new HashSet<>();
        int roomNumWidth = (width - 2) / 4;
        int roomNumHeight = (height - 2) / 4;
        int maxOriginalRoomNum = roomNumWidth * roomNumHeight;
        while(randSet.size()<roomNum)
            randSet.add(RandomUtils.uniform(rand,0,maxOriginalRoomNum));

        int count = 0;
        for(int i : randSet){
            int w = i % roomNumWidth;
            int h = i / roomNumWidth;
            double randRoomChoose = RandomUtils.uniform(rand,0.0,1.0);
            double randVerticalChoose = RandomUtils.uniform(rand,0.0,1.0);

            if(randRoomChoose < roomRatio)
                rooms[count] = new Room(1 + 4*w,1 + 4*h);
            else if(randVerticalChoose < verticalWayRatio)
                rooms[count] = new Room(1 + 4*w,1 + 4*h,true);
            else
                rooms[count] = new Room(1 + 4*w,1 + 4*h,false);
            roomFlags[count] = count;
            count++;
        }
    }
    public void setMaxRoomNum(int num){
        maxRoomNum = num;
    }
    public void setRoomRatio(double r){
        roomRatio = r;
    }
    public void setVerticalWayRatio(double r){
        verticalWayRatio = r;
    }
    public void setRandomRoomNum(boolean b){
        randomRoomNum = b;
    }
    private boolean isFullConnect(){
        int flag = roomFlags[0];
        for(int i = 1;i < rooms.length;i++){
            if(flag != roomFlags[i])
                return false;
        }
        return true;
    }
    private boolean isGrow(){
        for(Room room: rooms)
            {
                if(room.isGrow()){
                    return true;
                }
            }
        return false;
    }
    private void setConnect(int srcIndex,int targetIndex){
        if(roomFlags[srcIndex] == roomFlags[targetIndex])
            return;
        int flag = roomFlags[targetIndex];
        for(int i = 0;i < roomFlags.length;i++)
            if(roomFlags[i] == flag){
                roomFlags[i] = roomFlags[srcIndex];
            }
    }
    private void checkAdjFromRoomSet(int roomIndex){
        for(int i = 0;i < rooms.length;i++){
            if(rooms[roomIndex].checkAdjRoomAndGenDoor(rand,rooms[i]))
                setConnect(roomIndex,i);
        }
    }
    private void growRoom(int roomIndex){
        for(int i = 0;i < 4;i++){
            rooms[roomIndex].growUp(rand,i,width,height);
            checkAdjFromRoomSet(roomIndex);
        }
    }
    public boolean generateWorld(TETile[][] world){
        int count = 30;
        while(isGrow() && !isFullConnect() && count > 0){
            for(int i = 0;i < rooms.length;i++){
                growRoom(i);
            }
            count--;
        }
        return isFullConnect();
    }
    public void draw(TETile[][] world){
        for(Room room : rooms){
            room.draw(world);
        }
    }
    public int getConnectNum(){
        Set<Integer> indexSet = new HashSet<>();
        for(int i = 0;i < roomFlags.length;i++)
            indexSet.add(roomFlags[i]);
        return indexSet.size();
    }
    public static void main(String[] args) {
        int width = 45;
        int height = 45;
        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        RandomWorldGenerator worldGen = new RandomWorldGenerator(100,width,height);
        worldGen.setRandomRoomNum(false);
        worldGen.setMaxRoomNum(70);
        worldGen.init();
        if(worldGen.generateWorld(world)){
            worldGen.draw(world);
            ter.renderFrame(world);
        }else{
            System.out.println("error: didn't get a fully connected world!");
        }
    }
}
