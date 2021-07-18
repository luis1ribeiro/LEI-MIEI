import static java.lang.System.out;
import java.io.*;
import java.util.*;

/**
 * Interface correspondente ao Cliente que permite fazer
 * algumas operaçoes sobre os clientes
 *
 * @author Grupo31
 * @version 2019
 */
public interface ICliente extends Serializable
{
    /** 
     * Funcao que nos da o codigo do cliente.
     * 
     * @return String correspondente ao codigo do cliente
     */
    public String getCodigo ();
    
    /** 
     * Funcao que nos permite alterar o codigo do cliente.
     * 
     * @param cod Novo codigo de cliente
     */
    public void setCodigo (String cod);
    
    /** 
     * Funcao que permite comparar dois clientes.
     * 
     * @param o Objeto que pretendemos comparar
     * @return true no caso de serem iguais, false em caso contrario
     */
    public boolean equals (Object o);
    
    /** 
     * Funcao que transforma o cliente numa String
     * para que seja possivel visualiza-lo.
     * 
     * @return String corresponde ao cliente na forma visual
     */
    public String toString ();
    
    /** 
     * Funcao que nos permite dar clone a um cliente,
     * criando um cliente exatamente igual.
     * 
     * @return Clone do cliente
     */
    public ICliente clone ();
    
    /** 
     * Funcao que calcula o hash code de um cliente.
     * 
     * @return inteiro correspondente ao hashCode
     */
    public int hashCode ();
    
    /** 
     * Funcao que compara clientes pelo seu codigo.
     * 
     * @param p cliente a comparar
     * @return >1 caso o p seja menor que o cliente em questao, 0 caso sejam iguais e <1 caso seja maior
     */
    public int compareTo (ICliente p);
}