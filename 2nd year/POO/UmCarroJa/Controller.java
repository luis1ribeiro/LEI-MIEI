import java.util.List;
import java.util.ArrayList;
import java.time.Month;
import java.time.LocalDate;
import java.util.Scanner;
import java.io.*;
import java.util.stream.Collectors;


public class Controller
{
    private static String handleMenuInicial (App app,int response){
        List <String> args;
        String s="";
        switch (response){
            case 0 : guardarEstado(app);
                     System.exit(0);
                     break;
            case 1 : try{
                        args= View.login();
                        app.login(Integer.parseInt(args.get(0)),args.get(1));
                        s=app.getPessoa(Integer.parseInt(args.get(0))).getClass().getSimpleName();
                        System.out.println("Bem-vindo " + app.getNomeLogado());
                     }
                     catch (NoUserException e){
                         System.out.println("Utilizador/Password nao existem/correspondem");
                     }
                     break;
            case 2 : args= View.signup();
                     if (args.get(0).equals("1")) app.addUser(new Cliente (args.get(3),Integer.parseInt(args.get(2)),args.get(1),args.get(4),
                                          args.get(5),LocalDate.now(),new Ponto (Double.parseDouble(args.get(6)),Double.parseDouble(args.get(7)))
                                          ,0,0,new ArrayList<Aluguer>()));
                     else app.addUser(new Proprietario (args.get(3),Integer.parseInt(args.get(2)),args.get(1),
                                          args.get(4),args.get(5),LocalDate.now(),0,0,
                                          new ArrayList<Aluguer>()));
                     try{
                         app.login(Integer.parseInt(args.get(2)),args.get(4));
                         s=app.getPessoa(Integer.parseInt(args.get(2))).getClass().getSimpleName();
                         System.out.println("Bem-vindo " + app.getNomeLogado());
                     }
                     catch (NoUserException e){
                         System.out.println("Utilizador/Password nao existem/correspondem");
                     }
                     break;
            default : System.out.println("Bad request");
        }
        return s;
    }

    private static void verPerfilProp(App app){
        int f = View.perfilProp(app.getProp(app.getNifLogado()).toString());
        if (f==1){
            try{
                LocalDate [] datas = View.entreDatasMenu();
                View.aluguerEntreDatas(app.getAlugueresData(datas[0],datas[1]));
            }
            catch (Exception e){
                System.out.println("Formato da data errado (dd-mm-aaaa)");
            }
        }
    }

    private static void verPerfilCliente(App app){
        int f=View.perfilCliente(app.getCliente(app.getNifLogado()).toString());
        if (f==1){
            try{
                LocalDate [] datas = View.entreDatasMenu();
                View.aluguerEntreDatas(app.getAlugueresData(datas[0],datas[1]));
            }
            catch (Exception e){
                System.out.println("Formato da data errado (dd-mm-aaaa)");
            }
        }
    }

    private static void addCarro(App app){
        List <String> args = View.addCarroMenu();
        switch (args.get(0)){
                        case "1": app.addVeiculo(new CarroGasolina (args.get(1),args.get(2),app.getNifLogado(),
                                                Double.parseDouble(args.get(3)), Double.parseDouble(args.get(4)),
                                                Double.parseDouble(args.get(5)), new Ponto (Double.parseDouble(args.get(7)),Double.parseDouble(args.get(8))),
                                                Double.parseDouble(args.get(6)),Double.parseDouble(args.get(6))));
                                  System.out.println("\nVeiculo adicionado com sucesso");
                                  break;
                        case "2": app.addVeiculo(new CarroEletrico (args.get(1),args.get(2),app.getNifLogado(),
                                                Double.parseDouble(args.get(3)), Double.parseDouble(args.get(4)),
                                                Double.parseDouble(args.get(5)), new Ponto (Double.parseDouble(args.get(7)),Double.parseDouble(args.get(8))),
                                                Double.parseDouble(args.get(6)),Double.parseDouble(args.get(6))));
                                  System.out.println("\nVeiculo adicionado com sucesso");
                                  break;
                        case "3": app.addVeiculo(new CarroHibrido (args.get(1),args.get(2),app.getNifLogado(),
                                                Double.parseDouble(args.get(3)), Double.parseDouble(args.get(4)),
                                                Double.parseDouble(args.get(5)), new Ponto (Double.parseDouble(args.get(7)),Double.parseDouble(args.get(8))),
                                                Double.parseDouble(args.get(6)),Double.parseDouble(args.get(6))));
                                  System.out.println("\nVeiculo adicionado com sucesso");
                                  break;
                        default: System.out.println("Bad request");
        }
    }

    private static void atestarCarros(App app){
        List <String> carros = app.getVeiculosAtestar();
        for(int i=0;i<carros.size();i++){
            int esc = View.atestarCarros(carros.get(i));
            if (esc==0);
            else{
                app.atestaVeiculo(carros.get(i).split("\n")[1].split(" ")[1]);
            }
        }
    }



    private static LocalDate parseData (String data){
        String [] args = data.split("-");
        return LocalDate.of(Integer.parseInt(args[2]),Integer.parseInt(args[1]),Integer.parseInt(args[0]));
    }

    private static void verCarros(App app){
        int esc=View.verCarros(app.getVeiculosProp(app.getNifLogado()));
        if (esc== 1)
            atestarCarros(app);
        if (esc==2){
            String [] args = View.faturadoCarroMenu();
            View.faturadoCarro(app.totalFaturadoViatura(args[0],parseData(args[1]),parseData(args[2])),args[0]);
        }
    }

    private static void alugarCarro(App app){
        List <String> args = View.alugarCarro();
        String comb="",pref="";
        switch (args.get(0)){
            case "1": comb="Gasolina";break;
            case "2": comb="Hibrido";break;
            case "3": comb="Electrico";break;
            default: System.out.println("Bad request");
        }
        switch (args.get(0)){
            case "1": pref="MaisBarato";break;
            case "2": pref="MaisPerto";break;
            default: System.out.println("Bad request");
        }
        PedidoAluguer pa = new PedidoAluguer (app.getNifLogado(),app.getCliente(app.getNifLogado()).getPos(),
                                              new Ponto (Double.parseDouble(args.get(2)),Double.parseDouble(args.get(3))),
                                              comb,pref);
        try{
            Veiculo v = app.veiculosParaAluguer(pa);
            String s = app.enviaPedidoAluguer(pa,v);
            System.out.println(s);
            System.out.println("Pedido de aluguer enviado");
        }
        catch (ProcuraException s){
            System.out.println(s);
        }
    }

    private static void topClientes(App app){
        String ord = View.topClientes();
        List <Cliente> l = app.topClientes(ord);
        List<String> clis = l.stream().map(c -> {
            StringBuilder sb = new StringBuilder ();
            sb.append("Nome: ").append(c.getNome()).append("\nNIF: ").append(c.getNif()).append("\nE-mail: ").append(c.getEmail()).append("\n");
            return sb.toString();
        }).collect(Collectors.toList());
        View.mostraTopClientes(clis);
    }

    
    private static void classifica (App app){
        View.clearScreen();
        int esc = View.classificaMenu();
        String [] args;
        while (esc!=0){
            switch (esc){
                case 1: args = View.classificaCarro();
                       try{
                          app.classifica(args[0],Integer.parseInt(args[1]));
                          break;
                       }
                       catch (ProcuraException e){
                           System.out.println("Veiculo nao existe");
                        }
                case 2: args = View.classificaProp();
                       try{
                        app.classifica(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
                        break;
                       }
                       catch (ProcuraException e){
                           System.out.println("Proprietario nao existe");
                        }
                default: System.out.println("Bad Request");
            }
            esc = View.classificaMenu();
        }
    }
            
    
    private static void handlePropAccount(App app){
        try{
            List<PedidoAluguer> pedidos = app.temPedidos(app.getNifLogado());
            for(int i=0;i<pedidos.size();i++){
                int esc = View.aceitaAluguer(pedidos.get(i));
                if (esc==2);
                else{
                    app.aceitaAluguer(pedidos.get(i),app.getNifLogado(),LocalDate.now());
                    int escolha = View.desejaClassificarCliente();
                    if (escolha==1){
                        int classi = View.classificaCliente();
                        try{
                            app.classifica(pedidos.get(i).getCliente(),classi);
                        }
                        catch(ProcuraException e){
                            System.out.println("Cliente nao existe");
                        }
                    }
                }
            }
        }
        catch (ProcuraException s){
            System.out.println("Nao tem pedidos");
        }
        int escolha = View.menuProp();
        while(escolha!=0){
            switch (escolha){
                case 1: verPerfilProp(app);
                        break;
                case 2: addCarro(app);
                        break;
                case 3: verCarros(app);
                        break;
                case 4: topClientes(app);
                        break;
                default: System.out.println("Bad request");
            }
        escolha = View.menuProp();
        }
        app.logout();
    }

    private static void handleCliAccount(App app){
        int escolha = View.menuCliente();
        while(escolha!=0){
            switch (escolha){
                case 1: verPerfilCliente(app);
                        break;
                case 2: alugarCarro(app);
                        break;
                case 3: topClientes(app);
                        break;
                case 4: classifica(app);
                        break;
                default: System.out.println("Bad request");
            }
        escolha = View.menuCliente();
        }
        app.logout();
    }

    public static void main (String args []){
        App app = new App ();
        int esc = View.escolheDados();
        while(esc!=1 && esc!=2){
            esc = View.escolheDados();
        }
        if (esc==1){
            String s = View.getNomeFich();
            app = lerFich(s);
        }
        else{
            app = carregarEstado();
        }
        int num=View.menuInicial();
        while(num!=0){
            String tipoConta = handleMenuInicial(app,num);
            if (tipoConta.equals("Proprietario")) handlePropAccount(app);
            if (tipoConta.equals("Cliente")) handleCliAccount(app);
            num=View.menuInicial();
        }
        guardarEstado(app);
    }


    private static App lerFich (String fich){
        App app = new App ();
        BufferedReader inFile = null;String linha=null;
        try{
            inFile = new BufferedReader (new FileReader (fich));
            while ((linha=inFile.readLine())!=null){
                String [] args = linha.split(":|,");
                app.executeLine(args);
            }
        }
        catch (IOException exc) {System.out.println(exc);}
        return app;
    }
    
    private static void guardarEstado (App app){
        try{
            File file = new File("./estado.bin");
            file.createNewFile();
            FileOutputStream f = new FileOutputStream ("./estado.bin");
            ObjectOutputStream o = new ObjectOutputStream (f);
            o.writeObject(app);
            o.flush();
            o.close();
            f.close();
        }
        catch(Exception e){
            System.out.println("Nao foi possivel guardar o estado da app" + e);
        }
    }
    
    private static App carregarEstado (){
        App app = new App ();
        try{
            File file = new File("./estado.bin");
            FileInputStream f = new FileInputStream ("./estado.bin");
            ObjectInputStream i = new ObjectInputStream (f);
            app= (App)i.readObject();
            i.close();
            f.close();
            file.delete();
        }
        catch(Exception e){
            System.out.println("Nao foi possivel carregar o estado da app" + e);
        }
        return app;
    }
}