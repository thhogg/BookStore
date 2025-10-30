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
-- BẢNG TÁC GIẢ (AUTHOR)
-- =========================================
CREATE TABLE Author (
    AuthorID INT IDENTITY(1,1) PRIMARY KEY,
    AuthorName NVARCHAR(100) NOT NULL,
    Bio NVARCHAR(500),
    ImageUrl NVARCHAR(255)
);
GO

-- =========================================
-- BẢNG NHÀ XUẤT BẢN (PUBLISHER)
-- =========================================
CREATE TABLE Publisher (
    PublisherID INT IDENTITY(1,1) PRIMARY KEY,
    PublisherName NVARCHAR(100) NOT NULL,
    [Address] NVARCHAR(255),
    Phone NVARCHAR(20)
);
GO

-- =========================================
-- BẢNG SÁCH (BOOK)
-- =========================================
CREATE TABLE Book (
    BookID INT IDENTITY(1,1) PRIMARY KEY,
    Title NVARCHAR(200) NOT NULL,
    AuthorID INT FOREIGN KEY REFERENCES Author(AuthorID),
    PublisherID INT FOREIGN KEY REFERENCES Publisher(PublisherID),
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
-- BẢNG NGƯỜI DÙNG (USERACCOUNT)
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
--  ĐÁNH GIÁ SÁCH (REVIEW)
-- =========================================
CREATE TABLE Review (
    ReviewID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT FOREIGN KEY REFERENCES Users(UserID),
    BookID INT FOREIGN KEY REFERENCES Book(BookID),
    Rating INT CHECK (Rating BETWEEN 1 AND 5),
    Comment NVARCHAR(500),
    CreatedAt DATETIME DEFAULT GETDATE()
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
-- DỮ LIỆU MẪU CHO CATEGORY
-- =========================================
INSERT INTO Category (CategoryName, Description)
VALUES
(N'Tiểu thuyết', N'Sách tiểu thuyết, truyện dài'),
(N'Khoa học', N'Sách khoa học phổ thông, nghiên cứu'),
(N'Thiếu nhi', N'Sách dành cho trẻ em'),
(N'Kinh tế', N'Sách kinh tế, quản trị, đầu tư'),
(N'Công nghệ', N'Sách về CNTT, lập trình, AI');
GO

-- =========================================
-- DỮ LIỆU MẪU CHO AUTHOR
-- =========================================
INSERT INTO Author (AuthorName, Bio, ImageUrl)
VALUES
(N'Nguyễn Nhật Ánh', N'Nhà văn nổi tiếng với các tác phẩm thiếu nhi.', 'https://example.com/author/anh.jpg'),
(N'Arthur Conan Doyle', N'Tác giả của Sherlock Holmes.', 'https://example.com/author/doyle.jpg'),
(N'Stephen Hawking', N'Nhà vật lý lý thuyết người Anh.', 'https://example.com/author/hawking.jpg'),
(N'Adam Smith', N'Nhà kinh tế học người Scotland.', 'https://example.com/author/smith.jpg'),
(N'Robert C. Martin', N'Tác giả của Clean Code và nhiều sách lập trình.', 'https://example.com/author/martin.jpg');
GO

-- =========================================
-- DỮ LIỆU MẪU CHO PUBLISHER
-- =========================================
INSERT INTO Publisher (PublisherName, [Address], Phone)
VALUES
(N'NXB Trẻ', N'161B Lý Chính Thắng, Q3, TP.HCM', '028-39316289'),
(N'NXB Kim Đồng', N'55 Quang Trung, Q.Hoàn Kiếm, Hà Nội', '024-38253812'),
(N'Oxford Press', N'Great Clarendon Street, Oxford, UK', '+44-1865-556767'),
(N'NXB Lao Động', N'175 Giảng Võ, Hà Nội', '024-38463549'),
(N'Pearson Education', N'221 River Street, Hoboken, NJ, USA', '+1-201-236-7000');
GO

-- =========================================
-- DỮ LIỆU MẪU CHO BOOK
-- =========================================
INSERT INTO Book (Title, AuthorID, PublisherID, CategoryID, ISBN, Price, Stock, [Description], ImageUrl)
VALUES
(N'Mắt Biếc', 1, 1, 1, 'ISBN001', 85000, 50, N'Tác phẩm nổi tiếng của Nguyễn Nhật Ánh.', 'https://example.com/book/matbiec.jpg'),
(N'Sherlock Holmes Toàn Tập', 2, 3, 1, 'ISBN002', 120000, 40, N'Truyện trinh thám cổ điển.', 'https://example.com/book/holmes.jpg'),
(N'Lược Sử Thời Gian', 3, 3, 2, 'ISBN003', 150000, 25, N'Sách khoa học nổi tiếng của Stephen Hawking.', 'https://example.com/book/time.jpg'),
(N'Wealth of Nations', 4, 3, 4, 'ISBN004', 200000, 30, N'Tác phẩm kinh điển về kinh tế học.', 'https://example.com/book/wealth.jpg'),
(N'Clean Code', 5, 5, 5, 'ISBN005', 300000, 15, N'Sách hướng dẫn viết code sạch.', 'https://example.com/book/cleancode.jpg');
GO

-- =========================================
-- DỮ LIỆU MẪU CHO USERS
-- =========================================
INSERT INTO Users (UserName, [Password], FullName, Email, Phone, [Address], [Role])
VALUES
('admin', '123456', N'Quản trị viên', 'admin@bookstore.com', '0909000000', N'Hà Nội', 'Admin'),
('minh123', '123456', N'Nguyễn Văn Minh', 'minh@gmail.com', '0909111222', N'TP.HCM', 'Customer'),
('linhnguyen', '123456', N'Linh Nguyễn', 'linh@gmail.com', '0909333444', N'Đà Nẵng', 'Customer'),
('thuyanh', '123456', N'Thúy Anh', 'anh@gmail.com', '0909777888', N'Cần Thơ', 'Customer');
GO

-- =========================================
-- DỮ LIỆU MẪU CHO CART
-- =========================================
INSERT INTO Cart (UserID)
VALUES (2), (3);
GO

-- =========================================
-- DỮ LIỆU MẪU CHO CARTITEM
-- =========================================
INSERT INTO CartItem (CartID, BookID, Quantity, Price)
VALUES
(1, 1, 2, 85000),
(1, 3, 1, 150000),
(2, 5, 1, 300000);
GO

-- =========================================
-- DỮ LIỆU MẪU CHO ORDER
-- =========================================
INSERT INTO [Order] (UserID, TotalAmount, PaymentMethod, [Status], ShippingAddress)
VALUES
(2, 320000, N'COD', N'Pending', N'TP.HCM'),
(3, 300000, N'Bank', N'Paid', N'Đà Nẵng');
GO

-- =========================================
-- DỮ LIỆU MẪU CHO ORDERDETAIL
-- =========================================
INSERT INTO OrderDetail (OrderID, BookID, Quantity, UnitPrice)
VALUES
(1, 1, 2, 85000),
(1, 3, 1, 150000),
(2, 5, 1, 300000);
GO

-- =========================================
-- DỮ LIỆU MẪU CHO REVIEW
-- =========================================
INSERT INTO Review (UserID, BookID, Rating, Comment)
VALUES
(2, 1, 5, N'Sách rất hay, cảm động!'),
(3, 5, 4, N'Rất hữu ích cho lập trình viên.'),
(4, 3, 5, N'Sách khoa học dễ hiểu, tuyệt vời!');
GO

-- =========================================
-- DỮ LIỆU MẪU CHO PAYMENT
-- =========================================
INSERT INTO Payment (OrderID, Amount, PaymentStatus, TransactionCode)
VALUES
(1, 320000, N'Pending', N'TXN001'),
(2, 300000, N'Paid', N'TXN002');
GO