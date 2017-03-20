
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server_ACTIVITY extends javax.swing.JFrame 
{    

    static ObjectInputStream ois=null;
    static ObjectOutputStream oos=null;
    static String activity=null;
    static boolean val=true;
    
    public Server_ACTIVITY() 
    {
        initComponents();
        jLabel1.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Shonar Bangla", 0, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Client Request:");

        jButton1.setFont(new java.awt.Font("Shonar Bangla", 0, 36)); // NOI18N
        jButton1.setText("ACCEPT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Shonar Bangla", 0, 36)); // NOI18N
        jButton2.setText("REJECT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Shonar Bangla", 0, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
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
            oos.writeObject("Accept");
            oos.flush();
        } 
        catch (IOException ex) {
            Logger.getLogger(Server_ACTIVITY.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(activity.equals("Remote Desktop"))
        {
            setVisible(false);
            Server_RD srd=new Server_RD();
            srd.server_rd_call();
        }
        else if(activity.equals("File Sharing"))
        {
            setVisible(false);
            Server_FS sfs=new Server_FS();
            String temps[]={"0"};
            try 
            {
                sfs.main(temps);
            } 
            catch (IOException ex) {
                Logger.getLogger(Server_ACTIVITY.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server_ACTIVITY.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dispose();
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
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 new Server_ACTIVITY().setVisible(true);
            }
        });
        while(val) 
        {
            ois=new ObjectInputStream(Front.ssocket.getInputStream());
            val=false;
            activity=(String)ois.readObject();
            jLabel2.setText(activity);
        } 
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton jButton1;
    private static javax.swing.JButton jButton2;
    private static javax.swing.JLabel jLabel1;
    private static javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
