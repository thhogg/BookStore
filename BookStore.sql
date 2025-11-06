-- =========================================
-- TẠO DATABASE BÁN SÁCH - BOOKSTOREDB
-- =========================================
IF DB_ID('BookStoreDB') IS NOT NULL
    DROP DATABASE BookStoreDB100;
GO

CREATE DATABASE BookStoreDB100;
GO
USE BookStoreDB100;
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
	UserName NVARCHAR(255) PRIMARY KEY,
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
--  BẢNG CHI TIẾT GIỎ HÀNG (CARTITEM)
-- =========================================
CREATE TABLE CartItem (
    CartItemID INT IDENTITY(1,1) PRIMARY KEY,
    
    -- Bỏ CartID, thay bằng UserName để liên kết trực tiếp
    UserName NVARCHAR(255) FOREIGN KEY REFERENCES Users(UserName) ON DELETE CASCADE,
    
    BookID INT FOREIGN KEY REFERENCES Book(BookID),
    Quantity INT NOT NULL CHECK (Quantity > 0),
    Price INT NOT NULL -- Lưu giá tại thời điểm thêm vào giỏ
);
GO

-- =========================================
-- BẢNG ĐƠN HÀNG (ORDER)
-- =========================================
CREATE TABLE [Order] (
    OrderID INT IDENTITY(1,1) PRIMARY KEY,
    UserName NVARCHAR(255) FOREIGN KEY REFERENCES Users(UserName),
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
-- THÊM DỮ LIỆU MẪU CHO CATEGORY
-- =========================================
INSERT INTO Category (CategoryName, Description)
VALUES
(N'Tiểu thuyết', N'Các tác phẩm văn học hư cấu'),
(N'Khoa học', N'Sách về kiến thức khoa học và công nghệ'),
(N'Lịch sử', N'Sách về các sự kiện lịch sử'),
(N'Thiếu nhi', N'Sách cho trẻ em và học sinh nhỏ tuổi'),
(N'Kinh tế', N'Sách về quản lý, tài chính, và kinh doanh'),
(N'Tâm lý - Kỹ năng', N'Sách phát triển bản thân'),
(N'Học thuật', N'Sách giáo khoa và tài liệu học tập'),
(N'Văn học nước ngoài', N'Tác phẩm dịch từ tiếng Anh, Pháp, Nhật...');

-- =========================================
-- THÊM DỮ LIỆU MẪU CHO BOOK
-- =========================================
INSERT INTO Book (Title, Author, Publisher, CategoryID, ISBN, Price, Stock, [Description], ImageUrl)
VALUES
(N'Đắc Nhân Tâm', N'Dale Carnegie', N'NXB Trẻ', 6, 'ISBN-001', 85000, 50, N'Sách dạy kỹ năng giao tiếp và ứng xử nổi tiếng nhất.', N'images/1.jpg'),
(N'To Kill a Mockingbird', N'Harper Lee', N'NXB Văn học', 8, 'ISBN-002', 120000, 30, N'Tác phẩm kinh điển về công lý và phân biệt chủng tộc.', N'images/2.jpg'),
(N'Lược Sử Thời Gian', N'Stephen Hawking', N'NXB Khoa học', 2, 'ISBN-003', 150000, 40, N'Sách về vũ trụ học, lỗ đen và nguồn gốc của thời gian.', N'images/3.jpg'),
(N'Truyện Kiều', N'Nguyễn Du', N'NXB Văn học', 1, 'ISBN-004', 60000, 100, N'Tác phẩm thơ nổi tiếng nhất Việt Nam.', N'images/4.jpg'),
(N'Rich Dad Poor Dad', N'Robert Kiyosaki', N'NXB Lao động', 5, 'ISBN-005', 110000, 60, N'Sách dạy về tài chính cá nhân và đầu tư.', N'images/5.jpg'),
(N'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', N'Nguyễn Nhật Ánh', N'NXB Trẻ', 4, 'ISBN-006', 90000, 80, N'Truyện thiếu nhi nổi tiếng, đậm chất đồng quê Việt Nam.', N'images/6.jpg'),
(N'Sapiens: Lược sử loài người', N'Yuval Noah Harari', N'NXB Thế giới', 3, 'ISBN-007', 180000, 35, N'Khám phá lịch sử tiến hóa của loài người.', N'images/7.jpg'),
(N'Thiên Tài Bên Trái, Kẻ Điên Bên Phải', N'Kevin Dutton', N'NXB Tâm lý học', 6, 'ISBN-008', 135000, 45, N'Phân tích ranh giới giữa thiên tài và người tâm thần.', N'images/8.jpg'),
(N'Giáo Trình Cấu Trúc Dữ Liệu và Giải Thuật', N'Nguyễn Văn A', N'ĐH Bách Khoa', 7, 'ISBN-009', 95000, 25, N'Tài liệu học tập về lập trình và thuật toán.', N'images/9.jpg'),
(N'1984', N'George Orwell', N'NXB Văn học', 8, 'ISBN-010', 130000, 20, N'Tiểu thuyết dystopia nổi tiếng cảnh báo về chủ nghĩa toàn trị.', N'images/10.jpg');

-- =========================================
-- THÊM DỮ LIỆU MẪU CHO USERS
-- =========================================
INSERT INTO Users (UserName, [Password], FullName, Email, Phone, [Address], [Role])
VALUES
('admin', 'admin123', N'Quản trị viên', 'admin@bookstore.vn', '0900000000', N'123 Trần Hưng Đạo, Hà Nội', 'Admin'),
('user1', '123456', N'Nguyễn Văn A', 'a@gmail.com', '0911111111', N'12 Lý Thường Kiệt, Hà Nội', 'Customer'),
('user2', '123456', N'Trần Thị B', 'b@gmail.com', '0922222222', N'45 Pasteur, TP.HCM', 'Customer'),
('user3', '123456', N'Lê Văn C', 'c@gmail.com', '0933333333', N'89 Hai Bà Trưng, Đà Nẵng', 'Customer'),
('user4', '123456', N'Phạm Thị D', 'd@gmail.com', '0944444444', N'15 Nguyễn Huệ, Huế', 'Customer'),
('user5', '123456', N'Hoàng Văn E', 'e@gmail.com', '0955555555', N'99 Quang Trung, Cần Thơ', 'Customer');

-- =========================================
-- THÊM DỮ LIỆU MẪU CHO ORDER
-- =========================================
INSERT INTO [Order] (UserName, TotalAmount, PaymentMethod, [Status], ShippingAddress)
VALUES
('user1', 185000, N'COD', N'Completed', N'12 Lý Thường Kiệt, Hà Nội'),
('user2', 270000, N'Bank', N'Shipped', N'45 Pasteur, TP.HCM'),
('user3', 90000, N'COD', N'Pending', N'89 Hai Bà Trưng, Đà Nẵng'),
('user4', 230000, N'PayPal', N'Paid', N'15 Nguyễn Huệ, Huế'),
('user5', 120000, N'COD', N'Cancelled', N'99 Quang Trung, Cần Thơ'),
('user2', 310000, N'Bank', N'Completed', N'45 Pasteur, TP.HCM'),
('user3', 150000, N'COD', N'Shipped', N'89 Hai Bà Trưng, Đà Nẵng');

-- =========================================
-- THÊM DỮ LIỆU MẪU CHO ORDERDETAIL
-- =========================================
INSERT INTO OrderDetail (OrderID, BookID, Quantity, UnitPrice)
VALUES
(1, 1, 1, 85000),
(1, 4, 1, 60000),
(2, 3, 1, 150000),
(2, 6, 1, 90000),
(3, 6, 1, 90000),
(4, 5, 2, 110000),
(5, 2, 1, 120000),
(6, 7, 1, 180000),
(6, 9, 1, 95000),
(7, 10, 1, 130000);

-- =========================================
-- THÊM DỮ LIỆU MẪU CHO CARTITEM
-- =========================================
INSERT INTO CartItem (UserName, BookID, Quantity, Price)
VALUES
('user1', 2, 1, 120000),
('user2', 5, 1, 110000),
('user3', 6, 2, 90000),
('user4', 1, 1, 85000),
('user5', 10, 1, 130000),
('user1', 7, 1, 180000),
('user2', 3, 1, 150000),
('user3', 9, 1, 95000);
GO
