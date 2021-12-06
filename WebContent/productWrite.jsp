<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>상품 등록 페이지</title>
		<link rel="stylesheet" href="styleProductWrite.css">
		<link rel="stylesheet" href="style.css">
	</head>
	<body>
	
		<jsp:include page="header.jsp"/>
		<div id="productWrap">
			<h1>상품 등록</h1>
			<form method="post" enctype="multipart/form-data" name="frm">
				<table>
					<tr>
						<th>상품명</th>
						<td><input type="text" name="name"></td>
					</tr>
					<tr>
						<th>상품가격</th>
						<td><input type="text" name="price">원</td>
					</tr>
					<tr>
						<th>메이커</th>
						<td><input type="text" name="maker"></td>
					</tr>
					<tr>
						<th>제품수량</th>
						<td><input type="number" name="quantity" value="1" min="0">개</td>
					</tr>
					<tr>
						<th>카테고리</th>
						<td>
							<input type="radio" name="category" value="패션의류" checked>패션의류
							<input type="radio" name="category" value="패션잡화">패션잡화
							<input type="radio" name="category" value="뷰티">뷰티
							<input type="radio" name="category" value="식품">식품
							<input type="radio" name="category" value="도서">도서
							<input type="radio" name="category" value="기타">기타
						</td>
					</tr>
					<tr>
						<th>사진</th>
						<td><input type="file" name="pictureurl"></td>
					</tr>
					<tr>
						<th>상품 설명</th>
						<td><textarea cols="80" rows="10" name="description"></textarea></td>
					</tr>
				</table>
				<input type="hidden" name="admin" value="${loginUser.admin }">
				<input type="hidden" name="id" value="${loginUser.id }">
				<br>
				<input type="submit" value="상품 등록" onclick="return check()">
				<input type="reset" value="다시작성">
				<c:choose>
					<c:when test="${loginUser.admin==0 }">
						<input type="button" value="내 상품 리스트" onclick="location.href='productList.do?startPage=1&&lastPage=10'">
					</c:when>
					<c:when test="${loginUser.admin==1 }">
						<input type="button" value="내 상품 리스트" onclick="location.href='productList.do?id=${loginUser.id }&&?startPage=1&&lastPage=10'">
					</c:when>		
				</c:choose>
				<input type="button" value="메인 페이지" onclick="location.href='adminMain.do'">

			</form>
		</div>
		<script>
			function check()
			{
				if(document.frm.name.value.length==0)
				{
					alert("상품명을 입력해주세요");
					frm.name.focus();
					return false;
				}
				if(document.frm.price.value.length==0)
				{
					alert("가격을 입력해주세요");
					frm.price.focus();
					return false;
				}
				if(isNaN(frm.price.value))
				{
					alert("숫자를 입력하셔야 합니다.");
					frm.price.focus();
					return false;
				}
				if(document.frm.maker.value.length==0)
				{
					alert("메이커를 입력해주세요");
					frm.maker.focus();
					return false;
				}
				if(document.frm.description.value.length==0)
				{
					alert("상품설명을 입력해주세요");
					frm.description.focus();
					return false;
				}
				return true;
			}
		
		</script>
		
		<jsp:include page="footer.jsp"/>
	</body>
</html>









