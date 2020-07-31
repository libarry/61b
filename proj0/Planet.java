public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;
   public Planet(double xP, double yP, double xV,
                  double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
    public double calcDistance(Planet p){
        return Math.sqrt(Math.pow((xxPos - p.xxPos), 2) + Math.pow((yyPos - p.yyPos), 2));
    }
    public double calcForceExertedBy(Planet p) {
        if (!this.equals(p)) {
            double r = calcDistance(p);
            return G * mass * p.mass / (r * r);
        }else return 0;
    }
    public double calcForceExertedByX(Planet p){
       if(!this.equals(p)) {
           double F = calcForceExertedBy(p);
           double r = calcDistance(p);
           return F * (p.xxPos - xxPos) / r;
       }else return 0;
    }
    public double calcForceExertedByY(Planet p){
       if(!this.equals(p)) {
           double F = calcForceExertedBy(p);
           double r = calcDistance(p);
           return F * (p.yyPos - yyPos) / r;
       }else return 0;
    }
    public double calcNetForceExertedByX(Planet[] p){
        double s = 0;
        for(int i = 0;i<p.length;i++){
            s += calcForceExertedByX(p[i]);
        }
        return s;
    }
    public double calcNetForceExertedByY(Planet[] p){
        double s = 0;
        for(int i = 0;i<p.length;i++){
            s += calcForceExertedByY(p[i]);
        }
        return s;
    }
    public void update(double t, double fx,double fy){
       double ax = fx / mass;
       double ay = fy / mass;
       xxVel += ax*t;
       yyVel += ay*t;
       xxPos += xxVel*t;
       yyPos += yyVel*t;
    }
    public void draw(){
       StdDraw.picture(xxPos,yyPos,"images/"+imgFileName);
    }
}
