# Booking-Labors

## Ask and question, reply about java spring boot

### Hibernate là gì?

```java

- Hibernate là 1 framework để kết nối đến cơ sở dữ liệu
- Hibernate framework là một giải pháp ORM (Object Relational Mapping) mã nguồn mở, gọn nhẹ. 
Hibernate giúp đơn giản hoá sự phát triển của ứng dụng java để tương tác với cơ sở dữ liệu.
- hibernate cung cấp cho chúng ta Tool ORM giúp đơn giản hoá việc tạo ra dữ liệu và
thao tác với dữ liệu. 

```
#### Example
```java
- Đây chính là mã nguồn mở về tool ORM mà Hibernate framework cung cấp cho chúng ta 
- nó có là 1 thư viện đc viết sẵn
```
<img src="image-1.png" width="50%" height="50%" />

### ORM là gì?

```java
- ORM là object relationship mapping : là một kỹ thuật lập trình để ánh xạ tới đối tượng 
và lấy ra dữ liệu trong cơ sở dữ liệu.
- Chúng ta có thể hiểu đơn giản là:
- Hibernate sẽ lấy dữ liệu trong csdl ra sau đó lưu dữ liệu đó vào trong 1 bộ nhớ cache và trả
về object chứa dữ liệu đó, và để lấy được dữ liệu đó ra để hiển thị cho ng dùng nhìn thấy thì
chúng ta phải mapping qua object đó nghĩa là phải duyệt object đó ra giống như duyệt mảng để 
hiển thị tường tận dữ liệu ra cho ng dùng nhìn thấy
- Ví dụ:
- 
```
<img src="image.png" width="50%" height="50%" />

### Ưu điểm của hibernate là gì?

```java

- ưu điểm là : 
+ mã nguồn mở và nhẹ
+ hiệu suất nhanh : hiệu xuất của hibernate framework nhanh bởi vì nó cung cấp cho chúng ta
bộ nhớ cache được sử dụng trong nội bộ hibernate framework.
+ vì được cung cấp 1 bộ nhớ cache nên sẽ ít truy cập trực tiếp đến cơ sở dữ liệu nên sẽ tương
tác với dữ liệu nhanh hơn
+ Truy vấn cơ sở dữ liệu độc lập
- Khi sử dụng hibernate framework chúng ta sẽ được cung cấp nhiều cách để tương tác vs csdl đó là: 
+ HQL (Hibernate sql): hibernate sql là sử dụng hàm được cung cấp sẵn bởi framework hibernate
 để tương tác với cơ sở dữ liệu theo ý muốn của chúng ta
+ sql native: sql native là tạo ra 1 hàm sau đó nhúng trực tiếp câu lệnh sql vào để tương
tác vs csdl
+ criteria
+ có thể mở rộng 1 cách hiệu quả và linh hoạt
+ hibernate framework hỗ trợ nhiều hệ cơ sở dữ liệu phổ biến ví dụ như: 
- MYSQL, PostgreSQL, Oracle, SQL server ...

```