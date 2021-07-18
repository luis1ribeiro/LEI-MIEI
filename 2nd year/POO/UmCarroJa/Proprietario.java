import java.awt.Point;
import java.lang.Object;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Proprietario extends Pessoa
{
    private int classificacao;
    private int votos;
    private List <Aluguer> historial;

    public Proprietario(){
        super();
        this.classificacao=0;
        this.votos=0;
        this.historial= new ArrayList <Aluguer> ();
    }

    public Proprietario(String email, int nif,String nome, String password, String morada,LocalDate nascimento,int classi,int votos,List<Aluguer> a)
    {
        super(email,nif,nome,password,morada,nascimento);
        this.votos=votos;
        this.classificacao=classi;
        this.setHistorial(a);
    }

    public Proprietario(Proprietario p){
        super(p);
        this.classificacao=p.getClassificacao();
        this.votos=p.getVotos();
        this.setHistorial(p.getHistorial());
    }

    public int getVotos(){
        return this.votos;
    }

    public void setVotos(int votos){
        this.votos=votos;
    }

    public int getClassificacao()
    {
        return this.classificacao;
    }

    public List <Aluguer> getHistorial ()
    {
        List<Aluguer> list = new ArrayList <>(this.historial.size());
        for(Aluguer i:this.historial)
            list.add(i.clone());
        return list;
    }

    public void setClassificacao(int classificacao)
    {
        this.classificacao=classificacao;
    }

    public void setHistorial (List <Aluguer> hist)
    {
        this.historial= new ArrayList <>(hist.size());
        for (Aluguer i:hist)
            this.historial.add(i.clone());
    }

    public Proprietario clone ()
    {
        return new Proprietario(this);
    }

    public boolean equals(Object obj)
    {
        if (obj==this) return true;
        if (obj==null || obj.getClass()!=this.getClass()) return false;
        Proprietario c = (Proprietario) obj;
        return this.getEmail().equals(c.getEmail()) && this.getNif()==c.getNif();
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder ();
        sb.append("E-mail: ").append(this.getEmail()).append("\nNif: ").append(this.getNif()).append("\nNome: ")
            .append(this.getNome()).append("\nPassword: ").append(this.getPassword()).append("\nMorada: ").append(this.getMorada())
            .append("\nNascimento: ").append(this.getNascimento()).append("\nClassificacao: ").append(this.classificacao).append("\nNumero Votos: ").append(this.votos)
            .append("\nHistorial: ").append(this.historial.toString());
        return sb.toString();
    }

    public void registaAluguer(Aluguer a){
        this.historial.add(a.clone());
    }

    public Aluguer aceitaAluguer (PedidoAluguer a,String mat, double velomed, double precokm,LocalDate data){
        double duracao = a.getDistancia()/velomed;
        double custo = a.getDistancia()*precokm;
        return new Aluguer (a.getCliente(),this.getNif(),mat,a.getPartida(),a.getDestino(),a.getDistancia(),duracao,custo,data);
    }

    public void classifica(int classi){
        this.votos++;
        this.classificacao=(this.classificacao+classi)/this.votos;
    }

    public List <Aluguer> alugueresEntreDatas(LocalDate inicio, LocalDate fim){
        return this.historial.stream().filter(x -> x.getData().isAfter(inicio) && x.getData().isBefore(fim)).map(a-> a.clone()).collect(Collectors.toList());
    }
}
