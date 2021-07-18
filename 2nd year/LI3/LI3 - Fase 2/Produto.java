import static java.lang.System.out;
import java.io.*;
import java.util.*;

/**
 * Classe correspondente ao Produto que permite fazer
 * algumas operaçoes sobre os produtos
 *
 * @author Grupo31
 * @version 2019
 */

public class Produto implements IProduto, Serializable, Comparable <IProduto>
{
    private String codigo;
    
    /** 
     * Construtor vazio da classe produto
     * 
     * @return novo produto vazio
     */
    public Produto (){
        this.codigo="";
    }
    
    /** 
     * Construtor parametrizado da classe produto
     * 
     * @param cod Codigo do novo produto
     * @return novo produto com o codigo recebido
     */
    
    public Produto (String cod){
        this.codigo=cod;
    }
    
    /** 
     * Construtor por copia da classe produto
     * 
     * @param p produto a copiar
     * @return novo produto com o mesmo codigo do produto recebido
     */
    
    public Produto (IProduto p){
        this.codigo= p.getCodigo();
    }
    
    public String getCodigo (){
        return this.codigo;
    }
    
    public void setCodigo (String cod){
        this.codigo=cod;
    }
    
    public IProduto clone (){
        return new Produto (this);
    }
    
    public boolean equals (Object o){
        if (this==o) return true;
        if(o==null || o.getClass()!=this.getClass()) return false;
        IProduto p = (IProduto) o;
        return this.codigo.equals(p.getCodigo());
    }
    
    public int hashCode (){
        return this.codigo.hashCode();
    }
    
    public int compareTo (IProduto p){
        return this.codigo.compareTo(p.getCodigo());
    }
    
    public String toString (){
        return this.codigo;
    }
}