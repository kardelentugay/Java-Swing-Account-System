package models;

import props.Basket;
import props.Categories;
import props.Customers;
import props.Products;
import utils.DB;
import views.BasketView;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BasketImpl implements IBasket {
    DB db = new DB();
    List<Basket> ls = new ArrayList<>();
    List<Customers> lsc = new ArrayList<>();
    List<Customers> lsSearch = new ArrayList<>();
    int customerId;

    public BasketImpl() {
        lsc = orderCustomersList();
        lsSearch = lsc;
    }


    @Override
    public int basketInsert(Basket basket) {
        int status=0;
        try{
            String sql="insert into basket values(null,?,?,?,?,?,?,?)";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,basket.getCid());
            pre.setInt(2,basket.getPid());
            pre.setInt(3,basket.getKtid());
            pre.setString(4,basket.getUuid());
            pre.setString(5,basket.getDate());
            pre.setInt(6,basket.getAmount());
            pre.setInt(7,1);
            status = pre.executeUpdate();
        }catch (Exception ex){
            System.err.println("basketInsert Error : " +ex);
        }
        finally {
            db.close();
        }
        return status;
    }

    @Override
    public int basketDelete(int bid) {
        int status=0;
        try{
            String sql="delete from basket where bid = ?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,bid);
            status = pre.executeUpdate();
        }catch (Exception ex){
            System.out.println("basketDelete Error : " +ex);
        }
        finally {
            db.close();
        }
        return status;
    }

    @Override
    public int basketComplete(int bid) {
        int status = 0;
        try{
            String sql=" update basket set status= ?  where bid=?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,2);
            pre.setInt(2,bid);
            status = pre.executeUpdate();
        }catch (Exception ex){
            System.out.println("basketComplete Error : " +ex);
        }
        finally {
            db.close();
        }
        return status;
    }

    @Override
    public List<Integer> StockList() {
        List<Integer> stockList = new ArrayList<>();
        try {
            String sql = "select stock from products";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            ResultSet rs =pre.executeQuery();
            while (rs.next()){
                int stock= rs.getInt("stock");
                stockList.add(stock);
            }
        }catch (Exception ex){
            System.err.println("categoriesList Error" + ex);
        }finally {
            db.close();
        }
        return stockList;
    }


    @Override
    public int setAmount(int result,String product_name) {
        int status = 0;
        try{
            String sql=" update products set stock= ?  where product_name=?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,result);
            pre.setString(2,product_name);
            status = pre.executeUpdate();
        }catch (Exception ex){
            System.out.println("amountSet Error : " +ex);
        }
        finally {
            db.close();
        }
        return status;
    }





    @Override
    public List<Basket> basketList(int cid) {
        List<Basket> basketList = new ArrayList<>();
        try {
            String sql = "select bid, c.cid, c.name, p.pid, product_name, cat.ktid, ct_name, uuid, date, amount, status " +
                    "from basket inner join customers c on  basket.cid = c.cid inner join products p " +
                    "on  basket.pid = p.pid inner join categories cat on  basket.ktid = cat.ktid where basket.cid=? and status=1 order by bid desc";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,cid);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int bid=rs.getInt("bid");
                int cId=rs.getInt("cid");
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
    public DefaultTableModel basketTableModel(int cid) {
        List<Basket> basketLs = new ArrayList<>();
        DefaultTableModel md = new DefaultTableModel();
        md.addColumn("Basket No");
        md.addColumn("CustomerName");
        md.addColumn("ProductName");
        md.addColumn("CategoryName");
        md.addColumn("Date");
        md.addColumn("Amount");
        md.addColumn("Status");
        basketLs = basketList(cid);

        for (Basket item : basketLs) {
            Object[] row = {item.getBid(), item.getCustomer().getName(), item.getProduct().getProduct_name(),item.getCategory().getCt_name(),
                    item.getDate(), item.getAmount(),item.getStatus()};
            md.addRow(row);
        }
        return md;
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

    @Override
    public boolean amountControl(int pid, int amount) {

        try{
            String sql="select stock from products where pid =  ? ";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setInt(1,pid);
            ResultSet rs=pre.executeQuery();
            if(rs.next()) {
                int stock = rs.getInt("stock");
                if(amount > stock){
                    return false;
                }
            }
        }catch (Exception ex){
            System.out.println(ex);
        }
        finally {
            db.close();
        }
        return true;
    }
}
