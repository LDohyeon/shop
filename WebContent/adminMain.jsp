<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${empty loginUser }">
	<jsp:forward page="login.do"></jsp:forward>
</c:if>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>샵 홈페이지 메인</title>
		<link rel="stylesheet" href="style.css">
		<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		<style>
			#header
			{
				min-width: 1345px;
			}
		</style>

	</head>
		<c:if test="${message !=null  }">
			<script>
				alert("회원 수정 및 권한 부여 완료");
			</script>
		</c:if>
	<body>
		<div id="header">
			<div id="header2">
				<ul id="uheader">
					
					<li><a>구매후기</a></li>
					<li><a href="board.do?startPage=1&&listPage=10">고객센터</a></li>
					<li><a href="noticeBoardSelect.do?startPage=1&&lastPage=10">공지사항</a></li>
					
					<c:choose>
						<c:when test="${loginUser.admin==0 }">
							<li><a href="productList.do?startPage=1&&lastPage=10">상품조회</a></li>
						</c:when>
						<c:when test="${loginUser.admin==1 }">
							<li><a href="productList.do?id=${loginUser.id }&&startPage=1&&lastPage=10">상품조회</a></li>
						</c:when>		
					</c:choose>

					<li><a href="productWrite.do">상품등록</a></li>
					
					
					<c:if test="${loginUser.admin==0 }">
						<li><a href="adminstartMainPicture.do">메인화면이미지등록</a></li>
						<li><a href="adminmemberList.do">회원관리</a></li>
						<li><a href="adminMemberCreate.do">회원 가입자수 검색</a></li>
						<li><a href="payQuantity.do">남녀대비 구매수량 확인</a></li>
						<li><a href="payAllList.do">연간 매출 확인</a></li>
					</c:if>

					<li><a href="logout.do">로그아웃</a></li>
					<li>${loginUser.email }</li>
					<li>${loginUser.name }님</li>		

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
					
					<img id="srhsub" src="image/search.png">
					
					<input type="hidden" name="startPage" value="1">
					<input type="hidden" name="lastPage" value="20">
				</form>
			</div>
		</div>
		<!-- title 끝 -->
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
		
		
		
		<div id="body">
			<img id="image1" src=".${mainImage[0] }">
			<img id="image2" src=".${mainImage[1] }" style="display:none;">
			<img id="image3" src=".${mainImage[2] }" style="display:none;">
			<a href="#"><span id="leftBtn">◁</span></a>
			<a href="#"><span id="rightBtn">▷</span></a>
		</div>
		<script>
			var i=1002;
			var j=0;
			
			
			
			$("#leftBtn").click(function(){
				i--;
				rightHide();
				rightFadeIn();
			});
			

			$("#rightBtn").click(function(){
				i++;
				rightHide();
				rightFadeIn();
				
			});
			$(document).ready(function(){
				
				setInterval("auto()", 5000);
				

			});
			
			function auto()
			{
				j++;
				if(j==9)
				{
					i=1002;	
				}
				i++;
				rightHide();
				rightFadeIn();

			}
			
			function rightHide()
			{
				if(i%3==0)
				{
					$("#image1").hide();
					$("#image2").hide();
					$("#image3").hide();
				}
				else if(i%3==1)
				{
					$("#image2").hide();
					$("#image3").hide();
					$("#image1").hide();
				}
				else if(i%3==2)
				{
					$("#image3").hide();
					$("#image2").hide();
					$("#image1").hide();
				}
			}
			function rightFadeIn()
			{
				if(i%3==0)
				{
					$("#image1").fadeIn();
				}
				else if(i%3==1)
				{
					$("#image2").fadeIn();
				}
				else if(i%3==2)
				{
					$("#image3").fadeIn();
				}
			}

		</script>
		
		
		

		<div id="wrap">
			<c:choose>
				<c:when test="${search ==null }">
					<h1 style="text-align:center;">신규 상품</h1>
				</c:when>
				<c:otherwise>
					<h1 style="text-align:center;">${search }</h1>
				</c:otherwise>
			</c:choose>
			
			<div>
				<c:forEach items="${productList }" var="product">
					<div class="pro">
						<a href="startEachProduct.do?num=${product.getNum() }">
							<c:choose>
								<c:when test="${product.getPictureurl()== '/files/null'}">
									이미지가 없습니다.
								</c:when>
								<c:otherwise>
									<img id="productImg" src=".${product.getPictureurl() }">
								</c:otherwise>
							</c:choose>		
							<br>
							${product.getName() }
							<br>
							<fmt:formatNumber type="number" value="${product.getPrice() }"/>원		
							<br>
							<c:choose>
								<c:when test="${product.getQuantity() <1 }">
									<span class="sell">품절</span>
								</c:when>
								<c:otherwise>
									<span class="soldOut">판매</span>
								</c:otherwise>
							</c:choose>
						</a>	
					</div>
				</c:forEach>
			</div>
			
			
			<ul id="pageBtn">
				<c:if test="${startPage != 1 }">
					<li><a href="start.do?startPage=${1 }&&lastPage=20">처음</a></li>
					<li><a href="start.do?startPage=${startPage-1 }&&lastPage=20">이전 페이지</a></li>
				</c:if>
				<c:forEach begin="1" end="${pageBtn }" var="i">
					<c:choose>
						<c:when test="${startPage eq i }">
							<li><a>현재 ${i }</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="start.do?startPage=${i }&&lastPage=20">${i }</a></li>	
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${startPage < pageBtn }">
					<li><a href="start.do?startPage=${startPage+1 }&&lastPage=20">다음 페이지</a></li>
					<li><a href="start.do?startPage=${pageBtn }&&lastPage=20">끝</a></li>
				</c:if>
			</ul>
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
		
		
		
		<%//회원 가입
				
		
				if( (String)session.getAttribute("mem")!=null)
				{
					out.print("<script>alert('회원가입 완료');</script>");
					session.invalidate();
				}
					
				
		%><!-- 회원가입 완료문 -->

	</body>
</html>







