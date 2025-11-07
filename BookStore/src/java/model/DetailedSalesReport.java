package model;

import java.sql.Timestamp;

/**
 * Model này (POJO) dùng để chứa dữ liệu báo cáo chi tiết
 * được JOIN từ 4 bảng: Order, OrderDetail, Users, Book.
 */
public class DetailedSalesReport {
    
    // Từ Order
    private int orderID;
    private Timestamp orderDate;
    private int orderTotalAmount; // Tổng tiền của *cả* đơn hàng
    private String status;
    private String userName; // Từ Order (hoặc Users)

    // Từ Users
    private String customerFullName;
    
    // Từ Book
    private String bookTitle;
    
    // Từ OrderDetail
    private int quantity;
    private int unitPrice; // Giá bán của 1 cuốn tại thời điểm mua

    // Constructor đầy đủ
    public DetailedSalesReport(int orderID, Timestamp orderDate, int orderTotalAmount, String status, String userName,
            String customerFullName, String bookTitle, int quantity, int unitPrice) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.orderTotalAmount = orderTotalAmount;
        this.status = status;
        this.userName = userName;
        this.customerFullName = customerFullName;
        this.bookTitle = bookTitle;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters (Bạn có thể thêm Setters nếu cần)
    
    public int getOrderID() {
        return orderID;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public int getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public String getStatus() {
        return status;
    }

    public String getUserName() {
        return userName;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }
}