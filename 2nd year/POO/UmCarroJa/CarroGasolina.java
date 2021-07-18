import java.util.List;
import java.awt.Point;
import java.lang.Object;
import java.util.ArrayList;

public class CarroGasolina extends Veiculo
{
    public CarroGasolina(){
        super();
    }
    
    public CarroGasolina(CarroGasolina g){
        super(g);
    }
    
    public CarroGasolina(String marca, String matricula, int nif,double velomed, double precokm,double consumokm, Ponto posicao, double autonomiaMax, double autonomia)
    {
        super(marca,matricula,nif,velomed,precokm,consumokm,posicao,autonomiaMax,autonomia);
    }

    public CarroGasolina clone ()
    {
        return new CarroGasolina (this);
    }
    
    public boolean equals(Object obj)
    {
        if (obj==this) return true;
        if (obj==null || obj.getClass()!=this.getClass()) return false;
        CarroGasolina c = (CarroGasolina) obj;
        return this.getMatricula().equals(c.getMatricula());
    }
 
    public String toString()
    {
        StringBuilder sb = new StringBuilder ();
        sb.append("Tipo de Veiculo: Carro Gasolina").append("\nMarca:").append(this.getMarca()).append("\nMatricula:").append(this.getMatricula())
            .append("\nNif:").append(this.getNif()).append("\nVelocidade media: ").append(this.getVelomed()).append("\nPreço por km: ")
            .append(this.getPrecokm()).append("\nConsumo per km: ").append(this.getConsumokm()).append("\nPosiçao: ").append(this.getPos().toString())
            .append("\nPercentagem do deposito: ").append(this.getPercDep()).append("%").append("\nClassificacao: ").append(this.getClassificacao())
            .append("\nNumero de votos: ").append(this.getVotos());
        return sb.toString();
    }
}
