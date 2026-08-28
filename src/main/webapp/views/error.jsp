<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Loi He Thong</title>
</head>
<body>
	<div class="error-container">
		<h2>Da Xay Ra Loi!</h2>
		<p>Rai rat xin loi, he thong dang gap su co ngoai y muon.</p>

		<!-- Kiểm tra va hien thi loi -->
		<c:if test="${!empty exception}">
			<div class="error-detail">
				<strong>Chi tiet loi:</strong> ${exception.message}
			</div>
		</c:if>

		<a class="back-link" href="login.jsp">Quay lai trang dang nhap</a>
	</div>
</body>
</html>