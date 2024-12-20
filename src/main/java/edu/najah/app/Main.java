package edu.najah.app;

import edu.najah.menus.AdminMenu;
import edu.najah.menus.ClientMenu;
import edu.najah.menus.InstructorMenu;
import edu.najah.utilities.JsonFileHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends javax.swing.JFrame {
    public static void main(String[] args) {
        Main LoginFrame = new Main();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null);
    }

    public Main() {
        initComponents();
    }

    class jPanelGradient extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            {
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(30, 36, 46);
                Color color2 = new Color(174, 45, 60);
                GradientPaint gp = new GradientPaint(0, 0, color1, 180, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);

            }
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Right = new jPanelGradient();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Left = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        Right.setBackground(new java.awt.Color(255, 102, 102));
        Right.setPreferredSize(new java.awt.Dimension(400, 500));

        jLabel6.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Fitness Management System");

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("copyright © Software project All rights reserved");

        javax.swing.GroupLayout RightLayout = new javax.swing.GroupLayout(Right);
        Right.setLayout(RightLayout);
        RightLayout.setHorizontalGroup(
                RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(RightLayout.createSequentialGroup()
                                .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(RightLayout.createSequentialGroup()
                                                .addGap(145, 145, 145)
                                                .addComponent(jLabel5))
                                        .addGroup(RightLayout.createSequentialGroup()
                                                .addGap(42, 42, 42)
                                                .addComponent(jLabel6)))
                                .addContainerGap(29, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RightLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(58, 58, 58))
        );
        RightLayout.setVerticalGroup(
                RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(RightLayout.createSequentialGroup()
                                .addGap(136, 136, 136)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(71, 71, 71))
        );

        jPanel1.add(Right);
        Right.setBounds(0, 0, 400, 500);

        Left.setBackground(new java.awt.Color(255, 255, 255));
        Left.setMinimumSize(new java.awt.Dimension(400, 500));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(60, 113, 196));
        jLabel1.setText("Welcome, there!");

        jLabel2.setBackground(new java.awt.Color(102, 102, 102));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Username");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));

        jLabel3.setBackground(new java.awt.Color(102, 102, 102));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Password");

        jButton1.setBackground(new java.awt.Color(60, 113, 196));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 133, 255), 1, true));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(60, 113, 196));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Sign up");
        jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 133, 255), 1, true));
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Instructor");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LeftLayout = new javax.swing.GroupLayout(Left);
        Left.setLayout(LeftLayout);
        LeftLayout.setHorizontalGroup(
                LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(LeftLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(LeftLayout.createSequentialGroup()
                                                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jTextField1)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(LeftLayout.createSequentialGroup()
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(42, 42, 42)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jCheckBox1)
                                                .addContainerGap())))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftLayout.createSequentialGroup()
                                .addContainerGap(62, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(62, 62, 62))
        );
        LeftLayout.setVerticalGroup(
                LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(LeftLayout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(jLabel1)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jCheckBox1))
                                .addContainerGap(137, Short.MAX_VALUE))
        );

        jPanel1.add(Left);
        Left.setBounds(400, 0, 400, 500);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String username = jTextField1.getText();
        String password = new String(jPasswordField1.getPassword());


        if (username.isEmpty() || password.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Username or Password cannot be empty.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        Map<String, Object> data;
        try {
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new java.util.HashMap<>();
            }
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading user data. Please try again later.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        Map<String, Map<String, String>> users = (Map<String, Map<String, String>>) data.get("users");
        if (users == null) {
            users = new java.util.HashMap<>();
            data.put("users", users);
        }

        if (users.containsKey(username)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Username already exists. Please try again.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isInstructor) {
            Map<String, Map<String, String>> awaitingApproval = (Map<String, Map<String, String>>) data.get("instructorsAwaitingApproval");
            if (awaitingApproval == null) {
                awaitingApproval = new java.util.HashMap<>();
                data.put("instructorsAwaitingApproval", awaitingApproval);
            }

            // Check if username already exists in instructorsAwaitingApproval
            if (awaitingApproval.containsKey(username)) {
                javax.swing.JOptionPane.showMessageDialog(this, "An instructor application with this username already exists. Please try again.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            Map<String, String> userDetails = new java.util.HashMap<>();
            userDetails.put("password", password);
            userDetails.put("role", "Instructor");
            awaitingApproval.put(username, userDetails);

            try {
                JsonFileHandler.saveJsonData(data);
                javax.swing.JOptionPane.showMessageDialog(this, "Thank you for applying as an Instructor. Please wait for admin approval.", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error saving application data. Please try again later.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } else {
            Map<String, String> userDetails = new java.util.HashMap<>();
            userDetails.put("password", password);
            userDetails.put("role", "Client");
            users.put(username, userDetails);

            try {
                JsonFileHandler.saveJsonData(data);
                javax.swing.JOptionPane.showMessageDialog(this, "Sign up successful! You can now log in.", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error saving user data. Please try again later.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String username = jTextField1.getText();
        String password = new String(jPasswordField1.getPassword()); // Use getPassword for secure retrieval

        Map<String, Map<String, String>> users;
        try {
            users = (Map<String, Map<String, String>>) JsonFileHandler.loadJsonData().get("users");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading user data", e);
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading user data. Please try again later.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (users != null && users.containsKey(username)) {
            Map<String, String> userDetails = users.get(username);
            if (userDetails.get("password").equals(password)) {
                String role = userDetails.get("role");
                LOGGER.log(Level.INFO, "Login successful for user: {0} with role: {1}", new Object[]{username, role});

                // Choose the role menu to be displayed
                javax.swing.JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + username + "!", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                switch (role.toLowerCase()) {
                    case "admin":
                        AdminMenu.display();
                        break;
                    case "instructor":
                        InstructorMenu.display();
                        break;
                    case "client":
                        ClientMenu.display();
                        break;
                    default:
                        LOGGER.log(Level.WARNING, "Unknown role for user: {0}", username);
                        javax.swing.JOptionPane.showMessageDialog(this, "Unknown role. Please contact support.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
        }

        // Login failed
        LOGGER.log(Level.WARNING, "Login failed for user: {0}", username);
        javax.swing.JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        jPasswordField1.setText("");

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        isInstructor = jCheckBox1.isSelected();

    }//GEN-LAST:event_jCheckBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    boolean isInstructor = false;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Left;
    private javax.swing.JPanel Right;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

class App extends javax.swing.JFrame {

}
