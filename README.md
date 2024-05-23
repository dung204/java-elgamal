# Thuật toán Elgamal bằng Java

## 1. Các công cụ cần thiết

- Java Development Kit (JDK) 8 trở lên
- Git

## 2. Cách chạy

- Clone project về máy:

```bash
git clone
```

- Mở project bằng IntelliJ IDEA hoặc Eclipse, chạy file `App.java`
- Nếu mở project bằng VSCode, cài thêm extension [`Extension Pack for Java`](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack), trước khi chạy

- Nếu muốn chạy trên terminal, chạy các lệnh sau (đảm bảo đường dẫn hiện tại là đường dẫn của project):

```bash
cd src
javac src/App.java -d build
cd build
java App
```

## 3. Lưu ý

- Giao diện khi chạy lần đầu có thể không hiện lên đầy đủ, chạy lại lần nữa có thể sẽ hiện đầy đủ
- Một số nút bấm sẽ không hoạt động ngay, mà cần phải click thêm một vài lần nữa để hoạt động
