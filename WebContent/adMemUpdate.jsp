<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>회원 정보 수정 및 권한 부여</title>
		<style>
			*
			{
				margin:0;
				padding:0;
				
			}
			#wrapUpdateMember
			{
				width:600px;
				margin:30px auto;
				border:5px solid lightgray;
				padding:25px;
			}
			input
			{
				margin:10px;
			}
		</style>
	</head>
	<body>
		<!-- getMember을 통해 값을 전부 받아와 수정할 때 모든 값을 귀찮게 일일이 수정할 필요 없게 하였다. -->
		<div id="wrapUpdateMember">
			<h1>회원 수정</h1>
			<form id="frm" name="frm" method="post" action="memUpdate.do">
					
				<br>
				<h3>${id }님의 회원 정보 수정하기 및 권한 부여</h3>
				<p>*은 필수 항목 입니다.</p>
				
				
				
				<input type="hidden" name="id" value="${id }">
				*비밀번호 : <input type="password" name="pw" value="${pw }"><br>
				<span id="spw" style='color:red; display:none;'>비밀번호를 입력하세요</span>
				
				
				
				*비밀번호 확인 : <input type="password" name="pw2" value="${pw }"><br>
				<span id="spw2" style='color:red; display:none;'></span>
				
				
				*이름 : <input type="text" name="name" value="${name }"><br>
				<span id="sName" style='display:none; color:red;'>이름을 입력하세요.</span>
				
				
				
				핸드폰 : <input type="number" name="phone" value="${phone }" placeholder="-자리 없이 입력"><br>
				<span id="sphone" style='display:none; color:red;'>핸드폰 번호를 입력하세요.</span>
				이메일 : <input type="text" name="email" value="${email }"><br>
				<span id="semail" style='display:none; color:red;'>이메일을 입력하세요.</span>
				
				<c:if test="${ad==1 }">
					관리자 권한 취소<input type="checkbox" name="admin" value="2"><br>
				</c:if>
				<c:if test="${ad==2 }">
					관리자 권한 부여<input type="checkbox" name="admin" value="1"><br>
				</c:if>
				
				<input type="hidden" name="adminCheck" value="${ad }">
				<input type="submit" value="수정 및 권한 부여" onclick="return sub()">
				
				
			</form>
		</div>


		<script>
			var sid=document.getElementById("sid");
			var spw=document.getElementById("spw");
			var spw2=document.getElementById("spw2");
			var sName=document.getElementById("sName");
			
			
			var red=document.getElementById("red");//아이디 중복 체크를 안 하면 안 넘어가게 한다.
			function sub()
			{
				if(document.frm.pw.value=="")
				{
					spw.style.display="block";
					document.frm.pw.focus()
					return false;
				}
				if(document.frm.pw.value!="")
				{
					spw.style.display="none";
				}
				if(document.frm.pw2.value=="")
				{
					spw2.style.display="block";
					spw2.innerHTML="비밀번호를 입력하세요";
					document.frm.pw2.focus()
					return false;
				}
				if(document.frm.pw2.value!="")
				{
					spw2.style.display="block";
				}
				if(document.frm.pw.value.length!=document.frm.pw2.value.length)
				{
					spw2.style.display="block";
					spw2.innerHTML="비밀번호가 일치하지 않습니다.";
					document.frm.pw2.focus()
					return false;
				}
				if(document.frm.pw.value.length==document.frm.pw2.value.length)
				{
					spw2.style.display="none";
				}
				if(document.frm.name.value.length==0)
				{
					sName.style.display="block";
					document.frm.name.focus()
					return false;
				}
				if(document.frm.name.value.length!=0)
				{
					sName.style.display="none";
				}


				return true;
			}
			
			
			
			//아이디 중복 체크 확인
			var frm=document.getElementById("frm");
			function ic()
			{
				frm.action="idCheck.do";
				frm.submit();
			}
			

		</script>
	</body>
</html>







