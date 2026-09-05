<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Them San pham</title>
</head>
<body>
    <h2 class="mb-4">Them San pham</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-danger py-2"><c:out value="${error}" /></div>
    </c:if>

    <form class="needs-validation" style="max-width:560px;" novalidate
          action="<c:url value="/admin/product/insert"/>" method="post" enctype="multipart/form-data">

        <div class="mb-3">
            <label class="form-label" for="productname">Ten san pham</label>
            <input type="text" id="productname" name="productname" value="${param.productname}"
                   class="form-control ${not empty fieldErrors.productname ? 'is-invalid' : ''}"
                   maxlength="255" required>
            <div class="invalid-feedback">
                <c:out value="${not empty fieldErrors.productname ? fieldErrors.productname : 'Vui long nhap ten san pham.'}" />
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label" for="price">Gia san pham (VND)</label>
            <input type="number" id="price" name="price" value="${param.price}"
                   class="form-control ${not empty fieldErrors.price ? 'is-invalid' : ''}"
                   min="1" step="1" required>
            <div class="invalid-feedback">
                <c:out value="${not empty fieldErrors.price ? fieldErrors.price : 'Gia phai la so lon hon 0.'}" />
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label" for="description">Mo ta</label>
            <textarea id="description" name="description" class="form-control" rows="4">${param.description}</textarea>
        </div>

        <div class="mb-3">
            <label class="form-label" for="categoryid">Danh muc</label>
            <select id="categoryid" name="categoryid"
                    class="form-select ${not empty fieldErrors.categoryid ? 'is-invalid' : ''}" required>
                <option value="" disabled ${empty param.categoryid ? 'selected' : ''}>-- Chon danh muc --</option>
                <c:forEach items="${listcate}" var="c">
                    <option value="${c.categoryId}" ${param.categoryid == c.categoryId ? 'selected' : ''}>${c.categoryname}</option>
                </c:forEach>
            </select>
            <div class="invalid-feedback">
                <c:out value="${not empty fieldErrors.categoryid ? fieldErrors.categoryid : 'Vui long chon danh muc.'}" />
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label" for="images">Link anh (neu khong upload file)</label>
            <input type="text" id="images" name="images" value="${param.images}" class="form-control">
        </div>

        <div class="mb-3">
            <label class="form-label" for="images1">Upload anh</label>
            <input type="file" id="images1" name="images1" accept="image/*" class="form-control">
        </div>

        <button type="submit" class="btn btn-primary">Them san pham</button>
        <a class="btn btn-outline-secondary" href="<c:url value="/admin/products"/>">Quay lai danh sach</a>
    </form>
</body>
</html>
