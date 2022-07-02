/*
 * Created by JFormDesigner on Thu Apr 28 18:02:32 TRT 2022
 */

package views;

import java.awt.event.*;
import models.UserImpl;
import props.Users;

import java.awt.*;
import java.util.Locale;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author unknown
 */
public class ChangePassword extends JFrame {
    UserImpl userImpl=new UserImpl();
    public static String rePassword="";
    public ChangePassword() {
        initComponents();
        txtEmail.setText(UserImpl.emailAddress);
    }





    private void btnChangePasswordClicked(ActionEvent e) {


    }

    public Users fncDataValid(){

        String email=txtEmail.getText().trim().toLowerCase(Locale.ROOT);
        String oldPassword=txtOldPassword.getText();
        String newPassword=txtNewPassword.getText();
        rePassword=txtRePassword.getText();

        if(email.equals("")){
            lblError.setText("Please Enter Email");
            txtEmail.requestFocus();
        }else if(!email.equals(UserImpl.emailAddress)){
            //lblError.setText("Please Enter a Valid Email Address");
            ////////txtEmail.requestFocus();
        }else if(oldPassword.equals("")){
            lblError.setText("Please Enter Old Password");
            txtOldPassword.requestFocus();
        }else if(!oldPassword.equals(UserImpl.userPassword)){
            lblError.setText("Password Want to Change Does Not Match");
            txtOldPassword.requestFocus();
        }else if(newPassword.equals("")){
            lblError.setText("Please Enter New Password");
            txtNewPassword.requestFocus();
        }else if(rePassword.equals("")){
            lblError.setText("Please Enter RePassword");
            txtRePassword.requestFocus();
        }else if(!newPassword.equals(rePassword)){
            lblError.setText("New Password and RePassword do not Match");
            txtNewPassword.requestFocus();
        }else {
            lblError.setText("Password Change Succesfull");
            Users users=new Users(email,rePassword);

            return users;
        }
        return null;
    }

    private void btnChangePasswordClick(ActionEvent e) {
        Users user=fncDataValid();

        int answer=JOptionPane.showConfirmDialog(this,"Are you sure you want to update?","Update Window",JOptionPane.YES_OPTION);
        if(answer==0){
            userImpl.usersChangePassword(user);
        }else{
            JOptionPane.showMessageDialog(this,"Please Choose!");
        }
        Login login = new Login();
        login.setVisible(true);
        dispose();
    }

        private void thisWindowClosing(WindowEvent e) {
            new Login().setVisible(true);
            dispose();
        }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        txtEmail = new JTextField();
        txtOldPassword = new JPasswordField();
        txtNewPassword = new JPasswordField();
        txtRePassword = new JPasswordField();
        btnChangePassword = new JButton();
        lblError = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();

        //======== this ========
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("E-Mail");

        //---- btnChangePassword ----
        btnChangePassword.setText("Change");
        btnChangePassword.addActionListener(e -> btnChangePasswordClick(e));

        //---- lblError ----
        lblError.setText(" ");

        //---- label2 ----
        label2.setText("Confirm Password");

        //---- label3 ----
        label3.setText("New Password");

        //---- label4 ----
        label4.setText("Old Password");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtRePassword, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                                .addComponent(txtNewPassword, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                                .addComponent(txtOldPassword, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                                .addComponent(txtEmail, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)))
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addComponent(lblError, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                            .addComponent(btnChangePassword)))
                    .addContainerGap(35, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1)
                        .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtOldPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label4))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNewPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label3))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtRePassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label2))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnChangePassword)
                        .addComponent(lblError))
                    .addContainerGap(40, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JTextField txtEmail;
    private JPasswordField txtOldPassword;
    private JPasswordField txtNewPassword;
    private JPasswordField txtRePassword;
    private JButton btnChangePassword;
    private JLabel lblError;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
