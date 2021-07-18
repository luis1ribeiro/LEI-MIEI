import static java.lang.System.out;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe correspondente ao catalogo de produtos, que permite
 * fazer algumas operaçoes sobre o catalago
 *
 * @author Grupo31
 * @version 2019
 */

public class CatProds implements ICatProds, Serializable
{
    private Set <IProduto> produtos;
    
    /** 
     * Construtor vazio da classe CatProds
     * 
     * @return novo catalogo de produtos vazio
     */
    
    public CatProds (){
        this.produtos= new TreeSet <IProduto> ();
    }
    
    /** 
     * Construtor parametrizado da classe CatProds
     * 
     * @param prods Set com o novo catalogo de produtos
     * @return novo CatProds com o Set recebido como parametro
     */
    
    public CatProds (Set <IProduto> prods){
        this.setProdutos(prods);
    }
    
    /** 
     * Construtor por copia da classe CatProds
     * 
     * @param catp CatProds a copiar
     * @return copia do catalogo de produtos em questao
     */
    
    public CatProds (ICatProds catp){
        this.produtos=catp.getProdutos();
    }
    
    public Set <IProduto> getProdutos (){
        return this.produtos.stream().map(IProduto::clone).collect(Collectors.toSet());
    }
    
    public void setProdutos (Set <IProduto> catp){
        this.produtos=catp.stream().map(IProduto::clone).collect(Collectors.toSet());
    }
    
    public ICatProds clone (){
        return new CatProds (this);
    }
    
    public boolean existeProduto (IProduto p){
        return this.produtos.contains(p);
    }
    
    public void insereProduto (IProduto p){
        this.produtos.add(p.clone());
    }
    
    public int numeroProdutos (){
        return this.produtos.size();
    }
}