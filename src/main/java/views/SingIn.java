/*
 * Created by JFormDesigner on Thu Apr 21 23:05:09 TRT 2022
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
public class SingIn extends JFrame {
    UserImpl userImpl=new UserImpl();
    public SingIn() {
        initComponents();
    }

    public Users fncSignInDataValid(){
        String SignInName=txtSignInName.getText().trim();
        String SignISurName=txtSignInSurname.getText().trim();
        String SignInEmail=txtSignInEmail.getText().trim().toLowerCase(Locale.ROOT);
        String SignInPassword=txtSignInPassword.getText();

        if(SignInName.equals("")){
            lblError.setText("Please Enter Name");
            txtSignInName.requestFocus();;
        }else if(SignISurName.equals("")){
            lblError.setText("Please Enter Surname");
            txtSignInSurname.requestFocus();
        }else if(SignInEmail.equals("")){
            lblError.setText("Please Enter Email");
            txtSignInEmail.requestFocus();
        }else if(SignInPassword.equals("")){
            lblError.setText("Please Enter Phone");
            txtSignInPassword.requestFocus();
        }else {
            lblError.setText("");
            Users user=new Users(0,SignInEmail,SignInPassword,SignInName,SignISurName);

            return user;
        }
        return null;
    }
    public void fncSignInTextClear(){
        txtSignInName.setText("");
        txtSignInSurname.setText("");
        txtSignInEmail.setText("");
        txtSignInPassword.setText("");
    }

    private void btnSignInAddClick(ActionEvent e) {
        Users user=fncSignInDataValid();
        if(user!=null){
            int status= userImpl.usersInsert(user);
            if(status>0){
                System.out.println("Ekleme Başarılı");
                fncSignInTextClear();
            }else{
                if(status==-1){
                    lblError.setText("Email or Phone Number Already Exists.");
                }else {
                    lblError.setText("Insert Error");
                }
            }
        }
    }

    private void thisWindowClosing(WindowEvent e) {
        new Login().setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        txtSignInName = new JTextField();
        txtSignInSurname = new JTextField();
        txtSignInEmail = new JTextField();
        txtSignInPassword = new JPasswordField();
        lblError = new JLabel();
        button1 = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Name");

        //---- label2 ----
        label2.setText("Surname");

        //---- label3 ----
        label3.setText("E-Mail");

        //---- label4 ----
        label4.setText("Password");

        //---- lblError ----
        lblError.setText(" ");

        //---- button1 ----
        button1.setText("SIGN IN");
        button1.addActionListener(e -> btnSignInAddClick(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtSignInName, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtSignInSurname, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label3, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtSignInEmail, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label4, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(lblError, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button1, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtSignInPassword, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(39, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1)
                        .addComponent(txtSignInName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(13, 13, 13)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label2)
                        .addComponent(txtSignInSurname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(6, 6, 6)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label3)
                        .addComponent(txtSignInEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(6, 6, 6)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label4)
                        .addComponent(txtSignInPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblError, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addComponent(button1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(37, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JTextField txtSignInName;
    private JTextField txtSignInSurname;
    private JTextField txtSignInEmail;
    private JPasswordField txtSignInPassword;
    private JLabel lblError;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
