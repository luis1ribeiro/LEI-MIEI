/**
 * Interface correspondente ao Controlador no modelo MVC que
 * gere as operaçoes da aplicaçao
 *
 * @author Grupo31
 * @version 2019
 */

public interface IGereVendasController
{
    /**
     * Funcao que altera o model da app.
     * 
     * @param model Novo model da app
     */
    public void setModel(IGereVendasModel model);
    
    /**
     * Funcao que altera a view da app.
     * 
     * @param view Nova view da app
     */
    public void setView(IGereVendasView view);
    
    /**
     * Funcao que inicia o controlador.
     * 
     */
    public void start ();
}
