import static java.lang.System.out;
import java.io.*;
import java.util.*;

public class GereVendasAppMVC implements Serializable
{   
    public static void main(String [] args)
    {
        IGereVendasModel model = new GereVendasModel();
        model.createData();
        if (model==null){
            out.println("ERRO...");
            System.exit(-1);
        }
        IGereVendasView view = new GereVendasView(); //menu principal, alguns dialogos e navegador
        IGereVendasController control = new GereVendasController();
        control.setModel(model);
        control.setView(view);
        control.start();
        System.exit(0);
    }
}