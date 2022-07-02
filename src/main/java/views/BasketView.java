/*
 * Created by JFormDesigner on Mon Apr 25 14:57:57 TRT 2022
 */

package views;

import models.BasketImpl;
import models.ProductImpl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author unknown
 */
public class BasketView extends JFrame {
    BasketImpl basket = new BasketImpl();
    int row = -1;
    int rowBasket = -1;
    public BasketView() {
        initComponents();
        //tblBasket.setModel(basket.basketTableModel());
        tblBasketCustomer.setModel(basket.orderCustomersTable(null));
        lblBasketCustomer.setText("Customer");
    }

    private void btnAddProducts(ActionEvent e) {
        dispose();
    }

    private void txtCustomerBasketSearchKeyReleased(KeyEvent e) {
        String txtSearch = txtCustomerBasketSearch.getText().trim();
        tblBasketCustomer.setModel(basket.orderCustomersTable(txtSearch));
    }

    private void btnBasketList(ActionEvent e) {
        row = tblBasketCustomer.getSelectedRow();
        int selectedId = (int) tblBasketCustomer.getValueAt(row,0);
        tblBasket.setModel(basket.basketTableModel(selectedId));
        lblBasketCustomer.setText((String) tblBasketCustomer.getValueAt(row,1));
    }
    private void btnBasketDelete(ActionEvent e) {
        rowBasket = tblBasket.getSelectedRow();
        int selectedId = (int) tblBasket.getValueAt(rowBasket,0);
        if (selectedId != 0) {
            int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the product?","Deletion process",JOptionPane.YES_NO_OPTION);
            if(answer==0){
                basket.basketDelete(selectedId);
                tblBasket.setModel(basket.basketTableModel(selectedId));
            }
        }
        else
            JOptionPane.showMessageDialog(this,"Please make a choice!");
    }

    public int callAmount() {
        rowBasket = tblBasket.getSelectedRow();
        int amount= Integer.parseInt(tblBasket.getValueAt(rowBasket,5).toString());
        int stock = basket.StockList().get(rowBasket);
        int result = stock-amount;
        return result;
    }

    private void btnCompleteSale(ActionEvent e) {
        rowBasket = tblBasket.getSelectedRow();
        int resultStock = callAmount();
        String product_name = (String) tblBasket.getValueAt(rowBasket,2);
        int selectedId = (int) tblBasket.getValueAt(rowBasket,0);
        if (selectedId != 0) {
            int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to complete the sale?","Deletion process",JOptionPane.YES_NO_OPTION);
            if(answer==0){
                basket.basketComplete(selectedId);
                JOptionPane.showMessageDialog(this,"Sale Completed");
                System.out.println(resultStock);
                basket.setAmount(resultStock,product_name);
                tblBasket.setModel(basket.basketTableModel(selectedId));
            }
        }
        else
            JOptionPane.showMessageDialog(this,"Please make a choice!");
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane1 = new JScrollPane();
        tblBasket = new JTable();
        btnAddProducts = new JButton();
        btnCompleteSale = new JButton();
        label1 = new JLabel();
        txtCustomerBasketSearch = new JTextField();
        scrollPane2 = new JScrollPane();
        tblBasketCustomer = new JTable();
        label2 = new JLabel();
        btnBasket = new JButton();
        btnBasketDelete = new JButton();
        lblBasketCustomer = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(tblBasket);
        }

        //---- btnAddProducts ----
        btnAddProducts.setText("Add More Products");
        btnAddProducts.addActionListener(e -> btnAddProducts(e));

        //---- btnCompleteSale ----
        btnCompleteSale.setText("Complete the Sale");
        btnCompleteSale.addActionListener(e -> btnCompleteSale(e));

        //---- label1 ----
        label1.setText("Search Customer");

        //---- txtCustomerBasketSearch ----
        txtCustomerBasketSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtCustomerBasketSearchKeyReleased(e);
            }
        });

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(tblBasketCustomer);
        }

        //---- label2 ----
        label2.setText("Basket of");

        //---- btnBasket ----
        btnBasket.setText("List Basket");
        btnBasket.addActionListener(e -> btnBasketList(e));

        //---- btnBasketDelete ----
        btnBasketDelete.setText("Product Delete");
        btnBasketDelete.addActionListener(e -> btnBasketDelete(e));

        //---- lblBasketCustomer ----
        lblBasketCustomer.setText("Customer");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(btnBasketDelete, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAddProducts)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnCompleteSale))
                                .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
                                    .addComponent(label1)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtCustomerBasketSearch, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 459, Short.MAX_VALUE))
                                .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)))
                        .addComponent(scrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(btnBasket))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(270, 270, 270)
                                    .addComponent(label2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblBasketCustomer)))
                            .addGap(0, 278, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1)
                        .addComponent(txtCustomerBasketSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(btnBasket)
                    .addGap(2, 2, 2)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label2)
                        .addComponent(lblBasketCustomer))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCompleteSale, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddProducts, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBasketDelete))
                    .addGap(7, 7, 7))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane1;
    private JTable tblBasket;
    private JButton btnAddProducts;
    private JButton btnCompleteSale;
    private JLabel label1;
    private JTextField txtCustomerBasketSearch;
    private JScrollPane scrollPane2;
    private JTable tblBasketCustomer;
    private JLabel label2;
    private JButton btnBasket;
    private JButton btnBasketDelete;
    private JLabel lblBasketCustomer;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
