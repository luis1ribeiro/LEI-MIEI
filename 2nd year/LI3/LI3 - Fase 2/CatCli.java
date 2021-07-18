import static java.lang.System.out;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Classe correspondente ao catalogo de clientes, que permite
 * fazer algumas operaçoes sobre o catalago
 *
 * @author Grupo31
 * @version 2019
 */

public class CatCli implements ICatCli,Serializable
{
    private Set <ICliente> clientes;
    
    /** 
     * Construtor vazio da classe CatCli
     */
    
    public CatCli (){
        this.clientes= new TreeSet <ICliente> ();
    }
    
    /** 
     * Construtor parametrizado da classe CatCli
     * 
     * @param clientes Set com o novo catalogo de clientes
     */
    
    public CatCli (Set <ICliente> clientes){
        this.setClientes(clientes);
    }
    
    /** 
     * Construtor por copia da classe CatCli
     * 
     * @param c CatCli a copiar
     */
    
    public CatCli (ICatCli c){
        this.clientes= c.getClientes();
    }
    
    public Set <ICliente> getClientes(){
        return this.clientes.stream().map(ICliente::clone).collect(Collectors.toSet());
    }
    
    public void setClientes (Set <ICliente> clientes){
        this.clientes=clientes.stream().map(ICliente::clone).collect(Collectors.toSet());
    }
    
    public ICatCli clone (){
        return new CatCli (this);
    }
    
    public boolean existeCliente (ICliente c){
        return this.clientes.contains(c);
    }
    
    public void insereCliente (ICliente c){
        this.clientes.add(c.clone());
    }
    
    public int numeroClientes () {
        return this.clientes.size();
    }
}