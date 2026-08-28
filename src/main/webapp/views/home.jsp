<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div>
    Xin chào, <b>${sessionScope.account.username}</b> |
    <a href="<c:url value='/admin/categories'/>">Quản lý Category</a> |
    <a href="<c:url value='/admin/products'/>">Quản lý Product</a> |
    <a href="<c:url value='/logout'/>">Đăng xuất</a>
</div>
<hr>
<h2>Top 10 Sản Phẩm Mới Nhất</h2>
<div style="display: flex; flex-wrap: wrap; gap: 20px;">
    <c:forEach items="${top10Products}" var="p">
        <div style="border: 1px solid #ccc; padding: 10px; width: 180px;">
            <a href="<c:url value='/product-detail?id=${p.productId}'/>">
                <img src="${p.images}" width="100%" height="150px"/>
                <h4>${p.productName}</h4>
            </a>
            <p>Giá: ${p.price} VNĐ</p>
        </div>
    </c:forEach>
</div>
<br>
<a href="<c:url value='/product'/>">Xem tất cả sản phẩm (Phân trang)</a>