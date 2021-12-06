<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>상품 리스트</title>
		<link rel="stylesheet" href="styleProductDo.css">
		<link rel="stylesheet" href="style.css">
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		
		<div id="productWrap">
			<h1>상품 리스트</h1>
			<table>
				<tr>
					<td colspan="8" style="text-align:right">
						<a href="productWrite.do">상품 등록 페이지</a>
					</td>
				</tr>
				<tr>
					<th>상품 번호</th>
					<th>상품 등록 아이디(관리자)</th>
					<th>상품 이름</th>
					<th>가격</th>
					<th>메이커</th>
					<th>카테고리</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
				<c:forEach items="${productList }" var="product">
					<tr>
						<td>${product.getNum() }</td>
						<td>${product.getId()}</td>
						<td>${product.getName() }</td>
						<td>${product.getPrice() }원</td>
						<td>${product.getMaker() }</td>
						<td>${product.getCategory() }</td>
						<td><a href="productUpdate.do?num=${product.getNum() }">수정</a></td>
						<td><a href="productDelete.do?num=${product.getNum() }">삭제</a></td>
					</tr>
				</c:forEach>
			</table>
			
			
			<ul id="pageBtn">
				<c:if test="${startPage != 1 }">
					<li><a href="productList.do?startPage=${1 }&&lastPage=10">처음</a></li>
					<li><a href="productList.do?startPage=${startPage-1 }&&lastPage=10">이전 페이지</a></li>
				</c:if>
				<c:forEach begin="1" end="${pageBtn }" var="i">
					<c:choose>
						<c:when test="${startPage eq i }">
							<li>현재 ${i }</li>
						</c:when>
						<c:otherwise>
							<li><a href="productList.do?startPage=${i }&&lastPage=10">${i }</a></li>	
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${startPage < pageBtn }">
					<li><a href="productList.do?startPage=${startPage+1 }&&lastPage=10">다음 페이지</a></li>
					<li><a href="productList.do?startPage=${pageBtn }&&lastPage=10">끝</a></li>
				</c:if>
			</ul>
		</div>	
		<jsp:include page="footer.jsp"/>
	</body>
</html>










