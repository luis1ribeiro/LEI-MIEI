import static java.lang.System.out;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Classe que representa uma filial e as operaçoes que
 * se podem realizar sobre ela
 * 
 * @author Grupo31
 * @version 2019
 */
public class Filial implements IFilial, Serializable
{
    private List <DadosMesFilial> filial;
    
    /**
     * Construtor vazio da classe Filial
     */
    public Filial (){
        List <DadosMesFilial> d = new ArrayList <> ();
        int i;
        for(i=0;i<Constantes.MESES;i++){
            d.add(new DadosMesFilial ());
        }
        this.filial=d;
    }
    
    /**
     * Construtor parametrizado da classe Filial
     * 
     * @param fil filial representada numa List
     */
    
    public Filial (List <DadosMesFilial> fil){
        this.setFilial(fil);
    }
    
    /**
     * Construtor de copia da classe Filial
     * 
     * @param f filial a copiar
     */
    
    public Filial (IFilial f){
        this.filial=f.getFilial();
    }
    
    public List <DadosMesFilial> getFilial (){
        return this.filial.stream().map(DadosMesFilial::clone).collect(Collectors.toList());
    }
    
    public void setFilial (List <DadosMesFilial> fil){
        this.filial= fil.stream().map(DadosMesFilial::clone).collect(Collectors.toList());
    }
    
    public IFilial clone (){
        return new Filial (this);
    }
    
    public void insereVenda (IVenda v){
        this.filial.get(v.getMes()-1).insereVenda(v);
    }
    
    public List <ICliente> clientesDiferentesMes (int mes){
        return this.filial.get(mes-1).clientesDiferentes();
    }
    
    public int totalVendasMes (int mes){
        return this.filial.get(mes-1).totalVendas();
    }
    
    public int comprasMesCliente (ICliente c, int mes){
        return this.filial.get(mes-1).nCompras(c);
    }
    
    public List<IProduto> produtosClienteComprouMes (ICliente c, int mes){
        return this.filial.get(mes-1).produtosClienteComprou(c);
    }
    
    public double gastoTotalClienteMes (ICliente c, int mes){
        return this.filial.get(mes-1).gastoTotalCliente(c);
    }
    
    public List<ICliente> clientesCompraramProdMes (IProduto p, int mes){
        return this.filial.get(mes-1).clientesCompraramProd(p);
    }
    
    public List<DadosFilial> prodsMaisCompradosCli (ICliente c){
        List <DadosFilial> aux = new ArrayList <> ();
        this.filial.stream().forEach(d ->{ aux.addAll(d.produtosCompradosCli(c));});
        return aux;
    }
    
    public List<ICliente> clientesDist (IProduto p){
        List<ICliente> aux= new ArrayList<>();
        this.filial.stream().forEach(d -> { aux.addAll(d.clientesCompraramProd(p));});
        return aux;
    }
    
    public List<ParQuerieSete> maioresCompradores (){
        List<ParQuerieSete> l = new ArrayList <> ();
        this.filial.stream().forEach(d->{l.addAll(d.maioresCompradores());});
        return l.stream().collect(Collectors.groupingBy(ParQuerieSete::getCliente)).entrySet().stream().
        map(e-> new ParQuerieSete (e.getKey(),e.getValue().stream().mapToDouble(ParQuerieSete::getGasto).sum())).sorted().
        limit(3).collect(Collectors.toList());
    }
    
    public List<QuerieOitoAux> maisCompraramDif(){
        List<QuerieOitoAux> l = new ArrayList<> ();
        this.filial.stream().forEach(d->{l.addAll(d.maisCompraramDif());});
        return l;
    }
    
    public List<TrioQuerieNove> clientesMaisCompraram(IProduto p){
        List<TrioQuerieNove> l = new ArrayList <> ();
        this.filial.stream().forEach(d->{l.addAll(d.clientesMaisCompraram(p));});
        return l;
    }
    
    public List<List<ParQuerieDez>> faturacaoTotalProduto (){
        List<List<ParQuerieDez>> l = new ArrayList <> ();
        this.filial.stream().forEach(d->{l.add(d.faturacaoTotalProduto());});
        return l;
    }
    
    public List<String> todosClientes (){
        List<String> l = new ArrayList <> ();
        this.filial.stream().forEach(d->l.addAll(d.clientes()));
        return l;
    }
    
    public List<String> totalFaturadoMeses(){
        List<String> l = new ArrayList <> ();
        this.filial.stream().forEach(d->l.add(Double.valueOf(d.totalFaturado()).toString()));
        return l;
    }
    
    public List<String> clientesDistMeses (){
        List<String> l = new ArrayList <> ();
        this.filial.stream().forEach(d->l.add(Integer.valueOf(d.clientesDist()).toString()));
        return l;
    }
}