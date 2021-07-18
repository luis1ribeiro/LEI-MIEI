package view;

import MVC.View;
import business.Media.Media;
import business.Media.Musica;


import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author xaLuG
 */
public class ListPlaylists extends javax.swing.JFrame implements View {

    public ListPlaylists(Socket s, List<String> l) {
        this.socket=s;
        initComponents();
        addRowToJTable(l);
    }
    
    
    
    public void addRowToJTable(List<String> list) {
        //DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int m = list.size();
        Object rD[][] = new Object [m][2];
        
        for (int i=0; i < list.size(); i++){
          /* javax.swing.JButton b = new javax.swing.JButton();
           b = filtrarButton;
           b.setText("ola");
           */
            rD[i][0] = list.get(i);
           // model.addRow(rowData);
        }
        
        
        
        String columnheaders [] = {"Playlist"};
        
        
        TableModel tableModel = new DefaultTableModel(rD,columnheaders);
  
        JTable table=new JTable(tableModel);
   
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(sorter);
        
        
        
        table.getColumnModel().getColumn(0).setCellRenderer(new ButtonRenderer());;
        table.getColumnModel().getColumn(0).setCellEditor(new ButtonEditor(new JTextField(),this.socket,this));
       
        TableColumn col0 = table.getColumnModel().getColumn(0);
        
        col0.setResizable(false);
        
        
        
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
   private String nome;
   private Boolean clicked;
   private Socket socket;
   private View tabela;

   public ButtonEditor(JTextField txt,Socket s,View v) {
    super(txt);
    this.socket=s;
    btn=new JButton();
    btn.setOpaque(true);
    this.tabela=v;

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
     nome=(obj==null) ? "":obj.toString();
     btn.setText(nome);
     clicked=true;
    return btn;
  }

  //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
   @Override
  public Object getCellEditorValue() {

     if(clicked)
      {
          try {
              PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
              pw.println("musicasPlaylist?"+nome);
              pw.flush();
              ObjectInputStream input = new ObjectInputStream(this.socket.getInputStream());
              List<Media> list = (List<Media>) input.readObject();
              if (list.isEmpty()){
                  JOptionPane.showMessageDialog((ListPlaylists)this.tabela, "Playlist Vazia");
                  return nome;
              }
              if (list.get(0) instanceof Musica)
                new PlaylistMusica(this.socket,list).run();
              else new PlaylistVideo(this.socket,list).run();
              this.tabela.setVisible(false);
              dispose();
          }
          catch (IOException | ClassNotFoundException e){
              JOptionPane.showMessageDialog((ListPlaylists)this.tabela, "Erro de conexão");
          }
      }
    //SET IT TO FALSE NOW THAT ITS CLICKED
    clicked=false;
    return nome;
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

        jLabel1 = new javax.swing.JLabel();
        back = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        playlistPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+ File.separator
                +"src"+File.separator+"view"+File.separator+"icons"+File.separator+"mediacenter.png")); // NOI18N

        back.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+ File.separator
                +"src"+File.separator+"view"+File.separator+"icons"+File.separator+"back.png")); // NOI18N
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        playlistPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout playlistPanel1Layout = new javax.swing.GroupLayout(playlistPanel1);
        playlistPanel1.setLayout(playlistPanel1Layout);
        playlistPanel1Layout.setHorizontalGroup(
            playlistPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );
        playlistPanel1Layout.setVerticalGroup(
            playlistPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 225, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(playlistPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(back)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jLabel1)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(back)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        new Menu_playlist(this.socket).run();
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_backActionPerformed


    public void run() {
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    
    private Socket socket;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel playlistPanel1;
    // End of variables declaration//GEN-END:variables
}
