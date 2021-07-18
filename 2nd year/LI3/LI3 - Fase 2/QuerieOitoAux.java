import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.*;

/**
 * Classe auxiliar para a realizacao da querie 8
 *
 * @author Grupo31
 * @version 2019
 */
public class QuerieOitoAux implements Serializable
{
    // instance variables - replace the example below with your own
    private String cliente;
    private List<String> produtos;

    public QuerieOitoAux()
    {
        this.cliente="";
        this.produtos= new ArrayList <> ();
    }
    
    public QuerieOitoAux (String cli,List<String> p)
    {
        this.cliente=cli;
        this.setProdutos(p);
    }
    
    public QuerieOitoAux(QuerieOitoAux a)
    {
        this.cliente=a.getCliente();
        this.produtos= a.getProdutos();
    }

    public String getCliente(){
        return this.cliente;
    }
    
    public void setCliente(String c){
        this.cliente=c;
    }
    
    public List<String> getProdutos (){
        return this.produtos.stream().collect(Collectors.toList());
    }
    
    public void setProdutos(List <String> p){
        this.produtos= p.stream().collect(Collectors.toList());
    }
    
    public QuerieOitoAux clone (){
        return new QuerieOitoAux (this);
    }
}