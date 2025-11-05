package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    
    // Danh sách các mặt hàng (Item) trong giỏ
    private List<Item> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    /**
     * Lấy một Item từ giỏ hàng dựa trên BookID.
     * @param bookId ID của sách.
     * @return Trả về Item nếu tìm thấy, ngược lại trả về null.
     */
    private Item getItemByBookId(int bookId) {
        for (Item item : items) {
            if (item.getBook().getBookID() == bookId) {
                return item;
            }
        }
        return null;
    }

    /**
     * Thêm một mặt hàng (Item) vào giỏ hàng.
     * Logic:
     * - Nếu sách đã có trong giỏ -> tăng số lượng.
     * - Nếu sách chưa có trong giỏ -> thêm mới vào danh sách.
     * @param newItem Item mới cần thêm.
     */
    public void addItem(Item newItem) {
        // Kiểm tra xem item (sách) đã có trong giỏ hàng chưa
        Item existingItem = getItemByBookId(newItem.getBook().getBookID());

        if (existingItem != null) {
            // Nếu đã có -> Cộng dồn số lượng
            int newQuantity = existingItem.getQuantity() + newItem.getQuantity();
            existingItem.setQuantity(newQuantity);
        } else {
            // Nếu chưa có -> Thêm item mới vào danh sách
            items.add(newItem);
        }
    }

    /**
     * Xóa một mặt hàng khỏi giỏ hàng dựa trên BookID.
     * @param bookId ID của sách cần xóa.
     */
    public void removeItem(int bookId) {
        Item item = getItemByBookId(bookId);
        if (item != null) {
            items.remove(item);
        }
    }

    /**
     * Tính tổng tiền của toàn bộ giỏ hàng.
     * @return Tổng tiền.
     */
    public double getTotalMoney() {
        double total = 0;
        for (Item item : items) {
            total += item.getTotalPrice(); // Dùng hàm getTotalPrice() của Item
        }
        return total;
    }
}