import java.io.*;

/**
 * Classe auxiliar a DadosMesFilial e consequentemente a filial e que representa os dados de uma venda. 
 *
 * @author Grupo31
 * @version 2019
 */

public class DadosFilial implements Serializable
{
    // instance variables - replace the example below with your own
    private IProduto produto;
    private char tipo;
    private int unidades;
    private double preco;
    
    public DadosFilial (){
        this.produto = new Produto ();
        this.tipo='N';
        this.unidades=0;
        this.preco=0;
    }
    
    public DadosFilial(IProduto p, char tipo, int uni, double preco)
    {
        this.produto= p.clone();
        this.tipo = tipo;
        this.unidades=uni;
        this.preco=preco;
    }
    
    public DadosFilial(DadosFilial d)
    {
        this.produto = d.getProduto();
        this.tipo = d.getTipo();
        this.unidades=d.getUnidades();
        this.preco=d.getPreco();
    }
    
    public IProduto getProduto()
    {
        return this.produto.clone();
    }
    
    public char getTipo()
    {
        return this.tipo;
    }
    
    public int getUnidades()
    {
        return this.unidades;
    }
    
    public double getPreco()
    {
        return this.preco;
    }
    
    public void setProduto (IProduto p){
        this.produto=p.clone();
    }
    
    public void setTipo (char t){
        this.tipo=t;
    }
    
    public void setUnidades(int uni){
        this.unidades=uni;
    }
    
    public void setPreco (double preco){
        this.preco=preco;
    }
    
    public DadosFilial clone(){
        return new DadosFilial (this);
    }
    
    public boolean equals (Object o){
        if (this==o) return true;
        if (o==null || o.getClass()!=this.getClass()) return false;
        DadosFilial f = (DadosFilial) o;
        return this.produto.equals(f.getProduto()) && this.tipo==f.getTipo() && this.unidades==f.getUnidades() && this.preco==f.getPreco();
    }
    
    public String toString(){
        StringBuilder sb =  new StringBuilder ();
        sb.append("Produto: ").append(this.produto).append("\nTipo :").append(this.tipo).append("\nUnidades :").append(this.unidades).append("\nPreco :").append(this.preco);
        return sb.toString();
    }
    
    public double totalGasto (){
        return this.unidades*this.preco;
    }
}