package view;


import MVC.View;
import business.Utilizador.Utilizador;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author xaLuG
 */
public class Lista_Users extends javax.swing.JFrame implements View {

    /**
     * Creates new form Lista_Users
     */
    public Lista_Users(Socket s,List<Utilizador> u) {
        this.socket=s;
        initComponents();
        this.users=u;
    }

    
    
    public void addRowToJTable(List<Utilizador> list) {
        //DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int m = list.size();
        Object rD[][] = new Object [m][3];
        
        for (int i=0; i < list.size(); i++){
          /* javax.swing.JButton b = new javax.swing.JButton();
           b = filtrarButton;
           b.setText("ola");
           */
            rD[i][0] = list.get(i).getEmail();
            rD[i][1] = list.get(i).getNome();
            //rD[i][2] = list.get(i).password;
           // model.addRow(rowData);
        }
        
        
        
        String columnheaders [] = {"User", "E-mail"};
        
        
        TableModel tableModel = new DefaultTableModel(rD,columnheaders);
  
        JTable table=new JTable(tableModel);
   
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(sorter);
        
        
        
        table.getColumnModel().getColumn(0).setCellRenderer(new ButtonRenderer());;
        table.getColumnModel().getColumn(0).setCellEditor(new ButtonEditor(new JTextField(),this.socket,this));
       
        TableColumn col0 = table.getColumnModel().getColumn(0);
        TableColumn col1 = table.getColumnModel().getColumn(1);
        
        col0.setResizable(false);
        col1.setResizable(false);
        
        
        
        JScrollPane pane=new JScrollPane(table);
        jScrollPane1.setViewportView(pane);
       
        
       
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }   
    
    
   
    //BUTTON RENDERER CLASS
class ButtonRenderer extends JButton implements  TableCellRenderer
{

  //CONSTRUCTOR
  public ButtonRenderer() {
    //SET BUTTON PROPERTIES
    setOpaque(true);
  }
  @Override
  public Component getTableCellRendererComponent(JTable table, Object obj,
      boolean selected, boolean focused, int row, int col) {

    //SET PASSED OBJECT AS BUTTON TEXT
      setText((obj==null) ? "":obj.toString());

    return this;
  }

}

//BUTTON EDITOR CLASS
class ButtonEditor extends DefaultCellEditor
{
   private JButton btn;
   private String email;
   private Socket socket;
   private Lista_Users view;
   private Boolean clicked;

   public ButtonEditor(JTextField txt,Socket s,Lista_Users view) {
    super(txt);
    this.socket=s;
    this.view=view;
    btn=new JButton();
    btn.setOpaque(true);

    //WHEN BUTTON IS CLICKED
    btn.addActionListener(new ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        fireEditingStopped();
      }
    });
  }

   //OVERRIDE A COUPLE OF METHODS
   @Override
  public Component getTableCellEditorComponent(JTable table, Object obj,
      boolean selected, int row, int col) {

      //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
     email=(obj==null) ? "":obj.toString();
     btn.setText(email);
     clicked=true;
    return btn;
  }

  //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
   @Override
  public Object getCellEditorValue() {

     if(clicked)
      {
      //SHOW US SOME MESSAGE
          try {
              PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
              pw.println("removeUtilizador?" + this.email);
              pw.flush();
              BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
              String res = br.readLine();
              if (!res.equals("Sucesso")){
                  JOptionPane.showMessageDialog(btn, "Ocorreu um erro a interpretar a mensagem");
              }
              else JOptionPane.showMessageDialog(btn, email+" eliminado");
          }
          catch (IOException e){
              JOptionPane.showMessageDialog(btn, "Erro de conexão");
          }
          this.view.refresh();
      }
    //SET IT TO FALSE NOW THAT ITS CLICKED
    clicked=false;
    return new String(email);
  }

   @Override
  public boolean stopCellEditing() {

         //SET CLICKED TO FALSE FIRST
      clicked=false;
    return super.stopCellEditing();
  }

   @Override
  protected void fireEditingStopped() {
    // TODO Auto-generated method stub
    super.fireEditingStopped();
  }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        usersPanel = new javax.swing.JPanel();
        back = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 367, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+ File.separator
                +"src"+File.separator+"view"+File.separator+"icons"+File.separator+"mediacenter.png")); // NOI18N

        usersPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout usersPanelLayout = new javax.swing.GroupLayout(usersPanel);
        usersPanel.setLayout(usersPanelLayout);
        usersPanelLayout.setHorizontalGroup(
            usersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );
        usersPanelLayout.setVerticalGroup(
            usersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 225, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(usersPanel);

        back.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+ File.separator
                +"src"+File.separator+"view"+File.separator+"icons"+File.separator+"back.png")); // NOI18N
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(back))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(back)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        new Inicio_Admin(this.socket).run();
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_backActionPerformed

    public void refresh (){
        try {
            PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
            pw.println("getListaUsers");
            pw.flush();
            ObjectInputStream input = new ObjectInputStream(this.socket.getInputStream());
            List<Utilizador> list = (List<Utilizador>) input.readObject();
            new Lista_Users(this.socket,list).run();
            this.setVisible(false);
            dispose();
        }
        catch (IOException | ClassNotFoundException e){
            JOptionPane.showMessageDialog(this, "Erro de conexão");
        }
    }

    public void run() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        addRowToJTable(this.users);
        this.setVisible(true);
    }

    private List<Utilizador> users;
    private Socket socket;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel usersPanel;
    // End of variables declaration//GEN-END:variables
}
