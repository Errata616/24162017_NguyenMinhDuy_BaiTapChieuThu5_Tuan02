<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt lại mật khẩu</title>
</head>
<body>
    <h2>Đặt lại mật khẩu</h2>
    <p>Mã OTP đã được gửi tới email: <b>${sessionScope.emailReset}</b></p>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/reset-password" method="post">
        <label>Mã OTP:</label><br>
        <input type="text" name="otp" required><br><br>

        <label>Mật khẩu mới:</label><br>
        <input type="password" name="newPassword" required><br><br>

        <button type="submit">Đặt lại mật khẩu</button>
    </form>
</body>
</html>
