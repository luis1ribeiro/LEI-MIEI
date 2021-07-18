import java.io.*;

/**
 * Classe auxiliar para a realizacao da querie 4
 *
 * @author Grupo31
 * @version 2019
 */
public class TrioQuerieQuatro implements Serializable
{
    // instance variables - replace the example below with your own
    private int vendas;
    private int clientes;
    private double faturado;

    /**
     * Constructor for objects of class TrioQuerieQuatro
     */
    public TrioQuerieQuatro()
    {
        // initialise instance variables
        this.vendas=0;
        this.clientes=0;
        this.faturado=0.0;
    }
    
    public TrioQuerieQuatro(int vendas, int clientes, double faturado)
    {
        // initialise instance variables
        this.vendas=vendas;
        this.clientes=clientes;
        this.faturado=faturado;
    }
    
    public TrioQuerieQuatro(TrioQuerieQuatro trio)
    {
        // initialise instance variables
        this.vendas=trio.getVendas();
        this.clientes=trio.getClientes();
        this.faturado=trio.getFaturado();
    }

    public int getVendas (){
        return this.vendas;
    }
    
    public void setVendas (int vendas){
        this.vendas=vendas;
    }
    
    public int getClientes (){
        return this.clientes;
    }
    
    public void setClientes (int clientes){
        this.clientes=clientes;
    }
    
    public double getFaturado (){
        return this.faturado;
    }
    
    public void setFaturado (int faturado){
        this.faturado=faturado;
    }
    
    public TrioQuerieQuatro clone (){
        return new TrioQuerieQuatro (this);
    }
}