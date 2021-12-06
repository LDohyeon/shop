<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import ="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<div id="header">
			<div id="header2">
				<ul id="uheader">
					<c:if test="${loginUser.id!=null }">
						<li><a href="cartList.do?id=${loginUser.id }">장바구니</a></li>
					</c:if>
					
					
					<li><a>구매후기</a></li>
					<li><a href="board.do?startPage=1&&listPage=10">고객센터</a></li>
					<li><a href="noticeBoardSelect.do?startPage=1&&lastPage=10">공지사항</a></li>
					<c:if test="${loginUser.id==null }">
						<li><a href="memberShip.do">회원가입</a></li>
						<li><a href="login.do">로그인</a></li>
					</c:if>
					<c:if test="${loginUser.id!=null }">
						<li><a href="payList.do?id=${loginUser.id }&&startPage=1&&listPage=20">마이페이지</a></li>
						<li><a href="logout.do">로그아웃</a></li>
						<li>${loginUser.email }</li>
						<li>${loginUser.name }님</li>		
					</c:if>

				</ul>
			</div>
		</div><!-- header 끝 -->
		<div id="titleWrap">
			<div id="title">
				<span>
					<c:if test="${loginUser.id==null }">
						<span><a href="startFirst.do"><img id="bear" src="image/an.png"></a></span>
					</c:if>
					<c:if test="${loginUser.admin==2}">
						<span><a href="main.jsp"><img id="bear" src="image/an.png"></a></span>
					</c:if>	
					<c:if test="${loginUser.admin==0  || loginUser.admin==1}">
						<span><a href="adminMain.do"><img id="bear" src="image/an.png"></a></span>
					</c:if>
				</span><!-- 이미지가 들어갈 자리 -->
				<form name="srhFrm" method="get" action="productSearch.do">
					<input id="srh" type="text" name="search">
					
					<img id="srhsub" style="width:45px; height:30px;" src="image/search.png">
					
					<input type="hidden" name="startPage" value="1">
					<input type="hidden" name="lastPage" value="20">
				</form>
			</div><!-- title 끝 -->
		</div>
		<script>
			var srhsub=document.getElementById("srhsub");
			srhsub.addEventListener("click", function(){
				srhFrm.submit();
			});
		</script>
		<div id="memu">
			<div id="memu2">
				<ul>
					<li>三</li>
					<a href="startEach.do?category=패션의류&&startPage=1&&lastPage=20"><li>패션의류</li></a>
					<a href="startEach.do?category=패션잡화&&startPage=1&&lastPage=20"><li>패션잡화</li></a>
					<a href="startEach.do?category=뷰티&&startPage=1&&lastPage=20"><li>뷰티</li></a>
					<a href="startEach.do?category=식품&&startPage=1&&lastPage=20"><li>식품</li></a>
					<a href="startEach.do?category=도서&&startPage=1&&lastPage=20"><li>도서</li></a>
					<a href="startEach.do?category=기타&&startPage=1&&lastPage=20"><li>기타</li></a>
				</ul>
			</div>
		</div>