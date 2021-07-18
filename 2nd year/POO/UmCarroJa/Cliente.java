import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Cliente extends Pessoa
{
    private Ponto posicao;
    private int classificacao;
    private int votos;
    private List <Aluguer> historial;
    
    public Cliente(){
        super();
        this.posicao=new Ponto ();
        this.classificacao=0;
        this.votos=0;
        this.historial= new ArrayList <Aluguer> ();
    }
    
    public Cliente(String email, int nif, String nome, String password, String morada,LocalDate nascimento,Ponto pos,int classi,int votos, List<Aluguer> a)
    {
        super(email,nif,nome,password,morada,nascimento);
        this.votos=votos;
        this.classificacao=classi;
        this.setPos(pos);
        this.setHistorial(a);
    }
    
    public Cliente(Cliente c){
        super(c);
        this.setPos(c.getPos());
        this.classificacao=c.getClassificacao();
        this.votos=c.getVotos();
        this.setHistorial(c.getHistorial());
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
    
    public void setClassificacao(int classificacao)
    {
        this.classificacao=classificacao;
    }
    
    public Ponto getPos()
    {
        return this.posicao.clone();
    }
    
    public List <Aluguer> getHistorial ()
    {
        List<Aluguer> list = new ArrayList <>(this.historial.size());
        for(Aluguer i:this.historial)
            list.add(i.clone());
        return list;
    }
    
    public void setPos(Ponto posicao)
    {
        this.posicao= posicao.clone();
    }
    
    public void setHistorial (List <Aluguer> hist)
    {
        this.historial= new ArrayList <>(hist.size());
        for (Aluguer i:hist)
            this.historial.add(i.clone());
    }
    
    public Cliente clone ()
    {
        return new Cliente (this);
    }
    
    public boolean equals(Object obj)
    {
        if (obj==this) return true;
        if (obj==null || obj.getClass()!=this.getClass()) return false;
        Cliente c = (Cliente) obj;
        return this.getEmail().equals(c.getEmail()) && this.getNif()==c.getNif();
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder ();
        sb.append("E-mail: ").append(this.getEmail()).append("\nNif:").append(this.getNif()).append("\nNome: ")
            .append(this.getNome()).append("\nPassword: ").append(this.getPassword()).append("\nMorada: ").append(this.getMorada())
            .append("\nNascimento: ").append(this.getNascimento()).append("\nPosicao: ").append(this.posicao.toString()).append("\nClassificacao: ")
            .append(this.classificacao).append("\nNumero Votos: ").append(this.votos).append("\nHistorial: ").append(this.historial.toString());
        return sb.toString();
    }
     
    public void registaAluguer(Aluguer a){
        this.setPos(a.getDest());
        this.historial.add(a.clone());
    }
    
    public void classifica(int classi){
        this.votos++;
        this.classificacao=(this.classificacao+classi)/this.votos;
    }
        
    public List <Aluguer> alugueresEntreDatas(LocalDate inicio, LocalDate fim){
        return this.historial.stream().filter(x -> x.getData().isAfter(inicio) && x.getData().isBefore(fim)).map(a-> a.clone()).collect(Collectors.toList());
    }
}