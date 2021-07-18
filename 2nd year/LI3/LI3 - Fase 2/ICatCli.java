import java.util.Set;

/**
 * Interface correspondente ao catalogo de clientes, que permite
 * fazer algumas operaçoes sobre o catalago
 *
 * @author Grupo31
 * @version 2019
 */
public interface ICatCli
{        
    /**
     * Funcao que nos devolve o catalogo de clientes
     * 
     * @return catalogo de clientes na forma de um Set
     */
    
    public Set <ICliente> getClientes();
    
    
    /**
     * Funcao que nos permite alterar o catalogo de clientes
     * 
     * @param novo catalogo de clientes na forma de um Set
     */
    
    public void setClientes (Set <ICliente> clientes);
    
    /**
     * Funcao que da clone a um catalogo de clientes, ou seja, 
     * cria uma copia do catalogo em questao
     * 
     * @return copia do catalogo de clientes
     */
    public ICatCli clone ();
    
    /**
     * Verifica se o cliente enviado como argumento existe no catalogo
     *
     * @param  c cliente que pretende procurar
     * @return   retorna true ou false consoante o cliente estar ou nao presente
     */
    public boolean existeCliente (ICliente c);
    
    /**
    * Adiciona um cliente ao catalogo
    * 
    * @param c cliente para adicionar
    */
  
    public void insereCliente (ICliente c);
    
    /**
     * Calcula o numero de clientes existentes no catalogo
     * 
     * @return retorna o numero de clientes
     */
    
    public int numeroClientes ();
}
