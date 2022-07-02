/*
 * Created by JFormDesigner on Thu Apr 14 23:14:42 TRT 2022
 */

package views;

import java.awt.event.*;

import models.*;
import props.Basket;
import props.Categories;
import props.Customers;
import props.Products;

import java.awt.*;
import java.util.Locale;
import java.util.UUID;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class Dashboard extends JFrame {
   ProductImpl productImpl = new ProductImpl();
   CategoryImpl cat = new CategoryImpl();
   CustomerImpl cus = new CustomerImpl();
   OrderImpl order = new OrderImpl();
   BasketImpl basketImpl = new BasketImpl();
   ReportImpl report = new ReportImpl();
   int row = -1;
   int rowCustomer = -1;
   int rowStock = -1;
   int selectedId = 0;
   int pid = 0;
    public static void main(String[] args) {
        new Dashboard().setVisible(true);
    }
    public Dashboard() {
        initComponents();
        tblCustomer.setModel(cus.model());
        cmbCategory.setModel(cat.comboBoxCategories());
        cmbSalesCategory.setModel(cat.comboBoxCategories());
        tblProductManagement.setModel(productImpl.productTable());
        tblCategoryList.setModel(cat.categoriesTable());
        tblOrderCustomers.setModel(order.orderCustomersTable(null));
        tblOrderProducts.setModel(productImpl.productTable());
        tblBasketReport.setModel(report.basketTableModel());

    }

    public Products fncProductDataValid() {
        try {
            if (txtProductName.getText().equals("")){
                txtProductName.requestFocus();
                lblProductError.setText("Product Name Empty!");
            }else if (cmbCategory.getSelectedItem().equals("")){
                lblProductError.setText("Category Name Empty!");
            }else if (txtPurchasePrice.getText().equals("")){
                txtPurchasePrice.requestFocus();
                lblProductError.setText("Purchase Price Empty!");
            }else if (txtSalePrice.getText().equals("")){
                txtSalePrice.requestFocus();
                lblProductError.setText("Sales Price Empty!");
            }else if (txtStock.getText().equals("")){
                txtStock.requestFocus();
                lblProductError.setText("Stock Empty!");
            }else if (txtInfo.getText().equals("")){
                txtInfo.requestFocus();
                lblProductError.setText("Info Empty!");
            }else {
                String productName = txtProductName.getText().toLowerCase(Locale.ROOT).trim();
                int ctid = cat.categoryIdList().get(cmbCategory.getSelectedIndex());
                int purchasePrice = Integer.parseInt(txtPurchasePrice.getText().toLowerCase(Locale.ROOT).trim());
                int salePrice = Integer.parseInt(txtSalePrice.getText().toLowerCase(Locale.ROOT).trim());
                int stock = Integer.parseInt(txtStock.getText().toLowerCase(Locale.ROOT).trim());
                String info = txtInfo.getText().toLowerCase(Locale.ROOT).trim();

                Products products = new Products(0,ctid,productName,purchasePrice,salePrice,stock,info);
                return products;
            }
        }catch (Exception ex) {
            System.err.println("fncProductDataValid Error : " +ex);
        }
        return null;
    }

    public void textProductClear() {
        txtProductName.setText("");
        txtPurchasePrice.setText("");
        txtSalePrice.setText("");
        txtStock.setText("");
        txtInfo.setText("");
    }

    public void rowSelectProduct() {
        int column = 0;
        row = tblProductManagement.getSelectedRow();

        int pid = Integer.parseInt(String.valueOf(tblProductManagement.getValueAt(row,0)));
        String categoryName = String.valueOf(tblProductManagement.getValueAt(row,1));
        String productName = String.valueOf(tblProductManagement.getValueAt(row,2));
        int purchasePrice = Integer.parseInt(String.valueOf(tblProductManagement.getValueAt(row,3)));
        int salePrice = Integer.parseInt(String.valueOf(tblProductManagement.getValueAt(row,4)));
        int stock = Integer.parseInt(String.valueOf(tblProductManagement.getValueAt(row,5)));
        String info = String.valueOf(tblProductManagement.getValueAt(row,6));

        txtProductName.setText(productName);
        cmbCategory.setSelectedItem(categoryName);
        txtPurchasePrice.setText(String.valueOf(purchasePrice));
        txtSalePrice.setText(String.valueOf(salePrice));
        txtStock.setText(String.valueOf(stock));
        txtInfo.setText(info);

    }

    private void tblProductManagementKeyReleased(KeyEvent e) {
        rowSelectProduct();
    }

    private void tblProductManagementMouseClicked(MouseEvent e) {
        rowSelectProduct();
    }

    private void btnAddProduct(ActionEvent e) {
        Products products = fncProductDataValid();
            if (products != null ) {
                int status = productImpl.productInsert(products);
                if (status > 0) {
                    tblProductManagement.setModel(productImpl.productTable());
                    textProductClear();
                }else {
                    lblProductError.setText("Insert Error!");
                }
            }
    }

    private void btnUpdateProduct(ActionEvent e) {
       Products products = fncProductDataValid();
        if (row!=-1){
            row = tblProductManagement.getSelectedRow();
            int answer=JOptionPane.showConfirmDialog(this,"Are you sure you want to update the product?","Update Window",JOptionPane.YES_OPTION);
            pid = Integer.parseInt(String.valueOf(tblProductManagement.getValueAt(row,0)));
            if (answer==0){
                productImpl.productUpdate(products,pid);
                tblProductManagement.setModel(productImpl.productTable());
                row=-1;
            }
        }else{
            JOptionPane.showMessageDialog(this,"Please choose.");
        }
    }

    private void btnDeleteProductClick(ActionEvent e) {
        if (row != -1) {
            int answer=JOptionPane.showConfirmDialog(this,"Are you sure you want to delete the product?","Delete Window",JOptionPane.YES_OPTION);
            row = tblProductManagement.getSelectedRow();
            pid = Integer.valueOf(tblProductManagement.getModel().getValueAt(row,column).toString());
            if (answer==0) {
                productImpl.productDelete(pid);
                tblProductManagement.setModel(productImpl.productTable());
                row = -1;
            }
        }else {
            JOptionPane.showMessageDialog(this,"Please choose.");
        }
    }

    //---------------CATEGORY-------------
    private Categories fncCategoryDataValid(){
        String categoryName = txtCategoryName.getText().trim();
        String caegoryDefinition = txtDefinition.getText().trim();

        if (categoryName.equals("")){
            lblCategoryError.setText("Name is empty");
            txtCategoryName.requestFocus();
        }else if (caegoryDefinition.equals("")){
            lblCategoryError.setText("Definition is empty");
            txtCategoryName.requestFocus();
        }else{
            lblCategoryError.setText("");
            Categories c= new Categories(0 , categoryName, caegoryDefinition);
            return c;
        }
        return null;
    }
    void rowCategorySelect(){
        row = tblCategoryList.getSelectedRow();
        String categoryName = (String) tblCategoryList.getValueAt(row, 0);
        String categoryDefinition = (String) tblCategoryList.getValueAt(row, 1);

        txtCategoryName.setText(categoryName);
        txtDefinition.setText(categoryDefinition);
    }

    private void btnCategoryAddClick(ActionEvent e) {
        Categories c = fncCategoryDataValid();
        if (c != null){
            int status = cat.categoryInsert(c);
            if (status > 0){
                tblCategoryList.setModel(cat.categoriesTable());
                txtCategoryName.setText("");
                txtDefinition.setText("");
                cmbCategory.setModel(cat.comboBoxCategories());
                cmbSalesCategory.setModel(cat.comboBoxCategories());
            }else{
                lblCategoryError.setText("Insert Error");
            }
        }
    }
    private void btnCategoryUpdateClick(ActionEvent e) {
        Categories categories = fncCategoryDataValid();
        int selectedId = cat.categoryIdList().get(row);
        if (row!=-1){
            int answer=JOptionPane.showConfirmDialog(this,"Are you sure you want to update the category?","Update Window",JOptionPane.YES_OPTION);
            if (answer==0){
                cat.categoryUpdate(categories,selectedId);
                tblCategoryList.setModel(cat.categoriesTable());
                cmbSalesCategory.setModel(cat.comboBoxCategories());
                cmbCategory.setModel(cat.comboBoxCategories());
                txtCategoryName.setText("");
                txtDefinition.setText("");
                row=-1;
            }
        }else{
            JOptionPane.showMessageDialog(this,"Please choose.");
        }
    }

    private void btnCategoryDeleteClick(ActionEvent e) {
        int selectedId = cat.categoryIdList().get(row);
        if (row != -1) {
            int answer=JOptionPane.showConfirmDialog(this,"Are you sure you want to delete the category?","Delete Window",JOptionPane.YES_OPTION);
            if (answer==0) {
                cat.categoryDelete(selectedId);
                tblCategoryList.setModel(cat.categoriesTable());
                txtCategoryName.setText("");
                txtDefinition.setText("");
                cmbCategory.setModel(cat.comboBoxCategories());
                cmbSalesCategory.setModel(cat.comboBoxCategories());
                row = -1;

            }
        }else {
            JOptionPane.showMessageDialog(this,"Please choose.");
        }
    }
    private void tblCategoryListMouseClicked(MouseEvent e) {
        rowCategorySelect();
    }
    private void tblCategoryListKeyReleased(KeyEvent e) {
        rowCategorySelect();
    }
    private Customers fncDataValid(){
        String name=txtName.getText().trim();
        String surname=txtSurname.getText().trim();
        String phone=txtPhone.getText().trim();
        String email=txtEmail.getText().trim();
        String address=txtAddress.getText().trim();

        if (name.equals("")){
            lblError.setText("Name is Empty!!!");
            txtName.requestFocus();
        }else if (surname.equals("")){
            lblError.setText("Surname is Empty!!!");
            txtSurname.requestFocus();
        }else if (phone.equals("")){ //boşşa sıfırsa
            lblError.setText("Phone is Empty!!!");
            txtPhone.requestFocus();//imleç otomatik olarak passwworde gelicek
        }else if (email.equals("")){ //boşşa sıfırsa
            lblError.setText("Email is Empty!!!");
            txtEmail.requestFocus();//imleç otomatik olarak passwworde gelicek
        }
        else if (address.equals("")){ //boşşa sıfırsa
            lblError.setText("Adress is Empty!!!");
            txtAddress.requestFocus();//imleç otomatik olarak passwworde gelicek
        }else {
            lblError.setText("");
            Customers c = new Customers(0,name,surname,phone,email,address);
            return c;
        }
        return null; //olumsuz halinde

    }

    private void btnCustomerAddClick(ActionEvent e) {
        Customers c = fncDataValid();
        if(c!=null){
            int status = cus.customerInsert(c);
            if (status>0){
                System.out.println("Ekleme basarili");
                txtName.setText("");
                txtSurname.setText("");
                txtPhone.setText("");
                txtEmail.setText("");
                txtAddress.setText("");
                tblCustomer.setModel(cus.model() );
                tblOrderCustomers.setModel(cus.model());
            }
            else {
                if (status == -1){
                    lblError.setText("E-mail or Phone have already used");
                }
                else {
                    lblError.setText("Insert Error");
                }
            }
        }
    }

    void rowVal(){
        row = tblCustomer.getSelectedRow();
        String name = (String) tblCustomer.getValueAt(row, 1);
        String surname = (String) tblCustomer.getValueAt(row, 2);
        String phone = (String) tblCustomer.getValueAt(row, 3);
        String email = (String) tblCustomer.getValueAt(row, 4);
        String address = (String) tblCustomer.getValueAt(row, 5);

        txtName.setText(name);
        txtSurname.setText(surname);
        txtPhone.setText(phone);
        txtEmail.setText(email);
        txtAddress.setText(address);
    }
    private void tblCustomerMouseClicked(MouseEvent e) {
        rowVal();
    }

    private void tblCustomerKeyReleased(KeyEvent e) {
        rowVal();
    }
    int cid = 0;
    int column = 0;
    private void btnCustomerUpdateClick(ActionEvent e) {
        Customers c = fncDataValid();
        if(row != -1 ) {
            row = tblCustomer.getSelectedRow();
            cid = Integer.valueOf(tblCustomer.getModel().getValueAt(row,column).toString());
            int answer = JOptionPane.showConfirmDialog(this, "Guncellemek istediginizden emin misniz?", "Guncelleme islemi", JOptionPane.YES_NO_OPTION);
            if (answer == 0) {
                cus.customerUpdate(c,cid);
                tblCustomer.setModel(cus.model());
                tblOrderCustomers.setModel(cus.model());
                row = -1;
            }
        }else{
            JOptionPane.showMessageDialog(this, "Lutfen secim yapiniz.");
        }
    }

    private void btnCustomerDeleteClick(ActionEvent e) {
        if(row != -1 ) {
            row = tblCustomer.getSelectedRow();
            cid = Integer.valueOf(tblCustomer.getModel().getValueAt(row,column).toString());
            int answer = JOptionPane.showConfirmDialog(this, "Silmek istediginizden emin miisniz?", "Silme islemi", JOptionPane.YES_NO_OPTION);
            if(answer==0){
                cus.customerDelete(cid);
                tblCustomer.setModel(cus.model());
                row = -1;
                tblOrderCustomers.setModel(cus.model());
            }
        } else{
            JOptionPane.showMessageDialog(this, "Lutfen secim yapiniz.");
        }
    }

    private void txtCustomerSearchKeyReleased(KeyEvent e) {
        String txtSearch = txtCustomerSearch.getText().trim();
        tblOrderCustomers.setModel(order.orderCustomersTable(txtSearch));
    }
    public void rowChosenProduct() {
        int column = 0;
        row =tblOrderProducts.getSelectedRow();

        String productName = String.valueOf(tblOrderProducts.getValueAt(row,2));

        txtProduct.setText(productName);

    }

    public void rowChosenCustomer() {
        int column = 0;
        rowCustomer=tblOrderCustomers.getSelectedRow();

        String customerName = String.valueOf(tblOrderCustomers.getValueAt(rowCustomer,1));
        String customerSurname = String.valueOf(tblOrderCustomers.getValueAt(rowCustomer,2));
        txtOrderCustomer.setText(customerName+ " " + customerSurname);
    }

    private void tblOrderProductsKeyReleased(KeyEvent e) {
        rowChosenProduct();
    }

    private void tblOrderProductsMouseClicked(MouseEvent e) {
        rowChosenProduct();
    }

    private void tblOrderCustomersKeyReleased(KeyEvent e) {
        rowChosenCustomer();
    }

    private void tblOrderCustomersMouseClicked(MouseEvent e) {
        rowChosenCustomer();
    }

    private void btnList(ActionEvent e) {
        int selectedId = cat.categoryIdList().get(cmbSalesCategory.getSelectedIndex());
        tblOrderProducts.setModel(order.orderProductTable(selectedId));
    }

    private void btnGoToBasket(ActionEvent e) {
        BasketView basket = new BasketView();
        basket.setVisible(true);
    }
    public Basket fncBasketDataValid() {
        try {
            if (txtOrderCustomer.getText().equals("")){
                txtOrderCustomer.requestFocus();
                lblSalesError.setText("Customer Name Empty!");
            }else if (cmbSalesCategory.getSelectedItem().equals("")){
                lblSalesError.setText("Category Name Empty!");
            }else if (txtProduct.getText().equals("")){
                txtProduct.requestFocus();
                lblSalesError.setText("Product Empty!");
            }else if (txtAmount.getText().equals("")){
                txtAmount.requestFocus();
                lblSalesError.setText("Amount Empty!");
            }else {
                int ktid = cat.categoryIdList().get(cmbSalesCategory.getSelectedIndex());
                int cid = (int) tblOrderCustomers.getValueAt(rowCustomer,0);
                int pid = (int) tblOrderProducts.getValueAt(row,0);
                String uuid = String.valueOf(UUID.randomUUID());
                String date = utils.Util.dateTimeNow();
                int amount = Integer.parseInt(txtAmount.getText().toLowerCase(Locale.ROOT).trim());
                int status = 0;

               Basket basket = new Basket(0,cid,pid,ktid,uuid,date,amount,status);
                return basket;
            }
        }catch (Exception ex) {
            System.err.println("fncProductDataValid Error : " +ex);
        }
        return null;
    }

    private void btnAddBasket(ActionEvent e) {
        Basket basket = fncBasketDataValid();
        rowStock = tblOrderProducts.getSelectedRow();
        int selectedId = (int) tblOrderProducts.getValueAt(rowStock,0);
        int amount = Integer.parseInt(txtAmount.getText());
        boolean control = basketImpl.amountControl(selectedId,amount);
        if (!control) {
            JOptionPane.showMessageDialog(this,"Please reduce the amount");
        }else {
            if (basket != null ) {
                basketImpl.basketInsert(basket);
                JOptionPane.showMessageDialog(this,"Product added to basket");
            }else {
                lblProductError.setText("Insert Error!");
            }
        }
    }

    private void btnLogoutClick(ActionEvent e) {
        Login login = new Login();
        login.setVisible(true);
        dispose();
    }

    private void btnChangePassword(ActionEvent e) {
        new ChangePassword().setVisible(true);
        dispose();
    }








    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        btnLogout = new JButton();
        label1 = new JLabel();
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        panel17 = new JPanel();
        scrollPane4 = new JScrollPane();
        tblCustomer = new JTable();
        panel18 = new JPanel();
        label11 = new JLabel();
        txtName = new JTextField();
        label13 = new JLabel();
        txtAddress = new JTextField();
        label15 = new JLabel();
        txtSurname = new JTextField();
        label16 = new JLabel();
        txtPhone = new JTextField();
        btnCustomerDelete = new JButton();
        btnCustomerUpdate = new JButton();
        btnCustomerAdd = new JButton();
        lblError = new JLabel();
        label21 = new JLabel();
        txtEmail = new JTextField();
        panel7 = new JPanel();
        label2 = new JLabel();
        radioButton1 = new JRadioButton();
        radioButton2 = new JRadioButton();
        radioButton3 = new JRadioButton();
        textField1 = new JTextField();
        panel8 = new JPanel();
        scrollPane1 = new JScrollPane();
        tblBasketReport = new JTable();
        panel9 = new JPanel();
        panel10 = new JPanel();
        panel6 = new JPanel();
        panel12 = new JPanel();
        cmbSalesCategory = new JComboBox();
        label12 = new JLabel();
        btnList = new JButton();
        scrollPane5 = new JScrollPane();
        tblOrderProducts = new JTable();
        lblSalesError = new JLabel();
        label17 = new JLabel();
        txtProduct = new JTextField();
        label18 = new JLabel();
        label19 = new JLabel();
        txtAmount = new JTextField();
        btnAddBasket = new JButton();
        scrollPane6 = new JScrollPane();
        tblOrderCustomers = new JTable();
        txtCustomerSearch = new JTextField();
        label20 = new JLabel();
        label22 = new JLabel();
        txtOrderCustomer = new JTextField();
        btnGoToBasket = new JButton();
        panel3 = new JPanel();
        panel13 = new JPanel();
        scrollPane2 = new JScrollPane();
        tblProductManagement = new JTable();
        panel14 = new JPanel();
        label5 = new JLabel();
        txtProductName = new JTextField();
        label6 = new JLabel();
        cmbCategory = new JComboBox();
        label7 = new JLabel();
        txtPurchasePrice = new JTextField();
        label8 = new JLabel();
        txtSalePrice = new JTextField();
        label9 = new JLabel();
        txtStock = new JTextField();
        label10 = new JLabel();
        txtInfo = new JTextField();
        btnDeleteProduct = new JButton();
        btnUpdateProduct = new JButton();
        btnAddProduct = new JButton();
        lblProductError = new JLabel();
        panel5 = new JPanel();
        panel15 = new JPanel();
        scrollPane3 = new JScrollPane();
        tblCategoryList = new JTable();
        panel16 = new JPanel();
        lblCategoryName = new JLabel();
        lblDefinition = new JLabel();
        txtCategoryName = new JTextField();
        txtDefinition = new JTextField();
        btnUpdate2 = new JButton();
        btnDelete = new JButton();
        btnCategoryAdd = new JButton();
        lblCategoryError = new JLabel();
        button1 = new JButton();

        //======== this ========
        Container contentPane = getContentPane();

        //---- btnLogout ----
        btnLogout.setText("Log out");
        btnLogout.addActionListener(e -> btnLogoutClick(e));

        //---- label1 ----
        label1.setText("KEOS");

        //======== tabbedPane1 ========
        {

            //======== panel1 ========
            {

                //======== panel17 ========
                {
                    panel17.setBorder(new TitledBorder("Customer List"));

                    //======== scrollPane4 ========
                    {

                        //---- tblCustomer ----
                        tblCustomer.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                tblCustomerMouseClicked(e);
                            }
                        });
                        tblCustomer.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                tblCustomerKeyReleased(e);
                            }
                        });
                        scrollPane4.setViewportView(tblCustomer);
                    }

                    GroupLayout panel17Layout = new GroupLayout(panel17);
                    panel17.setLayout(panel17Layout);
                    panel17Layout.setHorizontalGroup(
                        panel17Layout.createParallelGroup()
                            .addGroup(panel17Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane4, GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                                .addContainerGap())
                    );
                    panel17Layout.setVerticalGroup(
                        panel17Layout.createParallelGroup()
                            .addGroup(panel17Layout.createSequentialGroup()
                                .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 12, Short.MAX_VALUE))
                    );
                }

                //======== panel18 ========
                {
                    panel18.setBorder(new TitledBorder("Add New Customer"));

                    //---- label11 ----
                    label11.setText("Name");

                    //---- label13 ----
                    label13.setText("Address");

                    //---- label15 ----
                    label15.setText("Surname");

                    //---- label16 ----
                    label16.setText("Phone");

                    //---- btnCustomerDelete ----
                    btnCustomerDelete.setText("Delete");
                    btnCustomerDelete.addActionListener(e -> btnCustomerDeleteClick(e));

                    //---- btnCustomerUpdate ----
                    btnCustomerUpdate.setText("Update");
                    btnCustomerUpdate.addActionListener(e -> btnCustomerUpdateClick(e));

                    //---- btnCustomerAdd ----
                    btnCustomerAdd.setText("Add");
                    btnCustomerAdd.addActionListener(e -> btnCustomerAddClick(e));

                    //---- lblError ----
                    lblError.setText("text");

                    //---- label21 ----
                    label21.setText("Email");

                    GroupLayout panel18Layout = new GroupLayout(panel18);
                    panel18.setLayout(panel18Layout);
                    panel18Layout.setHorizontalGroup(
                        panel18Layout.createParallelGroup()
                            .addGroup(panel18Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel18Layout.createParallelGroup()
                                    .addComponent(label11, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label13, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel18Layout.createParallelGroup()
                                    .addGroup(panel18Layout.createSequentialGroup()
                                        .addGroup(panel18Layout.createParallelGroup()
                                            .addComponent(txtAddress)
                                            .addComponent(txtName, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel18Layout.createParallelGroup()
                                            .addComponent(label15, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label16, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel18Layout.createParallelGroup()
                                            .addComponent(txtSurname, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                            .addComponent(txtPhone, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)))
                                    .addGroup(panel18Layout.createSequentialGroup()
                                        .addGap(90, 90, 90)
                                        .addGroup(panel18Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(label21)
                                            .addComponent(btnCustomerAdd))
                                        .addGroup(panel18Layout.createParallelGroup()
                                            .addGroup(panel18Layout.createSequentialGroup()
                                                .addGap(48, 48, 48)
                                                .addComponent(btnCustomerUpdate)
                                                .addGap(40, 40, 40)
                                                .addComponent(btnCustomerDelete))
                                            .addGroup(panel18Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 202, Short.MAX_VALUE)))
                                .addContainerGap())
                            .addGroup(panel18Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(lblError, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(557, Short.MAX_VALUE))
                    );
                    panel18Layout.setVerticalGroup(
                        panel18Layout.createParallelGroup()
                            .addGroup(panel18Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel18Layout.createParallelGroup()
                                    .addGroup(panel18Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label11)
                                        .addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel18Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label15)
                                        .addComponent(txtSurname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addGap(5, 5, 5)
                                .addGroup(panel18Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label13)
                                    .addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label16)
                                    .addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel18Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label21)
                                    .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addGroup(panel18Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnCustomerAdd)
                                    .addComponent(btnCustomerUpdate)
                                    .addComponent(btnCustomerDelete))
                                .addGap(18, 18, 18)
                                .addComponent(lblError)
                                .addContainerGap(17, Short.MAX_VALUE))
                    );
                }

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel1Layout.createParallelGroup()
                                .addComponent(panel17, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel18, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addContainerGap())
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(panel17, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(panel18, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addContainerGap())
                );
            }
            tabbedPane1.addTab("Customers", panel1);

            //======== panel7 ========
            {
                panel7.setBorder(new TitledBorder("Filter"));

                //---- label2 ----
                label2.setText("Search Area:");

                //---- radioButton1 ----
                radioButton1.setText("Customer");

                //---- radioButton2 ----
                radioButton2.setText("Product");

                //---- radioButton3 ----
                radioButton3.setText("Category");

                //======== panel8 ========
                {
                    panel8.setBorder(new TitledBorder("Result"));

                    //======== scrollPane1 ========
                    {
                        scrollPane1.setViewportView(tblBasketReport);
                    }

                    GroupLayout panel8Layout = new GroupLayout(panel8);
                    panel8.setLayout(panel8Layout);
                    panel8Layout.setHorizontalGroup(
                        panel8Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel8Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 668, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                    );
                    panel8Layout.setVerticalGroup(
                        panel8Layout.createParallelGroup()
                            .addGroup(panel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                                .addContainerGap())
                    );
                }

                //======== panel9 ========
                {
                    panel9.setBorder(new TitledBorder("PROFIT AND LOSS"));

                    GroupLayout panel9Layout = new GroupLayout(panel9);
                    panel9.setLayout(panel9Layout);
                    panel9Layout.setHorizontalGroup(
                        panel9Layout.createParallelGroup()
                            .addGap(0, 311, Short.MAX_VALUE)
                    );
                    panel9Layout.setVerticalGroup(
                        panel9Layout.createParallelGroup()
                            .addGap(0, 102, Short.MAX_VALUE)
                    );
                }

                //======== panel10 ========
                {
                    panel10.setBorder(new TitledBorder("Sold"));

                    GroupLayout panel10Layout = new GroupLayout(panel10);
                    panel10.setLayout(panel10Layout);
                    panel10Layout.setHorizontalGroup(
                        panel10Layout.createParallelGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                    );
                    panel10Layout.setVerticalGroup(
                        panel10Layout.createParallelGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                    );
                }

                GroupLayout panel7Layout = new GroupLayout(panel7);
                panel7.setLayout(panel7Layout);
                panel7Layout.setHorizontalGroup(
                    panel7Layout.createParallelGroup()
                        .addGroup(panel7Layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addGroup(panel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(panel7Layout.createSequentialGroup()
                                    .addComponent(label2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(radioButton1)
                                    .addGap(0, 0, 0)
                                    .addComponent(radioButton2)
                                    .addGap(18, 18, 18)
                                    .addComponent(radioButton3)
                                    .addGap(38, 38, 38)
                                    .addComponent(textField1))
                                .addGroup(panel7Layout.createSequentialGroup()
                                    .addComponent(panel9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(35, 35, 35)
                                    .addComponent(panel10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(panel8, GroupLayout.PREFERRED_SIZE, 689, GroupLayout.PREFERRED_SIZE))
                            .addGap(73, 73, 73))
                );
                panel7Layout.setVerticalGroup(
                    panel7Layout.createParallelGroup()
                        .addGroup(panel7Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label2)
                                .addComponent(radioButton1)
                                .addComponent(radioButton2)
                                .addComponent(radioButton3)
                                .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(panel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel7Layout.createParallelGroup()
                                .addGroup(panel7Layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(panel9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addComponent(panel10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addContainerGap())
                );
            }
            tabbedPane1.addTab("Reports", panel7);

            //======== panel6 ========
            {

                //======== panel12 ========
                {
                    panel12.setBorder(new TitledBorder("Sale Page"));

                    //---- label12 ----
                    label12.setText("Choose the Product Category to List");

                    //---- btnList ----
                    btnList.setText("List");
                    btnList.addActionListener(e -> btnList(e));

                    //======== scrollPane5 ========
                    {

                        //---- tblOrderProducts ----
                        tblOrderProducts.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                tblOrderProductsKeyReleased(e);
                            }
                        });
                        tblOrderProducts.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                tblOrderProductsMouseClicked(e);
                            }
                        });
                        scrollPane5.setViewportView(tblOrderProducts);
                    }

                    //---- lblSalesError ----
                    lblSalesError.setText("Choose from the list to sale");

                    //---- label17 ----
                    label17.setText("Chosen Product:");

                    //---- label18 ----
                    label18.setText("Amount:");

                    //---- label19 ----
                    label19.setText("Customers:");

                    //---- btnAddBasket ----
                    btnAddBasket.setText("Add to basket");
                    btnAddBasket.addActionListener(e -> btnAddBasket(e));

                    //======== scrollPane6 ========
                    {

                        //---- tblOrderCustomers ----
                        tblOrderCustomers.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                tblOrderCustomersKeyReleased(e);
                            }
                        });
                        tblOrderCustomers.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                tblOrderCustomersMouseClicked(e);
                            }
                        });
                        scrollPane6.setViewportView(tblOrderCustomers);
                    }

                    //---- txtCustomerSearch ----
                    txtCustomerSearch.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            txtCustomerSearchKeyReleased(e);
                        }
                    });

                    //---- label20 ----
                    label20.setText("Search:");

                    //---- label22 ----
                    label22.setText("Chosen Customer");

                    //---- btnGoToBasket ----
                    btnGoToBasket.setText("Go to Basket");
                    btnGoToBasket.addActionListener(e -> btnGoToBasket(e));

                    GroupLayout panel12Layout = new GroupLayout(panel12);
                    panel12.setLayout(panel12Layout);
                    panel12Layout.setHorizontalGroup(
                        panel12Layout.createParallelGroup()
                            .addGroup(panel12Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(panel12Layout.createParallelGroup()
                                    .addGroup(panel12Layout.createSequentialGroup()
                                        .addGroup(panel12Layout.createParallelGroup()
                                            .addComponent(cmbSalesCategory, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label12, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(btnList, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(panel12Layout.createSequentialGroup()
                                        .addGroup(panel12Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addGroup(GroupLayout.Alignment.LEADING, panel12Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addGroup(GroupLayout.Alignment.LEADING, panel12Layout.createSequentialGroup()
                                                    .addComponent(label19, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(scrollPane6, GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE))
                                                .addGroup(GroupLayout.Alignment.LEADING, panel12Layout.createSequentialGroup()
                                                    .addGroup(panel12Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(label18, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label17, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label22, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(panel12Layout.createParallelGroup()
                                                        .addGroup(panel12Layout.createSequentialGroup()
                                                            .addComponent(txtAmount, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                                                            .addGap(58, 58, 58)
                                                            .addComponent(label20, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                            .addComponent(txtCustomerSearch, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(panel12Layout.createSequentialGroup()
                                                            .addGroup(panel12Layout.createParallelGroup()
                                                                .addGroup(panel12Layout.createSequentialGroup()
                                                                    .addComponent(txtOrderCustomer, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                                                                    .addGap(34, 34, 34)
                                                                    .addComponent(btnAddBasket, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
                                                                    .addGap(18, 18, 18)
                                                                    .addComponent(btnGoToBasket, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(txtProduct, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
                                                            .addGap(0, 0, Short.MAX_VALUE)))))
                                            .addGroup(panel12Layout.createParallelGroup()
                                                .addComponent(lblSalesError, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 657, GroupLayout.PREFERRED_SIZE)))
                                        .addGap(63, 63, 63))))
                    );
                    panel12Layout.setVerticalGroup(
                        panel12Layout.createParallelGroup()
                            .addGroup(panel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panel12Layout.createSequentialGroup()
                                        .addComponent(cmbSalesCategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label12)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel12Layout.createParallelGroup()
                                    .addGroup(panel12Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addGroup(panel12Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(btnAddBasket, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnGoToBasket, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE))
                                    .addGroup(panel12Layout.createSequentialGroup()
                                        .addComponent(lblSalesError)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                        .addGroup(panel12Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label22)
                                            .addComponent(txtOrderCustomer, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel12Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label17)
                                            .addComponent(txtProduct, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
                                        .addGap(2, 2, 2)))
                                .addGroup(panel12Layout.createParallelGroup()
                                    .addGroup(panel12Layout.createSequentialGroup()
                                        .addGroup(panel12Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label18)
                                            .addComponent(txtCustomerSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label20))
                                        .addGap(13, 13, 13))
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel12Layout.createSequentialGroup()
                                        .addComponent(txtAmount, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(panel12Layout.createParallelGroup()
                                    .addGroup(panel12Layout.createSequentialGroup()
                                        .addComponent(label19)
                                        .addContainerGap(67, Short.MAX_VALUE))
                                    .addComponent(scrollPane6, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)))
                    );
                }

                GroupLayout panel6Layout = new GroupLayout(panel6);
                panel6.setLayout(panel6Layout);
                panel6Layout.setHorizontalGroup(
                    panel6Layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panel12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                );
                panel6Layout.setVerticalGroup(
                    panel6Layout.createParallelGroup()
                        .addComponent(panel12, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("Sales Management", panel6);

            //======== panel3 ========
            {

                //======== panel13 ========
                {
                    panel13.setBorder(new TitledBorder("Product List"));

                    //======== scrollPane2 ========
                    {

                        //---- tblProductManagement ----
                        tblProductManagement.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                tblProductManagementKeyReleased(e);
                            }
                        });
                        tblProductManagement.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                tblProductManagementMouseClicked(e);
                            }
                        });
                        scrollPane2.setViewportView(tblProductManagement);
                    }

                    GroupLayout panel13Layout = new GroupLayout(panel13);
                    panel13.setLayout(panel13Layout);
                    panel13Layout.setHorizontalGroup(
                        panel13Layout.createParallelGroup()
                            .addGroup(panel13Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                                .addContainerGap())
                    );
                    panel13Layout.setVerticalGroup(
                        panel13Layout.createParallelGroup()
                            .addGroup(panel13Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                }

                //======== panel14 ========
                {
                    panel14.setBorder(new TitledBorder("Add New Product"));

                    //---- label5 ----
                    label5.setText("Product Name");

                    //---- label6 ----
                    label6.setText("Category");

                    //---- label7 ----
                    label7.setText("Purchase Price");

                    //---- label8 ----
                    label8.setText("Sale Price");

                    //---- label9 ----
                    label9.setText("Stock");

                    //---- label10 ----
                    label10.setText("Info");

                    //---- btnDeleteProduct ----
                    btnDeleteProduct.setText("Delete");
                    btnDeleteProduct.addActionListener(e -> btnDeleteProductClick(e));

                    //---- btnUpdateProduct ----
                    btnUpdateProduct.setText("Update");
                    btnUpdateProduct.addActionListener(e -> btnUpdateProduct(e));

                    //---- btnAddProduct ----
                    btnAddProduct.setText("Add");
                    btnAddProduct.addActionListener(e -> btnAddProduct(e));

                    //---- lblProductError ----
                    lblProductError.setText(" ");
                    lblProductError.setBackground(Color.red);

                    GroupLayout panel14Layout = new GroupLayout(panel14);
                    panel14.setLayout(panel14Layout);
                    panel14Layout.setHorizontalGroup(
                        panel14Layout.createParallelGroup()
                            .addGroup(panel14Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel14Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(label8, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                    .addComponent(label7, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                    .addComponent(label6, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                    .addComponent(label5, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(panel14Layout.createParallelGroup()
                                    .addGroup(panel14Layout.createSequentialGroup()
                                        .addGroup(panel14Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtProductName, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                            .addComponent(cmbCategory, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                            .addComponent(txtPurchasePrice, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
                                        .addGap(33, 33, 33)
                                        .addGroup(panel14Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(label9, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                            .addComponent(label10, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(panel14Layout.createParallelGroup()
                                            .addComponent(txtStock, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                            .addComponent(txtInfo, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)))
                                    .addGroup(panel14Layout.createSequentialGroup()
                                        .addGroup(panel14Layout.createParallelGroup()
                                            .addComponent(txtSalePrice, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblProductError, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                                        .addComponent(btnAddProduct)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnUpdateProduct)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnDeleteProduct)))
                                .addContainerGap())
                    );
                    panel14Layout.setVerticalGroup(
                        panel14Layout.createParallelGroup()
                            .addGroup(panel14Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel14Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel14Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnDeleteProduct)
                                        .addComponent(btnUpdateProduct)
                                        .addComponent(btnAddProduct))
                                    .addGroup(panel14Layout.createSequentialGroup()
                                        .addGroup(panel14Layout.createParallelGroup()
                                            .addGroup(panel14Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label5)
                                                .addComponent(txtProductName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panel14Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label9)
                                                .addComponent(txtStock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addGroup(panel14Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label6)
                                            .addComponent(cmbCategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label10)
                                            .addComponent(txtInfo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(panel14Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label7)
                                            .addComponent(txtPurchasePrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panel14Layout.createParallelGroup()
                                            .addComponent(label8)
                                            .addComponent(txtSalePrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addComponent(lblProductError)
                                .addContainerGap(8, Short.MAX_VALUE))
                    );
                }

                GroupLayout panel3Layout = new GroupLayout(panel3);
                panel3.setLayout(panel3Layout);
                panel3Layout.setHorizontalGroup(
                    panel3Layout.createParallelGroup()
                        .addGroup(panel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel3Layout.createParallelGroup()
                                .addComponent(panel13, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel14, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addContainerGap())
                );
                panel3Layout.setVerticalGroup(
                    panel3Layout.createParallelGroup()
                        .addGroup(panel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(panel13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(panel14, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addContainerGap())
                );
            }
            tabbedPane1.addTab("Product Management", panel3);

            //======== panel5 ========
            {

                //======== panel15 ========
                {
                    panel15.setBorder(new TitledBorder("Category List"));

                    //======== scrollPane3 ========
                    {

                        //---- tblCategoryList ----
                        tblCategoryList.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                tblCategoryListKeyReleased(e);
                            }
                        });
                        tblCategoryList.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                tblCategoryListMouseClicked(e);
                            }
                        });
                        scrollPane3.setViewportView(tblCategoryList);
                    }

                    GroupLayout panel15Layout = new GroupLayout(panel15);
                    panel15.setLayout(panel15Layout);
                    panel15Layout.setHorizontalGroup(
                        panel15Layout.createParallelGroup()
                            .addComponent(scrollPane3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    );
                    panel15Layout.setVerticalGroup(
                        panel15Layout.createParallelGroup()
                            .addComponent(scrollPane3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    );
                }

                //======== panel16 ========
                {
                    panel16.setBorder(new TitledBorder("Category Operations"));

                    //---- lblCategoryName ----
                    lblCategoryName.setText("Category Name: ");

                    //---- lblDefinition ----
                    lblDefinition.setText("Definition:");

                    //---- btnUpdate2 ----
                    btnUpdate2.setText("Update");
                    btnUpdate2.addActionListener(e -> btnCategoryUpdateClick(e));

                    //---- btnDelete ----
                    btnDelete.setText("Delete");
                    btnDelete.addActionListener(e -> btnCategoryDeleteClick(e));

                    //---- btnCategoryAdd ----
                    btnCategoryAdd.setText("Add");
                    btnCategoryAdd.addActionListener(e -> btnCategoryAddClick(e));

                    GroupLayout panel16Layout = new GroupLayout(panel16);
                    panel16.setLayout(panel16Layout);
                    panel16Layout.setHorizontalGroup(
                        panel16Layout.createParallelGroup()
                            .addGroup(panel16Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(panel16Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel16Layout.createSequentialGroup()
                                        .addComponent(lblCategoryName, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCategoryName))
                                    .addGroup(panel16Layout.createSequentialGroup()
                                        .addGroup(panel16Layout.createParallelGroup()
                                            .addGroup(panel16Layout.createSequentialGroup()
                                                .addComponent(lblDefinition, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
                                                .addGap(28, 28, 28))
                                            .addGroup(GroupLayout.Alignment.TRAILING, panel16Layout.createSequentialGroup()
                                                .addComponent(btnCategoryAdd, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)))
                                        .addGroup(panel16Layout.createParallelGroup()
                                            .addGroup(panel16Layout.createSequentialGroup()
                                                .addComponent(btnUpdate2, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtDefinition))))
                                .addContainerGap())
                    );
                    panel16Layout.setVerticalGroup(
                        panel16Layout.createParallelGroup()
                            .addGroup(panel16Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(panel16Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCategoryName)
                                    .addComponent(txtCategoryName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel16Layout.createParallelGroup()
                                    .addComponent(lblDefinition)
                                    .addComponent(txtDefinition, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel16Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdate2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCategoryAdd, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                }

                //---- lblCategoryError ----
                lblCategoryError.setText("text");

                GroupLayout panel5Layout = new GroupLayout(panel5);
                panel5.setLayout(panel5Layout);
                panel5Layout.setHorizontalGroup(
                    panel5Layout.createParallelGroup()
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addComponent(panel15, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(panel16, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(33, 33, 33))
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addGap(223, 223, 223)
                            .addComponent(lblCategoryError, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(302, Short.MAX_VALUE))
                );
                panel5Layout.setVerticalGroup(
                    panel5Layout.createParallelGroup()
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addGap(115, 115, 115)
                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(panel16, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(40, 40, 40)
                            .addComponent(lblCategoryError)
                            .addContainerGap(82, Short.MAX_VALUE))
                );
            }
            tabbedPane1.addTab("Category Management", panel5);
        }

        //---- button1 ----
        button1.setText("Change Password");
        button1.addActionListener(e -> btnChangePassword(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(label1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 477, Short.MAX_VALUE)
                    .addComponent(button1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btnLogout)
                    .addGap(30, 30, 30))
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabbedPane1, GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLogout)
                        .addComponent(label1)
                        .addComponent(button1))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, 482, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(13, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(radioButton1);
        buttonGroup1.add(radioButton2);
        buttonGroup1.add(radioButton3);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton btnLogout;
    private JLabel label1;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel panel17;
    private JScrollPane scrollPane4;
    private JTable tblCustomer;
    private JPanel panel18;
    private JLabel label11;
    private JTextField txtName;
    private JLabel label13;
    private JTextField txtAddress;
    private JLabel label15;
    private JTextField txtSurname;
    private JLabel label16;
    private JTextField txtPhone;
    private JButton btnCustomerDelete;
    private JButton btnCustomerUpdate;
    private JButton btnCustomerAdd;
    private JLabel lblError;
    private JLabel label21;
    private JTextField txtEmail;
    private JPanel panel7;
    private JLabel label2;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JTextField textField1;
    private JPanel panel8;
    private JScrollPane scrollPane1;
    private JTable tblBasketReport;
    private JPanel panel9;
    private JPanel panel10;
    private JPanel panel6;
    private JPanel panel12;
    private JComboBox cmbSalesCategory;
    private JLabel label12;
    private JButton btnList;
    private JScrollPane scrollPane5;
    private JTable tblOrderProducts;
    private JLabel lblSalesError;
    private JLabel label17;
    private JTextField txtProduct;
    private JLabel label18;
    private JLabel label19;
    private JTextField txtAmount;
    private JButton btnAddBasket;
    private JScrollPane scrollPane6;
    private JTable tblOrderCustomers;
    private JTextField txtCustomerSearch;
    private JLabel label20;
    private JLabel label22;
    private JTextField txtOrderCustomer;
    private JButton btnGoToBasket;
    private JPanel panel3;
    private JPanel panel13;
    private JScrollPane scrollPane2;
    private JTable tblProductManagement;
    private JPanel panel14;
    private JLabel label5;
    private JTextField txtProductName;
    private JLabel label6;
    private JComboBox cmbCategory;
    private JLabel label7;
    private JTextField txtPurchasePrice;
    private JLabel label8;
    private JTextField txtSalePrice;
    private JLabel label9;
    private JTextField txtStock;
    private JLabel label10;
    private JTextField txtInfo;
    private JButton btnDeleteProduct;
    private JButton btnUpdateProduct;
    private JButton btnAddProduct;
    private JLabel lblProductError;
    private JPanel panel5;
    private JPanel panel15;
    private JScrollPane scrollPane3;
    private JTable tblCategoryList;
    private JPanel panel16;
    private JLabel lblCategoryName;
    private JLabel lblDefinition;
    private JTextField txtCategoryName;
    private JTextField txtDefinition;
    private JButton btnUpdate2;
    private JButton btnDelete;
    private JButton btnCategoryAdd;
    private JLabel lblCategoryError;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
