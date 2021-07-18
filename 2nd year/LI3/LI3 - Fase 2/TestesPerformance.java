import static java.lang.System.out;
import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.stream.Collectors;

public class TestesPerformance {

    public static void main (String [] args){
        int esc=3;
        while(esc!=0){
            esc=3;
            while (esc!=0 && esc!=2 && esc!=1){
                out.println("|----------------------------|");
                out.println("|                            |");
                out.println("|    Testes de Performance   |");
                out.println("|                            |");
                out.println("|----------------------------|");
                out.println("\nEscolha o que deseja fazer:");
                out.println("[1] Testes gerais (procura de cliente e produto em TreeSet)");
                out.println("[2] Testes com diferentes tipos de estruturas");
                out.println("\n[0] Sair");
                esc=Input.lerInt();
            }
            switch (esc){
                case 1: testesGerais();break;
                case 2: testesEstruturas();break;
            }
        }
    }
        
        
    private static void testesEstruturas(){
        Collection<String> prods = lerParaTreeSet("Produtos.txt");
        Collection<String> clis = lerParaTreeSet("Clientes.txt");
        out.println("\nClientes e Produtos em TreeSet:\n");
        out.println("\n                          BufferReader                 ReadAllLines");
        Crono.start();
        leituraBufferComParsingValidacao("Vendas_1M.txt",prods,clis);
        out.print("\n[Vendas_1M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsingValidacao("Vendas_1M.txt",prods,clis);
        out.println("                " + Crono.stop() + " s");
        Crono.start();
        leituraBufferComParsingValidacao("Vendas_3M.txt",prods,clis);
        out.print("\n[Vendas_3M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsingValidacao("Vendas_3M.txt",prods,clis);
        out.println("                 " + Crono.stop() + " s");
        Crono.start();
        leituraBufferComParsingValidacao("Vendas_5M.txt",prods,clis);
        out.print("\n[Vendas_5M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsingValidacao("Vendas_5M.txt",prods,clis);
        out.println("                 " + Crono.stop() + " s");
        prods = lerParaHashSet("Produtos.txt");
        clis = lerParaHashSet("Clientes.txt");
        out.println("\n\nClientes e produtos em HashSet:\n");
        out.println("\n                          BufferReader                 ReadAllLines");
        Crono.start();
        leituraBufferComParsingValidacao("Vendas_1M.txt",prods,clis);
        out.print("\n[Vendas_1M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsingValidacao("Vendas_1M.txt",prods,clis);
        out.println("                 " + Crono.stop() + " s");
        Crono.start();
        leituraBufferComParsingValidacao("Vendas_3M.txt",prods,clis);
        out.print("\n[Vendas_3M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsingValidacao("Vendas_3M.txt",prods,clis);
        out.println("                 " + Crono.stop() + " s");
        Crono.start();
        leituraBufferComParsingValidacao("Vendas_5M.txt",prods,clis);
        out.print("\n[Vendas_5M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsingValidacao("Vendas_5M.txt",prods,clis);
        out.println("                 " + Crono.stop() + " s");
        prods = lerParaLinkedHashSet("Produtos.txt");
        clis = lerParaLinkedHashSet("Clientes.txt");
        out.println("\n\nProdutos e Clientes em LinkedHashSet:\n");
        out.println("\n                          BufferReader                 ReadAllLines");
        Crono.start();
        leituraBufferComParsingValidacao("Vendas_1M.txt",prods,clis);
        out.print("\n[Vendas_1M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsingValidacao("Vendas_1M.txt",prods,clis);
        out.println("                 " + Crono.stop() + " s");
        Crono.start();
        leituraBufferComParsingValidacao("Vendas_3M.txt",prods,clis);
        out.print("\n[Vendas_3M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsingValidacao("Vendas_3M.txt",prods,clis);
        out.println("                 " + Crono.stop() + " s");
        Crono.start();
        leituraBufferComParsingValidacao("Vendas_5M.txt",prods,clis);
        out.print("\n[Vendas_5M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsingValidacao("Vendas_5M.txt",prods,clis);
        out.println("                 " + Crono.stop() + " s");
    }
    
    private static void testesGerais (){
        out.println("\nTeste de performance sem parsing de linhas:\n");
        out.println("\n                          BufferReader                 ReadAllLines");
        Crono.start();
        leituraBufferSemParsing("Vendas_1M.txt");
        out.print("\n[Vendas_1M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesSemParsing("Vendas_1M.txt");
        out.println("                " + Crono.stop() + " s");
        Crono.start();
        leituraBufferSemParsing("Vendas_3M.txt");
        out.print("\n[Vendas_3M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesSemParsing("Vendas_3M.txt");
        out.println("                 " + Crono.stop() + " s");
        Crono.start();
        leituraBufferSemParsing("Vendas_5M.txt");
        out.print("\n[Vendas_5M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesSemParsing("Vendas_5M.txt");
        out.println("                 " + Crono.stop() + " s");
        out.println("\n\nTeste de performance com parsing de linhas:\n");
        out.println("\n                          BufferReader                 ReadAllLines");
        Crono.start();
        leituraBufferComParsing("Vendas_1M.txt");
        out.print("\n[Vendas_1M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsing("Vendas_1M.txt");
        out.println("                 " + Crono.stop() + " s");
        Crono.start();
        leituraBufferComParsing("Vendas_3M.txt");
        out.print("\n[Vendas_3M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsing("Vendas_3M.txt");
        out.println("                 " + Crono.stop() + " s");
        Crono.start();
        leituraBufferComParsing("Vendas_5M.txt");
        out.print("\n[Vendas_5M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsing("Vendas_5M.txt");
        out.println("                 " + Crono.stop() + " s");
        Set<String> prods = lerParaTreeSet("Produtos.txt");
        Set<String> clis = lerParaTreeSet ("Clientes.txt");
        out.println("\n\nTeste de performance com parsing e validacao de linhas:\n");
        out.println("\n                          BufferReader                 ReadAllLines");
        Crono.start();
        leituraBufferComParsingValidacao("Vendas_1M.txt",prods,clis);
        out.print("\n[Vendas_1M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsingValidacao("Vendas_1M.txt",prods,clis);
        out.println("                 " + Crono.stop() + " s");
        Crono.start();
        leituraBufferComParsingValidacao("Vendas_3M.txt",prods,clis);
        out.print("\n[Vendas_3M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsingValidacao("Vendas_3M.txt",prods,clis);
        out.println("                 " + Crono.stop() + " s");
        Crono.start();
        leituraBufferComParsingValidacao("Vendas_5M.txt",prods,clis);
        out.print("\n[Vendas_5M.txt]:         " + Crono.stop()+" s");
        Crono.start();
        leituraFilesComParsingValidacao("Vendas_5M.txt",prods,clis);
        out.println("                 " + Crono.stop() + " s");
    }
   
   private static List<String> leituraBufferSemParsing (String fichtxt){
        List<String> linhas =new ArrayList<> ();
        BufferedReader inFile = null; 
        String linha=null;
        try{
            inFile = new BufferedReader (new FileReader (fichtxt));
            while ((linha=inFile.readLine())!=null) linhas.add(linha);
        }
        catch(IOException exc) {out.println(exc);}
        return linhas;
    }
    
   private static List<IVenda> leituraBufferComParsing (String fichtxt){
       List<IVenda> linhas =new ArrayList<> ();
       BufferedReader inFile = null; 
       String linha=null;
       try{
           inFile = new BufferedReader (new FileReader (fichtxt));
           while ((linha=inFile.readLine())!=null) linhas.add(linhaToVendaSemValidacao(linha));
       }
       catch(IOException exc) {out.println(exc);}
       return linhas;
   }
   
   private static List<IVenda> leituraBufferComParsingValidacao (String fichtxt,Collection <String> prods, Collection <String> cli){
       List<IVenda> linhas =new ArrayList<> ();
       BufferedReader inFile = null; 
       String linha=null;
       try{
           inFile = new BufferedReader (new FileReader (fichtxt));
           while ((linha=inFile.readLine())!=null) linhas.add(linhaToVendaComValidacao(linha,prods,cli));
       }
       catch(IOException exc) {out.println(exc);}
       return linhas;
   }
   
   private static List<String> leituraFilesSemParsing(String fichtxt){
     List<String> linhas = new ArrayList<>();
     try{
          linhas = Files.readAllLines(Paths.get(fichtxt));
     } catch (IOException exc) {out.println(exc);}
     return linhas;
   }
    
   private static List<IVenda> leituraFilesComParsing(String fichtxt){
     List<String> linhas = new ArrayList<>();
     try{
         linhas = Files.readAllLines(Paths.get(fichtxt));
     } catch (IOException exc) {out.println(exc);}
     return linhas.stream().map(s-> linhaToVendaSemValidacao(s)).collect(Collectors.toList());
   }
   
   private static List<IVenda> leituraFilesComParsingValidacao(String fichtxt,Collection <String> prods, Collection <String> cli){
     List<String> linhas = new ArrayList<>();
     try{
         linhas = Files.readAllLines(Paths.get(fichtxt));
     } catch (IOException exc) {out.println(exc);}
     return linhas.stream().map(s-> linhaToVendaComValidacao(s,prods,cli)).collect(Collectors.toList());
   }
   
   private static IVenda linhaToVendaSemValidacao (String linha){
        String codProd, codCli;
        char tipo;
        int mes = 0; int filial=0; int quant=0; double preco=0;
        String [] campos = linha.split(" ");
        codProd = campos [0];
        codCli = campos [4];
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
        return new Venda(new Produto (codProd),preco, quant,tipo,new Cliente (codCli),mes,filial);
   }
   
   private static Set<String> lerParaTreeSet(String fichtxt){
        Set<String> linhas =new TreeSet <> ();
        BufferedReader inFile = null; 
        String linha=null;
        try{
            inFile = new BufferedReader (new FileReader (fichtxt));
            while ((linha=inFile.readLine())!=null) linhas.add(linha);
        }
        catch(IOException exc) {out.println(exc);}
        return linhas;
    }
    
    private static Set<String> lerParaHashSet (String fichtxt){
        Set<String> linhas =new HashSet <> ();
        BufferedReader inFile = null; 
        String linha=null;
        try{
            inFile = new BufferedReader (new FileReader (fichtxt));
            while ((linha=inFile.readLine())!=null) linhas.add(linha);
        }
        catch(IOException exc) {out.println(exc);}
        return linhas;
    }
    
    private static Set<String> lerParaLinkedHashSet (String fichtxt){
        Set<String> linhas =new LinkedHashSet <> ();
        BufferedReader inFile = null; 
        String linha=null;
        try{
            inFile = new BufferedReader (new FileReader (fichtxt));
            while ((linha=inFile.readLine())!=null) linhas.add(linha);
        }
        catch(IOException exc) {out.println(exc);}
        return linhas;
    }
    
    private static IVenda linhaToVendaComValidacao (String linha,Collection <String> prods, Collection <String> cli){
        String codProd, codCli;
        char tipo;
        int mes = 0; int filial=0; int quant=0; double preco=0;
        String [] campos = linha.split(" ");
        codProd = campos [0];
        codCli = campos [4];
        if (!prods.contains(codProd) || !prods.contains(codCli)) return null;
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
        if (filial <=0 || filial >3) return null;
        return new Venda(new Produto (codProd),preco, quant,tipo,new Cliente (codCli),mes,filial);
   }
}