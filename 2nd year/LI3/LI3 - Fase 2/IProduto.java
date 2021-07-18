/**
 * Interface correspondente ao Produto que permite fazer
 * algumas operaçoes sobre os produtos
 *
 * @author Grupo31
 * @version 2019
 */

public interface IProduto
{
    /** 
     * Funcao que nos da o codigo do produto.
     * 
     * @return String correspondente ao codigo do produto
     */
    
    public String getCodigo ();
    
    /** 
     * Funcao que nos permite alterar o codigo do produto.
     * 
     * @param cod Novo codigo de produto
     */
    public void setCodigo (String cod);
    
    /** 
     * Funcao que permite comparar dois produtos.
     * 
     * @param o Objeto que pretendemos comparar
     * @return true no caso de serem iguais, false em caso contrario
     */
    public boolean equals (Object o);
    
    /** 
     * Funcao que calcula o hash code de um produto.
     * 
     * @return inteiro correspondente ao hashCode
     */
    public int hashCode ();
    
    /** 
     * Funcao que compara produtos pelo seu codigo.
     * 
     * @param p produto a comparar
     * @return >1 caso o p seja menor que o produto em questao, 0 caso sejam iguais e <1 caso seja maior
     */
    public int compareTo (IProduto p);
    
    /** 
     * Funcao que transforma o produto numa String
     * para que seja possivel visualiza-lo.
     * 
     * @return String corresponde ao produto na forma visual
     */
    public String toString ();
    
    /** 
     * Funcao que nos permite dar clone a um produto,
     * criando um produto exatamente igual.
     * 
     * @return Clone do produto
     */
    public IProduto clone ();
}