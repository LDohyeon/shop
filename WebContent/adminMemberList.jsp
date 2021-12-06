<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>관리자 권한 부여</title>
		<link rel="stylesheet" href="style.css">
	</head>
		<style>
			#memberWrap
			{
				margin:100px auto;
				width:1425px;
				padding:50px;
				border:10px solid lightgray;
			}
			table
			{
				border-collapse:collapse;
			}
			table, td, th
			{
				border:1px solid gray;
				text-align:center;
			}
			th, td 
			{
			    width: 125px;
			}
		</style>
	<body>
	
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

			<div id="memberWrap">
				<a href="adminmemberList.do"><h1>회원 목록</h1></a>
				
				<br>
				<form action="memberList.do" method="post">
					회원 검색 : <input type="text" name="search" placeholder="아이디를 입력하세요">
					<input type="submit" value="검색">
				</form>
				<br>
				<form method="post" action="adminVipMember.do">
					vip 회원 보기 : <input name="page" type="number" min="10" max="100" value="10">명
					<input type="submit" value="보기">
				</form>
				<br>
				<table>
					<tr>
						<c:if test="${vip !=null }">
							<td>순위</td>
						</c:if>
						<th>회원번호</th>
						<th>아이디</th>
						<th>비밀번호</th>
						<th>총 결제 금액</th>
						<th>이름</th>
						<th>성별</th>
						<th>나이</th>
						<th>연락처</th>
						<th>이메일</th>
						<th>회원 가입 날짜</th>
						<th>관리자 등급</th>
						<th>회원정보 수정</th>
						<th>회원정보 삭제</th>
						<th>회원의 장바구니 보기</th>
						<th>회원의 결제내역 보기</th>
					</tr>
					
					<c:forEach items="${m }" var="m">
						<tr>
							<c:if test="${vip !=null }">
								<td>${m.getRank() }</td>
							</c:if>
							<td>${m.getNum() }</td>
							<td>${m.getId() }</td>
							<td>${m.getPw() }</td>
							<td>
								<fmt:formatNumber type="number" value="${m.getPriceHap() }"/>원
							</td>
							<td>${m.getName() }</td>
							<c:if test="${m.getGender()== 0}">
								<td>남자</td>
							</c:if>
							<c:if test="${m.getGender()== 1}">
								<td>여자</td>
							</c:if>
							<td>${m.getAge() }</td>
							<td>${m.getPhone() }</td>
							<td>${m.getEmail() }</td>
							<td>${m.getCreate_date() }</td>
							<c:if test="${m.getAdmin()==2 }">
								<td>일반 등급 회원</td>
							</c:if>
							<c:if test="${m.getAdmin()==1 }">
								<td>관리자 등급</td>
							</c:if>
							<c:if test="${m.getAdmin()==0 }">
								<td>최고 등급</td>
							</c:if>
							<td><a href="adMemUpdate.do?id=${m.getId() }">수정</a></td>
							<td><a href="adMemDelete.do?num=${m.getNum() }">삭제</a></td>
							<td><a href="cartList.do?id=${m.getId() }">장바구니</a></td>
							<td><a href="adminPayList.do?id=${m.getId() }&&startPage=1&&listPage=20">결제내역</a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			
			<div id="footer3">
				<div id="footer">
					<div id="footer2">
						<div>만든이 : 이도현</div>
						<div>이도현의 포트폴리오</div>
						<div>자격증 현황</div>
						<div>정보처리산업기사 자격증 보유</div>
						<div>iot지식능력검정 자격증 보유</div>
						<div>만들어진 곳: 북부기술 교육원</div>
					</div>
				</div>
			</div>
			
		</body>
	</html>