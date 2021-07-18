/**
 * Classe correspondente a um pedido de aluguer realizado pelo Cliente.
 *
 * @author Grupo38
 * @version 2019/05/17
 */
public class PedidoAluguer implements java.io.Serializable
{
    // instance variables - replace the example below with your own
    private int nifCliente;
    private Ponto partida;
    private Ponto destino;
    private String combustivel;
    private String preferencia;

    /**
     * Contstrutores da classe PedidoAluguer
     */

    /**
     * Construtor de inicializaçao
     */
    public PedidoAluguer()
    {
        // initialise instance variables
        this.nifCliente=0;
        this.partida=new Ponto ();
        this.destino= new Ponto();
        this.combustivel="";
        this.preferencia="";
    }

    /**
     * Construtor parametrizado
     */
    public PedidoAluguer(int nif, Ponto p, Ponto d, String comb, String pref){
        this.nifCliente=nif;
        this.setPartida(p);
        this.setDestino(d);
        this.combustivel=comb;
        this.preferencia=pref;
    }

    /**
     * Construtor de copia
     */
    public PedidoAluguer(PedidoAluguer a){
        this.nifCliente=a.getCliente();
        this.setPartida(a.getPartida());
        this.setDestino(a.getDestino());
        this.setCombustivel(a.getCombustivel());
        this.setPreferencia(a.getPreferencia());
    }

    /**
     * Retorna o cliente que requisitou o aluguer
     *
     * @return    Cliente do pedido
     */
    public int getCliente ()
    {
        // put your code here
        return this.nifCliente;
    }

    public void setCliente(int c){
        this.nifCliente=c;
    }

    public Ponto getPartida(){
        return this.partida.clone();
    }

    public void setPartida(Ponto p){
        this.partida=p.clone();
    }

    public Ponto getDestino (){
        return this.destino.clone();
    }

    public void setDestino(Ponto p){
        this.destino=p.clone();
    }

    public String getCombustivel (){
        return this.combustivel;
    }

    public void setCombustivel(String comb){
        this.combustivel=comb;
    }

    public String getPreferencia(){
        return this.preferencia;
    }

    public void setPreferencia(String preferencia){
        this.preferencia=preferencia;
    }

    public double getDistancia (){
        return this.partida.dist(this.destino);
    }

    public boolean equals (Object obj){
        if (this==obj) return true;
        if (this.getClass() != obj.getClass()) return false;
        PedidoAluguer a = (PedidoAluguer) obj;
        return this.nifCliente==a.getCliente() && this.partida.equals(a.getPartida()) && this.destino.equals(a.getDestino())
         && this.combustivel.equals(a.getCombustivel()) && this.preferencia.equals(a.getPreferencia());
    }

    public String toString (){
        StringBuilder sb = new StringBuilder ();
        sb.append("Cliente: ").append(this.nifCliente).append("\nPartida: ").append(this.partida.toString()).append("\nDestino: ")
        .append(this.destino.toString()).append("\nCombustivel: ").append(this.combustivel).append("\nPreferencia: ").append(this.preferencia);
        return sb.toString();
    }

    public PedidoAluguer clone (){
        return new PedidoAluguer (this);
    }
}
