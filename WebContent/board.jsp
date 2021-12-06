<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import ="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>게시판 목록</title>
		<link rel="stylesheet" href="styleBoard.css">
	
	</head>
	<body>
	
		<jsp:include page="header.jsp"/>
		
		
		<!-- 게시판 고객 센터 시작 -->
		
		<div id="one">
			<h1>1:1 문의</h1>
		</div>
		<hr>
		<div id="wrap">
			<table>
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>날짜</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${data }" var="data">
						<tr>
							<td>${data.getNum() }</td>
							<td><a href="boardContent.do?num=${data.getNum() }">${data.getTitle() }</a></td>
							<td>${data.getId() }</td>
							<td>${data.getDate() }</td>
							<td>${data.getHits() }</td>
						</tr>
					</c:forEach>	
				</tbody>
			</table>		
			
			<ul id="pagebtn">
				<c:if test="${startPage!=1 }">
					<li><a href="board.do?startPage=${startPage-startPage+1 }&&listPage=${listPage}">맨 처음</a></li>
					<li><a href="board.do?startPage=${startPage-1 }&&listPage=${listPage}">이전 페이지</a></li>
				</c:if>
				<c:forEach begin="1" end="${page }" var="i">
					<c:choose>
						<c:when test="${startPage eq i }">
							<li>${i} 현재</li>
						</c:when>
						<c:otherwise>
							<li><a href="board.do?startPage=${i }&&listPage=${listPage}">${i }</a></li><!-- 10써도 되지만 변수 쓰는 걸 좋아해서 -->
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${startPage lt page }">
					<li><a href="board.do?startPage=${startPage+1 }&&listPage=${listPage}">다음 페이지</a></li>
					<li><a href="board.do?startPage=${page }&&listPage=${listPage}">맨 끝</a></li>
				</c:if>
				
			</ul>





	
			<c:if test="${loginUser.id!=null }">
				<a href=boardIn.do>글쓰기</a>
			</c:if>
		</div>
		
		


		
		
		
		<jsp:include page="footer.jsp"/>
		
	<script>
	//window.onkeydown = function(e) {
		   //var key = e.keyCode;
		 //  if(key==116){
		//	   return false;
		  // }
	//	}	
	</script>
	</body>
</html>





