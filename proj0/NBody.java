public class NBody {
    public static double readRadius(String file){
        In in = new In(file);
        in.readInt();
        return in.readDouble();
    }
    public static Planet[] readPlanets(String file){
        In in = new In(file);
        int n = in.readInt();
        in.readDouble();
        Planet[] p = new Planet[n];
        for(int i = 0;i < n;i++){
            p[i] = new Planet(in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readString());
        }
        return p;
    }
    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] p = readPlanets(filename);
        double R = readRadius(filename);

        StdDraw.enableDoubleBuffering();

        StdDraw.setScale(-R, R);
        StdDraw.picture(0,0,"images/starfield.jpg");
        for(int i = 0;i<p.length;i++){
            p[i].draw();
        }
        StdDraw.show();
        double[] xForce = new double[p.length];
        double[] yForce = new double[p.length];
        for(int t = 0;t < T;t += dt){
            for(int i = 0;i < p.length;i++){
                xForce[i] = p[i].calcNetForceExertedByX(p);
                yForce[i] = p[i].calcNetForceExertedByY(p);
            }
            StdDraw.picture(0,0,"images/starfield.jpg");
            for(int i = 0;i<p.length;i++){
                p[i].update(dt,xForce[i],yForce[i]);
                p[i].draw();

            }
            StdDraw.show();
            StdDraw.pause(10);
        }

    }
}
