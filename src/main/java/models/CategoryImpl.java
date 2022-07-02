package models;

import props.Categories;
import utils.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryImpl implements ICategory {
    DB db = new DB();


    @Override
    public int categoryInsert(Categories categories) {
        int status = 0;
        try {
            String sql= "insert into categories values(null, ?, ?)";
            PreparedStatement pre =db.connect().prepareStatement(sql);
            pre.setString(1, categories.getCt_name());
            pre.setString(2, categories.getCt_detail());
            status = pre.executeUpdate();
        }catch (Exception ex){
            System.err.println("categoriesInsert Error: " + ex);
            if (ex.toString().contains("UNIQUE")){
                status = -1;
            }
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public int categoryUpdate(Categories categories, int ktid) {
        int status = 0;
        try {
            String sql = "update categories set ct_name = ?, ct_detail = ? where ktid = ?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setString(1,categories.getCt_name());
            pre.setString(2, categories.getCt_detail());
            pre.setInt(3,ktid);
            status = pre.executeUpdate();
        }catch (Exception ex){
            System.err.println("categoriesUpdate Error: " + ex);
            if(ex.toString().contains("UNIQUE")){
                status = -1;
            }
        }finally {
            db.close();
        }
        return status;
    }


    @Override
    public int categoryDelete(int ktid) {
        int status = 0;
        try {
        String sql = "delete from categories where ktid = ?";
        PreparedStatement pre = db.connect().prepareStatement(sql);
        pre.setInt(1, ktid);
        status = pre.executeUpdate();
        }catch (Exception ex){
            System.out.println("categoriesDelete Error: " + ex);
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public List<Categories> categoryList() {
        List<Categories> cat = new ArrayList<>();
        try {
            String sql = "select * from categories";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            ResultSet rs =pre.executeQuery();
            while (rs.next()){
                int ktid= rs.getInt("ktid");
                String ct_name = rs.getString("ct_name");
                String ct_detail = rs.getString("ct_detail");
                Categories c = new Categories(ktid, ct_name, ct_detail);
                cat.add(c);
            }
        }catch (Exception ex){
            System.err.println("categoriesList Error" + ex);
        }finally {
            db.close();
        }
        return cat;
    }

    @Override
    public List<Integer> categoryIdList() {
        List<Integer> catId = new ArrayList<>();
        try {
            String sql = "select ktid from categories";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            ResultSet rs =pre.executeQuery();
            while (rs.next()){
                int ktid= rs.getInt("ktid");
                catId.add(ktid);
            }
        }catch (Exception ex){
            System.err.println("categoriesList Error" + ex);
        }finally {
            db.close();
        }
        return catId;
    }

    @Override
    public DefaultTableModel categoriesTable() {
        List<Categories> lsCategoriesTable = new ArrayList<>();
        DefaultTableModel categoriesTable = new DefaultTableModel();
        categoriesTable.addColumn("Category Name");
        categoriesTable.addColumn("Category Detail");
        lsCategoriesTable = categoryList();
        for (Categories item : lsCategoriesTable){
            Object[] row = {item.getCt_name(), item.getCt_detail()};
            categoriesTable.addRow(row);
        }
        return categoriesTable;
    }

    @Override
    public DefaultComboBoxModel<String> comboBoxCategories() {
        List<Categories> lsCategoriesCombo = new ArrayList<>();
        DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>();
        lsCategoriesCombo = categoryList();
        for (Categories item : lsCategoriesCombo){

            comboModel.addElement(String.valueOf(item.getCt_name()));
        }
        return comboModel;
    }

}
