
/**
 * Write a description of class VeiculoComparator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Comparator;

public class VeiculoComparator implements Comparator<Veiculo> {
    Ponto pcliente;
    // 1 -> custo da viagem && 2 -> distancia ao cliente
    int tipoComp;
    
    public VeiculoComparator(Ponto cli,int tipoComp){
        this.pcliente=cli;
    }
    
    public int compare(Veiculo v1, Veiculo v2){
        if (this.tipoComp==2){
            return (Double.valueOf(this.pcliente.dist(v1.getPos())))
            .compareTo(Double.valueOf(this.pcliente.dist(v2.getPos())));
        }
        else{
            return (Double.valueOf(v1.getPrecokm())).compareTo(Double.valueOf(v2.getPrecokm()));
        }
    }
}