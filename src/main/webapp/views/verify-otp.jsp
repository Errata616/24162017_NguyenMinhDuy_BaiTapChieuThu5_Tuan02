<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác thực OTP</title>
</head>
<body>
    <h2>Xác thực OTP kích hoạt tài khoản</h2>
    <p>Mã OTP đã được gửi tới email: <b>${sessionScope.emailVerify}</b></p>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/verify-otp" method="post">
        <label>Nhập mã OTP:</label><br>
        <input type="text" name="otp" required><br><br>

        <button type="submit">Xác nhận</button>
    </form>
</body>
</html>
