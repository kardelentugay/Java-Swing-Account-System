package models;

import props.Products;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IProduct {
    int productInsert(Products products);
    int productUpdate (Products products, int pid);
    int productDelete (int pid);
    List<Products> productList();
    DefaultTableModel productTable();

}
