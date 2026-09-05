<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Loi He Thong</title>
	<!-- Trang nay khong duoc SiteMesh decorate (loi xay ra o dispatcher ERROR)
	     nen phai tu nhung Bootstrap de hien thi dep -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container py-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card shadow-sm border-danger">
					<div class="card-body text-center p-4">
						<h2 class="text-danger">Da Xay Ra Loi!</h2>
						<p>Rat tiec, he thong dang gap su co ngoai y muon.</p>

						<c:if test="${not empty exception}">
							<div class="alert alert-secondary text-start">
								<strong>Chi tiet loi:</strong> <c:out value="${exception.message}" />
							</div>
						</c:if>

						<a class="btn btn-primary" href="${pageContext.request.contextPath}/login">Quay lai trang dang nhap</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
