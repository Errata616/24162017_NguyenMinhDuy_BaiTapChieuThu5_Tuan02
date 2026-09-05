<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dat lai mat khau</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow-sm mt-4">
                <div class="card-body p-4">
                    <h2 class="card-title text-center mb-3">Dat lai mat khau</h2>
                    <p class="text-center text-muted">
                        Ma OTP da duoc gui toi email: <b>${sessionScope.emailReset}</b>
                    </p>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger py-2"><c:out value="${error}" /></div>
                    </c:if>

                    <form class="needs-validation" novalidate
                          action="${pageContext.request.contextPath}/reset-password" method="post">

                        <div class="mb-3">
                            <label class="form-label">Ma OTP</label>
                            <input type="text" name="otp" pattern="[0-9]{6}" maxlength="6"
                                   class="form-control ${not empty fieldErrors.otp ? 'is-invalid' : ''}" required>
                            <div class="invalid-feedback">
                                <c:out value="${not empty fieldErrors.otp ? fieldErrors.otp : 'Ma OTP gom 6 chu so.'}" />
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mat khau moi</label>
                            <input type="password" name="newPassword" minlength="6"
                                   class="form-control ${not empty fieldErrors.newPassword ? 'is-invalid' : ''}" required>
                            <div class="invalid-feedback">
                                <c:out value="${not empty fieldErrors.newPassword ? fieldErrors.newPassword : 'Mat khau moi toi thieu 6 ky tu.'}" />
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary w-100">Dat lai mat khau</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
