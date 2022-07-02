/*
 * Created by JFormDesigner on Thu Apr 14 23:09:35 TRT 2022
 */

package views;

import java.awt.event.*;
import models.UserImpl;
import utils.Util;

import java.awt.*;
import java.util.Locale;
import java.util.Random;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author unknown
 */
public class Login extends JFrame {
    UserImpl user = new UserImpl();
    public static String emailChange="";
    boolean status=false;
    public static String verificationCode="";

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
    public Login() {
        initComponents();
    }

    public void usersLogin() {
        String user_email = txtEmail.getText().trim().toLowerCase();
        String password = String.valueOf(txtPassword.getPassword());
        if (user_email.equals("")) {
            txtEmail.requestFocus();
            lblError.setText("Please Entry E-Mail!");
        }else if (!Util.isValidEmailAddress(user_email)) {
            lblError.setText("E-Mail Format Error!");
        }else if (password.length() == 0) {
            emailChange = txtEmail.getText();
            lblError.setText("Please Entry Password");
            txtPassword.requestFocus();
        }else {
            lblError.setText("");
            boolean status = user.usersLogin(user_email, password);
            if (status) {
                Dashboard dashboard = new Dashboard();
                dashboard.setVisible(true);
                dispose();
            }else {
                lblError.setText("E-Mail or Password False!");
            }
        }
    }

    private void btnUsersLogin(ActionEvent e) {
        usersLogin();
        new Dashboard().setVisible(true);
        dispose();
    }

    private void txtEmailKeyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            usersLogin();
        }
    }

    private void txtPasswordKeyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            usersLogin();
        }
    }

    private void btnSignInClick(ActionEvent e) {
        new SingIn().setVisible(true);
        dispose();
    }

    private void btnForgotPasswordClick(ActionEvent e) {
        String email=txtEmail.getText().trim().toLowerCase(Locale.ROOT);
        status=user.userGetEmail(email);
        if(status){
            generatingVerificationCode();


            String to= UserImpl.emailAddress;
            String sub="Java Project Forget Password";
            String msg="\n\n\t\tVerification Code: "+verificationCode;

            Util.sendMail(to,sub,msg);



            new ForgotPassword().setVisible(true);
            dispose();
        }else{
            lblError.setText("Please Enter a Valid Email Address");
        }
    }
    public void generatingVerificationCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        verificationCode = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(verificationCode);
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        label2 = new JLabel();
        txtEmail = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton();
        lblError = new JLabel();
        button1 = new JButton();
        button2 = new JButton();

        //======== this ========
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Email:");

        //---- label2 ----
        label2.setText("Password:");

        //---- txtEmail ----
        txtEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtEmailKeyReleased(e);
            }
        });

        //---- txtPassword ----
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtPasswordKeyReleased(e);
            }
        });

        //---- btnLogin ----
        btnLogin.setIcon(new ImageIcon(getClass().getResource("/images/login.png")));
        btnLogin.addActionListener(e -> btnUsersLogin(e));

        //---- button1 ----
        button1.setText("Sign In");
        button1.addActionListener(e -> btnSignInClick(e));

        //---- button2 ----
        button2.setText("Forgot Password");
        button2.addActionListener(e -> btnForgotPasswordClick(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addGap(0, 78, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(button2, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(label1, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtPassword, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                                .addComponent(txtEmail, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(lblError, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                            .addGap(9, 9, 9))
                        .addComponent(button1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
                    .addGap(139, 139, 139))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(151, 151, 151)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(txtEmail)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(lblError, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(button1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(button2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(62, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JLabel label2;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblError;
    private JButton button1;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
