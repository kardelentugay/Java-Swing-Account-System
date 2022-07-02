package models;

import props.Basket;
import props.Orders;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IReport {
    List <Orders> ReportOrderList();
    DefaultTableModel reportOrderTable();
    List<Basket> basketList();
    DefaultTableModel basketTableModel();
}
