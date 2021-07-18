import static java.lang.System.out;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe auxiliar a faturacao que representa um mes da faturacao. 
 * Contem as funçoes que permitem a faturacao realizar operaçoes sobre os meses.
 *
 * @author Grupo31
 * @version 03/06/2019
 */
public class DadosMesFaturacao implements Serializable
{
    private Map <IProduto,List<DadosFaturacao>> fat;
    
    public DadosMesFaturacao (){
        this.fat=new HashMap <> ();
    }
    
    public DadosMesFaturacao (Map <IProduto,List<DadosFaturacao>> fat){
        this.setFat(fat);
    }
    
    public DadosMesFaturacao (DadosMesFaturacao d){
        this.fat=d.getFat();
    }
    
    public Map <IProduto,List<DadosFaturacao>> getFat (){
        return this.fat.entrySet().stream().collect(Collectors.toMap(p -> p.getKey().clone(), l -> l.getValue().stream().map(DadosFaturacao::clone).collect(Collectors.toList())));
    }
    
    public void setFat (Map <IProduto,List <DadosFaturacao>> f){
        this.fat=f.entrySet().stream().collect(Collectors.toMap(p->p.getKey().clone(), l->l.getValue().stream().map(DadosFaturacao::clone).collect(Collectors.toList())));
    }
    
    public DadosMesFaturacao clone (){
        return new DadosMesFaturacao (this);
    }
    
    public void insereVenda (IVenda v){
        DadosFaturacao d = new DadosFaturacao (v.getQuantidade(),v.getPreco(),v.getFilial());
        if (this.fat.containsKey(v.getProduto())){
            this.fat.get(v.getProduto()).add(d.clone());
        }
        else{
            List<DadosFaturacao> l = new ArrayList <> ();
            l.add(d.clone());
            this.fat.put(v.getProduto(),l);
        }
    }
    
    public boolean produtoComprou (IProduto p){
        return this.fat.containsKey(p);
    }
    
    public int vendasProduto (IProduto p){
        if (!this.fat.containsKey(p)) return 0;
        return this.fat.get(p).size();
    }
    
    public double faturadoTotalProduto (IProduto p){
        if (!this.fat.containsKey(p)) return 0;
        return this.fat.get(p).stream().mapToDouble(DadosFaturacao::totalFaturado).sum();
    }
    
    public List<ParQuerieCinco> convert2Par (){
        return this.fat.entrySet().stream().map(x-> new ParQuerieCinco (x.getKey().getCodigo(),x.getValue().stream().mapToInt(DadosFaturacao::getUnidades).sum())).
        collect(Collectors.toList());
    }
    
    public List<String> todosProdutos (){
        return this.fat.keySet().stream().map(IProduto::getCodigo).collect(Collectors.toList());
    }
}