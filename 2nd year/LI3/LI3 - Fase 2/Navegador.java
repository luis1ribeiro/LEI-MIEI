import java.util.*;
import java.util.stream.Collectors;
import static java.lang.System.out;

/**
 * Classe que torna possivel a navegaçao com sistema de paginas no ecra.
 *
 * @author Grupo31
 * @version 2019
 */
public class Navegador
{
    // instance variables - replace the example below with your own
    private int numeroLinhas;
    private int numeroColunas;

    public Navegador (){
        this.numeroLinhas=0;
        this.numeroColunas=0;
    }
    
    public Navegador(int nl, int nc)
    {
        this.numeroLinhas=nl;
        this.numeroColunas=nc;
    }
    
    public Navegador (Navegador n){
        this.numeroLinhas=n.getNumeroLinhas();
        this.numeroColunas=n.getNumeroColunas();
    }
    
    public int getNumeroLinhas (){
        return this.numeroLinhas;
    }
    
    public int getNumeroColunas(){
        return this.numeroColunas;
    }
    
    public Navegador clone(){
        return new Navegador (this);
    }
    
    public void print (List<String> strings){
        double div = (double)strings.size()/(this.numeroLinhas*this.numeroColunas);
        int paginas= (int) Math.ceil(div);
        int l,c,i,page=1,iPorP,fst=0;
        i=0;
        String esc;
        while(i>=0 && i<strings.size()){
            iPorP = 0;
            if (fst!=0) clearScreen();
            fst=1;
            out.println("\nNumero elementos:" + strings.size());
            out.println("\nPage: " + page + " of " + paginas +"\n");
            for (l=0;l<this.numeroLinhas;l++){
                for(c=0;c<this.numeroColunas;c++){
                    if(i>=strings.size()) break;
                    iPorP++;
                    out.printf("%s ",strings.get(i++));
                }
                out.println();
            }
            out.println("\n[P] Proxima Pagina");
            out.println("[A] Pagina Anterior");
            out.println("[B] Atras");
            esc=Input.lerString();
            switch (esc){
                case "p" : if (page<paginas) page++;
                           else i=i-iPorP;
                           break;
                case "a" : if (page>1) {page--;i=i-(iPorP+(this.numeroLinhas*this.numeroColunas));}
                           else i=i-iPorP;
                           break;
                case "b" : return;
                default : i=i-iPorP; 
            }
        }
    }
    
    public void printOrdenadamente (TreeSet<String> strings){
        double div = (double)strings.size()/(this.numeroLinhas*this.numeroColunas);
        int paginas= (int) Math.ceil(div);
        int l,c,i,page=1,iPorP,fst=0;
        i=0;
        String esc;
        while(i>=0 && i<strings.size()){
            iPorP = 0;
            if (fst!=0) clearScreen();
            fst=1;
            out.println("\nNumero elementos:" + strings.size());
            out.println("\nPage: " + page + " of " + paginas +"\n");
            for (l=0;l<this.numeroLinhas;l++){
                for(c=0;c<this.numeroColunas;c++){
                    if(i>=strings.size()) break;
                    iPorP++;
                    out.printf("%s ",strings.pollFirst());
                    i++;
                }
                out.println();
            }
            out.println("\n[P] Proxima Pagina");
            out.println("[A] Pagina Anterior");
            out.println("[B] Atras");
            esc=Input.lerString();
            switch (esc){
                case "p" : if (page<paginas) page++;
                           else i=i-iPorP;
                           break;
                case "a" : if (page>1) {page--;i=i-(iPorP+(this.numeroLinhas*this.numeroColunas));}
                           else i=i-iPorP;
                           break;
                case "b" : return;
                default : i=i-iPorP; 
            }
        }
    }
    
    public void clearScreen() {  
        out.print("\033[H\033[2J");  
        out.flush();  
    }  
}