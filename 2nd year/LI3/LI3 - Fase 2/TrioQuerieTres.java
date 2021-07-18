import java.io.*;

/**
 * Classe auxiliar para a realizacao da querie 3
 *
 * @author Grupo31
 * @version 2019
 */
public class TrioQuerieTres implements Serializable
{
    // instance variables - replace the example below with your own
    private int compras;
    private int produtos;
    private double gasto;

    /**
     * Constructor for objects of class TrioQuerieTres
     */
    public TrioQuerieTres()
    {
        this.compras=0;
        this.produtos=0;
        this.gasto=0;
    }
    
    public TrioQuerieTres(int comp, int prod, double gasto)
    {
        this.compras=comp;
        this.produtos=prod;
        this.gasto=gasto;
    }
    
    public TrioQuerieTres(TrioQuerieTres t)
    {
        this.compras=t.getCompras();
        this.produtos=t.getProdutos();
        this.gasto=t.getGasto();
    }

    public int getCompras (){
        return this.compras;
    }
    
    public int getProdutos (){
        return this.produtos;
    }
    
    public double getGasto (){
        return this.gasto;
    }
    
    public TrioQuerieTres clone ()
    {
        return new TrioQuerieTres (this);
    }
}
