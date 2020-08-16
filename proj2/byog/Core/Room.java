package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.*;

public class Room {
    public Rectangle room;
    private List<Point> door;
    private boolean[] growControl;
    private List<Room> connectedRoom;

    public Room(int x,int y){
        room = new Rectangle(x,y);
        door = new LinkedList<>();
        connectedRoom = new LinkedList<>();
        growControl = new boolean[]{true,true,true,true};
    }
    public Room(int x,int y,boolean vertical){
        room = new Rectangle(x,y);
        door = new LinkedList<>();
        connectedRoom = new LinkedList<>();
        if(vertical)
            growControl = new boolean[]{false,true,true,false};
        else
            growControl = new boolean[]{true,false,false,true};
    }
    public void setDoor(Point a,Room adjRoom){
        if(room.isPointOnEdge(a)){
            door.add(a);
            connectedRoom.add(adjRoom);
        }else{
            throw new IllegalArgumentException("The point is not on edge!");
        }
    }
    public boolean isGrow(){
        return growControl[0] || growControl[1] || growControl[2] || growControl[3];
    }
    public boolean isGrow(int index){
        return growControl[index];
    }
    public void shutDownEdgeGrow(int lineIndex){
        if(lineIndex < 0|| lineIndex > 3)
            throw new IllegalArgumentException("lineIndex must be in [0,4)!");
        growControl[lineIndex] = false;
    }
    public void growUp(Random rand,int edgeIndex,int W,int H){
        if(growControl[edgeIndex]){
            growControl[edgeIndex] = room.moveLine(edgeIndex,1,W,H);
        }
    }

    private Point genDoor(int p,Line l){
        if(l.getLow() + 1 < l.getHigh()) {
            int base = l.getBase();
            if (l.isVertical()) {
                return new Point(base, p);
            } else
                return new Point(p, base);
        }
        else return null;
    }

    public boolean checkAdjRoomAndGenDoor(Random rand,Room b){
        Line adj = null;
        for(int i = 0;i < 4;i++)
            for(int j = 0;j < 4;j++){
                adj = room.edge[i].adjacentLine(b.room.edge[j]);
                if(adj != null){
                    shutDownEdgeGrow(i);
                    b.shutDownEdgeGrow(j);
                    if(adj.getLength() > 2 && !connectedRoom.contains(b)){
                        int randDoorIndex = RandomUtils.uniform(rand, adj.getLow() + 1, adj.getHigh());
                        Point d = genDoor(randDoorIndex,adj);
                        setDoor(d,b);
                        Line adj2 = b.room.edge[j].adjacentLine(room.edge[i]);
                        if(adj2.getLength()!= adj.getLength())
                            throw new RuntimeException("The length of two adjacent lines is different!");
                        d = genDoor(randDoorIndex,adj2);
                        b.setDoor(d,this);
                        return true;
                    }
                }
            }
        return false;
    }
    public void draw(TETile[][] world){
        room.draw(world);
        for(int i = 0;i < door.size();i++)
            door.get(i).draw(world);
    }
    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        ter.initialize(40,40);
        TETile[][] world = new TETile[40][40];
        for (int x = 0; x < 40; x += 1) {
            for (int y = 0; y < 40; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Room[] rooms = new Room[2];
        rooms[0] = new Room(10,10);
        rooms[1] = new Room(25,25);
        rooms[0].draw(world);
        rooms[1].draw(world);
        ter.renderFrame(world);
    }

}
