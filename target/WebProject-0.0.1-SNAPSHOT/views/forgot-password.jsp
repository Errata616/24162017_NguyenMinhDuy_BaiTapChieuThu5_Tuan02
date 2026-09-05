<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quen mat khau</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow-sm mt-4">
                <div class="card-body p-4">
                    <h2 class="card-title text-center mb-4">Quen mat khau</h2>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger py-2"><c:out value="${error}" /></div>
                    </c:if>

                    <form class="needs-validation" novalidate
                          action="${pageContext.request.contextPath}/forgot-password" method="post">
                        <div class="mb-3">
                            <label class="form-label">Nhap email da dang ky</label>
                            <input type="email" name="email" value="${param.email}" class="form-control" required>
                            <div class="invalid-feedback">Vui long nhap email hop le.</div>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Gui ma OTP</button>
                    </form>

                    <p class="text-center mt-3 mb-0">
                        <a href="${pageContext.request.contextPath}/login">Quay lai dang nhap</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
