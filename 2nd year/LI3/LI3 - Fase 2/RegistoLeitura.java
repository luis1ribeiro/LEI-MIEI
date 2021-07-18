import java.io.*;
import java.util.*;

/**
 * Classe que reprenseta um registo de leitura de ficheiros de vendas.
 *
 * @author Grupo31
 * @version 2019
 */
public class RegistoLeitura implements Serializable
{
    private String nomeFich;
    private int vendasErradas;

    public RegistoLeitura()
    {
        this.nomeFich="";
        this.vendasErradas = 0;
    }
    
    public RegistoLeitura(String nome, int vendas)
    {
        this.nomeFich=nome;
        this.vendasErradas =vendas;
    }
    
    public RegistoLeitura(RegistoLeitura r)
    {
        this.nomeFich=r.getNomeFich();
        this.vendasErradas = r.getVendasErradas();
    }

    public String getNomeFich (){
        return this.nomeFich;
    }
    
    public int getVendasErradas(){
        return this.vendasErradas;
    }
    
    public void setNomeFich (String nome){
        this.nomeFich = nome;
    }
    
    public void setVendasErradas (int vendas){
        this.vendasErradas = vendas;
    }
    
    public RegistoLeitura clone (){
        return new RegistoLeitura (this);
    }
}