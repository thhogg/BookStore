package model;

/**
 * Model này (POJO) dùng để chứa kết quả thống kê
 * sách bán chạy nhất từ OrderDAO.
 */
public class BestSeller {
    
    private String title;
    private int totalSold;

    public BestSeller() {
    }

    public BestSeller(String title, int totalSold) {
        this.title = title;
        this.totalSold = totalSold;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public int getTotalSold() {
        return totalSold;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotalSold(int totalSold) {
        this.totalSold = totalSold;
    }
}