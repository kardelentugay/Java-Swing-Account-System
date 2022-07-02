package models;

import props.Categories;
import props.Customers;
import props.Products;
import utils.DB;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderImpl implements IOrder {

    DB db = new DB();
    List<Products> lsp = new ArrayList<>();
    List<Customers> lsc = new ArrayList<>();
    List<Customers> lsSearch = new ArrayList<>();

    public OrderImpl() {
        lsc = orderCustomersList();
        lsSearch = lsc;
    }

    @Override
    public List<Products> orderProductsList() {
    List<Products> lsp = new ArrayList<>();
        return null;
    }


    @Override
    public DefaultTableModel orderProductsTable(int ktid) {
        return null;
    }

    @Override
    public List<Products> orderSelectedProductList(int ktid) {
        List<Products> productsList = new ArrayList<>();
        try {
            String sql = "select * from products p inner join categories c on p.ctid=c.ktid where ktid=?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1, ktid);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                int productNo = rs.getInt("pid");
                int katId = rs.getInt("ktid");
                String catName = rs.getString("ct_name");
                String productName = rs.getString("product_name");
                int purchasePrice = rs.getInt("purchase_price");
                int salePrice = rs.getInt("sale_price");
                int stock = rs.getInt("stock");
                String info = rs.getString("info");

                Categories categories = new Categories(catName);
                Products products = new Products(productNo,ktid,productName,purchasePrice,salePrice,stock,info,categories);
                productsList.add(products);

            }
        } catch (Exception ex) {
            System.err.println("orderSelectedProductList error");
        } finally {
            db.close();
        }
        return productsList;
    }

    @Override
    public DefaultTableModel orderProductTable(int ktid) {
        List<Products> lsProductsTable = new ArrayList<>();
        DefaultTableModel productManagementTable = new DefaultTableModel();
        productManagementTable.addColumn("Product No");
        productManagementTable.addColumn("Category Name");
        productManagementTable.addColumn("Product Name");
        productManagementTable.addColumn("Purchase Price");
        productManagementTable.addColumn("Sale Price");
        productManagementTable.addColumn("Stock");
        productManagementTable.addColumn("Info");
        lsProductsTable =orderSelectedProductList(ktid);

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


    @Override
    public List<Customers> orderCustomersList() {
        List<Customers> customersList = new ArrayList<>();
        try {
            String sql = "select * from customers order by cid desc";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            //customersList.clear();

            while (rs.next()) {
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");

                Customers customer = new Customers(cid, name, surname, phone, email, address);
                customersList.add(customer);

            }
        } catch (Exception ex) {
            System.err.println("serviceCustomerList Error: " + ex.toString());
            ex.printStackTrace();
        } finally {
            db.close();
        }
        return customersList;
    }

    @Override
    public DefaultTableModel orderCustomersTable(String cData) {
        List<Customers> customersList = new ArrayList<>();
        customersList = lsc;
        lsc = lsSearch;
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Cid");
        model.addColumn("Name");
        model.addColumn("Surname");
        model.addColumn("Phone");
        model.addColumn("Email");
        model.addColumn("Address");

        if (cData != null && !cData.equals("")) {
            cData = cData.toLowerCase(Locale.ROOT);
            List<Customers> csubLs = new ArrayList<>();
            for (Customers item : lsc) {
                if (item.getName().toLowerCase(Locale.ROOT).contains(cData)
                        || item.getSurname().toLowerCase(Locale.ROOT).contains(cData)
                        || item.getPhone().toLowerCase(Locale.ROOT).contains(cData)
                        || item.getEmail().toLowerCase(Locale.ROOT).contains(cData)
                        || item.getAddress().toLowerCase(Locale.ROOT).contains(cData)) {
                    csubLs.add(item);
                }

            }
            lsc = csubLs;
        }
        for (Customers item1 : lsc) {
            Object[] row = {item1.getCid(), item1.getName(), item1.getSurname(), item1.getPhone(), item1.getEmail(), item1.getAddress()};
            model.addRow(row);
        }
        return model;
    }
}





