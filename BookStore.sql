-- =========================================
-- TẠO DATABASE BÁN SÁCH - BOOKSTOREDB
-- =========================================
IF DB_ID('BookStoreDB') IS NOT NULL
    DROP DATABASE BookStoreDB;
GO

CREATE DATABASE BookStoreDB;
GO
USE BookStoreDB;
GO

-- =========================================
-- BẢNG DANH MỤC SÁCH (CATEGORY)
-- =========================================
CREATE TABLE Category (
    CategoryID INT IDENTITY(1,1) PRIMARY KEY,
    CategoryName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(255)
);
GO

-- =========================================
-- BẢNG SÁCH (BOOK)
-- =========================================
CREATE TABLE Book (
    BookID INT IDENTITY(1,1) PRIMARY KEY,
    Title NVARCHAR(200) NOT NULL,
    Author NVARCHAR(100),
    Publisher NVARCHAR(100),
    CategoryID INT FOREIGN KEY REFERENCES Category(CategoryID),
    ISBN NVARCHAR(50),
    Price INT NOT NULL,
    Stock INT DEFAULT 0,
    [Description] NVARCHAR(MAX),
    ImageUrl NVARCHAR(255),
    CreatedAt DATETIME DEFAULT GETDATE()
);
GO

-- =========================================
-- BẢNG NGƯỜI DÙNG (USERS)
-- =========================================
CREATE TABLE Users (
    UserID INT IDENTITY(1,1) PRIMARY KEY,
	UserName NVARCHAR(255) NOT NULL,
	[Password] NVARCHAR(255) NOT NULL,
    FullName NVARCHAR(100) NOT NULL,
    Email NVARCHAR(100) UNIQUE NOT NULL, 
    Phone NVARCHAR(20),
    [Address] NVARCHAR(255),
    [Role] NVARCHAR(20) DEFAULT 'Customer', -- Admin / Customer
    CreatedAt DATETIME DEFAULT GETDATE()
);
GO

-- =========================================
-- BẢNG GIỎ HÀNG (CART)
-- =========================================
CREATE TABLE Cart (
    CartID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT FOREIGN KEY REFERENCES Users(UserID),
    CreatedAt DATETIME DEFAULT GETDATE()
);
GO

-- =========================================
--  BẢNG CHI TIẾT GIỎ HÀNG (CARTITEM)
-- =========================================
CREATE TABLE CartItem (
    CartItemID INT IDENTITY(1,1) PRIMARY KEY,
    CartID INT FOREIGN KEY REFERENCES Cart(CartID),
    BookID INT FOREIGN KEY REFERENCES Book(BookID),
    Quantity INT NOT NULL CHECK (Quantity > 0),
    Price INT NOT NULL
);
GO

-- =========================================
-- BẢNG ĐƠN HÀNG (ORDER)
-- =========================================
CREATE TABLE [Order] (
    OrderID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT FOREIGN KEY REFERENCES Users(UserID),
    OrderDate DATETIME DEFAULT GETDATE(),
    TotalAmount INT NOT NULL,
    PaymentMethod NVARCHAR(50),  -- COD, Bank, PayPal, etc.
    [Status] NVARCHAR(50) DEFAULT 'Pending', -- Pending, Paid, Shipped, Completed, Cancelled
    ShippingAddress NVARCHAR(255)
);
GO

-- =========================================
--  CHI TIẾT ĐƠN HÀNG (ORDERDETAIL)
-- =========================================
CREATE TABLE OrderDetail (
    OrderDetailID INT IDENTITY(1,1) PRIMARY KEY,
    OrderID INT FOREIGN KEY REFERENCES [Order](OrderID) ON DELETE CASCADE,
    BookID INT FOREIGN KEY REFERENCES Book(BookID),
    Quantity INT NOT NULL,
    UnitPrice INT NOT NULL
);
GO

-- =========================================
--  THANH TOÁN (PAYMENT)
-- =========================================
CREATE TABLE Payment (
    PaymentID INT IDENTITY(1,1) PRIMARY KEY,
    OrderID INT FOREIGN KEY REFERENCES [Order](OrderID),
    PaymentDate DATETIME DEFAULT GETDATE(),
    Amount INT,
    PaymentStatus NVARCHAR(50),  -- Paid / Failed / Refunded
    TransactionCode NVARCHAR(100)
);
GO

-- =========================================
-- DỮ LIỆU MẪU
-- =========================================

-- CATEGORY
INSERT INTO Category (CategoryName, Description)
VALUES
(N'Tiểu thuyết', N'Sách văn học, tiểu thuyết nổi tiếng'),
(N'Khoa học', N'Sách về khoa học và công nghệ'),
(N'Kinh tế', N'Sách tài chính, kinh doanh'),
(N'Thiếu nhi', N'Sách cho trẻ em'),
(N'Tâm lý - Kỹ năng sống', N'Sách phát triển bản thân');
GO

-- BOOK
INSERT INTO Book (Title, Author, Publisher, CategoryID, ISBN, Price, Stock, [Description], ImageUrl)
VALUES
(N'Harry Potter và Hòn đá Phù thủy', N'J.K. Rowling', N'NXB Trẻ', 1, '9786042101332', 120000, 30, N'Tập đầu tiên của series Harry Potter.', 'harry1.jpg'),
(N'Harry Potter và Phòng chứa Bí mật', N'J.K. Rowling', N'NXB Trẻ', 1, '9786042101349', 130000, 25, N'Tập 2 của Harry Potter.', 'harry2.jpg'),
(N'Đắc Nhân Tâm', N'Dale Carnegie', N'NXB Tổng hợp TP.HCM', 5, '9786045857113', 95000, 40, N'Sách kỹ năng sống nổi tiếng.', 'dnt.jpg'),
(N'Bố Già', N'Mario Puzo', N'NXB Văn học', 1, '9786042102346', 150000, 20, N'Tác phẩm kinh điển về mafia.', 'bo_gia.jpg'),
(N'Tư duy nhanh và chậm', N'Daniel Kahneman', N'NXB Thế Giới', 5, '9786047726196', 180000, 15, N'Sách tâm lý học kinh tế nổi tiếng.', 'fast_slow.jpg');
GO

-- USERS
INSERT INTO Users (UserName, [Password], FullName, Email, Phone, [Address], [Role])
VALUES
('admin', 'admin123', N'Quản trị viên', 'admin@bookstore.com', '0123456789', N'Hà Nội', 'Admin'),
('leominh', '123456', N'Lê Minh', 'leminh@gmail.com', '0987654321', N'TP. Hồ Chí Minh', 'Customer'),
('ngocanh', 'abc123', N'Ngọc Ánh', 'ngocanh@gmail.com', '0971122334', N'Đà Nẵng', 'Customer');
GO

-- CART
INSERT INTO Cart (UserID)
VALUES (2), (3);
GO

-- CART ITEM
INSERT INTO CartItem (CartID, BookID, Quantity, Price)
VALUES
(1, 1, 2, 120000),
(1, 3, 1, 95000),
(2, 4, 1, 150000);
GO

-- ORDER
INSERT INTO [Order] (UserID, TotalAmount, PaymentMethod, [Status], ShippingAddress)
VALUES
(2, 335000, 'COD', 'Paid', N'123 Đường Lê Lợi, TP.HCM'),
(3, 150000, 'Bank', 'Pending', N'45 Nguyễn Văn Linh, Đà Nẵng');
GO

-- ORDER DETAIL
INSERT INTO OrderDetail (OrderID, BookID, Quantity, UnitPrice)
VALUES
(1, 1, 2, 120000),
(1, 3, 1, 95000),
(2, 4, 1, 150000);
GO

-- PAYMENT
INSERT INTO Payment (OrderID, Amount, PaymentStatus, TransactionCode)
VALUES
(1, 335000, 'Paid', 'PAY2025103001'),
(2, 150000, 'Pending', 'PAY2025103002');
GO
