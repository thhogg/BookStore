package model;

/**
 * Model này dùng để chứa dữ liệu xếp hạng khách hàng cho Dashboard
 */
public class CustomerRanking {
    private String userName;
    private long totalSpent; // Tổng số tiền đã chi tiêu
    private int orderCount; // Tổng số đơn đã mua

    public CustomerRanking(String userName, long totalSpent, int orderCount) {
        this.userName = userName;
        this.totalSpent = totalSpent;
        this.orderCount = orderCount;
    }

    // (Thêm đầy đủ Getter và Setter)
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(long totalSpent) {
        this.totalSpent = totalSpent;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}