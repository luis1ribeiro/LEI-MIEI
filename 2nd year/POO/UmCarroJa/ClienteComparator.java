import java.util.Comparator;
/**
 * Write a description of class clienteComparartor here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ClienteComparator implements Comparator<Pessoa>
{
    // 1 ordena por numero de alugueres
    // 2 ordena por km's percorridos
    private int x;

    /**
     * Constructor for objects of class clienteComparartor
     */
    public ClienteComparator(int x)
    {
        this.x=x;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int compare(Pessoa c1, Pessoa c2)
    {
        if (this.x==1){
            int c1size = c1.getHistorial().size(); int c2size = c2.getHistorial().size(); 
            if (c1size>c2size) return -1;
            if (c2size>c1size) return 1;
            else return 0;
        }
        if (this.x==2){
            double c1km = c1.getHistorial().stream().mapToDouble(a-> a.getDistancia()).sum(); 
            double c2km = c2.getHistorial().stream().mapToDouble(a-> a.getDistancia()).sum();
            if (c1km>c2km) return -1;
            if (c2km>c1km) return 1;
            else return 0;
        }
        return 0;
    }
}