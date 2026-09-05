<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h2>Danh Sách Sản Phẩm (Phân Trang)</h2>
<div style="display: flex; flex-wrap: wrap; gap: 20px;">
    <c:forEach items="${productList}" var="p">
        <div style="border: 1px solid #ccc; padding: 10px; width: 200px;">
            <a href="<c:url value='/product-detail?id=${p.productId}'/>">
                <img src="${p.images}" width="100%" height="150px"/>
                <h4>${p.productName}</h4>
            </a>
            <p>Giá: ${p.price} VNĐ</p>
        </div>
    </c:forEach>
</div>
<br>
<!-- Thanh phân trang -->
<div>
    <c:forEach begin="1" end="${totalPages}" var="i">
        <a href="<c:url value='/product?page=${i}'/>" style="margin: 5px; ${i == currentPage ? 'font-weight:bold;' : ''}">[${i}]</a>
    </c:forEach>
</div>