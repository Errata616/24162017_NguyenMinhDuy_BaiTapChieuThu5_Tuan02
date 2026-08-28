<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quên mật khẩu</title>
</head>
<body>
    <h2>Quên mật khẩu</h2>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/forgot-password" method="post">
        <label>Nhập email đã đăng ký:</label><br>
        <input type="email" name="email" required><br><br>

        <button type="submit">Gửi mã OTP</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a></p>
</body>
</html>
