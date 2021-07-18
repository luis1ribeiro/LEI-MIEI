import java.util.List;

/**
 * Interface que representa a faturacao e as operaçoes que
 * se podem realizar sobre ela
 * 
 * @author Grupo31
 * @version 2019
 */
public interface IFaturacao
{
    /**
     * Funcao que insere uma venda a faturacao
     * 
     * @param v venda a adicionar
     */
    public void insereVenda (IVenda v);
    
    /**
     * @return clone da venda
     */
    public IFaturacao clone ();
    
    /**
     * @return faturacao
     */
    public List<DadosMesFaturacao> getFaturacao ();
    
    /**
     * Funcao que verifica se determinado produto foi comprado
     * 
     * @param p produto a verificar
     */
    public boolean produtoComprou(IProduto p);
    
    /**
     * Funcao que procura o numero de vendas de um produto num mes
     * 
     * @param p produto a procurar
     * @param mes mes em questao
     * 
     * @return numero de vendas
     */
    
    public int vendasMesProduto (IProduto p, int mes);
    
    /**
     * Funcao que calcula o total faturado de um produto num mes
     * 
     * @param p produto a pesquisar
     * @param mes mes em questao
     * 
     * @return total faturado
     */
    public double faturadoTotalProdutoMes (IProduto p, int mes);
    
    /**
     * Funcao que encontra os X produtos mais vendidos no ano todo
     * 
     * @param x numero maximo de produtos a apresentar
     * 
     * @return lista com os produtos mais vendidos
     */
    public List<ParQuerieCinco> maisCompradosAno (int x);
    
    /**
     * Funcao que calcula o numero de produtos distintos que foram comprados
     * 
     * @return numero produtos distintos
     */
    public int produtosCompradosDist ();
    
    /**
     * Funcao que calcula a faturacao total com todos os produtos no ano
     * 
     * @return faturacao total
     */
    public double faturacaoTotal ();
    
    /**
     * Funcao que calcula quantas vendas gratis houveram, ou seja,
     * valor da venda seja igual a 0
     * 
     * @return numero de vendas gratis
     */
    public int comprasGratis();
    
    /**
     * Funcao que calcula o numero total de vendas que houveram em cada mes
     * 
     * @return lista com numero total de vendas por cada mes
     */
    public List<String> getTotalVendasMes ();
}
