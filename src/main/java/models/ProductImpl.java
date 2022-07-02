package models;

import props.Categories;
import props.Products;
import utils.DB;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductImpl implements IProduct {

    DB db = new DB();
    List<Products> lsProducts = new ArrayList<>();
    public ProductImpl() {
        //lsProducts = productList();
    }

    @Override
    public int productInsert(Products products) {
        int status = 0;
        try {
            String sql = "insert into products values(null,?,?,?,?,?,?)";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,products.getCtid());
            pre.setString(2, products.getProduct_name());
            pre.setInt(3,products.getPurchase_price());
            pre.setInt(4,products.getSale_price());
            pre.setInt(5,products.getStock());
            pre.setString(6, products.getInfo());
            status = pre.executeUpdate();
        }catch (Exception ex){
            System.err.println("productInsert Error : " +ex);
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public int productUpdate(Products products,int pid) {
        int status = 0;
        try {
            String sql = "update products set ctid=?, product_name=?, purchase_price=?, sale_price=?, stock=?, info=? where pid=?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,products.getCtid());
            pre.setString(2, products.getProduct_name());
            pre.setInt(3,products.getPurchase_price());
            pre.setInt(4,products.getSale_price());
            pre.setInt(5,products.getStock());
            pre.setString(6, products.getInfo());
            pre.setInt(7,pid);
            status = pre.executeUpdate();

        }catch (Exception ex) {
            System.err.println("productUpdate Error : " +ex);
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public int productDelete(int pid) {
        int status = 0;
        try {
            String sql = "delete from products where pid=?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,pid);
            status = pre.executeUpdate();
        }catch (Exception ex) {
            System.err.println("productDeleteDelete Error : " +ex);
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public List<Products> productList() {
        List<Products> productManagementList = new ArrayList<>();
        try {
            String sql = "select pid, ktid, ct_name, product_name, purchase_price, sale_price, stock, info from products p" +
                    "inner join categories c on  ktid = ctid order by pid desc";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int pid=rs.getInt("Pid");
                int ktid=rs.getInt("ktid");
                String ct_name= rs.getString("ct_name");
                String product_name=rs.getString("product_name");
                int purchase_price=rs.getInt("purchase_price");
                int sale_price=rs.getInt("sale_price");
                int stock=rs.getInt("stock");
                String info= rs.getString("info");
                Categories categories = new Categories(ct_name);
                Products products = new Products(pid,ktid,product_name,purchase_price,sale_price,stock,info,categories);
                productManagementList.add(products);
            }
        }catch (Exception ex) {
            System.err.println("productList Error : " +ex);
        }finally {
            db.close();
        }
        return productManagementList;
    }

    @Override
    public DefaultTableModel productTable() {
        List<Products> lsProductsTable = new ArrayList<>();
        DefaultTableModel productManagementTable = new DefaultTableModel();
        productManagementTable.addColumn("Product No");
        productManagementTable.addColumn("Category Name");
        productManagementTable.addColumn("Product Name");
        productManagementTable.addColumn("Purchase Price");
        productManagementTable.addColumn("Sale Price");
        productManagementTable.addColumn("Stock");
        productManagementTable.addColumn("Info");
        lsProductsTable =productList();

        for (Products item : lsProductsTable) {
            Object[] row ={item.getPid(),
                    item.getCategories().getCt_name(),
                    item.getProduct_name(),
                    item.getPurchase_price(),
                    item.getSale_price(),
                    item.getStock(),
                    item.getInfo()};
            productManagementTable.addRow(row);
        }

        return productManagementTable;
    }
}
