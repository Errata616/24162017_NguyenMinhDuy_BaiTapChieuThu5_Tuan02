<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h2>Thêm Sản phẩm</h2>

<form action="<c:url value="/admin/product/insert"/>" method="post" enctype="multipart/form-data">
    <!-- Tên sản phẩm -->
    <label for="productname">Tên sản phẩm:</label><br>
    <input type="text" id="productname" name="productname" required><br><br>

    <!-- Giá -->
    <label for="price">Giá sản phẩm (VNĐ):</label><br>
    <input type="number" id="price" name="price" min="0" step="1" required><br><br>

    <!-- Mô tả -->
    <label for="description">Mô tả:</label><br>
    <textarea id="description" name="description" rows="4" cols="40"></textarea><br><br>

    <!-- Danh mục -->
    <label for="categoryid">Danh mục:</label><br>
    <select id="categoryid" name="categoryid" required>
        <option value="" disabled selected>-- Chọn danh mục --</option>
        <c:forEach items="${listcate}" var="c">
            <option value="${c.categoryId}">${c.categoryname}</option>
        </c:forEach>
    </select><br><br>

    <!-- Link ảnh -->
    <label for="images">Link ảnh (nếu không upload file):</label><br>
    <input type="text" id="images" name="images"><br><br>

    <!-- Upload ảnh -->
    <label for="images1">Upload ảnh:</label><br>
    <input type="file" id="images1" name="images1" accept="image/*"><br><br>

    <input type="submit" value="Thêm sản phẩm">
</form>
<br>
<a href="<c:url value="/admin/products"/>">Quay lại danh sách</a>