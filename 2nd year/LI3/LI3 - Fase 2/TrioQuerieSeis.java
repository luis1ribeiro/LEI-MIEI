import java.io.*;

/**
 * Classe auxiliar para a realizacao da querie 6
 *
 * @author Grupo31
 * @version 2019
 */
public class TrioQuerieSeis implements Comparable <TrioQuerieSeis>, Serializable
{
    // instance variables - replace the example below with your own
    private String produto;
    private int quantidade;
    private int nClientes;

    public TrioQuerieSeis()
    {
        this.produto="";
        this.quantidade=0;
        this.nClientes=0;
    }
    
    public TrioQuerieSeis(String prod, int quant, int nCli)
    {
        this.produto=prod;
        this.quantidade=quant;
        this.nClientes=nCli;
    }
    
    public TrioQuerieSeis(TrioQuerieSeis p)
    {
        this.produto=p.getProduto();
        this.quantidade=p.getQuantidade();
        this.nClientes=p.getNClientes();
    }

    public String getProduto (){
        return this.produto;
    }
    
    public int getQuantidade(){
        return this.quantidade;
    }
    
    public int getNClientes(){
        return this.nClientes;
    }
    
    public void setProduto (String prod){
        this.produto=prod;
    }
    
    public void setQuantidade (int quant){
        this.quantidade=quant;
    }
    
    public void setNClientes (int n){
        this.nClientes=n;
    }
    
    public TrioQuerieSeis clone (){
        return new TrioQuerieSeis (this);
    }
    
    public int compareTo (TrioQuerieSeis p){
        return Integer.compare(p.getQuantidade(),this.quantidade);
    }
}
