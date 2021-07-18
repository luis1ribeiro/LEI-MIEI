import java.lang.Object;
import java.lang.Math;
import java.time.LocalDate;

public class Aluguer implements java.io.Serializable
{ 
   private int nifCliente;
   private int nifProp;
   private String matricula;
   private Ponto origem;
   private Ponto destino;
   private double custo;
   private double distancia;
   private double duracao;
   private LocalDate data;
   private boolean clienteClassificou;

   
   public Aluguer (){
       this.nifCliente = 0;
       this.nifProp = 0;
       this.matricula = "";
       this.origem = new Ponto ();
       this.destino = new Ponto ();
       this.custo=0;
       this.distancia=0;
       this.duracao=0;
       this.data=LocalDate.now();
   }
   
   public Aluguer (int nifCliente, int nifProp, String matricula, Ponto orig, Ponto dest,double distancia,double duracao,double custo,LocalDate data)
   {
       this.nifCliente=nifCliente;
       this.nifProp=nifProp;
       this.matricula=matricula;
       this.origem=orig.clone();
       this.destino=dest.clone();
       this.distancia=distancia;
       this.duracao=duracao;
       this.custo=custo;
       this.data=data;
   }
   
   public Aluguer (Aluguer a){
       this.nifCliente=a.getCliente();
       this.nifProp=a.getProp();
       this.matricula=a.getMatricula();
       this.setOrig(a.getOrig());
       this.setDest(a.getDest());
       this.distancia=a.getDistancia();
       this.duracao=a.getDuracao();
       this.custo=a.getCusto();
       this.data=a.getData();
   }
      
   public LocalDate getData(){
       return this.data;
   }
    
   public void setData(LocalDate data){
       this.data=data;
   }
    
   public int getCliente()
   {
       return this.nifCliente;
   }
   
   public int getProp()
   {
       return this.nifProp;
   }
   
   public String getMatricula()
   {
       return this.matricula;
   }
   
   public Ponto getOrig()
   {
       return this.origem.clone();
   }
   
   public Ponto getDest()
   {
       return this.destino.clone();
   }
   
   public double getCusto()
   {
       return this.custo;
   }
   
   public double getDistancia()
   {
       return this.distancia;
   }
   
   public double getDuracao()
   {
       return this.duracao;
   }
   
   
   public void setCliente (int cli)
   {
       this.nifCliente=cli;
   }

   public void setProp (int prop)
   {
       this.nifProp=prop;
   }
   
   public void setMatricula (String mat)
   {
       this.matricula=mat;
   }
   
   public void setOrig (Ponto p)
   {
       this.origem=p.clone();
   }
   
   public void setDest (Ponto p)
   {
       this.destino=p.clone();
   }
   
   public void setCusto (double c)
   {
       this.custo=c;
   }
   
   public void setDistancia (double d)
   {
       this.distancia=d;
   }
   
   public void setDuracao (double d)
   {
       this.duracao=d;
   }
   
   public Aluguer clone()
   {
      return new Aluguer (this);
   }
   
   public boolean equals(Object obj)
   {
       if(obj==this) return true;
       if (obj==null || obj.getClass()!=this.getClass()) return false;
       Aluguer a = (Aluguer) obj;
       return this.getCliente()==a.getCliente() && this.getProp()==a.getProp() && this.getMatricula().equals(a.getMatricula()) && this.getOrig().equals(a.getOrig()) 
            && this.getDest().equals(a.getDest()) && custo==a.getCusto() && distancia==a.getDistancia() && duracao==a.getDuracao() && this.data.equals(a.getData());
   }
   
   public String toString()
   {
       StringBuilder sb = new StringBuilder ();
       sb.append("Cliente: \n").append(this.getCliente()).append("\n\nProprietario: \n").append(this.getProp())
        .append("\n\nVeiculo: \n").append(this.getMatricula()).append("\nData: ").append(this.data.toString()).append("\n\nOrigem: ").append(this.getOrig().toString())
        .append("\nDestino: ").append(this.getDest().toString()).append("\nDistancia: ").append(this.getDistancia()).append("\nDuraçao: ")
        .append(this.getDuracao()).append("\nCusto: ").append(this.custo);
       return sb.toString();
   }
}