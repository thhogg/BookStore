package model;

public class OrderDetail {

    private int orderDetailID;
    private int orderID; // Tên cột là OrderID
    private int bookID; // Tên cột là BookID
    private int quantity; // Tên cột là Quantity
    private int unitPrice; // Tên cột là UnitPrice

    public OrderDetail() {
    }

    // Constructor hữu ích khi tạo chi tiết đơn hàng
    public OrderDetail(int orderID, int bookID, int quantity, int unitPrice) {
        this.orderID = orderID;
        this.bookID = bookID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Thêm đầy đủ Getter và Setter cho tất cả các trường
    // (Bấm chuột phải -> Insert Code -> Getter and Setter... trong NetBeans)

    public int getOrderDetailID() {
        return orderDetailID;
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

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }
    
    // ... (Thêm getter/setter cho các trường còn lại) ...

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
}