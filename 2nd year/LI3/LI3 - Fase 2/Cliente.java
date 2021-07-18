import static java.lang.System.out;
import java.io.*;
import java.util.*;

/**
 * Classe correspondente ao Cliente que permite fazer
 * algumas operaçoes sobre os clientes
 *
 * @author Grupo31
 * @version 2019
 */

public class Cliente implements ICliente, Comparable <ICliente>,Serializable
{   
    private String codigo;
    
    /** 
     * Construtor vazio da classe Cliente
     * 
     */
    public Cliente (){
        this.codigo="";
    }
    
    /** 
     * Construtor parametrizado da classe Cliente
     * 
     * @param codigo Codigo do novo cliente
     */
    
    public Cliente (String codigo){
        this.codigo=codigo;
    }
    
    /** 
     * Construtor por copia da classe Cliente
     * 
     * @param c Cliente a copiar
     */
    
    public Cliente (Cliente c){
        this.codigo = c.getCodigo();
    }
    
    public String getCodigo (){
        return this.codigo;
    }
    
    public void setCodigo (String cod){
        this.codigo=cod;
    }
    
    public boolean equals (Object o){
        if (this==o) return true;
        if (o==null || o.getClass()!=this.getClass()) return false;
        ICliente c = (ICliente) o;
        return this.codigo.equals(c.getCodigo());
    }
    
    public String toString (){
        return this.codigo;
    }
    
    public ICliente clone (){
        return new Cliente (this);
    }
    
    public int hashCode (){
        return this.codigo.hashCode();
    }
    
    public int compareTo (ICliente p){
        return this.codigo.compareTo(p.getCodigo());
    }
}