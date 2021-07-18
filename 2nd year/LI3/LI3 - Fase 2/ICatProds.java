import java.util.Set;

/**
 * Interface correspondente ao catalogo de produtos que permite
 * fazer algumas operaçoes sobre o catalago
 *
 * @author Grupo31
 * @version 03/06/2019
 */
public interface ICatProds
{
    /**
     * Funcao que nos devolve o catalogo de produtos
     * 
     * @return catalogo de produtos na forma de um Set
     */
    
    public Set <IProduto> getProdutos ();
    
    /**
     * Funcao que nos permite alterar o catalogo de produtos
     * 
     * @param novo catalogo de produtos na forma de um Set
     */
    
    public void setProdutos (Set <IProduto> catp);
    
    /**
     * Funcao que da clone a um catalogo de produtos, ou seja, 
     * cria uma copia do catalogo em questao
     * 
     * @return copia do catalogo de produtos
     */
    
    public ICatProds clone ();
    
    /**
     * Verifica se o produto enviado como argumento existe no catalogo
     *
     * @param  p produto que pretende procurar
     * @return   retorna true ou false consoante o produto estar ou nao presente
     */
    
    public boolean existeProduto (IProduto p);
    
    /**
    * Adiciona um produto ao catalogo
    * 
    * @param p produto para adicionar
    */
  
    public void insereProduto (IProduto p);
    
    /**
     * Calcula o numero de produtos existentes no catalogo
     * 
     * @return retorna o numero de produtos
     */
    
    public int numeroProdutos ();
}
