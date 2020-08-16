package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Rectangle {
    private Point[] corner = new Point[4];
    public Line[] edge = new Line[4];
    private TETile edgeTile = Tileset.WALL;
    private TETile insideTile = Tileset.FLOOR;
    public Rectangle(int x,int y){
        if(x < 0 || y < 0)
            throw new IllegalArgumentException("Coordinates must be positive or zero!");
        corner[0] = new Point(x - 1,y + 1);
        corner[1] = new Point(x - 1,y - 1);
        corner[2] = new Point(x + 1,y + 1);
        corner[3] = new Point(x + 1,y - 1);
        edge[0] = new Line(corner[0],corner[1]);
        edge[1] = new Line(corner[0],corner[2]);
        edge[2] = new Line(corner[1],corner[3]);
        edge[3] = new Line(corner[2],corner[3]);
    }
    public Rectangle(int point1x,int point1y,int point2x,int point2y){
        if(point1x == point2x || point1y == point2y)
            throw new IllegalArgumentException("The Rectangle cannot get two points at one line!");
        int LUx = Math.min(point1x,point2x);
        int LUy = Math.max(point1y,point2y);
        int RLx = Math.max(point1x,point2x);
        int RLy = Math.min(point1y,point2y);
        corner[0] = new Point(LUx,LUy);
        corner[1] = new Point(LUx,RLy);
        corner[2] = new Point(RLx,LUy);
        corner[3] = new Point(RLx,RLy);
        edge[0] = new Line(corner[0],corner[1]);
        edge[1] = new Line(corner[0],corner[2]);
        edge[2] = new Line(corner[1],corner[3]);
        edge[3] = new Line(corner[2],corner[3]);
    }
    public void setEdgeTile(TETile tile){
        this.edgeTile = tile;
    }
    public void setInsideTile(TETile tile){
        this.insideTile = tile;
    }
    public Point getLeftUpper(){
        return new Point(corner[0].x,corner[0].y);
    }
    public Point getRightLower(){
        return new Point(corner[3].x,corner[3].y);
    }
    public boolean moveLine(int lineIndex,int offset,int maxW,int maxH){
        if(offset < 0){
            throw new IllegalArgumentException("Offset must be positive or zero!");
        }
        if(lineIndex < 0|| lineIndex > 3)
            throw new IllegalArgumentException("lineIndex must be in [0,4)!");
        if(offset != 0){
            switch (lineIndex){
                case 0:
                    if(edge[0].getBase() - offset < 0)
                        return false;
                    edge[lineIndex].move(-offset,0);
                    break;
                case 1:
                    if(edge[1].getBase() + offset >= maxH)
                        return false;
                    edge[lineIndex].move(0,offset);
                    break;
                case 2:
                    if(edge[2].getBase() - offset < 0)
                        return false;
                    edge[lineIndex].move(0,-offset);
                    break;
                case 3:
                    if(edge[3].getBase() + offset >= maxW)
                        return false;
                    edge[lineIndex].move(offset,0);
                    break;
            }
        }
        return true;
    }

    public boolean isPointOnEdge(Point p){
        for(int i = 0;i < 4;i++){
            if(edge[i].isAdjacentPoint(p,0,false))
                return true;
        }
        return false;
    }
    public Line adjacentRect(Rectangle b){
        Line adj = null;
        for(int i = 0;i < 4;i++)
            for(int j = 0;j < 4;j++){
                adj = edge[i].adjacentLine(b.edge[j]);
                if(adj != null)
                    return adj;
            }
        return adj;
    }
    public void draw(TETile[][] world){
        for(int i = 0; i < 4;i++){
            edge[i].setTile(edgeTile);
            edge[i].draw(world);
        }
        Point leftUpper = getLeftUpper();
        Point rightLower = getRightLower();
        for(int x = leftUpper.x + 1;x < rightLower.x;x++)
            for(int y = rightLower.y + 1;y < leftUpper.y;y++)
                world[x][y] = insideTile;

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
        Rectangle rec1 = new Rectangle(5,5);
        Rectangle rec2 = new Rectangle(15,5);
        rec1.moveLine(3,3,50,50);
        rec2.moveLine(0,4,50,50);
        rec1.moveLine(2,2,50,50);
        Line adj = rec1.adjacentRect(rec2);
        rec1.draw(world);
        rec2.draw(world);
        if(adj != null)
        {
            adj.setTile(Tileset.WATER);
            adj.draw(world);
        }
        ter.renderFrame(world);
    }
}
