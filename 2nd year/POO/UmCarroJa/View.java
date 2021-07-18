import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class View here.
 *
 * @author  Grupo
 * @version (a version number or a date)
 */
public class View
{
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public static int menuInicial ()
    {
        clearScreen();
        System.out.println("     ==> UmCarroJa! <==    ");
        System.out.println("\n\n [1] Entrar");
        System.out.println(" [2] Registar");
        System.out.println("\n\n [0] Sair");
        Scanner input = new Scanner (System.in);
        return input.nextInt();
    }

    public static List <String> login (){
        List <String> args = new ArrayList <String> ();
        Scanner input = new Scanner (System.in);
        clearScreen();
        System.out.println("       ==> Login <==    ");
        System.out.println("Insira o seu nif:");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira a sua password:");
        args.add(input.nextLine());
        clearScreen();
        return args;
    }

    public static List <String> signup (){
        List <String> args = new ArrayList <String> ();
        Scanner input = new Scanner (System.in);
        clearScreen();
        System.out.println("       ==> Signup <==    ");
        System.out.println("\nInsira o tipo de conta:");
        System.out.println("\n  [1] Cliente");
        System.out.println("  [2] Proprietario");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira o seu nome:");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira o seu nif:");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira o seu e-mail:");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira a sua password:");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira a seu morada:");
        args.add(input.nextLine());
        clearScreen();
        if (args.get(0).equals("1")){
            System.out.println("Insira a sua posicao em X:");
            args.add(input.nextLine());
            clearScreen();
            System.out.println("Insira a sua posicao em Y:");
            args.add(input.nextLine());
            clearScreen();
        }
        return args;
    }

    public static int menuProp(){
        Scanner input = new Scanner (System.in);
        clearScreen();
        System.out.println("     ==> UmCarroJa! <==    ");
        System.out.println("\n[1] Ver Perfil");
        System.out.println("[2] Adicionar um Carro");
        System.out.println("[3] Meus Carros");
        System.out.println("[4] Top 10 Clientes");
        System.out.println("\n[0] Logout");
        return input.nextInt();
    }

    public static int menuCliente(){
        Scanner input = new Scanner (System.in);
        clearScreen();
        System.out.println("     ==> UmCarroJa! <==    ");
        System.out.println("\n[1] Ver Perfil");
        System.out.println("[2] Alugar um Carro");
        System.out.println("[3] Top 10 Clientes");
        System.out.println("[4] Classificar");
        System.out.println("\n[0] Logout");
        return input.nextInt();
    }

    public static int perfilProp (String prop){
        clearScreen();
        System.out.println(prop);
        System.out.println("\n[1] Alugueres efetuados (entre datas)");
        System.out.println("\n[0] Voltar");
        Scanner input = new Scanner (System.in);
        return input.nextInt();
    }

    public static int perfilCliente (String cli){
        clearScreen();
        System.out.println(cli);
        System.out.println("\n[1] Alugueres efetuados (entre datas)");
        System.out.println("\n[0] Voltar");
        Scanner input = new Scanner (System.in);
        return input.nextInt();
    }

    public static List<String> addCarroMenu(){
        List <String> args = new ArrayList <String> ();
        Scanner input = new Scanner (System.in);
        clearScreen();
        System.out.println("     ==> UmCarroJa! <==    ");
        System.out.println("\nInsira o combustivel do carro:");
        System.out.println("\n[1] Gasolina");
        System.out.println("[2] Eletrico");
        System.out.println("[3] Hibrido");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira a marca do carro:");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira a matricula do carro:");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira a velocidade media do carro:");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira o preco por km do carro:");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira o consumo por km do carro:");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira a autonomia do carro:");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira a posicao X do carro:");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira a posicao Y do carro:");
        args.add(input.nextLine());
        return args;
    }

    public static int verCarros(List <Veiculo> l){
        clearScreen();
        System.out.println("     ==> UmCarroJa! <==    ");
        for ( Veiculo v : l){
            System.out.println("\n" + v.toString()+ "\n");
        }
        System.out.println("\n[1] Abastecer");
        System.out.println("[2] Faturado pelo carro (entre datas)");
        System.out.println("\n[0] Voltar");
        Scanner input = new Scanner (System.in);
        return input.nextInt();
    }

    public static String topClientes(){
        Scanner input = new Scanner (System.in);
        clearScreen();
        System.out.println("     ==> UmCarroJa! <==    ");
        System.out.println("\n[1] Por km");
        System.out.println("[2] Por Numero de Alugueres");
        System.out.println("\n[0] Atras");
        int esc = input.nextInt();
        if (esc==1)
            return "MaisKms";
        else return "MaisVezes";
    }

    public static void mostraTopClientes(List<String> clis){
        Scanner input = new Scanner (System.in);
        clearScreen();
        System.out.println("     ==> UmCarroJa! <==    ");
        for (String s : clis){
            System.out.println("\n" + s.toString());
        }
        System.out.println("\n[0] Voltar");
        int f=1;
        while(f!=0){
            f=input.nextInt();
        }
    }

    public static List<String> alugarCarro(){
        List <String> args = new ArrayList <String> ();
        Scanner input = new Scanner (System.in);
        clearScreen();
        System.out.println("     ==> UmCarroJa! <==    ");
        System.out.println("\nInsira o combustivel que prefere:");
        System.out.println("\n[1] Gasolina");
        System.out.println("[2] Hibrido");
        System.out.println("[3] Eletrico");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira a sua preferencia:");
        System.out.println("\n[1] Mais barato");
        System.out.println("[2] Mais perto");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira a posicao X do destino:");
        args.add(input.nextLine());
        clearScreen();
        System.out.println("Insira a posicao Y do destino:");
        args.add(input.nextLine());
        return args;
    }

    public static int aceitaAluguer (PedidoAluguer a){
        clearScreen();
        System.out.println("     ==> UmCarroJa! <==    ");
        System.out.println("\nTem pedidos de aluguer");
        System.out.println("\n"+a.toString());
        System.out.println("Deseja aceitar?");
        Scanner input = new Scanner (System.in);
        System.out.println("[1] Sim");
        System.out.println("[2] Nao");
        return input.nextInt();
    }

    public static int atestarCarros (String c){
        clearScreen();
        System.out.println(c);
        System.out.println("\nDeseja atestar este carro?");
        Scanner input = new Scanner (System.in);
        System.out.println("[1] Sim");
        System.out.println("[2] Nao");
        return input.nextInt();
    }

    public static LocalDate parseData (String data) throws DataFormatException{
        try{
            String [] args = data.split("-");
            return LocalDate.of(Integer.parseInt(args[2]),Integer.parseInt(args[1]),Integer.parseInt(args[0]));
        }
        catch (Exception e){
            throw new DataFormatException();
        }
    }

    public static LocalDate [] entreDatasMenu () throws DataFormatException{
        LocalDate [] datas = new LocalDate [2];
        clearScreen();
        System.out.println("     ==> UmCarroJa! <==    ");
        System.out.println("Escreva as datas pretendidas");
        System.out.println("Inicio: ");
        Scanner input = new Scanner (System.in);
        try{
            String s = input.nextLine();
            datas[0]=parseData(s);
            System.out.println("Fim: ");
            s=input.nextLine();
            datas[1]=parseData(s);
        }
        catch (DataFormatException e){
            throw new DataFormatException();
        }
        return datas;
    }

    public static void aluguerEntreDatas (List<Aluguer> l){
        clearScreen();
        System.out.println("     ==> UmCarroJa! <==    ");
        System.out.println(l.toString());
        System.out.println("\n[0] Voltar");
        Scanner input = new Scanner (System.in);
        int f=1;
        while(f!=0)
            f=input.nextInt();
    }

    public static void faturadoCarro(double fat, String matricula){
        System.out.println("Total Faturado pelo carro " + matricula + ":");
        System.out.println("    "+fat + " €");
        System.out.println("\n[0] Atras");
        Scanner input = new Scanner (System.in);
        int f=1;
        while(f!=0)
            f=input.nextInt();
    }

    public static String [] faturadoCarroMenu (){
        String [] args = new String [3];
        System.out.println("Indique a matricula do carro que quer verificar");
        Scanner input = new Scanner (System.in);
        args[0]=input.nextLine();
        System.out.println("Indique a data de inicio");
        args[1]=input.nextLine();
        System.out.println("Indique a data de inicio");
        args[2]=input.nextLine();
        return args;
    }

    public static int desejaClassificarCliente (){
        System.out.println("Deseja classificar o cliente?");
        System.out.println("[1] Sim");
        System.out.println("[2] Nao");
        Scanner input = new Scanner (System.in);
        return input.nextInt();
    }
    
    public static int classificaCliente (){
        System.out.println("Que classificacao deseja dar ao cliente? (0-100)");
        Scanner input = new Scanner (System.in);
        return input.nextInt();
    }
    
    public static int classificaMenu (){
        System.out.println("O que deseja classificar");
        System.out.println("[1] Veiculo");
        System.out.println("[2] Proprietario");
        System.out.println("\n[0] Voltar");
        Scanner input = new Scanner (System.in);
        return input.nextInt();
    }
    
    public static String [] classificaCarro (){
        System.out.println("Insira a matricula do carro");
        Scanner input = new Scanner (System.in);
        String [] args = new String [2];
        args[0]=input.nextLine();
        System.out.println("Insira a classificao que deseja (0-100)");
        args[1]=input.nextLine();
        return args;
    }
    
    public static String [] classificaProp (){
        System.out.println("Insira o nif do proprietario");
        Scanner input = new Scanner (System.in);
        String [] args = new String [2];
        args[0]=input.nextLine();
        System.out.println("Insira a classificao que deseja (0-100)");
        args[1]=input.nextLine();
        return args;
    }
    
    
    public static int escolheDados (){
        System.out.println("Selecione os dados que deseja carregar?");
        System.out.println("[1] Ficheiro logs");
        System.out.println("[2] Dados desde a ultima vez que foi utilizado");
        Scanner input = new Scanner (System.in);
        return input.nextInt();
    }
    
    public static String getNomeFich (){
        System.out.println("Insira o nome do ficheiro que deseja carregar");
        Scanner input = new Scanner (System.in);
        return input.nextLine();
    }

    
    
    
    public static void clearScreen(){
      System.out.print('\u000C');
    }
}
