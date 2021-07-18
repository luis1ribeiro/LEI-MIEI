import static java.lang.System.out;
import java.io.*;
import java.util.*;

/**
 * Classe correspondente ao Controlador no modelo MVC que
 * gere as operaçoes da aplicaçao
 *
 * @author Grupo31
 * @version 2019
 */

public class GereVendasController implements IGereVendasController, Serializable
{
    // instance variables - replace the example below with your own
    private IGereVendasModel model;
    private IGereVendasView view;
    
    /**
     * Construtor vazio da classe GereVendasController
     * 
     * @return novo controlador vazio
     */
    
    public GereVendasController()
    {
        this.model= new GereVendasModel ();
        this.view= new GereVendasView ();
    }
    
    public void setModel (IGereVendasModel model)
    {
        this.model=model;
    }
    
    public void setView (IGereVendasView view)
    {
        this.view=view;
    }
    
    /**
     * Funcao que controla as operaçoes da querie 1
     */
    
    public void querie1 (){
        this.view.querie1(this.model.querie1());
    }
    
    /**
     * Funcao que controla as operaçoes da querie 2
     */
    
    public void querie2 (){
        int mes = this.view.menu2();
        if (mes>0 && mes<=Constantes.MESES){
            this.view.querie2(this.model.querie2(mes));
        }
    }
    
    /**
     * Funcao que controla as operaçoes da querie 3
     */
    
    public void querie3 (){
        String c = this.view.menu3 ();
        ICliente cli = new Cliente (c);
        if (this.model.getCatCli().existeCliente(cli))
            this.view.querie3(this.model.querie3(cli));
        else
            this.view.clienteNaoExiste();
        }
    
    /**
     * Funcao que controla as operaçoes da querie 4
     */
    
    public void querie4 (){
        String p = this.view.menu4();
        IProduto prod = new Produto (p);
        if (this.model.getCatProds().existeProduto(prod))
            this.view.querie4(this.model.querie4(prod));
        else
            this.view.produtoNaoExiste();
    }
    
    /**
     * Funcao que controla as operaçoes da querie 5
     */

    public void querie5(){
        String c = this.view.menu5();
        ICliente cli = new Cliente (c);
        if (this.model.getCatCli().existeCliente(cli))
            this.view.querie5(this.model.querie5(cli));
        else
            this.view.clienteNaoExiste();
    }
    
    /**
    * Funcao que controla as operaçoes da querie 6
    */
    
    public void querie6(){
        int x = this.view.menu6();
        this.view.querie6(this.model.querie6(x));
    }
    
    /**
    * Funcao que controla as operaçoes da querie 7
    */
   
    public void querie7(){
        this.view.querie7(this.model.querie7());
    }
    
    /**
    * Funcao que controla as operaçoes da querie 8
    */
   
    public void querie8(){
        int x = this.view.menu8();
        this.view.querie8(this.model.querie8(x));
    }
    
    /**
    * Funcao que controla as operaçoes da querie 9
    */
   
    public void querie9(){
        String [] args = this.view.menu9();
        this.view.querie9(this.model.querie9(new Produto (args[0]) ,Integer.parseInt(args[1])));
    }
    
    /**
    * Funcao que controla as operaçoes da querie 10
    */
   
    public void querie10(){
        String p = this.view.menu10();
        IProduto prod = new Produto (p);
        if (!this.model.getCatProds().existeProduto(prod))
            this.view.produtoNaoExiste();
        else{
            this.view.querie10(this.model.querie10(p));
        }
    }
    
    /**
    * Funcao que controla as operaçoes da querie 11
    */
   
    public void querie11(){
        this.view.querie11(this.model.querie11());
    }
    
    /**
    * Funcao que controla as operaçoes da parte 2 da querie 12
    */
   
    public void querie122 (){
        int fil = this.view.menu122();
        this.view.querie122(this.model.querie122(fil));
    }
    
    /**
    * Funcao que controla as operaçoes da parte 3 da querie 12
    */
   
    public void querie123 (){
        int fil = this.view.menu122();
        this.view.querie123(this.model.querie123(fil));
    }
       
    /**
    * Funcao que controla as operaçoes da querie 12
    */
   
    public void querie12(){
        int esc = this.view.menu12();
        switch (esc){
             case 1 : this.view.querie121(this.model.querie121()); break;
             case 2 : this.querie122();break;
             case 3 : this.querie123();break;
        }
    }
    
    /**
    * Funcao que requisita a gravaçao do estado da app
    */
   
    public void escreverEstado(){
        File file = new File (Constantes.OBJECT_STREAM_FILE_NAME);
        file.delete();
        this.model.guardaEstado();
    }
    
    /**
    * Funcao que requisita o carregamento do estado da app
    */
   
    public void carregaEstado (){
        IGereVendasModel m = this.model.carregarEstado();
        this.model=m;
    }
    
    /**
     * Funcao que controla a operacao de leitura de vendas
     */
    
    public void lerVendas (){
        int esc = this.view.lerVendasMenu();
        switch (esc){
            case 1: this.model.lerVendas("Vendas_1M.txt");
            case 2: this.model.lerVendas("Vendas_3M.txt");
            case 3: this.model.lerVendas("Vendas_5M.txt");
        }
    }
    
    /**
     * Funcao que controla a operacao de leitura de ficheiros
     */
    
    public void lerFicheiros (){
        int fich = this.view.lerFicheirosMenu();
        switch (fich){
            case 1: this.model.lerProdutos("Produtos.txt");break;
            case 2: this.model.lerClientes("Clientes.txt");break;
            case 3: this.lerVendas();break;
        }
    }
    
    public void start ()
    {
        int esc = this.view.menuInicial();
        while(esc!=0){
            switch (esc){
            case 0 : System.exit(0);
            case 1 : this.querie1 (); break;
            case 2 : this.querie2 (); break;
            case 3 : this.querie3 (); break;
            case 4 : this.querie4 (); break;
            case 5 : this.querie5 (); break;
            case 6 : this.querie6 (); break;
            case 7 : this.querie7 (); break;
            case 8 : this.querie8 (); break;
            case 9 : this.querie9 (); break;
            case 10 : this.querie10 (); break;
            case 11 : this.querie11 (); break;
            case 12 : this.querie12 (); break;
            case 13 : this.escreverEstado();break;
            case 14 : this.carregaEstado();break; 
            case 15 : this.lerFicheiros();break;
            }
        esc = this.view.menuInicial();
        }
    }
}