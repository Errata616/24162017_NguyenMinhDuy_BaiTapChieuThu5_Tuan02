<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xac thuc OTP</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow-sm mt-4">
                <div class="card-body p-4">
                    <h2 class="card-title text-center mb-3">Xac thuc OTP kich hoat tai khoan</h2>
                    <p class="text-center text-muted">
                        Ma OTP da duoc gui toi email: <b>${sessionScope.emailVerify}</b>
                    </p>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger py-2"><c:out value="${error}" /></div>
                    </c:if>

                    <form class="needs-validation" novalidate
                          action="${pageContext.request.contextPath}/verify-otp" method="post">
                        <div class="mb-3">
                            <label class="form-label">Nhap ma OTP</label>
                            <input type="text" name="otp" pattern="[0-9]{6}" maxlength="6"
                                   class="form-control" required>
                            <div class="invalid-feedback">Ma OTP gom 6 chu so.</div>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Xac nhan</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
