
/**
 * Write a description of class Ponto here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Ponto implements java.io.Serializable
{
    // instance variables - replace the example below with your own
    private double x;
    private double y;

    /**
     * Constructor for objects of class Ponto
     */
    public Ponto()
    {
        this.x=0;
        this.y=0;
    }
    
    public Ponto(double x, double y)
    {
        this.x=x;
        this.y=y;
    }
    
    public Ponto(Ponto p)
    {
        this.x=p.getX();
        this.y=p.getY();
    }
    
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public double getX()
    {
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }
    
    public void setX(double x){
        this.x=x;
    }
    
    public void setY(double y){
        this.y=y;
    }
    
    public double dist (Ponto p){
        return Math.sqrt(Math.pow(this.x-p.getX(),2)+Math.pow(this.y-p.getY(),2));
    }
    
    public Ponto clone(){
        return new Ponto (this);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder ();
        sb.append("(").append(this.x).append(",").append(this.y).append(")");
        return sb.toString();
    }
    
    public boolean equals(Object o){
        if (this==o) return true;
        if (this.getClass()!=o.getClass()) return false;
        Ponto p = (Ponto) o;
        return this.x==p.getX() && this.y==p.getY();
    }
}
