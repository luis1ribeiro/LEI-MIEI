import java.time.LocalDate;
import java.util.List;

public abstract class Pessoa implements java.io.Serializable
{
    private String email;
    private int nif;
    private String nome;
    private String password;
    private String morada;
    private LocalDate nascimento;
    
    
    public Pessoa(){
        this.email="";
        this.nif=0;
        this.nome="";
        this.password="";
        this.morada="";
        this.nascimento= LocalDate.now();
    }
    
    public Pessoa(String email, int nif, String nome, String password, String morada, LocalDate nascimento)
    {
        this.email=email;
        this.nif=nif;
        this.nome=nome;
        this.password=password;
        this.morada=morada;
        this.nascimento=nascimento;
    }
    
    public Pessoa (Pessoa p){
        this.email=p.getEmail();
        this.nif=p.getNif();
        this.nome=p.getNome();
        this.password=p.getPassword();
        this.morada=p.getMorada();
        this.nascimento=p.getNascimento();
    }
    
    public int getNif(){
        return this.nif;
    }
    
    public void setNif(int n){
        this.nif=n;
    }
    
    public String getEmail ()
    {
        return this.email;
    }
     
    public String getNome()
    {
        return this.nome;
    }
    
    public String getPassword ()
    {
        return this.password;
    }
     
    public String getMorada ()
    {
        return this.morada;
    }
     
    public LocalDate getNascimento ()
    {
        return this.nascimento;
    }
    
    public void setEmail(String email)
    {
        this.email=email;
    }
    
    public void setNome(String nome)
    {
        this.nome=nome;
    }
    
    public void setPassword(String password)
    {
        this.password=password;
    }
    
    public void setMorada(String morada)
    {
        this.morada=morada;
    }
    
    public void setNascimento(LocalDate nascimento)
    {
        this.nascimento=nascimento;
    }
    
    public abstract Pessoa clone ();
    
    public abstract void registaAluguer(Aluguer a);
    
    public abstract void classifica(int classi);
    
    public abstract List<Aluguer> getHistorial ();
    
    public abstract List<Aluguer> alugueresEntreDatas (LocalDate inicio, LocalDate fim);
}