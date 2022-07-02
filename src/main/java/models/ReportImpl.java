package models;

import props.*;
import utils.DB;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportImpl implements IReport {
    DB db = new DB();
    @Override
    public List<Orders> ReportOrderList() {
        List<Orders> orders = new ArrayList<>();
        try {
            String sql = "select cid,ktid,pid,name,ct_name,product_name,piece,date from orders " +
                    "inner join products on orders.pid = products.pid " +
                    "inner join categories on categories.ktid = products.ctid" +
                    "inner join customers on customers.cid = products.cid ";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            ResultSet rs = pre.executeQuery();//satir ve sutun haline getirir. Iterator gibi calisir, tuketilince tekrar olusturmak gerekir.
            while (rs.next()){//son elemana kadar true doner, ilgili satir.
                int cid = rs.getInt("cid");
                int ktid = rs.getInt("ktid");
                int pid = rs.getInt("cid");
                String name = rs.getString("name");
                String ct_name = rs.getString("ct_name");
                String product_name = rs.getString("product_name");
                int piece = rs.getInt("piece");
                String date = rs.getString("date");
                /*Customer c = new Customer(cid,name,surname,email,phone);
                Service s = new Service(sid,cid,title,date,status,c);
                service.add(s);*/
            }
        }catch (Exception ex){
            System.err.println("ReportOrderList error" + ex );
        }
        finally {
            db.close();
        }
        return orders;
    }

    @Override
    public DefaultTableModel reportOrderTable() {
        return null;
    }

    @Override
    public List<Basket> basketList() {
        List<Basket> basketList = new ArrayList<>();
        try {
            String sql = "select bid, c.cid, c.name, p.pid, product_name, cat.ktid, ct_name, uuid, date, amount, status " +
                    "from basket inner join customers c on  basket.cid = c.cid inner join products p " +
                    "on  basket.pid = p.pid inner join categories cat on  basket.ktid = cat.ktid where status=2 order by bid desc";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int bid=rs.getInt("bid");
                int cid=rs.getInt("cid");
                String name= rs.getString("name");
                int pid=rs.getInt("pid");
                String product_name=rs.getString("product_name");
                int ktid=rs.getInt("ktid");
                String  ct_name=rs.getString("ct_name");
                String uuid=rs.getString("uuid");
                String date =rs.getString("date");
                int amount=rs.getInt("amount");
                int status=rs.getInt("status");
                Customers customers = new Customers(name);
                Products products = new Products(product_name);
                Categories categories = new Categories(ct_name);
                Basket basket = new Basket(bid,cid,pid,ktid, uuid, date, amount, status,  customers, products, categories);
                basketList.add(basket);
            }}catch (Exception ex) {
            System.err.println("basketList Error : " +ex);
        }
        return basketList;
    }

    @Override
    public DefaultTableModel basketTableModel() {
        List<Basket> basketLs = new ArrayList<>();
        DefaultTableModel md = new DefaultTableModel();
        md.addColumn("Basket No");
        md.addColumn("CustomerName");
        md.addColumn("ProductName");
        md.addColumn("CategoryName");
        md.addColumn("Date");
        md.addColumn("Amount");
        md.addColumn("Status");
        basketLs = basketList();

        for (Basket item : basketLs) {
            Object[] row = {item.getBid(), item.getCustomer().getName(), item.getProduct().getProduct_name(),item.getCategory().getCt_name(),
                    item.getDate(), item.getAmount(),item.getStatus()};
            md.addRow(row);
        }
        return md;
    }
}

