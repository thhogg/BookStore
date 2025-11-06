package model;

public class OrderDetail {

    private int orderDetailID;
    private int orderID;
    private int bookID;
    private int quantity;
    private int unitPrice;

    public OrderDetail() {
    }

    // SỬA LỖI: Thêm "orderDetailID" vào constructor
    public OrderDetail(int orderDetailID, int orderID, int bookID, int quantity, int unitPrice) {
        this.orderDetailID = orderDetailID;
        this.orderID = orderID;
        this.bookID = bookID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    // Constructor 4 tham số (dùng khi TẠO MỚI, chưa có ID)
    // (Chúng ta có thể giữ lại)
    public OrderDetail(int orderID, int bookID, int quantity, int unitPrice) {
        this.orderID = orderID;
        this.bookID = bookID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }


    // --- Getter và Setter (Đầy đủ) ---

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
}