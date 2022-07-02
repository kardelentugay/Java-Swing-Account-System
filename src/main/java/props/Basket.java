package props;

public class Basket {
    private int bid;
    private int cid;
    private int pid;
    private int ktid;
    private String uuid;
    private String date;
    private int amount;
    private int status;
    private Customers customer;
    private Products product;
    private Categories category;

    public Basket(){

    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Basket(int bid, int cid, int pid, int ktid, String uuid, String date, int amount, int status, Customers customer, Products product, Categories category) {
        this.bid = bid;
        this.cid = cid;
        this.pid = pid;
        this.ktid = ktid;
        this.uuid = uuid;
        this.date = date;
        this.amount = amount;
        this.status = status;
        this.customer = customer;
        this.product = product;
        this.category = category;
    }

    public Basket(int bid, int cid, int pid, int ktid, String uuid, String date, int amount, int status) {
        this.bid = bid;
        this.cid = cid;
        this.pid = pid;
        this.ktid = ktid;
        this.uuid = uuid;
        this.date = date;
        this.amount = amount;
        this.status = status;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getKtid() {
        return ktid;
    }

    public void setKtid(int ktid) {
        this.ktid = ktid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "bid=" + bid +
                ", cid=" + cid +
                ", pid=" + pid +
                ", ktid=" + ktid +
                ", uuid='" + uuid + '\'' +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}
