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
			#payWrap
			{
				margin:100px auto;
				width:1325px;
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
	
		<div id="payWrap">
			<h2>${id } 회원님의 결제 목록</h2>
			<h3>총 결제 금액 : <fmt:formatNumber type="number" value="${priceHap }"/>원</h3>
			
			<table>
				<tr>
					<th>상품번호</th>
					<th>상품 이미지</th>
					<th>상품 이름</th>
					<th>공급처</th>
					<th>메이커</th>
					<th>주문 수량</th>
					<th>가격</th>
					<th>적립된 포인트</th>
					<th>구매한 시간</th>
					<th>구매 여부</th>
					<th>환불 하기</th>
				</tr>
				<c:forEach items="${payList }" var="pay">
					<tr>
						<td>${pay.getNum() }</td>
						<td><img style="width:50px; height:50px;" src=".${pay.getPictureurl() }"></td>
						<td>${pay.getProduct_name() }</td>
						<td>공급처 : ${pay.getProduct_id() }</td>
						<td>${pay.getMaker() }</td>
						<td>${pay.getPurchase_quantity() }개</td>
						<td><fmt:formatNumber type="number" value="${pay.getPrice() }"/>원</td>
						<td>${pay.getPoint() }p</td>
						<td>${pay.getCreate_date() }</td>
						<c:choose>
							<c:when test="${pay.getPay_delete() ==0 }">
								<td>결제완료</td>
							</c:when>
							<c:otherwise>
								<td>환불</td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${pay.getPay_delete() ==0 }">
								<td><a href="payRefund.do?payNum=${pay.getNum() }&&id=${id }">환불하기</a></td>
							</c:when>
							<c:otherwise>
								<td>환불처리 진행되었음 ${pay.getDelete_date() }</td>
							</c:otherwise>
						</c:choose>
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
	
	
	
	
	
	
	
	