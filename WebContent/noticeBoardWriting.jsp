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


		<div id="one">
			<h1>공지사항 작성</h1>
		</div>
		<hr>
		
		<c:if test="${num==null }">
				<div id="wrap">
				<br>
				<h3>공지사항 쓰기</h3>
				<hr>
	
				<form name="frm" method="post" enctype="multipart/form-data">
					<br>
					<select id="options" name="options">
						<option value="-1">선택해주세요 필수 *</option>
						<option value="0">필독</option>
						<option value="1">공지</option>
						<option value="2">이벤트</option>
					</select>
					<br>
					<br>
					<hr>
					<p>제목</p>
					<div id="divTitle">
						<input type="text" name="title" maxlength="50" placeholder="제목을 입력해 주세요." required><!-- required 쓰진 않을 것 -->
					</div>
					<hr>
					<br>
					
					<p>공지사항 내용 작성</p>
					<textarea name="content" maxlength="65536">공지사항을 자유롭게 남겨주세요.</textarea>
					<p>공지사항 이미지로 작성 : <input type="file" name="pictureurl"></p> 
					
					<br>
					<br>
					<input type="hidden" name="id" value="${loginUser.id }">
					<input type="submit" value="작성완료" onclick="return check()">
				</form>
			</div>
		</c:if>
		<c:if test="${num!=null }">
				<div id="wrap">
				<br>
				<h3>공지사항 수정</h3>
				<hr>
	
				<form name="frm" method="post" action="noticeBoardUpdate.do" enctype="multipart/form-data">
					<br>
					<select id="options" name="options">
						<option value="-1">선택해주세요 필수 *</option>
						<option value="0">필독</option>
						<option value="1">공지</option>
						<option value="2">이벤트</option>
					</select>
					<br>
					<br>
					<hr>
					<p>제목</p>
					<div id="divTitle">
						<input value="${list.getTitle() }" type="text" name="title" maxlength="50" placeholder="제목을 입력해 주세요." required><!-- required 쓰진 않을 것 -->
					</div>
					<hr>
					<br>
					<p>공지사항 내용 작성</p>
					
					<c:if test="${list.getPictureurl() !=null }">
						<div id="divImg">
							<img src=".${list.getPictureurl() }">
						</div>
					</c:if>
					
					
					<br>
					<br>
					<textarea name="content" maxlength="65536">${list.getContent() }</textarea>
					<p>공지사항 이미지로 작성 : <input type="file" name="pictureurl"></p> 
					
					<br>
					<br>
					<input type="hidden" name="num" value="${num }">
					<input type="hidden" name="hiddenPictureurl" value="${list.getPictureurl() }">
					<input type="submit" value="작성완료" onclick="return check()">
				</form>
			</div>
		</c:if>

		
		
		
		<script>
		
		
			function check()
			{
				if(document.frm.options.value=="-1")
				{
					alert("옵션을 선택해주세요");
					frm.options.focus();
					return false;
				}
				if(document.frm.title.value.length==0)
				{
					alert("제목을 입력하세요");	
					frm.title.focus();
					return false;
				}
				if(document.frm.content.value=="" && document.frm.pictureurl.value=="")
				{
					alert("이미지 첨부나 내용을 입력하세요");
					frm.content.focus();
					return false;
				}

				return true;
			}

		</script>
		

		<jsp:include page="footer.jsp"/>
					
		
	</body>
</html>


