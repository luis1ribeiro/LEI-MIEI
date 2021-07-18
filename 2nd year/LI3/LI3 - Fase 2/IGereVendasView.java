import java.util.*;
/**
 * Interface correspondente ao View do modelo MVC, e responsavel
 * por todo o IO do sistema
 *
 * @author Grupo31
 * @version 2019
 */

public interface IGereVendasView
{
    /**
     * @return clone do view
     */
    public IGereVendasView clone ();
    /**
     * @return navegador do view
     */
    public Navegador getNav();
    /**
     * @param n novo navegador
     */
    public void setNav (Navegador n);
    /**
     * Funcao que apresenta o menuInicial ao user
     * 
     * @return escolha do user
     */
    public int menuInicial ();
    /**
     * Funcao que apresenta os resultados da querie 1
     * 
     * @param prods TreeSet com produtos nao comprados
     */
    public void querie1 (TreeSet <IProduto> prods);
    /**
     * Funcao que apresenta o menu da querie 2 ao user
     * 
     * @return escolha do user
     */
    public int menu2 ();
    /**
     * Funcao que apresenta os resultados da querie 2
     * 
     * @param prods Par com numero de vendas e clientes distintos para cada filial naquele mes
     */
    public void querie2 (ParQuerieDois [] lista);
    /**
     * Funcao que apresenta os resultados da querie 3
     * 
     * @param lista Trio com compras, produtos distintos e total gasto em cada mes para aquele cliente
     */
    public void querie3 (TrioQuerieTres [] lista);
    /**
     * Funcao que apresenta o menu da querie 3 ao user
     * 
     * @return escolha do user
     */
    public String menu3 ();
    /**
     * Funcao que apresenta o aviso de que o cliente nao existe
     */
    public void clienteNaoExiste();
    /**
     * Funcao que apresenta o aviso de que o produto nao existe
     */
    public void produtoNaoExiste();
    /**
     * Funcao que apresenta o menu da querie 4 ao user
     * 
     * @return escolha do user
     */
    public String menu4 ();
    /**
     * Funcao que apresenta os resultados da querie 4
     * 
     * @param lista Trio com compras, clientes distintos e total faturado em cada mes para aquele produto
     */
    public void querie4 (TrioQuerieQuatro [] lista);
    /**
     * Funcao que apresenta o menu da querie 5 ao user
     * 
     * @return escolha do user
     */
    public String menu5 ();
    /**
     * Funcao que apresenta os resultados da querie 5
     * 
     * @param lista Lista com os produtos comprados pelo cliente organizados pela quantidade comprada
     */
    public void querie5 (List<ParQuerieCinco> lista);
    /**
     * Funcao que apresenta o menu da querie 6 ao user
     * 
     * @return escolha do user
     */
    public int menu6 ();
    /**
     * Funcao que apresenta os resultados da querie 6
     * 
     * @param lista Lista com os X produtos mais vendidos no ano, numero de clientes e quantidade de vendida para cada um
     */
    public void querie6 (List<TrioQuerieSeis> lista);
    /**
     * Funcao que apresenta os resultados da querie 6
     * 
     * @param lista Lista com os tres maiores compradores para cada filial, organizados pelo total gasto por cada um
     */
    public void querie7 (List<List<ParQuerieSete>> lista);
    /**
     * Funcao que apresenta o menu da querie 8 ao user
     * 
     * @return escolha do user
     */
    public int menu8 ();
    /**
     * Funcao que apresenta os resultados da querie 8
     * 
     * @param lista Lista de clientes que compraram mais produtos diferentes
     */
    public void querie8 (List<ParQuerieOito> lista);
    /**
     * Funcao que apresenta o menu da querie 9 ao user
     * 
     * @return escolha do user
     */
    public String [] menu9 ();
    /**
     * Funcao que apresenta os resultados da querie 9
     * 
     * @param lista Lista com X maiores comprados daquele produto e respetivas unidades e faturacao totais
     */
    public void querie9 (List<TrioQuerieNove> lista);
    /**
     * Funcao que apresenta o menu da querie 10 ao user
     * 
     * @return escolha do user
     */
    public String menu10 ();
    /**
     * Funcao que apresenta os resultados da querie 10
     * 
     * @param lista Lista com total faturado por aquele produto, em cada mes e em cada filial
     */
    public void querie10 (List<List<List<ParQuerieDez>>> lista);
    /**
     * Funcao que apresenta os resultados da querie 11
     * 
     * @param lista Lista com informaçoes relativas a ultima leitura de ficheiros
     */
    public void querie11 (List<String> l);
    /**
     * Funcao que apresenta o menu da querie 12 ao user
     * 
     * @return escolha do user
     */
    public int menu12 ();
    /**
     * Funcao que apresenta os resultados da querie 12
     * 
     * @param lista Lista com total de compras por mes
     */
    public void querie121 (List<String> l);
    /**
     * Funcao que apresenta o menu da querie 12 ao user
     * 
     * @return escolha do user
     */
    public int menu122 ();
    /**
     * Funcao que apresenta os resultados da querie 12
     * 
     * @param lista Lista com a faturacao total por mes para uma filial
     */
    public void querie122 (List<String> l);
    /**
     * Funcao que apresenta os resultados da querie 12
     * 
     * @param lista Lista com clientes distintos por mes para uma filial
     */
    public void querie123 (List<String> l);
    /**
     * Funcao que apresenta o menu da leitura de vendas
     * 
     * @return escolha do user
     */
    public int lerVendasMenu();
    /**
     * Funcao que apresenta o menu da leitura de ficheiros
     * 
     * @return escolha do user
     */
    public int lerFicheirosMenu ();
}
