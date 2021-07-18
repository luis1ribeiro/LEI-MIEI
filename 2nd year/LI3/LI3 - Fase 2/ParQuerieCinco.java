import java.io.*;

/**
 * Classe auxiliar para a realizacao da querie 5
 *
 * @author Grupo31
 * @version 2019
 */
public class ParQuerieCinco implements Comparable <ParQuerieCinco>, Serializable
{
    // instance variables - replace the example below with your own
    private String produto;
    private int quantidade;

    public ParQuerieCinco()
    {
        this.produto="";
        this.quantidade=0;
    }
    
    public ParQuerieCinco(String prod, int quant)
    {
        this.produto=prod;
        this.quantidade=quant;
    }
    
    public ParQuerieCinco(ParQuerieCinco t)
    {
        this.produto=t.getProduto();
        this.quantidade=t.getQuantidade();
    }

    public String getProduto (){
        return this.produto;
    }
    
    public int getQuantidade (){
        return this.quantidade;
    }
    
    public void setProduto (String prod){
        this.produto=prod;
    }
    
    public void setQuantidade (int quantidade){
        this.quantidade=quantidade;
    }
    
    public ParQuerieCinco clone (){
        return new ParQuerieCinco (this);
    }
    
    public int compareTo (ParQuerieCinco t){
        if (this.quantidade==t.getQuantidade()) return this.produto.compareTo(t.getProduto());
        else return Integer.compare(t.getQuantidade(),this.quantidade);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder ();
        sb.append("[").append(this.getProduto()).append("]:").append(this.getQuantidade()).append(" ");
        return sb.toString();
    }
}