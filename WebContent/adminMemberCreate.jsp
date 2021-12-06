<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import ="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>회원 가입자 수 검색</title>
		<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
		<link rel="stylesheet" href="style.css">
		<style>
			*
			{
				margin:0;
				padding:0;
				
			}
			#payWrap
			{
				width:1168px;
				
				margin:0 auto;
				border:1px solid lightgray;
			}
			#payWrap > div
			{
				margin:30px;
			}

			#sub
			{
				text-align:center;
				display: inline-block;
				width:80px;
				height:30px;
				line-height: 30px;
				background-color:#d40c0c;
				color:white;
				margin-left:10px;
			}
			#footer3
			{
				margin-top:110px;
			}
			#divNone
			{
				display:none;
			}
		</style>
	</head>
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
					
					<img id="srhsub" src="image/search.png">
					
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
		
		
		<br>
		<br>
		<div id="payWrap">
			<div>
				<h1>회원 가입자 수 검색</h1>
				
						<span>연도 검색 : </span>
						<select onchange="sel()" id="year" name="year">
							<c:set var="years" value="2021"/>
							<c:forEach var="i" begin="0" end="10" step="1">
								<option value="${years }">${years }</option>
								<c:set var="years" value="${years-1 }"/>
							</c:forEach>
						</select>
						년	
						
						<input onclick="mon()" id="monthChecked" name="monthChecked" value="0" type="checkbox">
						<div id="divNone">
							<span>월별로 검색 : </span>
								<select onchange="monChange()" id="month" name="month">
									<c:forEach var="i" begin="1" end="12" step="1">
										<option value="${i }">${i }</option>
									</c:forEach>
								</select>
							월	
						</div>
							
					<div id="sub" onclick="subin()">검색하기</div>	
				<div id="chartWrap">
				  <canvas id="myChart"></canvas>
				</div>	
			</div>
		</div>
		


		

	
		
		<script>
			var divNone=document.getElementById("divNone");//체크박스
			
			var monthChecked=document.getElementById("monthChecked");
			
			
			function mon()
			{
				
				if(monthChecked.checked==true)
				{
					divNone.style.display="inline-block";
					monthChecked.value=1;//서블렛으로 넘기면 null되는데 자바스트립트는 또 전달이 된다.
				}
				else
				{
					monthChecked.value=0;
					divNone.style.display="none";
				}
				
			}			
			
			var monthSelect=document.getElementById("month").value;
			
			function monChange()
			{
				monthSelect=document.getElementById("month").value;
			}

			var year=document.getElementById("year").value;//onChange() 되기 전의 값
			
			function sel()
			{
				year=document.getElementById("year").value;//onChange() 됐을 때의 값

			}
			
			

			function subin()
			{
				$.ajax("adminMemberCreate.do",
				{
					type:"POST",
					data:
					{
						year,
						monCheck:monthChecked.value,
						monthSelect

					},
					success:function(response, status, xhr)
					{
						var chartWrap=document.getElementById("chartWrap");
						
						while(chartWrap.hasChildNodes())
						{
							chartWrap.removeChild(chartWrap.firstChild);
						}
						var canvas= document.createElement("canvas");
						
						chartWrap.appendChild(canvas);
						canvas.setAttribute("id", "myChart");
						
						
						var chart=response.split(",");
						var chart1 =new Array();
						var chart2 =new Array();
						
						for(var i=0; i<10; i++)
						{
							if(i<5)
							{
								chart1[i]=chart[i];
							}
							else
							{
								chart2[i-5]=chart[i];
							}

						}
						
						const labels=
						[
							"10대",
							"20대",
							"30대",
							"40대",
							"50대 이상"
						];
						
						const data =
						{
							labels:labels,
							datasets:
							[
								{
									label:"남자",
									backgroundColor:'rgb(99, 104, 255)',
									borderColor:'rgb(99, 104, 255)',
									data:chart1
								},
								{
									label:"여자",
									backgroundColor:'rgb(255, 99, 234)',
									borderColor:'rgb(255, 99, 234)',
									data:chart2
								}
							]
						
						}
						const config=
						{
							type: "bar",
							data:data,
							option:{}
						}
						
						var myChart = new Chart
						(
							document.getElementById("myChart"),
							config
						);
						
						
					},
					error:function(response, status, xhr)
					{
						
					}
				
				});
	

			}//subin() 끝
			
			
			
		</script>
		
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
