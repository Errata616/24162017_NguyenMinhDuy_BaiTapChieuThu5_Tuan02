<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %> <!-- Thêm taglib fn -->

<h2>Sửa Sản phẩm</h2>

<form action="<c:url value="/admin/product/update"/>" method="post" enctype="multipart/form-data">
    <!-- ID sản phẩm (ẩn) -->
    <input type="hidden" name="productid" value="${product.productId}">

    <!-- Tên sản phẩm -->
    <label for="productname">Tên sản phẩm:</label><br>
    <input type="text" id="productname" name="productname" value="${product.productName}" required><br><br>

    <!-- Giá -->
    <label for="price">Giá sản phẩm (VNĐ):</label><br>
    <input type="number" id="price" name="price" step="1" min="0" value="${product.price}" required><br><br>

    <!-- Mô tả -->
    <label for="description">Mô tả:</label><br>
    <textarea id="description" name="description" rows="4" cols="40">${product.description}</textarea><br><br>

    <!-- Danh mục -->
    <label for="categoryid">Danh mục:</label><br>
    <select id="categoryid" name="categoryid" required>
        <c:forEach items="${listcate}" var="c">
            <option value="${c.categoryId}" 
                ${(not empty product.category and c.categoryId == product.category.categoryId) ? 'selected' : ''}>
                ${c.categoryname}
            </option>
        </c:forEach>
    </select><br><br>

    <!-- Link ảnh -->
    <label for="images">Link ảnh (nếu không upload file):</label><br>
    <input type="text" id="images" name="images" value="${product.images}"><br><br>

    <!-- Xem trước ảnh an toàn bằng JSTL fn -->
    <c:choose>
        <c:when test="${not empty product.images and fn:startsWith(product.images, 'https')}">
            <c:url value="${product.images}" var="imgUrl"/>
        </c:when>
        <c:otherwise>
            <c:url value="/image?fname=${product.images}" var="imgUrl"/>
        </c:otherwise>
    </c:choose>
    <img height="120" width="160" src="${imgUrl}" alt="Ảnh sản phẩm"/><br><br>

    <!-- Upload ảnh mới -->
    <label for="images1">Upload ảnh mới (bỏ trống nếu giữ ảnh cũ):</label><br>
    <input type="file" id="images1" name="images1" accept="image/*"><br><br>

    <input type="submit" value="Cập nhật">
</form>
<br>
<a href="<c:url value="/admin/products"/>">Quay lại danh sách</a>