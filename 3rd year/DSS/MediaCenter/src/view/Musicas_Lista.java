package view;

import MVC.View;
import business.Media.Media;
import business.Media.Musica;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
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
public class Musicas_Lista extends javax.swing.JFrame implements View {

    /**
     * Creates new form Musicas_Lista
     */
    public Musicas_Lista(Socket s, List<Media> l) {
        this.socket=s;
        initComponents();
        this.list=l;
        this.rowData= new Object[this.list.size()][3];
        addRowToJTable(this.list,this.rowData);
    }

    public void addRowToJTable(List<Media> list, Object rowData[][]) {
        //DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int i=0; i < list.size(); i++){
          /* javax.swing.JButton b = new javax.swing.JButton();
           b = filtrarButton;
           b.setText("ola");
           */
            Media m = list.get(i);
            rowData[i][0] = m.getNome();
            rowData[i][1] = ((Musica)m).getArtista();
            rowData[i][2] = m.getGenero();
            // model.addRow(rowData);
        }

        String columnheaders [] = {"Música", "Artista", "Género"};


        TableModel tableModel = new DefaultTableModel(rowData,columnheaders);

        JTable table=new JTable(tableModel);

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(sorter);



        table.getColumnModel().getColumn(0).setCellRenderer(new ButtonRenderer());;
        table.getColumnModel().getColumn(0).setCellEditor(new ButtonEditor(new JTextField(), this.socket,this));

        JScrollPane pane=new JScrollPane(table);
        jScrollPane1.setViewportView(pane);
        setSize(800,360);



        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }



    //BUTTON RENDERER CLASS
    static class ButtonRenderer extends JButton implements  TableCellRenderer
    {

        //CONSTRUCTOR
        public ButtonRenderer() {
            //SET BUTTON PROPERTIES
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focused, int row, int col) {

            //SET PASSED OBJECT AS BUTTON TEXT
            setText((obj==null) ? "":obj.toString());
            return this;
        }

    }

    //BUTTON EDITOR CLASS
    static class ButtonEditor extends DefaultCellEditor
    {
        private Socket socket;
        private View tabela;
        private JButton btn;
        private String nome;
        private String artista;
        private Boolean clicked;

        public ButtonEditor(JTextField txt,Socket s,View tab) {
            super(txt);
            btn=new JButton();
            btn.setOpaque(true);
            this.tabela= (Musicas_Lista) tab;
            this.socket=s;

            //WHEN BUTTON IS CLICKED
            btn.addActionListener(e -> fireEditingStopped());
        }

        //OVERRIDE A COUPLE OF METHODS
        @Override
        public Component getTableCellEditorComponent(JTable table, Object obj,
                                                     boolean selected, int row, int col) {

            //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
            this.nome=(obj==null) ? "":obj.toString();
            this.btn.setText(nome);
            this.artista=table.getValueAt(row,1).toString();
            this.clicked=true;
            return btn;
        }

        //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
        @Override
        public Object getCellEditorValue() {

            if(clicked)
            {
                new Reproduzir_Musica(this.socket,nome,artista, this.tabela).run();
            }
            //SET IT TO FALSE NOW THAT ITS CLICKED
            this.clicked=false;
            return this.nome;
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
    private void initComponents() {

        ArtistaTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        filtrarButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        backbutton = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Género");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Artista");

        filtrarButton.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+ File.separator
                +"src"+File.separator+"view"+File.separator+"icons"+File.separator+"filter.png")); // NOI18N
        filtrarButton.setText("Filtrar");
        filtrarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrar(evt);
            }
        });

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

        jScrollPane1.setViewportView(jPanel1);

        backbutton.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+ File.separator
                +"src"+File.separator+"view"+File.separator+"icons"+File.separator+"back.png")); // NOI18N
        backbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backbuttonActionPerformed(evt);
            }
        });

        refresh.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+ File.separator
                +"src"+File.separator+"view"+File.separator+"icons"+File.separator+"refresh.png")); // NOI18N
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Selecione Categoria>", "Blues", "Classical", "Country", "Eletro", "Fado", "Funk", "Hip Hop", "Indie", "Jazz", "Kizomba", "Latin", "Metal", "Pop", "R&B", "Rap", "Reggae", "Rock", "Sertanejo" }));
        jComboBox1.setAlignmentX(5.0F);

        jLabel4.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+ File.separator
                +"src"+File.separator+"view"+File.separator+"icons"+File.separator+"mediacenter.png")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addContainerGap()
                                                        .addComponent(ArtistaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(41, 41, 41)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addComponent(backbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(filtrarButton)
                                                        .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel4)))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(42, 42, 42)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(578, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane1)
                                                .addGap(53, 53, 53))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(36, 36, 36)
                                                .addComponent(ArtistaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(16, 16, 16)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addComponent(filtrarButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(backbutton)
                                                        .addComponent(refresh))
                                                .addGap(22, 22, 22))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(100, 100, 100)
                                        .addComponent(jLabel3)
                                        .addContainerGap(198, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backbuttonActionPerformed
        new Musica_Video(this.socket).run();
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_backbuttonActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        refresh();
    }//GEN-LAST:event_refreshActionPerformed

    public void filtrar(java.awt.event.ActionEvent evt) {
        if (this.ArtistaTextField.getText().equals("") && this.jComboBox1.getSelectedItem().equals("<Selecione Categoria>")){
            JOptionPane.showMessageDialog(this, "Selecione um filtro!");
            return;
        }
        if(!this.ArtistaTextField.getText().equals("") && !this.jComboBox1.getSelectedItem().equals("<Selecione Categoria>")){
            JOptionPane.showMessageDialog(this, "Selecione apenas um filtro!");
            return;
        }
        if (this.ArtistaTextField.getText().equals("")){
            String gen = (String) this.jComboBox1.getSelectedItem();
            try {
                PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
                pw.println("filter?Musica?Genero?"+gen);
                pw.flush();
                ObjectInputStream input = new ObjectInputStream(this.socket.getInputStream());
                List<Media> list = (List<Media>) input.readObject();
                new Musicas_Lista(this.socket,list).run();
                this.setVisible(false);
                dispose();
            }
            catch (IOException | ClassNotFoundException e){
                JOptionPane.showMessageDialog(this, "Erro de conexão");
            }
        }
        else{
            String art = (String) this.ArtistaTextField.getText();
            try {
                PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
                pw.println("filter?Musica?Artista?"+art);
                pw.flush();
                ObjectInputStream input = new ObjectInputStream(this.socket.getInputStream());
                List<Media> list = (List<Media>) input.readObject();
                new Musicas_Lista(this.socket,list).run();
                this.setVisible(false);
                dispose();
            }
            catch (IOException | ClassNotFoundException e){
                JOptionPane.showMessageDialog(this, "Erro de conexão");
            }
        }
    }


    public void refresh (){
        try {
            PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
            pw.println("getListMusicas");
            pw.flush();
            ObjectInputStream input = new ObjectInputStream(this.socket.getInputStream());
            List<Media> list = (List<Media>) input.readObject();
            new Musicas_Lista(this.socket,list).run();
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
        this.setVisible(true);
    }

    private Socket socket;
    private Object rowData[][];
    private List<Media> list;
    private JTable pp;
    // Variables declaration - do not modify
    private javax.swing.JTextField ArtistaTextField;
    private javax.swing.JButton backbutton;
    private javax.swing.JButton filtrarButton;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refresh;
    // End of variables declaration
}