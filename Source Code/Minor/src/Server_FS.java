
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server_FS extends javax.swing.JFrame 
{
    static ObjectInputStream ois=null;
    static ObjectOutputStream oos=null;
    static boolean value=true;
    static String activity=null;
    static char ch;

    public Server_FS() 
    {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Shonar Bangla", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("File Sharing");

        jLabel2.setFont(new java.awt.Font("Shonar Bangla", 0, 24)); // NOI18N

        jButton1.setFont(new java.awt.Font("Shonar Bangla", 0, 24)); // NOI18N
        jButton1.setText("SEND");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Shonar Bangla", 0, 24)); // NOI18N
        jButton2.setText("REJECT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try 
        {
            oos=new ObjectOutputStream(Front.ssocket.getOutputStream());
        } 
        catch (IOException ex) {
            Logger.getLogger(Server_ACTIVITY.class.getName()).log(Level.SEVERE, null, ex);
        }
        try 
        {
            oos.writeObject("Send");
            oos.flush();
        } 
        catch (IOException ex) {
            Logger.getLogger(Server_ACTIVITY.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(ch=='f')
        {
            this.setVisible(false);
            Server_FS_File dsv_f=new Server_FS_File();
            dsv_f.execute();
        }
        else if(ch=='d')
        {
            this.setVisible(false);
            Server_FS_Directory dsv_d=new Server_FS_Directory();
            try {
                dsv_d.execute();
            } catch (IOException ex) {
                Logger.getLogger(Server_FS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try 
        {
            oos=new ObjectOutputStream(Front.ssocket.getOutputStream());
        } 
        catch (IOException ex) {
            Logger.getLogger(Server_ACTIVITY.class.getName()).log(Level.SEVERE, null, ex);
        }
        try 
        {
            oos.writeObject("Reject");
            oos.flush();
        } 
        catch (IOException ex) {
            Logger.getLogger(Server_ACTIVITY.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server_FS().setVisible(true);
            }
        });
        while(value) 
        {
            ois=new ObjectInputStream(Front.ssocket.getInputStream());
            value=false;
            activity=(String)ois.readObject();
            ch=activity.charAt(0);
            activity=activity.substring(1);
            jLabel2.setText("Client wants to copy: "+activity);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton jButton1;
    private static javax.swing.JButton jButton2;
    private static javax.swing.JLabel jLabel1;
    private static javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
