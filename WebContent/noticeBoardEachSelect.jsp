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
		<style>
			#tit
			{
				background-color:lightgray;
				padding-left:15px;
			}
			textarea
			{
				width:60%; height:80px;/*나중에 수정할 수도*/
			}
			input[type=text]
			{
				width:40%;
			}
			.cpDiv
			{
				color:red;
			}
			.ccpDiv
			{
				color:red;
			}
			#updebtn
			{
				text-align:right;
			}

	
		</style>
	</head>
	<body>
	
	<!-- 이건 댓글을 삭제한 후 한 번 새로고침을 하기 위해 만듬 -->
		<form name="panupDelete" method="get" action="boardContent.do">
			<input name="num" type="hidden" value="${num }">
		</form>
	
	<!--  -->
	
	
	
		<jsp:include page="header.jsp"/>

		
		<div id="one">
			<h1>1:1 문의</h1>
		</div>
		<hr>
		<div id="wrap">
			<br>
			
				
			<h3>${list.getTitle() }</h3>
			<hr>
			<div id="tit">
				<div>${list.getId() } </div>
				<div>${list.getDate() }</div>
				<div>조회수 ${list.getHits() } </div>
			</div>
			<hr>
			<br>
			<c:if test="${list.getPictureurl() !=null }">
				<div id="divImg">
					<img src=".${list.getPictureurl() }">
				</div>
			</c:if>


			<br>
			<br>
			<div>${list.getContent() }</div>
			<br>
					
			<c:if test="${loginUser.id==list.getId() }">
				<div id="updebtn">
					<span><a href="noticeBoardUpdate.do?num=${num }">수정</a></span>
					<span><a href="noticeBoardDelete.do?num=${num }">삭제</a></span>

				</div>
			</c:if>
				
			

			<br>
			<hr>
			<a href="noticeBoardSelect.do?startPage=1&&lastPage=10">목록</a>	
		</div>
		
		
		
		<jsp:include page="footer.jsp"/>
					
		
	</body>
</html>

