import java.io.*;
/**
 * Classe auxiliar para a realizacao da querie 9
 *
 * @author Grupo31
 * @version 2019
 */
public class TrioQuerieNove implements Comparable <TrioQuerieNove>, Serializable
{
    // instance variables - replace the example below with your own
    private String cliente;
    private int unidades;
    private double gasto;

    public TrioQuerieNove()
    {
        this.cliente="";
        this.unidades=0;
        this.gasto=0;
    }
    
    public TrioQuerieNove(String cli, int uni, double g)
    {
        this.cliente=cli;
        this.unidades=uni;
        this.gasto=g;
    }
    
    public TrioQuerieNove(TrioQuerieNove t)
    {
        this.cliente=t.getCliente();
        this.unidades=t.getUnidades();
        this.gasto=t.getGasto();
    }

    public String getCliente (){
        return this.cliente;
    }
    
    public void setCliente (String c){
        this.cliente=c;
    }
    
    public int getUnidades (){
        return this.unidades;
    }
    
    public void setUnidades (int u){
        this.unidades=u;
    }
    
    public double getGasto (){
        return this.gasto;
    }
    
    public void setGasto (double g){
        this.gasto=g;
    }
    
    public TrioQuerieNove clone (){
        return new TrioQuerieNove (this);
    }
    
    public int compareTo (TrioQuerieNove t){
        if (this.unidades==t.getUnidades()) return this.cliente.compareTo(t.getCliente());
        else return Integer.compare(t.getUnidades(),this.unidades);
    }
}
