import java.util.*;

/**
 * Interface que representa o Model no model MVC e
 * contem toda a "base de dados" da aplicacao e as operaçoes sobre ela
 * 
 * @author Grupo31
 * @version 2019
 */
public interface IGereVendasModel
{
    /**
     * Funcao que devolve o catalogo de clientes da app
     * 
     * @return catalogo de clientes da app
     */
    public ICatCli getCatCli ();
    
    /**
     * Funcao que devolve o catalogo de produtos da app
     * 
     * @return catalogo de produtos da app
     */
    public ICatProds getCatProds ();
    
    /**
     * Funcao que devolve a faturaçao da app
     * 
     * @return faturaçao da app
     */
    public IFaturacao getFaturacao ();
    
    /**
     * Funcao que devolve a lista das filiais da app
     * 
     * @return filiais da app
     */
    public List<IFilial> getFiliais ();
    
    /**
     * Funcao que devolve o registo da ultima leitura de ficheiros
     * 
     * @return registo da ultima leitura
     */
    public RegistoLeitura getRegistoLeitura ();
    
    /**
     * Funcao que altera o catalogo de clientes da app
     * 
     * @param catc novo catalogo de clientes
     */
    public void setCatCli (ICatCli catc);
    
    /**
     * Funcao que altera o catalogo de produtos da app
     * 
     * @param catp novo catalogo de produtos
     */
    public void setCatProds (ICatProds catp);
    
    /**
     * Funcao que altera a faturacao da app
     * 
     * @param fatp nova faturacao
     */
    public void setFaturacao (IFaturacao fatp);
    
    /**
     * Funcao que altera as filiais da app
     * 
     * @param fil novas filiais da app
     */
    public void setFiliais (List<IFilial> fil);
    
    /**
     * Funcao que altera o resgisto da ultima leitura de ficheiros
     * 
     * @param d registo da ultima leitura
     */
    public void setRegistoLeitura (RegistoLeitura d);
    
    /**
     * Funcao que da clone ao model, ou seja, cria uma
     * copia do model em questao
     * 
     * @return copia do IGereVendasModel em questao
     */
    public IGereVendasModel clone();
    
    /**
     * Funcao cria a "base de dados". E responsavel pela leituras
     * dos ficheiros e das configuraçoes da app
     * 
     */
    public void createData();
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 1
     * 
     * @return TreeSet com todos os produtos nao comprados para que sejam apresentados por ordem alfabetica
     */
    public TreeSet<IProduto> querie1 ();
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 2
     * 
     * @param mes Mes escolhido pelo user
     * @return Par com numero de vendas e clientes distintos para cada filial naquele mes
     */
    public ParQuerieDois [] querie2 (int mes);
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 3
     * 
     * @param c Cliente escolhido pelo user
     * @return Trio com compras, produtos distintos e total gasto em cada mes para aquele cliente
     */
    public TrioQuerieTres [] querie3 (ICliente c);
    
    /**
     * Funcao que le os produtos de um ficheiro para o catalogo de produtos
     * 
     * @param nomeFich nome do ficheiro a ler
     */
    public void lerProdutos (String nomeFich);
    
    /**
     * Funcao que le os clientes de um ficheiro para o catalogo de clientes
     * 
     * @param nomeFich nome do ficheiro a ler
     */
    public void lerClientes (String nomeFich);
    
    /**
     * Funcao que le as vendas de um ficheiro para as filiais e para a faturacao
     * 
     * @param nomeFich nome do ficheiro a ler
     */
    public void lerVendas (String nomeFich);
    
    /**
     * Funcao que da parsing a uma linha de venda, validando-a pelo meio
     * 
     * @param linha linha da venda
     * @return venda correspondente a linha, devolve null quando a venda e invalida
     */
    public IVenda linhaToVenda (String linha);
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 4
     * 
     * @param p Produto escolhido pelo user
     * @return Trio com compras, clientes distintos e total faturado em cada mes para aquele produto
     */
    public TrioQuerieQuatro [] querie4 (IProduto p);
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 5
     * 
     * @param c Cliente escolhido pelo user
     * @return Lista com os produtos comprados pelo cliente organizados pela quantidade comprada
     */
    public List<ParQuerieCinco> querie5 (ICliente c);
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 6
     * 
     * @param x Numero de resultados a apresentar
     * @return Lista com os X produtos mais vendidos no ano, numero de clientes e quantidade de vendida para cada um
     */
    public List<TrioQuerieSeis> querie6 (int x);
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 7
     * 
     * @return Lista com os tres maiores compradores para cada filial, organizados pelo total gasto por cada um
     */
    public List<List<ParQuerieSete>> querie7 ();
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 8
     * 
     * @param n Numero de resultados a apresentar
     * @return Lista de clientes que compraram mais produtos diferentes
     */
    public List<ParQuerieOito> querie8(int n);
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 9
     * 
     * @param x Numero de resultados a apresentar
     * @param p Produto escolhido pelo user
     * @return Lista com X maiores comprados daquele produto e respetivas unidades e faturacao totais
     */
    public List<TrioQuerieNove> querie9(IProduto p, int x);
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 10
     * 
     * @param p Produto escolhido pelo user
     * @return Lista com total faturado por aquele produto, em cada mes e em cada filial
     */
    public List<List<List<ParQuerieDez>>> querie10(String p);
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 11
     * 
     * @return Lista com informaçoes relativas a ultima leitura de ficheiros
     */
    public List<String> querie11 ();
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 12
     * 
     * @return Lista com total de compras por mes
     */
    public List<String> querie121 ();
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 12
     * 
     * @param filial Filial escolhida pelo user
     * @return Lista com a faturacao total por mes para uma filial
     */
    public List<String> querie122 (int filial);
    
    /**
     * Funcao que gere as operaçoes necessarias para a
     * realizaçao e posterior apresentaçao da querie 12
     * 
     * @param filial Filial escolhida pelo user
     * @return Lista com clientes distintos por mes para uma filial
     */
    public List<String> querie123(int filial);
    
    /**
     * Funcao que guarda o estado da app num ficheiro com nome retirado da classe Constantes
     */
    public void guardaEstado ();
    
    /**
     * Funcao que le o estado da app num ficheiro com nome retirado da classe Constantes
     * 
     * @return model com o estado carregado
     */
    public IGereVendasModel carregarEstado ();
}
