import static java.lang.System.out;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe auxiliar a filial que representa um mes da filial. 
 * Contem as funçoes que permitem a filial realizar operaçoes sobre os meses.
 *
 * @author Grupo31
 * @version 2019
 */

public class DadosMesFilial implements Serializable
{
    private Map<ICliente,List<DadosFilial>> fil;
    
    public DadosMesFilial (){
        this.fil= new HashMap <> ();
    }
    
    public DadosMesFilial (Map <ICliente,List<DadosFilial>> f){
        this.setFil(f);
    }
    
    public DadosMesFilial (DadosMesFilial f){
        this.fil=f.getFil();
    }
    
    public Map<ICliente,List<DadosFilial>> getFil (){
        return this.fil.entrySet().stream().collect(Collectors.toMap(c-> c.getKey().clone() , l -> l.getValue().stream().map(DadosFilial::clone).collect(Collectors.toList())));
    }
    
    public void setFil (Map<ICliente,List<DadosFilial>> f){
        this.fil = f.entrySet().stream().collect(Collectors.toMap(c-> c.getKey().clone() , l-> l.getValue().stream().map(DadosFilial::clone).collect(Collectors.toList())));
    }
    
    public DadosMesFilial clone (){
        return new DadosMesFilial (this);
    }
    
    public void insereVenda (IVenda v){
        if (this.fil.containsKey(v.getCliente())){
            DadosFilial d = new DadosFilial (v.getProduto(),v.getTipo(),v.getQuantidade(),v.getPreco());
            this.fil.get(v.getCliente()).add(d.clone());
        }
        else{
            DadosFilial d = new DadosFilial (v.getProduto(),v.getTipo(),v.getQuantidade(),v.getPreco());
            List <DadosFilial> list = new ArrayList <> ();
            list.add(d.clone());
            this.fil.put(v.getCliente(),list);
        }
    }
    
    public List<ICliente> clientesDiferentes (){
        return this.fil.keySet().stream().map(ICliente::clone).collect(Collectors.toList());
    }
    
    public int totalVendas (){
        return this.fil.values().stream().mapToInt(List::size).sum();
    }
    
    public int nCompras (ICliente c){
        if (!this.fil.containsKey(c)) return 0;
        return this.fil.get(c).size();
    }
    
    public List <IProduto> produtosClienteComprou (ICliente c){
        if (!this.fil.containsKey(c)) return new ArrayList <> ();
        return this.fil.get(c).stream().map(DadosFilial::getProduto).distinct().collect(Collectors.toList());
    }
    
    public double gastoTotalCliente (ICliente c){
        if (!this.fil.containsKey(c)) return 0;
        return this.fil.get(c).stream().mapToDouble(DadosFilial::totalGasto).sum();
    }
    
    public List<ICliente> clientesCompraramProd(IProduto p){
        return this.fil.entrySet().stream().filter(x ->{
            List <DadosFilial> l = x.getValue();
            return l.stream().filter(d -> d.getProduto().equals(p)).count()>0;
        }).map(x -> x.getKey().clone()).collect(Collectors.toList());
    }
    
    public List<DadosFilial> produtosCompradosCli (ICliente c){
        if (!this.fil.containsKey(c)) return new ArrayList <> ();
        return this.fil.get(c).stream().map(DadosFilial::clone).collect(Collectors.toList());
    }
    
    public List<ParQuerieSete> maioresCompradores (){
        return this.fil.entrySet().stream().map(e-> new ParQuerieSete (e.getKey().getCodigo(), e.getValue().stream().mapToDouble(DadosFilial::totalGasto).sum())).
        collect(Collectors.toList());
    }
    
    public List<QuerieOitoAux> maisCompraramDif(){
        return this.fil.entrySet().stream().map(e-> new QuerieOitoAux (e.getKey().getCodigo(), e.getValue().stream().map(d->d.getProduto().getCodigo()).collect(Collectors.toList()))).
        collect(Collectors.toList());
    }
    
    public List<TrioQuerieNove> clientesMaisCompraram (IProduto p){
        return this.fil.entrySet().stream().filter(x->{
            List <DadosFilial> l = x.getValue();
            return l.stream().filter(d -> d.getProduto().equals(p)).count()>0;
        }).map(x-> new TrioQuerieNove (x.getKey().getCodigo(), x.getValue().stream().mapToInt(DadosFilial::getUnidades).sum() , x.getValue().stream().mapToDouble(DadosFilial::totalGasto).sum())).
        collect(Collectors.toList());
    }
    
    public List<ParQuerieDez> faturacaoTotalProduto (){
        List<DadosFilial> l = new ArrayList <> ();
        this.fil.values().forEach(d-> l.addAll(d));
        return l.stream().collect(Collectors.groupingBy(DadosFilial::getProduto)).entrySet().stream()
        .map(e -> new ParQuerieDez (e.getKey().getCodigo(), e.getValue().stream().mapToDouble(DadosFilial::totalGasto).sum())).collect(Collectors.toList());
    }
    
    public List<String> clientes (){
        return this.fil.keySet().stream().map(ICliente::getCodigo).collect(Collectors.toList());
    }
    
    public double totalFaturado (){
        return this.fil.values().stream().mapToDouble(l-> l.stream().mapToDouble(DadosFilial::totalGasto).sum()).sum();
    }
    
    public int clientesDist (){
        return this.fil.keySet().size();
    }
}