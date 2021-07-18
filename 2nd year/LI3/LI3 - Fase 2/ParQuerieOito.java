import java.io.*;
/**
 * Classe auxiliar para a realizacao da querie 8
 *
 * @author Grupo31
 * @version 2019
 */
public class ParQuerieOito implements Comparable <ParQuerieOito>, Serializable
{
    // instance variables - replace the example below with your own
    private String cliente;
    private int produtosDist;

    public ParQuerieOito()
    {
        this.cliente="";
        this.produtosDist=0;
    }
    
    public ParQuerieOito(String cli, int g)
    {
        this.cliente=cli;
        this.produtosDist=g;
    }
    
    public ParQuerieOito(ParQuerieOito p)
    {
        this.cliente=p.getCliente();
        this.produtosDist=p.getProdutosDist();
    }

    public String getCliente (){
        return this.cliente;
    }
    
    public void setCliente (String c){
        this.cliente=c;
    }
    
    public int getProdutosDist (){
        return this.produtosDist;
    }
    
    public void setProdutosDist (int p){
        this.produtosDist=p;
    }
    
    public ParQuerieOito clone (){
        return new ParQuerieOito (this);
    }
    
    public int compareTo (ParQuerieOito p){
        return Integer.compare(p.getProdutosDist(),this.produtosDist);
    }
}