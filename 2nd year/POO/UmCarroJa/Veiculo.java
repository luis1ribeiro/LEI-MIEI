import java.util.List;
import java.awt.Point;
import java.lang.Object;
import java.util.ArrayList;
import java.util.stream.Collectors;



public abstract class Veiculo implements java.io.Serializable
{
    private String marca;
    private String matricula;
    private int nif;
    private double velomed;
    private double precokm;
    private double consumokm;
    private List<Aluguer> historial;
    private int classificacao;
    private int votos;
    private Ponto posicao;
    private double autonomiaMax;
    private double autonomia;
    private List<PedidoAluguer> espera;

    public Veiculo(){
        this.marca="";
        this.matricula="---";
        this.nif=0;
        this.velomed=0;
        this.precokm=0;
        this.consumokm=0;
        this.historial= new ArrayList <Aluguer> ();
        this.classificacao=0;
        this.votos=0;
        this.posicao= new Ponto ();
        this.autonomiaMax=0;
        this.autonomia=0;
        this.espera= new ArrayList <PedidoAluguer> ();
    }
    
    public Veiculo(String marca, String matricula, int nif, double velomed, double precokm,double consumokm,Ponto posicao, double autonomiaMax, double autonomia)
    {
        this.marca=marca;
        this.matricula=matricula;
        this.nif=nif;
        this.velomed=velomed;
        this.precokm=precokm;
        this.consumokm=consumokm;
        this.historial=new ArrayList<Aluguer>();
        this.classificacao=0;
        this.votos=0;
        this.posicao=posicao;
        this.autonomiaMax=autonomiaMax;
        this.autonomia=autonomia;
        this.espera= new ArrayList <PedidoAluguer> ();
    }
    
    public Veiculo(Veiculo v){
        this.marca=v.getMarca();
        this.matricula=v.getMatricula();
        this.nif=v.getNif();
        this.velomed=v.getVelomed();
        this.precokm=v.getPrecokm();
        this.consumokm=v.getConsumokm();
        this.setHistorial(v.getHistorial());
        this.classificacao=v.getClassificacao();
        this.votos=v.getVotos();
        this.setPos(v.getPos());
        this.autonomiaMax=v.getAutonomiaMax();
        this.autonomia=v.getAutonomia();
        this.espera=v.getEspera();
    }
    
    public List <PedidoAluguer> getEspera(){
        return this.espera.stream().map(pa -> pa.clone()).collect(Collectors.toList());
    }
    
    public void setEspera(List<PedidoAluguer> a){
        this.espera=a.stream().map(pa -> pa.clone()).collect(Collectors.toList());
    }
    
    public int getVotos(){
        return this.votos;
    }
    
    public void setVotos(int votos){
        this.votos=votos;
    }
    
    public String getMarca(){
        return this.marca;
    }
    
    public void setMarca(String marca){
        this.marca=marca;
    }
    
    public String getMatricula(){
        return this.matricula;
    }
    
    public void setMatricula(){
        this.matricula=matricula;
    }
    
    public int getNif(){
        return this.nif;
    }
    
    public void setNif(){
        this.nif=nif;
    }
    
    public double getVelomed()
    {
        return this.velomed;
    }
    
    public double getPrecokm()
    {
        return this.precokm;
    }
    
    public double getConsumokm()
    {
        return this.consumokm;
    }
    
    public List<Aluguer> getHistorial()
    {
        List<Aluguer> list = new ArrayList <>(this.historial.size());
        for(Aluguer i:this.historial)
            list.add(i.clone());
        return list;
    }
    
    public int getClassificacao()
    {
        return this.classificacao;
    }

    
    public void setVelomed(double velo){
        this.velomed=velo;
    }
    
    public void setPrecokm(double preco){
        this.precokm=preco;
    }
    
    public void setConsumokm (double consumo){
        this.consumokm=consumo;
    }
    
    public void setHistorial (List<Aluguer> hist){
        this.historial= new ArrayList <> (hist.size());
        for (Aluguer h: hist)
            this.historial.add(h.clone());
    }
    
    public void setClassificacao (int classi){
        this.classificacao=classi;
    }
    
    public Ponto getPos ()
    {
        return this.posicao.clone();
    }
    
    public double getAutonomia()
    {
        return this.autonomia;
    }
    
    public double getAutonomiaMax()
    {
        return this.autonomiaMax;
    }
        
    public void setPos (Ponto pos){
        this.posicao= pos.clone();
    }
    
    public void setAutonomiaMax (double aut){
        this.autonomiaMax=aut;
    }
    
    public void setAutonomia(double aut){
        this.autonomia=aut;
    }
    
    public void classifica (int classi){
        this.votos++;
        this.classificacao=(this.classificacao+classi)/(this.votos);
    }
    
    public int getPercDep (){
        return (int) ((this.getAutonomia()/this.getAutonomiaMax())*100);
    }
    
    public abstract Veiculo clone();
    
    public void registaAluguer (Aluguer a){
        this.setPos(a.getDest());
        this.autonomia-=a.getDistancia();
        this.historial.add(a.clone());
    }
    
    public void adicionaEspera(PedidoAluguer a){
        this.espera.add(a.clone());
    }
    
    public void eliminaEspera(PedidoAluguer a){
        this.espera.remove(a);
    }
}