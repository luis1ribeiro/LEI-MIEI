import java.io.*;
/**
 * Classe auxiliar para a realizacao da querie 7
 *
 * @author Grupo31
 * @version 2019
 */
public class ParQuerieSete implements Comparable <ParQuerieSete>, Serializable
{
    // instance variables - replace the example below with your own
    private String cliente;
    private double gasto;

    public ParQuerieSete()
    {
        this.cliente="";
        this.gasto=0;
    }
    
    public ParQuerieSete(String cli, double g)
    {
        this.cliente=cli;
        this.gasto=g;
    }
    
    public ParQuerieSete(ParQuerieSete p)
    {
        this.cliente=p.getCliente();
        this.gasto=p.getGasto();
    }

    public String getCliente (){
        return this.cliente;
    }
    
    public void setCliente (String c){
        this.cliente=c;
    }
    
    public double getGasto (){
        return this.gasto;
    }
    
    public void setGasto (double gasto){
        this.gasto=gasto;
    }
    
    public ParQuerieSete clone (){
        return new ParQuerieSete (this);
    }
    
    public int compareTo (ParQuerieSete p){
        return Double.compare(p.getGasto(),this.gasto);
    }
}
