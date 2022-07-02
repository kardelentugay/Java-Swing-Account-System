package models;

import props.Customers;
import props.Products;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IOrder {
    List<Products> orderProductsList();
    DefaultTableModel orderProductsTable(int ktid);

    List<Products> orderSelectedProductList(int ktid);
    DefaultTableModel orderProductTable(int ktid);

    List<Customers> orderCustomersList();
    DefaultTableModel orderCustomersTable(String cData);



}
