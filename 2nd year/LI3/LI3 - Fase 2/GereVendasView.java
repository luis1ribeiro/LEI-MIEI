import static java.lang.System.out;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe correspondente ao View do modelo MVC, e responsavel
 * por todo o IO do sistema
 *
 * @author Grupo31
 * @version 2019
 */
public class GereVendasView implements IGereVendasView, Serializable
{
    private Navegador nav;
    /**
     * Construtor vazio da classe GereVendasView
     */
    public GereVendasView (){
        this.nav = new Navegador (Constantes.NUMERO_LINHAS_NAVEGADOR,Constantes.NUMERO_COLUNAS_NAVEGADOR);
    }
    
    /**
     * Construtor parametrizado da classe GereVendasView
     * 
     * @param nav navegador da view
     */
    public GereVendasView (Navegador nav){
        this.nav= nav.clone();
    }
    
    /**
     * Construtor de copia da classe GereVendasView
     * 
     * @param v view para copia
     */
    public GereVendasView (IGereVendasView v){
        this.nav=v.getNav();
    }
    
    public Navegador getNav(){
        return this.nav.clone();
    }
    
    public void setNav (Navegador n){
        this.nav=n.clone();
    }
    
    public IGereVendasView clone()
    {
        return new GereVendasView (this);
    }
    
    public int menuInicial (){
        out.println("\n[1] Produtos nunca comprados");
        out.println("[2] Vendas e clientes num mes");
        out.println("[3] Compras, produtos e gastos do cliente");
        out.println("[4] Venda, clientes e faturado do produto");
        out.println("[5] Produtos mais comprados pelo cliente");
        out.println("[6] X produtos mais vendidos em todo o ano");
        out.println("[7] Tres maiores compradores");
        out.println("[8] X clientes que compraram mais produtos diferentes");
        out.println("[9] X clientes que mais compraram um produto");
        out.println("[10] Faturacao total por produto");
        out.println("\n[11] Dados gerais sobre ficheiro de vendas lido");
        out.println("[12] Dados gerais acerca das estrutuas");
        out.println("\n[13] Guardar estado (ObjectStream)");
        out.println("[14] Carregar estado (ObjectStream)");
        out.println("\n[15] Leitura de Ficheiros");
        out.println("\n[0] Sair");
        int esc = Input.lerInt();
        this.nav.clearScreen();
        return esc;
    }
    
    public void clienteNaoExiste(){
       int esc = 1;
       while(esc!=0){
           out.println("\nCliente nao existe");
           out.println("\n[0] Atras");
           esc=Input.lerInt();
           this.nav.clearScreen();
        }
    }
    
    public void produtoNaoExiste(){
       int esc = 1;
       while(esc!=0){
           out.println("\nProduto nao existe");
           out.println("\n[0] Atras");
           esc=Input.lerInt();
           this.nav.clearScreen();
       }       
    }
    
    
    public void querie1 (TreeSet <IProduto> prods){
        List <String> l = new ArrayList <> ();
        IProduto p;
        while((p=prods.pollFirst())!=null) l.add(p.getCodigo());
        this.nav.print(l);
        this.nav.clearScreen();
    }
    
    
    public int menu2 (){
        int esc=1;
        while(esc!=0){
            out.println("Indique o mes que pretende pesquisar");
            out.println("\n[0] Atras");
            esc=Input.lerInt();
            this.nav.clearScreen();
            if (esc>0 && esc<=Constantes.MESES) return esc;
            else{
                if (esc!=0) System.out.println("Escreva um mes entre 1 e 12");
            }
        }
        return 0;
    }
    
    
    public void querie2 (ParQuerieDois [] lista){
        int i;
        int esc=1;
        while(esc!=0){
            for (i=0;i<lista.length-1;i++){
                out.println("\nFilial " + (i+1) + ":");
                out.println("Numero total de vendas: " + lista[i].getVendas());
                out.println("Numero total de clientes: " + lista[i].getClientes());
            }
            out.println("\nGlobal:");
            out.println("Numero total de vendas: " + lista[i].getVendas());
            out.println("Numero total de clientes: " + lista[i].getClientes());
            out.println("\n[0] Atras");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
    }
    
    public String menu3 (){
        String cli;
        out.println("Indique o cliente que pretende pesquisar");
        cli=Input.lerString();
        this.nav.clearScreen();
        return cli;
    }
    
    public void querie3 (TrioQuerieTres [] lista){
        int i;
        int esc=1;
        while(esc!=0){
            for (i=0;i<lista.length;i++){
                out.println("\nMes: " + (i+1));
                out.println("[Total de compras]: " + lista[i].getCompras() +"   [Produtos distintos]: " + lista[i].getProdutos() + "    [Total gasto]: " + lista[i].getGasto() + " €");
            }
            out.println("\n[0] Atras");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
    }
    
    public String menu4 (){
        String prod;
        out.println("Indique o produto que pretende pesquisar");
        prod=Input.lerString();
        this.nav.clearScreen();
        return prod;
    }
    
    public void querie4 (TrioQuerieQuatro [] lista){
        int i;
        int esc=1;
        while(esc!=0){
            for (i=0;i<lista.length;i++){
                out.println("\nMes: " + (i+1));
                out.println("[Total de vendas]: " + lista[i].getVendas() + "    [Clientes distintos]: " + lista[i].getClientes() + "   [Total faturado]: " + lista[i].getFaturado() + " €");
            }
            out.println("\n[0] Atras");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
    }
    
    
    public String menu5 (){
        String cli;
        out.println("Indique o cliente que pretende pesquisar");
        cli=Input.lerString();
        this.nav.clearScreen();
        return cli;
    }
    
    public void querie5 (List<ParQuerieCinco> lista){
        int i;
        out.println("[Produto]:Numero de unidades vendidas");
        this.nav.print(lista.stream().map(ParQuerieCinco::toString).collect(Collectors.toList()));
    }
    
    public int menu6 (){
        int x;
        out.println("Indique o numero de produtos que pretende apresentar");
        x=Input.lerInt();
        this.nav.clearScreen();
        return x;
    }
    
    public void querie6 (List<TrioQuerieSeis> lista){
        int i;
        int esc=1;
        while(esc!=0){
            out.println("[Produto]: Unidades Vendidas || Clientes Distintos");
            for (i=0;i<lista.size();i++){
                out.println("\n"+(i+1)+"º");
                out.println("[" + lista.get(i).getProduto() + "]: " + lista.get(i).getQuantidade() + " || " + lista.get(i).getNClientes());
            }
            out.println("\n[0] Atras");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
    }
    
    public void querie7 (List<List<ParQuerieSete>> lista){
        int i,j;
        int esc=1;
        while(esc!=0){
            out.println("[Cliente]: Total Gasto");
            for (i=0;i<lista.size();i++){
                out.println("\nFilial " + (i+1) + ":\n");
                for(j=0;j<lista.get(i).size();j++){
                    out.println((j+1)+"º");
                    out.println("[" + lista.get(i).get(j).getCliente() + "]: " + lista.get(i).get(j).getGasto()+" €");
                }
            }
            out.println("\n[0] Atras");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
    }
    
    public int menu8 (){
        int x;
        out.println("Indique o numero de clientes que pretende apresentar");
        x=Input.lerInt();
        this.nav.clearScreen();
        return x;
    }
    
    public void querie8 (List<ParQuerieOito> lista){
        int i;
        int esc=1;
        while(esc!=0){
            out.println("[Clientes]: Produtos Comprados");
            for (i=0;i<lista.size();i++){
                out.println("\n"+(i+1)+"º");
                out.println("["+ lista.get(i).getCliente() +"]: "+ lista.get(i).getProdutosDist());
            }
            out.println("\n[0] Atras");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
    }
    
    public String [] menu9 (){
        String args [] = new String [2];
        out.println("Indique o produto que pretende pesquisar");
        args[0]=Input.lerString();
        out.println("Indique o numero de clientes que pretende apresentar");
        args[1]=Input.lerString();
        this.nav.clearScreen();
        return args;
    }
    
    public void querie9 (List<TrioQuerieNove> lista){
        int i;
        int esc=1;
        while(esc!=0){
            out.println("[Cliente]: Unidades Compradas || Total Gasto");
            for (i=0;i<lista.size();i++){
                out.println("\n"+(i+1)+"º");
                out.println("[" + lista.get(i).getCliente() + "]: " + lista.get(i).getUnidades() + " || " + lista.get(i).getGasto() + " €");
            }
            out.println("\n[0] Atras");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
    }
    
    public String menu10 (){
        String prod;
        out.println("Indique o produto que pretende pesquisar");
        prod=Input.lerString();
        this.nav.clearScreen();
        return prod;
    }
    
    public void querie10 (List<List<List<ParQuerieDez>>> lista){
        int i,j,p;
        int esc=1;
        while(esc!=0){
            out.println("[Produto]: Mes || Faturacao total");
            for (i=0;i<lista.size();i++){
                out.println("\nFilial: "+(i+1));
                for (j=0;j<lista.get(i).size();j++){
                    for (p=0;p<lista.get(i).get(j).size();p++){
                        out.println("[" + lista.get(i).get(j).get(p).getProduto() + "]: " + (j+1) + " || " + lista.get(i).get(j).get(p).getFaturado() + " €");
                    }
                }
            }
            out.println("\n[0] Atras");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
    }
    
    public void querie11 (List<String> l){
        int esc = 1;
        while(esc!=0){
            out.println("\nNome do Ficheiro: " + l.get(0));
            out.println("Registos de venda invalidos: "+l.get(1));
            out.println("\nNumero total de produtos: "+l.get(2));
            out.println("Produtos diferentes comprados: "+l.get(3));
            out.println("Produtos nao comprados: "+l.get(4));
            out.println("\nNumero total de clientes: "+l.get(5));
            out.println("Clientes diferentes que realizaram compras: "+l.get(6));
            out.println("Clientes que nao compraram: "+l.get(7));
            out.println("\nCompras com valor 0 € : "+l.get(9));
            out.println("Total faturado: "+l.get(8) + " €");
            out.println("\n[0] Atras");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
    }
    
    public int menu12 (){
        int esc=0;
        while(esc!=1 && esc!=2 && esc!=3){
            out.println("\n[1] Total de compras por mes");
            out.println("[2] Faturacao total por mes");
            out.println("[3] Clientes distintos por mes");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
        return esc;
    }
    
    public void querie121 (List<String> l){
        int esc=1;
        while(esc!=0){
            int i;
            for(i=0;i<l.size();i++){
                out.println("\nMes: "+ (i+1));
                out.println(l.get(i));
            }
            out.println("\n[0] Atras");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
    }
    
    public int menu122 (){
        int esc=0;
        int i=0;
        while(esc<=0 || esc>Constantes.FILIAIS){
            for(i=0;i<Constantes.FILIAIS;i++){
                out.println("["+(i+1)+"] Filial: " +(i+1));
            }
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
        return esc;
    }
    
    public void querie122 (List<String> l){
        int esc=1;
        while(esc!=0){
            int i;
            for(i=0;i<l.size();i++){
                out.println("\nMes: "+ (i+1));
                out.println(l.get(i) + " €");
            }
            out.println("\n[0] Atras");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
    }
    
    public void querie123 (List<String> l){
        int esc=1;
        while(esc!=0){
            int i;
            for(i=0;i<l.size();i++){
                out.println("\nMes: "+ (i+1));
                out.println(l.get(i));
            }
            out.println("\n[0] Atras");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
    }
    
    public int lerVendasMenu(){
        int esc=0;
        while(esc<=0 || esc >3){
            out.println("\n[1] Vendas_1M.txt");
            out.println("[2] Vendas_3M.txt");
            out.println("[3] Vendas_5M.txt");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
        return esc;
    }
    
    public int lerFicheirosMenu (){
        int esc=0;
        while(esc<=0 || esc >3){
            out.println("\n[1] Produtos.txt");
            out.println("[2] Clientes.txt");
            out.println("[3] Ficheiro de Vendas");
            esc=Input.lerInt();
            this.nav.clearScreen();
        }
        return esc;
    }
}