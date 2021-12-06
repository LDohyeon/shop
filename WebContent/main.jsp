<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${empty loginUser }">
	<jsp:forward page="login.do"></jsp:forward>
</c:if>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>샵 홈페이지 메인</title>
		<link rel="stylesheet" href="style.css">
	</head>
	<body>
	

<%
	String cart=request.getParameter("cartList");
	pageContext.setAttribute("cart", cart);
%>
		
		<c:choose>
			<c:when test="${cart==0 }">
				<a id="atag" href="cartList.do?id=${loginUser.getId() }"></a>
			</c:when>
			<c:when test="${loginUser.getAdmin() ==2 }">
				<a id="atag" href="startFirst.do"></a>
			</c:when>
			<c:otherwise>
				<a id="atag" href="adminMain.do"></a>
			</c:otherwise>
		</c:choose>
		

		<script>
			var atag= document.getElementById("atag");
			atag.click();
		</script>
		
		
		
		<!-- 
			원래는 로그인 후 이 화면에 나오게 하려고 했으나 그러면 servlet들을
			또 만들어야 한다는 것을 깨닫고 이렇게 수정.
			이렇게 수정함으로써 main.jsp 경로 처리한 데이터들의 값을 일일이 바꾸지 않고
			처리할 수 있었다. -->
	

		


	</body>
</html>





