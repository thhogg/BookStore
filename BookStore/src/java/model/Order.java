package model;

// 1. Thay đổi quan trọng: Dùng Timestamp thay vì Date
// Vì DATETIME trong SQL lưu cả ngày và giờ, java.sql.Date chỉ lưu ngày.
import java.sql.Timestamp; 

public class Order {

    private int orderID;
    
    // 2. Thay đổi: từ int userID sang String userName
    private String userName; 
    
    private Timestamp orderDate; // 1. Đã đổi sang Timestamp
    private int totalAmount;
    private String paymentMethod;
    private String status;
    private String shippingAddress;

    public Order() {
    }

    // Constructor để đọc từ CSDL (bao gồm cả orderID)
    public Order(int orderID, String userName, Timestamp orderDate, int totalAmount, String paymentMethod, String status, String shippingAddress) {
        this.orderID = orderID;
        this.userName = userName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }
    
    // Constructor để tạo đơn hàng mới (chưa có orderID)
    public Order(String userName, Timestamp orderDate, int totalAmount, String paymentMethod, String status, String shippingAddress) {
        this.userName = userName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    // 3. Đã cập nhật Getter và Setter
    
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
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

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
}