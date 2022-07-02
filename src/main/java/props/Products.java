package props;

public class Products {
    private int pid;
    private int ctid;
    private String product_name;
    private int purchase_price;
    private int sale_price;
    private int stock;
    private String info;
    private Categories categories;

    public Products(int pid, int ctid, String product_name, int purchase_price, int sale_price, int stock, String info, Categories categories) {
        this.pid = pid;
        this.ctid = ctid;
        this.product_name = product_name;
        this.purchase_price = purchase_price;
        this.sale_price = sale_price;
        this.stock = stock;
        this.info = info;
        this.categories = categories;
    }
    public Products(String product_name) {
        this.product_name = product_name;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public Products() {
    }

    public Products(int pid, int ctid, String product_name, int purchase_price, int sale_price, int stock, String info) {
        this.pid = pid;
        this.ctid = ctid;
        this.product_name = product_name;
        this.purchase_price = purchase_price;
        this.sale_price = sale_price;
        this.stock = stock;
        this.info = info;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCtid() {
        return ctid;
    }

    public void setCtid(int ctid) {
        this.ctid = ctid;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(int purchase_price) {
        this.purchase_price = purchase_price;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Products{" +
                "pid=" + pid +
                ", ctid=" + ctid +
                ", product_name='" + product_name + '\'' +
                ", purchase_price=" + purchase_price +
                ", sale_price=" + sale_price +
                ", stock=" + stock +
                ", info='" + info + '\'' +
                '}';
    }
}
