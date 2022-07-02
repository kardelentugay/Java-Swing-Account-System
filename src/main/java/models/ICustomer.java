package models;

import props.Customers;

import java.util.List;

public interface ICustomer {
    int customerInsert(Customers customer);
    //odev
    int customerDelete(int cId);
    int  customerUpdate(Customers customer, int cId);
    List<Customers> customerList();
    List<Customers> customerSearch(String data);//ctrl f gibi her sutunu arar or like or like..

}
