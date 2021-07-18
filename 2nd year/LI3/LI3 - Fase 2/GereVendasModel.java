import static java.lang.System.out;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que representa o Model no model MVC e
 * contem toda a "base de dados" da aplicacao e as operaçoes sobre ela
 * 
 * @author Grupo31
 * @version 2019
 */
public class GereVendasModel implements IGereVendasModel, Serializable
{
    // instance variables - replace the example below with your own
    private ICatCli catcli;
    private ICatProds catprods;
    private IFaturacao fatp;
    private List<IFilial> filiais;
    private RegistoLeitura data;

    /** 
     * Construtor vazio da classe GereVendasModel
     *
     */
    public GereVendasModel()
    {
        // initialise instance variables
        this.catcli = new CatCli ();
        this.catprods = new CatProds ();
        this.fatp = new Faturacao ();
        List<IFilial> lf = new ArrayList <> ();
        int i;
        for (i=0;i<Constantes.FILIAIS;i++){
            lf.add(new Filial ());
        }
        this.filiais=lf;
        this.data= new RegistoLeitura ();
    }
    
    /** 
     * Construtor parametrizado da classe GereVendasModel
     * 
     * @param catcli catalogo de clientes
     * @param catp catalogo produtos
     * @param fat faturacao
     * @param fil lista com filiais
     * @param d registo da ultima leitura
     */
    
    public GereVendasModel (ICatCli catcli,ICatProds catp,IFaturacao fat,List<IFilial> fil, RegistoLeitura d){
        this.catcli = catcli.clone();
        this.catprods = catp.clone();
        this.fatp = fat.clone();
        this.filiais = fil.stream().map(IFilial::clone).collect(Collectors.toList());
        this.data = d.clone();
    }
    
    
     /** 
     * Construtor por copia da classe GereVendasModel
     * 
     * @param model model para copiar
     * 
     */
    public GereVendasModel (IGereVendasModel model){
        this.catcli=model.getCatCli();
        this.catprods=model.getCatProds();
        this.fatp=model.getFaturacao();
        this.filiais=model.getFiliais();
        this.data= model.getRegistoLeitura();
    }
    
    public ICatCli getCatCli (){
        return this.catcli.clone();
    }
     
    public ICatProds getCatProds (){
        return this.catprods.clone();
    }
    
    public IFaturacao getFaturacao (){
        return this.fatp.clone();
    }
    
    public List<IFilial> getFiliais (){
        return this.filiais.stream().map(IFilial::clone).collect(Collectors.toList());
    }
    
    public RegistoLeitura getRegistoLeitura (){
        return this.data.clone();
    }
    
    public void setCatCli (ICatCli catc){
        this.catcli = catc.clone();
    }
    
    public void setCatProds (ICatProds catp){
        this.catprods = catp.clone();
    }
    
    public void setFaturacao (IFaturacao fatp){
        this.fatp= fatp.clone();
    }
    
    public void setFiliais (List<IFilial> fil){
        this.filiais = fil.stream().map(IFilial::clone).collect(Collectors.toList());
    }
    
    public void setRegistoLeitura (RegistoLeitura d){
        this.data=d.clone();
    }
    
    public IGereVendasModel clone(){
        return new GereVendasModel (this);
    }

    public void createData()
    {
        Crono.start();
        int i=0;
        String [] args = this.lerConfigs();
        for(i=0;i<Constantes.CONFIG_FILE_LINES;i++){
            if(args[i]==null) break;
        }
        if (i!=Constantes.CONFIG_FILE_LINES){
            args[0]="Produtos.txt";
            args[1]="Clientes.txt";
            args[2]="Vendas_1M.txt";
            args[3]="3";
        }
        Constantes.FILIAIS=Integer.parseInt(args[3]);
        this.lerProdutos(args[0]);
        this.lerClientes(args[1]);
        this.lerVendas(args[2]);
        out.println("\n[Tempo total de leitura de ficheiros]: " + Crono.stop() + " s");
    }
    
    public TreeSet<IProduto> querie1 (){
       Crono.start();
       TreeSet<IProduto> s = this.catprods.getProdutos().stream().filter(p -> !this.fatp.produtoComprou(p))
       .collect(Collectors.toCollection(TreeSet::new));
       out.println("\n[Query 1]: " + Crono.stop() + " s");
       return s;
    }
    
    public ParQuerieDois [] querie2 (int mes){
        Crono.start();
        ParQuerieDois [] lista = new ParQuerieDois [Constantes.FILIAIS+1];
        int vendTotal=0;
        List <ICliente> cliTotal = new ArrayList <ICliente> ();
        int i;
        for (i=0;i<Constantes.FILIAIS;i++){
            List<ICliente> cli = this.filiais.get(i).clientesDiferentesMes(mes);
            cliTotal.addAll(cli);
            int vend = this.filiais.get(i).totalVendasMes(mes);
            vendTotal+=vend;
            lista[i]= new ParQuerieDois (vend,cli.size());
        }
        int totalClientes = (int) cliTotal.stream().distinct().count();
        lista[i]= new ParQuerieDois(vendTotal,totalClientes);
        out.println("\n[Query 2]: " + Crono.stop() + " s");
        return lista;
    }
    
    public TrioQuerieTres [] querie3 (ICliente c){
        Crono.start();
        TrioQuerieTres [] lista =  new TrioQuerieTres [Constantes.MESES];
        int i,j;
        List <IProduto> produtos;
        for (i=0;i<Constantes.MESES;i++){
            int compras=0;double gasto=0.0;
            produtos = new ArrayList <> ();
            for(j=0;j<Constantes.FILIAIS;j++){
                compras+=this.filiais.get(j).comprasMesCliente(c,i+1);
                produtos.addAll(this.filiais.get(j).produtosClienteComprouMes(c,i+1));
                gasto+=this.filiais.get(j).gastoTotalClienteMes(c,i+1);
            }
            int prods= (int)produtos.stream().distinct().count();
            lista[i]= new TrioQuerieTres (compras,prods,gasto);
        }
        out.println("\n[Query 3]: " + Crono.stop() + " s");
        return lista;
    }
      
    public TrioQuerieQuatro [] querie4 (IProduto p){
        Crono.start();
        TrioQuerieQuatro [] lista = new TrioQuerieQuatro [Constantes.MESES];
        int i,j;
        List <ICliente> clientes;
        for (i=0;i<Constantes.MESES;i++){
            int vendas=0; double faturado=0.0;
            clientes= new ArrayList <> ();
            vendas=this.fatp.vendasMesProduto(p,i+1);
            faturado=this.fatp.faturadoTotalProdutoMes(p,i+1);
            for(j=0;j<Constantes.FILIAIS;j++){
                clientes.addAll(this.filiais.get(j).clientesCompraramProdMes(p,i+1));
            }
            int clis = (int) clientes.stream().distinct().count();
            lista[i]= new TrioQuerieQuatro (vendas,clis,faturado);
        }
        out.println("\n[Query 4]: " + Crono.stop() + " s");
        return lista;
    }  
    
    public List<ParQuerieCinco> querie5 (ICliente c){
        Crono.start();
        int i,j;
        List <DadosFilial> produtos = new ArrayList <> ();
        for(j=0;j<Constantes.FILIAIS;j++){
            produtos.addAll(this.filiais.get(j).prodsMaisCompradosCli(c));
        }
        List<ParQuerieCinco> l = produtos.stream().collect(Collectors.groupingBy(DadosFilial::getProduto)).entrySet().stream()
        .map(x-> new ParQuerieCinco (x.getKey().getCodigo(),x.getValue().stream().mapToInt(DadosFilial::getUnidades).sum())).sorted().
        collect(Collectors.toList());
        out.println("\n[Query 5]: " + Crono.stop() + " s");
        return l;
    }
    
    public List<TrioQuerieSeis> querie6 (int x){
        Crono.start();
        List<ParQuerieCinco> l = this.fatp.maisCompradosAno(x);
        List<TrioQuerieSeis> a = l.stream().map(p->{ 
                    int i;
                    List<ICliente> helper = new ArrayList <> ();
                    for (i=0;i<Constantes.FILIAIS;i++){
                        helper.addAll (this.filiais.get(i).clientesDist(new Produto (p.getProduto())));
                    }
                    int clientesDist = (int) helper.stream().distinct().count();
                    return new TrioQuerieSeis(p.getProduto(),p.getQuantidade(),clientesDist);
                }).collect(Collectors.toList());
        out.println("\n[Query 6]: " + Crono.stop() + " s");
        return a;
    }
    
    public List<List<ParQuerieSete>> querie7 (){
        Crono.start();
        List<List<ParQuerieSete>> l = new ArrayList <> ();
        int i;
        for (i=0;i<Constantes.FILIAIS;i++){
            l.add(this.filiais.get(i).maioresCompradores());
        }
        out.println("\n[Query 7]: " + Crono.stop() + " s");
        return l;
    }
    
    
    public List<ParQuerieOito> querie8(int n){
        Crono.start();
        List<QuerieOitoAux> l = new ArrayList <> ();
        int i;
        for(i=0;i<Constantes.FILIAIS;i++){
            l.addAll(this.filiais.get(i).maisCompraramDif());
        }
        List<ParQuerieOito> a = l.stream().collect(Collectors.groupingBy(QuerieOitoAux::getCliente)).entrySet().stream().
        map(x -> new ParQuerieOito (x.getKey(), x.getValue().stream().mapToInt(aux -> (int) aux.getProdutos().stream().distinct().count()).sum()))
        .sorted().limit(n).collect(Collectors.toList());
        out.println("\n[Query 8]: " + Crono.stop() + " s");
        return a;
    }
    
    
    public List<TrioQuerieNove> querie9(IProduto p, int x){
        Crono.start();
        List <TrioQuerieNove> l = new ArrayList <> ();
        int i;
        for(i=0;i<Constantes.FILIAIS;i++){
            l.addAll(this.filiais.get(i).clientesMaisCompraram(p.clone()));
        }
        List<TrioQuerieNove> a = l.stream().collect(Collectors.groupingBy(TrioQuerieNove::getCliente)).entrySet().stream().
        map(e-> new TrioQuerieNove (e.getKey(), e.getValue().stream().mapToInt(TrioQuerieNove::getUnidades).sum(), e.getValue().stream().mapToDouble(TrioQuerieNove::getGasto).sum())).
        sorted().limit(x).collect(Collectors.toList());
        out.println("\n[Query 9]: " + Crono.stop() + " s");
        return a;
    }
        
    public List<List<List<ParQuerieDez>>> querie10(String p){
        Crono.start();
        List<List<List<ParQuerieDez>>> l = new ArrayList <> ();
        int i;
        for(i=0;i<Constantes.FILIAIS;i++){
            l.add(this.filiais.get(i).faturacaoTotalProduto());
        }
        out.println("\n[Query 10]: " + Crono.stop() + " s");
        return l.stream().map(sl -> sl.stream().map(tl -> tl.stream().filter(d -> d.getProduto().equals(p)).collect(Collectors.toList())).collect(Collectors.toList())).collect(Collectors.toList());
    }
    
    private int clientesCompraramDist (){
        List<String> l = new ArrayList <> ();
        this.filiais.forEach(f -> l.addAll(f.todosClientes()));
        return (int) l.stream().distinct().count();
    }
    
    public List<String> querie11 (){
        Crono.start();
        List<String> l = new ArrayList <>();
        l.add(this.data.getNomeFich());
        l.add(Integer.valueOf(this.data.getVendasErradas()).toString());
        l.add(Integer.valueOf(this.catprods.numeroProdutos()).toString());
        l.add(Integer.valueOf(this.fatp.produtosCompradosDist()).toString());
        int produtosNComprados = Integer.parseInt(l.get(2))-Integer.parseInt(l.get(3));
        l.add(Integer.valueOf(produtosNComprados).toString());
        l.add(Integer.valueOf(this.catcli.numeroClientes()).toString());
        l.add(Integer.valueOf(this.clientesCompraramDist()).toString());
        int clientesNComprados = Integer.parseInt(l.get(5))-Integer.parseInt(l.get(6));
        l.add(Integer.valueOf(clientesNComprados).toString());
        l.add(Double.valueOf(this.fatp.faturacaoTotal()).toString());
        l.add(Integer.valueOf(this.fatp.comprasGratis()).toString());
        out.println("\n[Query 11]: " + Crono.stop() + " s");
        return l;
    }
    
    public List<String> querie121 (){
        Crono.start();
        List<String> l =this.fatp.getTotalVendasMes();
        out.println("\n[Query 12]: " + Crono.stop() + " s");
        return l;
    }
    
    public List<String> querie122 (int filial){
        Crono.start();
        List<String> l = this.filiais.get(filial-1).totalFaturadoMeses();
        out.println("\n[Query 12]: " + Crono.stop() + " s");;
        return l;
    }
    
    public List<String> querie123(int filial){
        Crono.start();
        List<String> l = this.filiais.get(filial-1).clientesDistMeses();
        out.println("\n[Query 12]: " + Crono.stop() + " s");
        return l;
    }
 
    
    
    
    
    public void guardaEstado (){
        try{
            Crono.start();
            FileOutputStream f = new FileOutputStream (Constantes.OBJECT_STREAM_FILE_NAME);
            ObjectOutputStream o = new ObjectOutputStream (f);
            o.writeObject(this);
            o.flush();
            o.close();
            f.close();
            out.println("\nDemorou " + Crono.stop() + " segundos a guardar o estado");
        }
        catch(Exception e){
            out.println("\nNao foi possivel guardar o estado da app" + e);
        }
    }
    
    
    public IGereVendasModel carregarEstado (){
        IGereVendasModel model = new GereVendasModel ();
        try{
            Crono.start();
            FileInputStream f = new FileInputStream (Constantes.OBJECT_STREAM_FILE_NAME);
            ObjectInputStream i = new ObjectInputStream (f);
            model = (IGereVendasModel)i.readObject();
            i.close();
            f.close();
            out.println("\nDemorou " + Crono.stop() + " segundos a carregar o estado");
            RegistoLeitura novoR = model.getRegistoLeitura();
            novoR.setNomeFich(Constantes.OBJECT_STREAM_FILE_NAME);
            model.setRegistoLeitura(novoR);
        }
        catch(Exception e){
            out.println("\nNao foi possivel carrgegar o estado da app" + e);
        }
        return model;
    }
    
    
    
    public String [] lerConfigs (){
        BufferedReader inFile=null;String linha=null;
        String [] args = new String [Constantes.CONFIG_FILE_LINES];int i=0;
        try{
            inFile = new BufferedReader (new FileReader (Constantes.CONFIGS_FILE_NAME));
            while((linha=inFile.readLine())!=null && i<Constantes.CONFIG_FILE_LINES){
                args[i++]=linha;
            }
        }
        catch(IOException exc) {out.println(exc);}
        return args;
    }
                
        
   
    
    public void lerProdutos (String nomeFich){
        BufferedReader inFile = null; String linha=null;
        try{
            this.catprods= new CatProds ();
            Crono.start();
            inFile = new BufferedReader (new FileReader (nomeFich));
            while ((linha=inFile.readLine())!=null){
                this.catprods.insereProduto(new Produto(linha));
            }
            out.println("\n[Leitura do ficheiro de produtos]: " + Crono.stop() + " s");
        }
        catch(IOException exc) {out.println(exc);}
    }
    
    public void lerClientes (String nomeFich){
        BufferedReader inFile = null; String linha=null;
        try{
            this.catcli = new CatCli ();
            Crono.start();
            inFile = new BufferedReader (new FileReader (nomeFich));
            while ((linha=inFile.readLine())!=null){
                this.catcli.insereCliente(new Cliente(linha));
            }
            out.println("\n[Leitura do ficheiro de clientes]: " + Crono.stop() + " s");
        }
        catch(IOException exc) {out.println(exc);}
    }
    
    public void lerVendas (String nomeFich){
        BufferedReader inFile = null; String linha=null;
        int erradas=0;
        try{
            this.fatp= new Faturacao ();
            this.filiais = new ArrayList <> ();
            int i=0;
            for(i=0;i<Constantes.FILIAIS;i++){
                this.filiais.add(new Filial ());
            }
            Crono.start();
            inFile = new BufferedReader (new FileReader (nomeFich));
            while ((linha=inFile.readLine())!=null){
                IVenda v = linhaToVenda (linha);
                if (v!=null){
                    this.fatp.insereVenda(v);
                    this.filiais.get(v.getFilial()-1).insereVenda(v);
                }
                else{erradas++;}
            }
            this.setRegistoLeitura(new RegistoLeitura (nomeFich,erradas));
            out.println("\n[Leitura do ficheiro de vendas]: " + Crono.stop() + " s");
        }
        catch(IOException exc) {out.println(exc);}
    }
    
    public IVenda linhaToVenda (String linha){
        String codProd, codCli;
        char tipo;
        int mes = 0; int filial=0; int quant=0; double preco=0;
        String [] campos = linha.split(" ");
        codProd = campos [0];
        if (!this.catprods.existeProduto(new Produto (codProd))) return null;
        codCli = campos [4];
        if (!this.catcli.existeCliente(new Cliente (codCli))) return null;
        tipo = campos[3].charAt(0);
        try{
            preco = Double.parseDouble(campos[1]);
        }
        catch(NumberFormatException e) {return null;}
        try{
            quant = Integer.parseInt(campos[2]);
        }
        catch(NumberFormatException e) {return null;}
        try{
            mes = Integer.parseInt(campos[5]);
        }
        catch(NumberFormatException e) {return null;}
        try{
            filial = Integer.parseInt(campos[6]);
        }
        catch(NumberFormatException e) {return null;}
        if ((filial<1) || (filial>Constantes.FILIAIS)) return null;
        return new Venda(new Produto (codProd),preco, quant,tipo,new Cliente (codCli),mes,filial);
    }
}
