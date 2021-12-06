<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>상품 삭제 페이지</title>
		<link rel="stylesheet" href="styleProduct.css">
		<link rel="stylesheet" href="style.css">
	</head>
	<body>
		<jsp:include page="header.jsp"/>
	
	
		<div id="productWrap">
			<h1>상품 개별 조회 및 삭제</h1>
			<c:forEach items="${product }" var="product">
				<form method="post" enctype="multipart/form-data" name="frm">
					<table>
						<tr>
							<td>
								<c:choose>
									<c:when test="${product.getPictureurl()== '/files/null'}">
										이미지가 없습니다.
									</c:when>
									<c:otherwise>
										<img src=".${product.getPictureurl() }">
									</c:otherwise>
								</c:choose>								
							</td>
							<td>
								<table>
									<tr>
										<th>상품명</th>
										<td>${product.getName() }</td>
									</tr>
									<tr>
										<th>상품가격</th>
										<td>${product.getPrice() }</td>
									</tr>
									<tr>
										<th>메이커</th>
										<td>${product.getMaker() }</td>
									</tr>
									<tr>
										<th>카테고리</th>
										<td>${product.getCategory() }</td>
									</tr>
									<tr>
										<th>상품 설명</th>
										<td><div style="height:220px;">${product.getDescription() }</div></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>

					<input type="hidden" name="num" value="${product.getNum() }">
					<br>
					<input type="submit" value="상품 삭제">
					<input type="button" value="내 상품 리스트" onclick="location.href='productList.do?startPage=1&&lastPage=10'">
					<input type="button" value="메인 페이지" onclick="location.href='adminMain.do'">
					
				</form>
			</c:forEach>
		</div>


		<jsp:include page="footer.jsp"/>
	</body>
</html>


