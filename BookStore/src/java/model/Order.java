package model;

import java.sql.Date; // Hoặc java.time.LocalDateTime nếu bạn dùng Java 8+

public class Order {

    private int orderID;
    private int userID; // Tên cột là UserID
    private Date orderDate; // Tên cột là OrderDate
    private int totalAmount; // Tên cột là TotalAmount
    private String paymentMethod; // Tên cột là PaymentMethod
    private String status; // Tên cột là Status
    private String shippingAddress; // Tên cột là ShippingAddress

    public Order() {
    }

    // Constructor hữu ích khi tạo đơn hàng mới
    public Order(int userID, Date orderDate, int totalAmount, String paymentMethod, String status, String shippingAddress) {
        this.userID = userID;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    // Thêm đầy đủ Getter và Setter cho tất cả các trường
    // (Bấm chuột phải -> Insert Code -> Getter and Setter... trong NetBeans)

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    // ... (Thêm getter/setter cho các trường còn lại) ...

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}