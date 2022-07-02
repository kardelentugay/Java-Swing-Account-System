package models;

import props.Basket;
import props.Customers;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IBasket {
    int basketInsert(Basket basket);
    int basketDelete(int bid);
    int basketComplete(int cid);
    int setAmount(int result, String product_name);
    List<Integer> StockList();
    List<Basket> basketList(int cid);
    DefaultTableModel basketTableModel(int cid);


    List<Customers> orderCustomersList();
    DefaultTableModel orderCustomersTable(String cData);
    boolean amountControl(int pid, int amount);
}
