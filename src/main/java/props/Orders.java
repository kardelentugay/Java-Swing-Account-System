package props;

public class Orders {
    private int oid;
    private int cid;
    private int ctid;
    private int pid;
    private int piece;
    private String date;

    public Orders() {
    }

    public Orders(int oid, int cid, int ctid, int pid, int piece, String date) {
        this.oid = oid;
        this.cid = cid;
        this.ctid = ctid;
        this.pid = pid;
        this.piece = piece;
        this.date = date;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCtid() {
        return ctid;
    }

    public void setCtid(int ctid) {
        this.ctid = ctid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "oid=" + oid +
                ", cid=" + cid +
                ", ctid=" + ctid +
                ", pid=" + pid +
                ", piece=" + piece +
                ", date='" + date + '\'' +
                '}';
    }
}
