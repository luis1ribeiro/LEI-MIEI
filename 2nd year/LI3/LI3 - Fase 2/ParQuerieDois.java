import java.io.*;

/**
 * Classe auxiliar para a realizacao da querie 2
 *
 * @author Grupo31
 * @version 2019
 */
public class ParQuerieDois implements Serializable
{
    private int vendas;
    private int clientes;

    /**
     * Constructor for objects of class TrioQuerieDois
     */
    public ParQuerieDois()
    {
        this.vendas=0;
        this.clientes=0;
    }
    
    public ParQuerieDois(int vendas, int clientes)
    {
        this.vendas=vendas;
        this.clientes=clientes;
    }

    public ParQuerieDois(ParQuerieDois p)
    {
        this.vendas=p.getVendas();
        this.clientes=p.getClientes();
    }
    
    public int getVendas (){
        return this.vendas;
    }
    
    public int getClientes(){
        return this.clientes;
    }

    public ParQuerieDois clone (){
        return new ParQuerieDois (this);
    }
}
