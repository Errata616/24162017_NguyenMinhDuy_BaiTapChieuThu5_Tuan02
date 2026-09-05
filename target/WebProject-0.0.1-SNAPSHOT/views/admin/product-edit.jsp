<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sua San pham</title>
</head>
<body>
    <h2 class="mb-4">Sua San pham</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-danger py-2"><c:out value="${error}" /></div>
    </c:if>

    <form class="needs-validation" style="max-width:560px;" novalidate
          action="<c:url value="/admin/product/update"/>" method="post" enctype="multipart/form-data">
        <input type="hidden" name="productid" value="${product.productId}">

        <div class="mb-3">
            <label class="form-label" for="productname">Ten san pham</label>
            <input type="text" id="productname" name="productname" value="${product.productName}"
                   class="form-control ${not empty fieldErrors.productname ? 'is-invalid' : ''}"
                   maxlength="255" required>
            <div class="invalid-feedback">
                <c:out value="${not empty fieldErrors.productname ? fieldErrors.productname : 'Vui long nhap ten san pham.'}" />
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label" for="price">Gia san pham (VND)</label>
            <input type="number" id="price" name="price" step="1" min="1" value="${product.price}"
                   class="form-control ${not empty fieldErrors.price ? 'is-invalid' : ''}" required>
            <div class="invalid-feedback">
                <c:out value="${not empty fieldErrors.price ? fieldErrors.price : 'Gia phai la so lon hon 0.'}" />
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label" for="description">Mo ta</label>
            <textarea id="description" name="description" class="form-control" rows="4">${product.description}</textarea>
        </div>

        <div class="mb-3">
            <label class="form-label" for="categoryid">Danh muc</label>
            <select id="categoryid" name="categoryid"
                    class="form-select ${not empty fieldErrors.categoryid ? 'is-invalid' : ''}" required>
                <c:forEach items="${listcate}" var="c">
                    <option value="${c.categoryId}"
                        ${(not empty product.category and c.categoryId == product.category.categoryId) ? 'selected' : ''}>
                        ${c.categoryname}
                    </option>
                </c:forEach>
            </select>
            <div class="invalid-feedback">
                <c:out value="${not empty fieldErrors.categoryid ? fieldErrors.categoryid : 'Vui long chon danh muc.'}" />
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label" for="images">Link anh (neu khong upload file)</label>
            <input type="text" id="images" name="images" value="${product.images}" class="form-control">
        </div>

        <div class="mb-2">
            <c:choose>
                <c:when test="${not empty product.images and fn:startsWith(product.images, 'https')}">
                    <c:url value="${product.images}" var="imgUrl"/>
                </c:when>
                <c:otherwise>
                    <c:url value="/image?fname=${product.images}" var="imgUrl"/>
                </c:otherwise>
            </c:choose>
            <img height="110" width="150" style="object-fit:cover;" class="rounded border" src="${imgUrl}" alt="Anh san pham"/>
        </div>

        <div class="mb-3">
            <label class="form-label" for="images1">Upload anh moi (bo trong neu giu anh cu)</label>
            <input type="file" id="images1" name="images1" accept="image/*" class="form-control">
        </div>

        <button type="submit" class="btn btn-primary">Cap nhat</button>
        <a class="btn btn-outline-secondary" href="<c:url value="/admin/products"/>">Quay lai danh sach</a>
    </form>
</body>
</html>
