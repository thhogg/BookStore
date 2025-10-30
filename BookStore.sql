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
    FullName NVARCHAR(100) NOT NULL,
    Email NVARCHAR(100) UNIQUE NOT NULL,
    Password NVARCHAR(255) NOT NULL,
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
(N'Tiểu thuyết', N'Sách thuộc thể loại tiểu thuyết'),
(N'Kinh doanh', N'Sách về kỹ năng và tư duy kinh doanh'),
(N'Công nghệ', N'Sách lập trình, máy tính, AI'),
(N'Thiếu nhi', N'Sách cho trẻ em'),
(N'Khoa học', N'Sách về khám phá và khoa học hiện đại');
GO

-- =========================================
-- DỮ LIỆU MẪU CHO AUTHOR
-- =========================================
INSERT INTO Author (AuthorName, Bio, ImageUrl)
VALUES
(N'Nguyễn Nhật Ánh', N'Tác giả nổi tiếng với các tác phẩm về tuổi học trò.', 'nguyen-nhat-anh.jpg'),
(N'Dale Carnegie', N'Tác giả của các sách về giao tiếp và phát triển bản thân.', 'dale-carnegie.jpg'),
(N'Stephen King', N'Tác giả Mỹ nổi tiếng thể loại kinh dị.', 'stephen-king.jpg'),
(N'Elon Musk', N'Doanh nhân và kỹ sư công nghệ, chủ đề đổi mới sáng tạo.', 'elon-musk.jpg'),
(N'J.K. Rowling', N'Tác giả bộ truyện Harry Potter nổi tiếng.', 'jk-rowling.jpg');
GO

-- =========================================
-- DỮ LIỆU MẪU CHO PUBLISHER
-- =========================================
INSERT INTO Publisher (PublisherName, [Address], Phone)
VALUES
(N'NXB Trẻ', N'161B Lý Chính Thắng, Q3, TP.HCM', '028-3848-9889'),
(N'NXB Kim Đồng', N'55 Quang Trung, Hà Nội', '024-3822-7275'),
(N'NXB Lao Động', N'175 Giảng Võ, Hà Nội', '024-3736-1234'),
(N'NXB Alpha Books', N'176 Thái Hà, Hà Nội', '024-3514-6823');
GO

-- =========================================
-- DỮ LIỆU MẪU CHO BOOK
-- =========================================
INSERT INTO Book (Title, AuthorID, PublisherID, CategoryID, ISBN, Price, Stock, [Description], ImageUrl)
VALUES
(N'Mắt biếc', 1, 1, 1, '9786042101234', 95000, 30, N'Câu chuyện tình buồn của Ngạn và Hà Lan.', 'mat-biec.jpg'),
(N'Đắc nhân tâm', 2, 4, 2, '9786047751231', 120000, 50, N'Sách kinh điển về nghệ thuật giao tiếp.', 'dac-nhan-tam.jpg'),
(N'IT - Gã hề ma quái', 3, 3, 1, '9780451169518', 180000, 20, N'Tiểu thuyết kinh dị nổi tiếng của Stephen King.', 'it-ga-he.jpg'),
(N'Elon Musk: Tesla, SpaceX', 4, 4, 3, '9780062301253', 220000, 15, N'Tiểu sử đầy cảm hứng của Elon Musk.', 'elon-musk.jpg'),
(N'Harry Potter và Hòn đá Phù thủy', 5, 2, 1, '9780747532743', 150000, 40, N'Tập đầu tiên của bộ truyện Harry Potter.', 'harry-potter1.jpg');
GO

-- =========================================
-- DỮ LIỆU MẪU CHO USERS
-- =========================================
INSERT INTO Users (FullName, Email, Password, Phone, [Address], [Role])
VALUES
(N'Admin', 'admin@bookstore.com', 'admin123', '0123456789', N'Hà Nội', 'Admin'),
(N'Nguyễn Văn A', 'a@gmail.com', '123456', '0987654321', N'Hồ Chí Minh', 'Customer'),
(N'Lê Thị B', 'b@gmail.com', '123456', '0911222333', N'Đà Nẵng', 'Customer'),
(N'Trần Văn C', 'c@gmail.com', '123456', '0933444555', N'Cần Thơ', 'Customer');
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
(1, 1, 2, 95000),
(1, 2, 1, 120000),
(2, 5, 3, 150000);
GO

-- =========================================
-- DỮ LIỆU MẪU CHO ORDER
-- =========================================
INSERT INTO [Order] (UserID, TotalAmount, PaymentMethod, [Status], ShippingAddress)
VALUES
(2, 310000, N'COD', N'Completed', N'Hồ Chí Minh'),
(3, 450000, N'Bank', N'Shipped', N'Đà Nẵng');
GO

-- =========================================
-- DỮ LIỆU MẪU CHO ORDERDETAIL
-- =========================================
INSERT INTO OrderDetail (OrderID, BookID, Quantity, UnitPrice)
VALUES
(1, 1, 2, 95000),
(1, 2, 1, 120000),
(2, 5, 3, 150000);
GO

-- =========================================
-- DỮ LIỆU MẪU CHO REVIEW
-- =========================================
INSERT INTO Review (UserID, BookID, Rating, Comment)
VALUES
(2, 1, 5, N'Câu chuyện rất cảm động.'),
(2, 2, 4, N'Sách hữu ích, đọc dễ hiểu.'),
(3, 5, 5, N'Rất thích Harry Potter.'),
(4, 3, 3, N'Hơi dài nhưng nội dung hấp dẫn.');
GO

-- =========================================
-- DỮ LIỆU MẪU CHO PAYMENT
-- =========================================
INSERT INTO Payment (OrderID, Amount, PaymentStatus, TransactionCode)
VALUES
(1, 310000, N'Paid', 'TXN1001'),
(2, 450000, N'Paid', 'TXN1002');
GO
