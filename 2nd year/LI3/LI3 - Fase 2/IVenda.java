
/**
 * Interface correspondente ao Venda que permite fazer
 * algumas operaçoes sobre as vendas
 *
 * @author Grupo31
 * @version 2019
 */
public interface IVenda
{
    /**
     * @return produto da venda
     */
    public IProduto getProduto ();
    /**
     * @return preco da venda
     */
    public double getPreco ();
    /**
     * @return quantidade da venda
     */
    public int getQuantidade ();
    /**
     * @return tipo da venda
     */
    public char getTipo ();
    /**
     * @return cliente da venda
     */
    public ICliente getCliente ();
    /**
     * @return mes da venda
     */
    public int getMes ();
    /**
     * @return filial da venda
     */
    public int getFilial ();
    /**
     * @param produto da venda
     */
    public void setProduto (IProduto p);
    /**
     * @param preco da venda
     */
    public void setPreco (double p);
    /**
     * @param quantiade da venda
     */
    public void setQuantidade (int quant);
    /**
     * @param tipo da venda
     */
    public void setTipo (char tipo);
    /**
     * @param cliente da venda
     */
    public void setCliente (ICliente c);
    /**
     * @param mes da venda
     */
    public void setMes (int mes);
    /**
     * @param filial da venda
     */
    public void setFilial (int fil);
    /**
     * @return representaçao visual da venda
     */
    public String toString();
    /**
     * @param o objeto a comparar
     * @return true se forem iguais, false caso contrario
     */
    public boolean equals(Object o);
    /**
     * @return clone da venda
     */
    public IVenda clone ();
}
