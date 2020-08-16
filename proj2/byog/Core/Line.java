package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


public class Line {
    public Point highPoint;
    public Point lowPoint;
    private final boolean vertical;
    TETile tile = Tileset.WALL;
    //Line must be vertical or horizontal.
    public Line(Point a,Point b){
        if(a.x != b.x && a.y != b.y){
            throw new IllegalArgumentException("Line is not vertical or horizontal!");
        }
        if(a.x == b.x) {
            vertical = true;
            highPoint = a.y > b.y ? a : b;
            lowPoint = a.y < b.y ? a : b;
        }
        else {
            vertical = false;
            highPoint = a.x > b.x ? a : b;
            lowPoint = a.x < b.x ? a : b;
        }
    }
    public Line(int base, int low,int high, boolean vertical){
        if(low > high)
            throw new IllegalArgumentException("Line's low point is higher than high point!");
        this.vertical = vertical;
        if(vertical){
            lowPoint = new Point(base,low);
            highPoint = new Point(base,high);
        }else{
            lowPoint = new Point(low, base);
            highPoint = new Point(high,base);
        }
    }
    public void setTile(TETile t){tile = t;}
    public int getLow(){
        if(vertical){
            return lowPoint.y;
        }else return lowPoint.x;
    }
    public int getHigh(){
        if(vertical){
            return highPoint.y;
        }else return highPoint.x;
    }
    public int getBase(){
        if(vertical)
            return lowPoint.x;
        else return lowPoint.y;
    }
    public boolean isVertical()
    {
        return vertical;
    }

    public boolean isAdjacentPoint(Point p,int dis,boolean considerEnd){
        if(dis < 0)
            throw new IllegalArgumentException("Bad value of distance.");
        if(isVertical()){
            if(considerEnd)
                return Math.abs(lowPoint.x - p.x) == dis &&
                        p.y <= highPoint.y &&
                        p.y >= lowPoint.y;
            else
                return Math.abs(lowPoint.x - p.x) == dis &&
                        p.y < highPoint.y &&
                        p.y > lowPoint.y;
        }else{
            if(considerEnd)
                return Math.abs(lowPoint.y - p.y) == dis &&
                        p.x <= highPoint.x &&
                        p.x >= lowPoint.x;
            else
                return Math.abs(lowPoint.y - p.y) == dis &&
                        p.x < highPoint.x &&
                        p.x > lowPoint.x;
        }
    }
    public Line adjacentLine(Line b){
        if(vertical != b.vertical)
            return null;
        boolean bLowTHis = isAdjacentPoint(b.lowPoint,1,true);
        boolean bHighThis = isAdjacentPoint(b.highPoint,1,true);
        boolean thisLowB = b.isAdjacentPoint(lowPoint,1,true);
        boolean thisHighB = b.isAdjacentPoint(highPoint,1,true);
        if(!bLowTHis && !bHighThis && !thisLowB && !thisHighB)
            return null;
        int low = Math.max(getHigh(),b.getHigh());
        int high = 0;
        if(bLowTHis){
            int position = vertical ? b.lowPoint.y : b.lowPoint.x;
            low = Math.min(low,position);
            high = Math.max(high,position);
        }
        if(bHighThis){
            int position = vertical ? b.highPoint.y : b.highPoint.x;
            low = Math.min(low,position);
            high = Math.max(high,position);
        }
        if(thisLowB){
            int position = vertical ? lowPoint.y : lowPoint.x;
            low = Math.min(low,position);
            high = Math.max(high,position);
        }
        if(thisHighB){
            int position = vertical ? highPoint.y : highPoint.x;
            low = Math.min(low,position);
            high = Math.max(high,position);
        }
        return new Line(getBase(),low,high,vertical);
    }
    public void move(int x,int y){
        lowPoint.x += x;
        highPoint.x += x;
        lowPoint.y += y;
        highPoint.y += y;
    }
    public void draw(TETile[][] world){
        int base = getBase();
        int low = getLow();
        int high = getHigh();
        for(int i = low;i <= high;i++){
            if(isVertical()){
                world[base][i] = tile;
            }else{
                world[i][base] = tile;
            }
        }
    }
    public int getLength(){
        return getHigh() - getLow() + 1;
    }

    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        ter.initialize(50,50);

        TETile[][] world = new TETile[50][50];
        for (int x = 0; x < 50; x += 1) {
            for (int y = 0; y < 50; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Line l1 = new Line(10,10,20,false);
        Line l2 = new Line(11,8,21,false);
        Line l3 = l1.adjacentLine(l2);
        l1.setTile(Tileset.WALL);
        l2.setTile(Tileset.FLOOR);
        l3.setTile(Tileset.WATER);
        l1.draw(world);
        l2.draw(world);
        l3.draw(world);
        ter.renderFrame(world);
    }
}
