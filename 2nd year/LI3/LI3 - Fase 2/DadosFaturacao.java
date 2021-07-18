import java.io.*;

/**
 * Classe auxiliar a DadosMesFaturacao e consequentemente a faturacao e que representa os dados de uma venda. 
 *
 * @author Grupo31
 * @version 2019
 */
public class DadosFaturacao implements Serializable
{
    private int unidades;
    private double preco;
    private int filial;

    public DadosFaturacao()
    {
        this.unidades=0;
        this.preco=0;
        this.filial=0;
    }
    
    public DadosFaturacao (int unidades, double preco, int filial){
        this.unidades=unidades;
        this.preco=preco;
        this.filial=filial;
    }

    public DadosFaturacao (DadosFaturacao d){
        this.unidades=d.getUnidades();
        this.preco=d.getPreco();
        this.filial=d.getFilial();
    }
    
    public int getUnidades ()
    {
        return this.unidades;
    }
    
    public double getPreco()
    {
        return this.preco;
    }
    
    public int getFilial ()
    {
        return this.filial;
    }
    
    public void setUnidades(int uni){
        this.unidades=uni;
    }
    
    public void setPreco (double preco){
        this.preco=preco;
    }
    
    public void setFilial (int filial){
        this.filial=filial;
    }
    
    public DadosFaturacao clone (){
        return new DadosFaturacao (this);
    }
    
    public boolean equals (Object o){
        if (o==this) return true;
        if(o==null || o.getClass()!=this.getClass()) return false;
        DadosFaturacao d = (DadosFaturacao) o;
        return this.unidades==d.getUnidades() && this.preco==d.getPreco() && this.filial==d.getFilial();
    }
    
    public double totalFaturado (){
        return this.unidades*this.preco;
    }
}