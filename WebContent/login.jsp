<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>로그인</title>
		<link rel="stylesheet" href="style.css">
		<style>
			*
			{
				margin:0;
				padding:0;
				
			}
			#loginWrap
			{
				width:1168px;
				margin:30px auto;
				border:1px solid lightgray;
				padding-top:50px;
				padding-bottom:150px;
			}
			input
			{
				margin:10px;
			}
			input:first-child
			{
				margin-left:25px;
			}
			
			#loginWrap > *
			{
				text-align:center;
			}
			#loginWrap > h1
			{
				font-size:50px;
			}
			#loginWrap > form > div
			{
				border:3px solid lightgray;
				display:inline-block;
				width:277px;
			}
			#loginWrap > form > div > input
			{
				height: 30px;
				width:220px;
				border:none;
				margin-left:0;
				outline: none;
			}
			#loginWrap > form > a > div
			{
				color:white;
				display:inline-block;
				background-color:yellowgreen;
				width: 280px;
				height: 50px;
				line-height: 50px;
			}
			#loginWrap > form > a:last-child > div
			{
				background-color:#1571e0;;
			}



			
		</style>
	</head>
	<body>
		<c:if test="${check==0 }">
			<script>
				alert("회원가입이 완료되었습니다.");
			</script>		
		</c:if>
		
		
		<jsp:include page="header.jsp"/>
		
		<div id="loginWrap">
			<h1>login</h1>
			<br>
			<form method="post" action="login.do" id="frm" name="frm">
				<div>
					<input type="text" name="id" placeholder="아이디" maxlength="10" >
				</div>
				<br>
				<span id="sid" style = 'color:red; display:none;'>아이디를 입력하세요</span>
				<br>
				<div>
					<input type="password" name="pw" placeholder="비밀번호" maxlength="10">
				</div>
				<br>
				<span id="spw" style = 'color:red; display:none;'>비밀번호를 입력하세요.</span>
				<c:if test="${check==1 }">
					<br>
					<div style = 'color:red;'>가입하지 않은 아이디이거나, 잘못된 비밀번호입니다</div>
					<br>
				</c:if>
				
				
				<br>
				<a><div onclick="return check()">로그인</div></a>
				
				<br>
				<br>
				<a href="memberShip.jsp"><div>회원가입</div></a>
				
			</form>

		</div>		
			

			
						
			<script>
	
				var sid=document.getElementById("sid");
				var spw=document.getElementById("spw");
				var frm=document.getElementById("frm");
				function check()
				{
					if(document.frm.id.value.length==0)
					{
						sid.style.display="block";
						return false;
					}
					if(document.frm.id.value.length!=0)
					{
						sid.style.display="none";
					}
					
					if(document.frm.pw.value.length==0)
					{
						spw.style.display="block";	
						return false;
					}
					if(document.frm.pw.value.length!=0)
					{
						sid.style.display="none";
					}
					
					sid.style.display="none";
					spw.style.display="none";
					frm.submit();
					return true;
	
				}
	
				function member()
				{
					location.href="memberShip.jsp";
				}
	
	
			</script>
		<jsp:include page="footer.jsp"/>
		
	</body>
</html>








