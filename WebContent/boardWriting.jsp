<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import ="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>게시판 목록</title>
		<link rel="stylesheet" href="styleBoard.css">

	</head>
	<body>
		<jsp:include page="header.jsp"/>
<%
//boardWriting.jsp를 두 개 만들기 귀찮아서 이렇게 해준다. 연습 좀 할겸
	String updateTitle=request.getParameter("updateTitle");
	String updateContent=request.getParameter("updateContent");
	String num=request.getParameter("num");
			
	pageContext.setAttribute("updateTitle", updateTitle);
	pageContext.setAttribute("updateContent", updateContent);
	pageContext.setAttribute("num", num);
%>		

		<div id="one">
			<h1>1:1 문의</h1>
		</div>
		<hr>
		<div id="wrap">
			<br>
			<c:if test="${pageScope.updateTitle ==null }"><h3>1:1 문의 글쓰기</h3></c:if>
			<c:if test="${pageScope.updateTitle !=null }"><h3>1:1 문의 글수정</h3></c:if>
			
			<hr>


			<c:if test="${pageScope.updateTitle ==null }">
				<form name="frm" method="post" action="boardIn.do">
					<p>제목</p>
					<div id="divTitle">
						<input type="text" name="title" maxlength="50" placeholder="제목을 입력해 주세요." required>
					</div>
					<hr>
					<br>
					<p>문의 내용 작성</p>
					<textarea name="content" maxlength="65536">문의사항을 자유롭게 남겨주세요.</textarea>
					<input type="hidden" name="id" value="${loginUser.id }">
					
					<input type="submit" value="작성완료" onclick="return check()">
				</form>
			</c:if>
			<c:if test="${pageScope.updateTitle !=null }">
				<form name="frm" method="get" action="boardUpdate.do">
					<p>제목</p>
					<div id="divTitle">
						<input value="${pageScope.updateTitle }" type="text" name="title" maxlength="50" placeholder="제목을 입력해 주세요." required>
					</div>
					<hr>
					<br>
					
					<p>문의 내용 수정</p>
					<textarea name="content" maxlength="65536">${pageScope.updateContent}</textarea>
					<input type="hidden" name="num" value="${num }">
					<input type="submit" value="수정하기!" onclick="return check()">
					<a href="boardContent.do?num=${num }">돌아가기</a>
				</form>
			</c:if>
		</div>
		
		<script>
		
			function check()
			{
				if(document.frm.title.value.length==0)
				{
					alert("제목을 입력하세요");	
					frm.title.focus();
					return false;
				}
				if(document.frm.content.value=="")
				{
					alert("내용을 입력하세요");
					frm.content.focus();
					return false;
				}
				return true;
			}

		</script>
		

		
		
		<jsp:include page="footer.jsp"/>
					
		
	</body>
</html>

