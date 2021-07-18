
/**
 * Classe correspondente a Venda que permite fazer
 * algumas operaçoes sobre as Venda
 *
 * @author Grupo31
 * @version 2019
 */
public class Venda implements IVenda, java.io.Serializable
{
    private IProduto produto;
    private double preco;
    private int quantidade;
    private char tipo;
    private ICliente cliente;
    private int mes;
    private int filial;

    
    /** 
     * Construtor vazio da classe Venda
     *
     */
    public Venda (){
        this.produto= new Produto ();
        this.preco=0;
        this.quantidade =0;
        this.tipo='N';
        this.cliente= new Cliente ();
        this.mes=0;
        this.filial=0;
    }
    
    /** 
     * Construtor parametrizado da classe Venda
     * 
     * @param prod produto
     * @param prec preco
     * @param quant quantidade
     * @param tipo tipo da compra
     * @param cli cliente
     * @param mes mes
     * @param filial filial
     */
    public Venda(IProduto prod, double prec, int quant, char tipo, ICliente cli, int mes, int filial)
    {
        this.produto=prod.clone();
        this.preco=prec;
        this.quantidade=quant;
        this.tipo=tipo;
        this.cliente=cli.clone();
        this.mes=mes;
        this.filial=filial;
    }

    
    /** 
     * Construtor por copia da classe Venda
     * 
     * @param c Venda a copiar
     */
    public Venda (IVenda v){
        this.produto=v.getProduto();
        this.preco=v.getPreco();
        this.quantidade=v.getQuantidade();
        this.tipo=v.getTipo();
        this.cliente=v.getCliente();
        this.mes=v.getMes();
        this.filial=v.getFilial();
    }
    
    public IProduto getProduto ()
    {
       return this.produto.clone();
    }
    
    public double getPreco ()
    {
       return this.preco;
    }
    
    public int getQuantidade ()
    {
       return this.quantidade;
    }
    
    public char getTipo ()
    {
       return this.tipo;
    }
    
    public ICliente getCliente ()
    {
       return this.cliente.clone();
    }
    
    public int getMes ()
    {
       return this.mes;
    }
    
    public int getFilial ()
    {
       return this.filial;
    }
    
    public void setProduto (IProduto p){
        this.produto=p.clone();
    }
    
    public void setPreco (double p){
        this.preco=p;
    }
    
    public void setQuantidade (int quant){
        this.quantidade=quant;
    }
    
    public void setTipo (char tipo){
        this.tipo=tipo;
    }
    
    public void setCliente (ICliente c){
        this.cliente=c.clone();
    }
    
    public void setMes (int mes){
        this.mes=mes;
    }
    
    public void setFilial (int fil){
        this.filial=filial;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder ();
        sb.append("Produto: ").append(this.produto).append("\nPreco: ").append(this.preco).append("\nQuantidade: ").append(this.quantidade).append("\nTipo: ").append(this.tipo).append("\nCliente: ").append(this.cliente).append("\nMes: ").append(this.mes).append("\nFilial: ").append(this.filial);
        return sb.toString();
    }

    public boolean equals(Object o){
        if (o==this) return true;
        if (o==null || o.getClass()!=this.getClass()) return false;
        IVenda v = (IVenda) o;
        return this.produto.equals(v.getProduto()) && this.preco == v.getPreco() && this.tipo == v.getTipo() && this.quantidade==v.getQuantidade() && this.cliente.equals(v.getCliente()) && this.mes==v.getMes() && this.filial == v.getFilial();
    }
    
    public IVenda clone (){
        return new Venda (this);
    }
}