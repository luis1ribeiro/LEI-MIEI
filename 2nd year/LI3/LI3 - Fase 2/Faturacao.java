import static java.lang.System.out;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que representa a faturacao e as operaçoes que
 * se podem realizar sobre ela
 * 
 * @author Grupo31
 * @version 2019
 */
public class Faturacao implements IFaturacao, Serializable
{
    private List<DadosMesFaturacao> faturacao;

    /**
     * Construtor vazio da classe Faturacao
     */
    public Faturacao(){
        List<DadosMesFaturacao> d = new ArrayList <> ();
        int i;
        for(i=0;i<Constantes.MESES;i++){
            d.add(new DadosMesFaturacao ());
        }
        this.faturacao=d;
    }
    
    /**
     * Construtor parametrizado da classe Faturacao
     * 
     * @param fat lista que representa a faturacao
     */
    public Faturacao(List<DadosMesFaturacao> fat){
        this.setFaturacao(fat);
    }
    
    /**
     * Construtor de copia da classe Faturacao
     * 
     * @param f faturacao
     */
    public Faturacao (IFaturacao f){
        this.faturacao=f.getFaturacao();
    }
    
    public List <DadosMesFaturacao> getFaturacao(){
        return this.faturacao.stream().map(DadosMesFaturacao::clone).collect(Collectors.toList());
    }
    
    public void setFaturacao (List<DadosMesFaturacao> f){
        this.faturacao=f.stream().map(DadosMesFaturacao::clone).collect(Collectors.toList());
    }
    
    public IFaturacao clone(){
        return new Faturacao (this);
    }
    
    public void insereVenda (IVenda v){
        this.faturacao.get(v.getMes()-1).insereVenda(v);
    }
    
    public boolean produtoComprou (IProduto p){
        int i;
        for (i=0;i<Constantes.MESES;i++){
            if (this.faturacao.get(i).produtoComprou(p)) return true;
        }
        return false;
    }
    
    public int vendasMesProduto (IProduto p, int mes){
        return this.faturacao.get(mes-1).vendasProduto(p);
    }
    
    public double faturadoTotalProdutoMes (IProduto p, int mes){
        return this.faturacao.get(mes-1).faturadoTotalProduto(p);
    }
    
    public List<ParQuerieCinco> maisCompradosAno (int x){
        int i;
        List<ParQuerieCinco> l = new ArrayList <> ();
        for (i=0;i<Constantes.MESES;i++){
            l.addAll(this.faturacao.get(i).convert2Par());
        }
        return l.stream().collect(Collectors.groupingBy(ParQuerieCinco::getProduto)).entrySet().stream()
        .map(p -> new ParQuerieCinco (p.getKey(),p.getValue().stream().mapToInt(ParQuerieCinco::getQuantidade).sum()))
        .sorted().limit(x).collect(Collectors.toList());
    }
    
    public int produtosCompradosDist (){
        List<String> l = new ArrayList<> ();
        this.faturacao.stream().forEach(d -> l.addAll(d.todosProdutos()));
        return (int) l.stream().distinct().count();
    }
    
    public double faturacaoTotal (){
        return this.faturacao.stream().mapToDouble(f -> f.getFat().values().stream().mapToDouble(l -> l.stream().mapToDouble(DadosFaturacao::totalFaturado).sum()).sum()).sum();
    }
    
    public int comprasGratis(){
        return (int) this.faturacao.stream().mapToLong(f -> f.getFat().values().stream().mapToLong(l -> l.stream().filter(d -> d.getPreco()==0).count()).sum()).sum();
    }
    
    public List<String> getTotalVendasMes (){
        return this.faturacao.stream().map(f-> Integer.valueOf(f.getFat().values().stream().mapToInt(List::size).sum()).toString()).collect(Collectors.toList());
    }
}