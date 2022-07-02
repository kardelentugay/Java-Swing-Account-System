package models;

import props.Customers;
import utils.DB;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerImpl implements ICustomer{
    DB db = new DB();
    List<Customers> cust = customerList();
    @Override
    public int customerInsert(Customers customer) {
        int status = 0;
        try {
            String sql = " insert into customers values(null, ?, ?, ?, ?, ?) ";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setString(1,customer.getName());
            pre.setString(2,customer.getSurname());
            pre.setString(4,customer.getPhone());
            pre.setString(3,customer.getEmail());
            pre.setString(5,customer.getAddress());
            status = pre.executeUpdate();

        }
        catch (Exception ex){
            System.err.println("customerInsert Error: " + ex);
            if (ex.toString().contains("UNIQUE")){
                status = -1;
            }
        }finally {
            db.close();
        }
        return status;
    }
    public DefaultTableModel model() {
        DefaultTableModel md = new DefaultTableModel();
        //add column
        md.addColumn("cId");
        md.addColumn("Name");
        md.addColumn("Surname");
        md.addColumn("Phone");
        md.addColumn("Email");
        md.addColumn("Address");

        cust = customerList();
        for (Customers item : cust) {
            Object[] row = {item.getCid(),item.getName(), item.getSurname(), item.getPhone(), item.getEmail() ,item.getAddress()};
            md.addRow(row);
        }

        return md;
    }


    @Override
    public int customerDelete(int cId) {
        int status = 0;
        try {
            String sql = " delete from customers where cid=? ";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,cId);
            status = pre.executeUpdate();

        }
        catch (Exception ex){
            System.err.println("customerDelete Error: " + ex);
        }finally {
            db.close();
        }
        return status;

    }


    @Override
    public int customerUpdate(Customers customer, int cId) {
        int status = 0;
        try {
            String sql = " update customers set name= ?, surname= ?, email= ?, phone = ?, address = ? where cid=?  ";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setString(1,customer.getName());
            pre.setString(2,customer.getSurname());
            pre.setString(4,customer.getPhone());
            pre.setString(3,customer.getEmail());
            pre.setString(5,customer.getAddress());
            pre.setInt(6,cId);
            status = pre.executeUpdate();

        }
        catch (Exception ex){
            System.err.println("customerUpdate Error: " + ex);
            if (ex.toString().contains("UNIQUE")){
                status = -1;
            }
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public List<Customers> customerList() {
        List<Customers> cust = new ArrayList<>();
        try {
            String sql = "select * from customers";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            ResultSet rs = pre.executeQuery();//satir ve sutun haline getirir. Iterator gibi calisir, tuketilince tekrar olusturmak gerekir.
            while (rs.next()){//son elemana kadar true doner, ilgili satir
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("phone");
                String phone = rs.getString("email");
                String address = rs.getString("address");

                Customers c = new Customers(cid,name,surname,phone,email,address);
                cust.add(c);
            }
        }catch (Exception ex){
            System.err.println("customerList error" + ex );
        }
        finally {
            db.close();
        }
        return cust;
    }


    @Override
    public List<Customers> customerSearch(String data) {
        return null;
    }
}
