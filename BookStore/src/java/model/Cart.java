package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    
    // Dùng Map để lưu trữ, với Key là BookID, Value là Item
    // Giúp tìm kiếm và cập nhật số lượng cực kỳ nhanh
    private Map<Integer, Item> items;

    public Cart() {
        items = new HashMap<>();
    }

    // =======================================================
    // HÀM addItem ĐÃ SỬA LẠI LOGIC
    // =======================================================
    /**
     * Thêm một Item vào giỏ hàng.
     * Tự động kiểm tra nếu sách đã tồn tại thì cộng dồn số lượng.
     */
    public void addItem(Item newItem) {
        int bookId = newItem.getBook().getBookID();

        // 1. Kiểm tra xem sách đã có trong giỏ hàng (Map) chưa
        if (items.containsKey(bookId)) {
            // 2. Nếu ĐÃ CÓ:
            // Lấy item cũ ra
            Item oldItem = items.get(bookId);
            
            // Cộng dồn số lượng
            int newQuantity = oldItem.getQuantity() + newItem.getQuantity();
            
            // Cập nhật lại số lượng cho item cũ
            oldItem.setQuantity(newQuantity);
            
        } else {
            // 3. Nếu CHƯA CÓ:
            // Thêm item mới vào giỏ (Map)
            items.put(bookId, newItem);
        }
    }

    /**
     * Xóa một item khỏi giỏ hàng
     */
    public void removeItem(int bookId) {
        if (items.containsKey(bookId)) {
            items.remove(bookId);
        }
    }
    
    /**
     * Lấy danh sách (List) các Item để JSP có thể lặp (forEach)
     */
    public List<Item> getItems() {
        // Chuyển đổi từ Map Values (các Item) sang một ArrayList
        return new ArrayList<>(items.values());
    }
    
    /**
     * Tính tổng tiền của toàn bộ giỏ hàng
     */
    public int getTotalMoney() {
        int total = 0;
        for (Item item : items.values()) {
            total += item.getTotalPrice();
        }
        return total;
    }
        public int getTotalQuantity() {
        int totalQuantity = 0;
        for (Item item : items.values()) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }
}