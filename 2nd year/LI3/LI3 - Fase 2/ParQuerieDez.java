import java.io.*;
/**
 * Classe auxiliar para a realizacao da querie 10
 *
 * @author Grupo31
 * @version 2019
 */
public class ParQuerieDez implements Serializable
{
    private double faturado;
    private String produto;

    public ParQuerieDez()
    {
        this.produto="";
        this.faturado=0;
    }
    
    public ParQuerieDez(String produto, double faturado)
    {
        this.produto=produto;
        this.faturado=faturado;
    }

    public ParQuerieDez(ParQuerieDez p)
    {
        this.produto=p.getProduto();
        this.faturado=p.getFaturado();
    }
    
    public String getProduto (){
        return this.produto;
    }
    
    public double getFaturado(){
        return this.faturado;
    }

    public ParQuerieDez clone (){
        return new ParQuerieDez (this);
    }
}
