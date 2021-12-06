<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import ="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>메인 화면 이미지 등록</title>
		<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		<link rel="stylesheet" href="style.css">
		<style>
			#pictureWrap
			{
				width:1170px;
				margin:30px auto;
				height:600px;
				border:4px solid lightgray;	
			}
			#pictureWrap > div
			{
				width:95%;
				margin: 30px auto;
			}
			p
			{
				color:red;
				font-size:12px;
			}

		</style>
	</head>
	<body>

			
		<jsp:include page="header.jsp"/>
		
		<div id="pictureWrap">
			<div>

				<h1>메인 이미지 삽입</h1>
				<br>
				<form method="post" enctype="multipart/form-data" name="frm">
					<div>첫 번째 이미지 : <input type="file" name="pictureurl1"></div>
					<br>
					<div>두 번째 이미지 : <input type="file" name="pictureurl2"></div>
					<br>
					<div>세 번째 이미지 : <input type="file" name="pictureurl3"></div>
					<br>
					<input type="submit" value="이미지 삽입">
					<br>
					<br>
					<p>*삭제를 원하면 아무 이미지를 넣지 않고 버튼을 클릭하세요. </p>
					<h1>공지사항 이미지 삽입</h1>
					<br>
					<div>공지사항 이미지 : <input type="file" name="notice"></div>
					<br>
					<input type="submit" value="공지사항 이미지 삽입 및 삭제">
				</form>

			</div>
		</div>
		

		
		<jsp:include page="footer.jsp"/>

	</body>
</html>


