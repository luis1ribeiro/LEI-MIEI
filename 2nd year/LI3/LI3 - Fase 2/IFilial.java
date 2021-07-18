import java.util.List;
import java.util.Map;

/**
 * Interface que representa uma filial e as operaçoes que
 * se podem realizar sobre ela
 * 
 * @author Grupo31
 * @version 2019
 */
public interface IFilial
{
    /**
     * @param fil filial representada por uma lista
     */
    public void setFilial (List <DadosMesFilial> fil);
    
    /**
     * @return representacao da filial numa lista
     */
    public List <DadosMesFilial> getFilial ();
    
    /**
     * Funcao que insere um venda na filial
     */
    public void insereVenda (IVenda v);
    
    /**
     * @return clone da filial
     */
    public IFilial clone ();
    
    /**
     * Funcao que procura os clientes diferentes que compraram num determinado mes
     * 
     * @param mes mes em questao
     * @return Lista com os clientes 
     */
    public List <ICliente> clientesDiferentesMes (int mes);
    
    /**
     * Funcao que calcula o numero total de vendas num mes
     * 
     * @param mes mes em questao
     * @return numero de vendas
     */
    public int totalVendasMes (int mes);
    
    /**
     * Funcao que calcula o numero de vendas que um cliente fez num mes
     * 
     * @param mes mes em questao
     * @param c cliente
     * @return numero de vendas
     */
    public int comprasMesCliente (ICliente c, int mes);
    
    /**
     * Funcao que procura os produtos que determinado cliente comprou num mes
     * 
     * @param mes mes em questao
     * @param c cliente
     * @return Lista com os produtos 
     */
    public List<IProduto> produtosClienteComprouMes (ICliente c, int mes);
    
    /**
     * Funcao que calcula o total gasto por um cliente num mes
     * 
     * @param mes mes em questao
     * @param c cliente
     * @return total gasto
     */
    public double gastoTotalClienteMes (ICliente c, int mes);
    
    /**
     * Funcao que procura os clientes que compraram um determinado produto num mes
     * 
     * @param mes mes em questao
     * @param p produto
     * @return Lista com os clientes 
     */
    
    public List<ICliente> clientesCompraramProdMes (IProduto p, int mes);
    
    /**
     * Funcao que procura os produtos mais comprados pelo cliente do ano
     * 
     * @param c cliente
     * @return Lista com os produtos
     */
    
    public List<DadosFilial> prodsMaisCompradosCli (ICliente c);
    
    /**
     * Funcao que procura os clientes distintos que compraram determinado produto
     * 
     * @param p produto
     * @return Lista com os clientes 
     */
    
    public List<ICliente> clientesDist (IProduto p);
    
    /**
     * Funcao que procura os maiores compradores
     * 
     * @return Lista com os clientes 
     */
    
    public List<ParQuerieSete> maioresCompradores ();
    
    /**
     * Funcao que procura os clientes que compraram mais produtos diferentes
     * 
     * @return Lista com os clientes 
     */
    
    public List<QuerieOitoAux> maisCompraramDif();
    
    /**
     * Funcao que procura os clientes que mais compraram um determinado produto
     * 
     * @param p produto
     * @return Lista com os clientes 
     */
    
    public List<TrioQuerieNove> clientesMaisCompraram(IProduto p);
    
    /**
     * Funcao que procura a faturacao total de cada produto
     * 
     * @return Lista com os clientes 
     */
    
    public List<List<ParQuerieDez>> faturacaoTotalProduto ();
    
    /**
     * Funcao que procura todos os clientes que realizaram compras
     * 
     * @return Lista com os clientes 
     */
    
    public List<String> todosClientes ();
    
    /**
     * Funcao que calcula o total faturado por mes
     * 
     * @return Lista com o total faturado em cada mes
     */
    
    public List<String> totalFaturadoMeses();
    
    /**
     * Funcao que calcula o numero total de clientes distintos que compraram por mes
     * 
     * @return Lista com numero de clientes distintos por mes
     */
    
    public List<String> clientesDistMeses ();
}