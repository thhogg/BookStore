package model;

public class Item {
    private Book book;
    private int quantity;
    
    // totalPrice được tính toán, không cần lưu
    // private int totalPrice; 

    public Item() {
    }

    public Item(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    // Hàm này tự động tính tổng tiền cho 1 item
    public int getTotalPrice() {
        return book.getPrice() * quantity;
    }
}