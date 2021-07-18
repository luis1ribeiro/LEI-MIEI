import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.TreeMap;
import java.time.LocalDate;
import java.util.NoSuchElementException;

public class App implements java.io.Serializable
{
    // instance variables
    private Map <Integer,Pessoa> users;
    private Map <Integer,List<Veiculo>> veiculos;
    private int nif_loggado;

    /**
     * Constructor for objects of class App
     */
    public App()
    {
        // initialise instance variables
        this.users= new HashMap <Integer,Pessoa> ();
        this.veiculos= new HashMap <Integer,List<Veiculo>> ();
        this.nif_loggado=0;
    }

    public App(Map <Integer,Pessoa> p, Map <Integer,List<Veiculo>> v,int nif){
        this.setUsers(p);
        this.setVeiculos(v);
        this.nif_loggado=nif;
    }

    public App(App p){
        this.setUsers(p.getUsers());
        this.setVeiculos(p.getVeiculos());
        this.nif_loggado=p.getNifLogado();
    }

    public void setUsers (Map <Integer,Pessoa> p){
        Map <Integer,Pessoa> a = new HashMap <> ();
        p.forEach((k,v)->{a.put(k,v.clone());});
        this.users=a;
    }

    public Map <Integer,Pessoa> getUsers (){
        Map<Integer,Pessoa> h = new HashMap <> ();
        this.users.forEach((k,v)->{h.put(k,v.clone());});
        return h;
    }

    public void setVeiculos(Map <Integer,List<Veiculo>> v){
        Map <Integer,List<Veiculo>> h = new HashMap <> ();
        v.forEach((k,val)->{
            List <Veiculo> veic = new ArrayList <Veiculo> ();
            val.forEach(s->{veic.add(s.clone());});
            h.put(k,veic);});
        this.veiculos=h;
    }

    public Map <Integer,List<Veiculo>> getVeiculos(){
        Map <Integer,List<Veiculo>> h = new HashMap <> ();
        this.veiculos.forEach((k,val)->{
            List <Veiculo> veic = new ArrayList <Veiculo> ();
            val.forEach(s->{veic.add(s.clone());});
            h.put(k,veic);});
        return h;
    }

    public List<String> getVeiculosAtestar(){
        List <String> carros = new ArrayList <> ();
        this.veiculos.get(this.nif_loggado).stream().filter(c->c.getPercDep()<10).forEach(c->{
            StringBuilder sb = new StringBuilder ();
            sb.append("Marca: ").append(c.getMarca()).append("\n")
            .append("Matricula: ").append(c.getMatricula()).append("\n")
            .append("Percentagem de Deposito: ").append(c.getPercDep()).append(" %");
            carros.add(sb.toString());
         });
        return carros;
    }

    public int getNifLogado(){
        return this.nif_loggado;
    }

    public void setNifLogado(int nif){
        this.nif_loggado=nif;
    }

    public String getNomeLogado(){
        return this.users.get(this.nif_loggado).getNome();
    }

    public Cliente getCliente(int nif){
        return (Cliente) this.users.get(nif).clone();
    }

    public Pessoa getPessoa (int nif){
        return this.users.get(nif).clone();
    }

    public Proprietario getProp(int nif){
        return (Proprietario) this.users.get(nif).clone();
    }


    public List<Veiculo> getVeiculosProp (int nif){
        return this.veiculos.get(nif).stream().map(c->c.clone()).collect(Collectors.toList());
    }

    public void addUser (Pessoa p){
        if (!this.nifRegistado(p.getNif()))
            this.users.put(p.getNif(),p.clone());
    }

    public void addVeiculo (Veiculo v){
        List <Veiculo> l;
        if (!this.existeVeiculo(v)){
            l = this.veiculos.get(v.getNif());
            if (l!=null){
                l.add(v.clone());
            }
            else{
                l = new ArrayList <Veiculo> ();
                l.add(v.clone());
                this.veiculos.put(v.getNif(),l);
            }
        }
    }

    public boolean nifRegistado (int nif){
        return this.users.containsKey(nif);
    }

    public boolean existeVeiculo (Veiculo v){
        List <Veiculo> l = this.veiculos.get(v.getNif());
        if (l==null) return false;
        return l.contains(v);
    }

    public void login (int nif, String password) throws NoUserException{
        if (this.nifRegistado(nif) && this.users.get(nif).getPassword().equals(password))
            this.nif_loggado=nif;
        if (this.nif_loggado == 0)
            throw new NoUserException();
    }

    public void logout (){
        this.nif_loggado=0;
    }

    public Veiculo veiculosParaAluguer(PedidoAluguer a) throws ProcuraException{
        List <Veiculo> l = new ArrayList <Veiculo> ();
        try{
        if (a.getCombustivel().equals("Electrico"))
            this.veiculos.values().stream().forEach(vs -> vs.stream().filter(x -> x instanceof CarroEletrico && x.getPercDep()>10 && x.getAutonomia()>a.getDistancia()).forEach(x->l.add(x.clone())));
        if (a.getCombustivel().equals("Gasolina"))
            this.veiculos.values().stream().forEach(vs -> vs.stream().filter(x -> x instanceof CarroGasolina && x.getPercDep()>10 && x.getAutonomia()>a.getDistancia()).forEach(x->l.add(x.clone())));
        if (a.getCombustivel().equals("Hibrido"))
            this.veiculos.values().stream().forEach(vs -> vs.stream().filter(x -> x instanceof CarroHibrido && x.getPercDep()>10 && x.getAutonomia()>a.getDistancia()).forEach(x->l.add(x.clone())));
        if (a.getPreferencia()=="MaisBarato")
            l.sort(new VeiculoComparator(a.getPartida(),1));
        else
            l.sort(new VeiculoComparator(a.getPartida(),2));
        return l.get(0);
        }
        catch (Exception e){
            throw new ProcuraException ("Nao existe veiculo para alugar");
        }
    }

    public void classifica (String matricula, int classi) throws ProcuraException{
        try{
            this.veiculos.entrySet().stream().filter(x->{
                List <Veiculo> l = x.getValue();
                Veiculo v = l.stream().filter(a->a.getMatricula().equals(matricula)).findFirst().get();
                v.classifica(classi);
                return true;
            }).findFirst();
        }
        catch (Exception e){
            throw new ProcuraException ("Veiculo nao existe");
        }
    }

    public void classifica (int nif, int classi) throws ProcuraException{
        try{
            this.users.get(nif).classifica(classi);
        }
        catch (Exception e){
            throw new ProcuraException ("NIF nao existe");
        }
    }

    public void resetPedidos(){
        List <PedidoAluguer> l = new ArrayList <>();
        this.veiculos.get(this.nif_loggado).forEach(v -> v.setEspera(l));
    }

    public void resetPedidos(int nif, String matricula){
        List <PedidoAluguer> l = new ArrayList <> ();
        this.veiculos.get(this.nif_loggado).forEach(v->v.setEspera(l));
    }

    public void aceitaAluguer(PedidoAluguer pa,int nif,LocalDate data){
        Veiculo veic = this.veiculos.get(nif).stream().filter(v -> v.getEspera().contains(pa)).findFirst().get();
        Aluguer a = ((Proprietario)this.users.get(veic.getNif())).aceitaAluguer(pa,veic.getMatricula(),veic.getVelomed(),veic.getPrecokm(),data);
        veic.eliminaEspera(pa);
        this.registaAluguer(a);
    }

    public void registaAluguer(Aluguer a){
        this.users.get(a.getCliente()).registaAluguer(a);
        this.users.get(a.getProp()).registaAluguer(a);
        this.veiculos.get(a.getProp()).stream().filter(x->x.getMatricula().equals(a.getMatricula())).findFirst().get().registaAluguer(a);
    }

    public List<PedidoAluguer> temPedidos (int nif) throws ProcuraException{
        List<PedidoAluguer> r= new ArrayList <> ();
        try{
            this.veiculos.get(nif).stream().filter(v -> v.getEspera().size() > 0).forEach(x ->x.getEspera().forEach(n-> r.add(n.clone())));
        }
        catch (Exception e){
            throw new ProcuraException ("Nao tem pedidos");
        }
        return r;
    }

    public String enviaPedidoAluguer(PedidoAluguer a, Veiculo v){
        int index = this.veiculos.get(v.getNif()).indexOf(v);
        this.veiculos.get(v.getNif()).get(index).adicionaEspera(a);
        return (this.veiculos.get(v.getNif()).get(index).getEspera().toString());
    }


    public void atestaVeiculo(String matricula){
        Veiculo v = this.veiculos.get(this.nif_loggado).stream().filter(c-> c.getMatricula().equals(matricula)).findFirst().get();
        v.setAutonomia(v.getAutonomiaMax());
    }


    public List <Cliente> topClientes (String ord){
        switch (ord){
            case "MaisKms" :
                return this.users.values().stream().filter(x -> x instanceof Cliente).sorted(new ClienteComparator(2))
                            .limit(10).map(x-> ((Cliente)x.clone())).collect(Collectors.toList());
            case "MaisVezes":
                return this.users.values().stream().filter(x -> x instanceof Cliente).sorted(new ClienteComparator(1))
                            .limit(10).map(x-> ((Cliente)x.clone())).collect(Collectors.toList());
            default:
                System.out.println("Bad request");
        }
        return new ArrayList <> ();
    }

    public List<Aluguer> getAlugueresData (LocalDate inicio, LocalDate fim){
        return this.users.get(this.getNifLogado()).alugueresEntreDatas(inicio,fim);
    }


    public double getNumeroKm(Cliente c){
        return c.getHistorial().stream().mapToDouble(a-> a.getDistancia()).sum();
    }


    public double totalFaturadoViatura (String matricula,LocalDate inicio, LocalDate fim){
        List <Veiculo> l = new ArrayList <> ();
        this.veiculos.values().stream().forEach(list-> list.stream().forEach(v->l.add(v)));
        return l.stream().filter(x->x.getMatricula().equals(matricula)).findFirst().get()
                        .getHistorial().stream().filter(a-> inicio.isBefore(a.getData()) && fim.isAfter(a.getData()))
                        .mapToDouble(a->a.getCusto()).sum();
    }



    public static LocalDate parseData (String data){
        String [] args = data.split("-");
        return LocalDate.of(Integer.parseInt(args[2]),Integer.parseInt(args[1]),Integer.parseInt(args[0]));
    }


    public void executeLine(String [] args){
        if (args[0].equals("NovoProp")){
            this.addUser(new Proprietario (args[3],Integer.parseInt(args[2]),args[1],
                                              args[3],args[4],LocalDate.now(),0,0,
                                              new ArrayList<Aluguer>()));
        }
        if (args[0].equals("NovoCliente")){
            this.addUser(new Cliente (args[3],Integer.parseInt(args[2]),args[1],args[3],
                                             args[4],LocalDate.now(),new Ponto (Double.parseDouble(args[5]),Double.parseDouble(args[6]))
                                             ,0,0,new ArrayList<Aluguer>()));
                                            }
        if (args[0].equals("NovoCarro")){
            if (args[1].equals("Electrico")){
                this.addVeiculo( new CarroEletrico (args[2],args[3],Integer.parseInt(args[4])
                                                     ,Double.parseDouble(args[5]),Double.parseDouble(args[6]),
                                                     Double.parseDouble(args[7]),new Ponto (Double.parseDouble(args[9]),Double.parseDouble(args[10])),
                                                     Double.parseDouble(args[8]),Double.parseDouble(args[8])));
            }
            if (args[1].equals("Gasolina")){
                this.addVeiculo( new CarroGasolina (args[2],args[3],Integer.parseInt(args[4])
                                                     ,Double.parseDouble(args[5]),Double.parseDouble(args[6]),
                                                     Double.parseDouble(args[7]),new Ponto (Double.parseDouble(args[9]),Double.parseDouble(args[10])),
                                                     Double.parseDouble(args[8]),Double.parseDouble(args[8])));
            }
            if (args[1].equals("Hibrido")){
                this.addVeiculo( new CarroHibrido (args[2],args[3],Integer.parseInt(args[4])
                                                     ,Double.parseDouble(args[5]),Double.parseDouble(args[6]),
                                                     Double.parseDouble(args[7]),new Ponto (Double.parseDouble(args[9]),Double.parseDouble(args[10])),
                                                     Double.parseDouble(args[8]),Double.parseDouble(args[8])));
            }
        }
        if (args[0].equals("Aluguer")){
            PedidoAluguer pa = new PedidoAluguer (Integer.parseInt(args[1]),this.getCliente(Integer.parseInt(args[1])).getPos(),
                                                 new Ponto (Double.parseDouble(args[2]),Double.parseDouble(args[3])),
                                                 args[4],args[5]);
            try{
                Veiculo v = this.veiculosParaAluguer(pa);
                enviaPedidoAluguer(pa,v);
                if (args.length==7)
                    aceitaAluguer(pa,v.getNif(),parseData(args[6]));
                else aceitaAluguer(pa,v.getNif(),LocalDate.now());
            }
            catch (ProcuraException s){}
        }
        if (args[0].equals("Classificar")){
            try{
                int nif = Integer.parseInt(args[1]);
                try{
                    this.classifica(nif,Integer.parseInt(args[2]));
                }
                catch (ProcuraException s){}
            }
            catch (NumberFormatException e) {
                try{
                    this.classifica(args[1],Integer.parseInt(args[2]));
                }
                catch (ProcuraException s){}
            }
         }
    }
}
