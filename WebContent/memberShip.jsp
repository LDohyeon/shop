<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>회원가입 입력</title>
		<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		<link rel="stylesheet" href="style.css">
		<style>
			*
			{
				margin:0;
				padding:0;
			}
			#memberWrap
			{
				width:1160px;
				margin:30px auto;
				border:5px solid lightgray;
			}
			#memberWrap > div
			{
				width:95%;
				border:1px solid lightgray;
				margin:50px auto;
				
			}
			#memberWrap > div > *
			{
 				padding: 10px;
			}
			
			   
			#memberWrap > h1
			{
				background: #fafafa;
			}
			#memberWrap input
			{
				margin:10px;
			}
			span
			{
				color:red;
				display:none;
			}
			.spanclass
			{
				display:inline-block;
			}

			.text
			{
				display:inline-block;
				text-align:left;
				width:120px;
				color:black;
				
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
				<span class="spanclass">
					<c:if test="${loginUser.id==null }">
						<span class="spanclass"><a href="startFirst.do"><img id="bear" src="image/an.png"></a></span>
					</c:if>
					<c:if test="${loginUser.admin==2}">
						<span class="spanclass"><a href="main.jsp"><img id="bear" src="image/an.png"></a></span>
					</c:if>	
					<c:if test="${loginUser.admin==0  || loginUser.admin==1}">
						<span class="spanclass"><a href="adminMain.do"><img id="bear" src="image/an.png"></a></span>
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

		<div id="memberWrap">
			<div>	
				<h1>회원가입</h1>
				<p>*은 필수 항목 입니다.</p>
	
				<form id="frm" name="frm" method="post" action="memberShip.do">
					
				
					<span class="text">*아이디 : </span><input onchange="idValue()" type="text" id="id" name="id">

					
					<input type="button" value="아이디 중복 확인" onclick="ic()"><br>
					<span id="sid">아이디를 입력하세요</span>
	
						
					<div id="green"></div>

			
					<span class="text">*비밀번호 : </span><input type="password" name="pw"><br>
					<span id="spw">비밀번호를 입력하세요</span>
					
					
					
					<span class="text">*비밀번호 확인 : </span><input type="password" name="pw2"><br>
					<span id="spw2"></span>
					
					
					<span class="text">*이름 : </span><input type="text" name="name"><br>
					<span id="sName">이름을 입력하세요.</span>
					
					<span class="text">성별 : </span>&nbsp;&nbsp;남자 : <input type="radio" name="gender" value="0" checked>
																여자 : <input type="radio" name="gender" value="1"><br>
				
					<span class="text">나이 : </span><input type="text" name="age"><br>
					<span id="sage">나이를 입력하세요</span>
					
					<span class="text">핸드폰 : </span><input type="number" name="phone" placeholder="-자리 없이 입력"><br>
					<span id="sphone">핸드폰 번호를 입력하세요.</span>
					<span class="text">이메일 : </span><input type="text" name="email"><br>
					<span id="semail">이메일을 입력하세요.</span>
					
						
					<input type="submit" value="회원가입" onclick="return sub()">
					
				
				</form>
			</div>
		</div>
		
		<jsp:include page="footer.jsp"/>

		<script>
		
			//아이디 중복 체크 확인
			var id=document.getElementById("id").value;
			var green = document.getElementById("green");
			

			function idValue()
			{
				id=document.getElementById("id").value;		
			}
			
			function ic()
			{

				$.ajax("idCheck.do",{
					type:"POST",
					data:
					{
						id
					},
					success:function(response, status, xhr)
					{
						var result=response;
						
						if(result == "-1")
						{
							green.style.color="red";
							green.innerHTML="중복된 아이디입니다";							
						}
						else
						{
							green.style.color="green";
							green.innerHTML="사용 가능한 아이디입니다.";
						}
						
					},
					error:function(response, status, xhr)
					{
						
					}
				});
				
			}
			
		
			var sid=document.getElementById("sid");
			var spw=document.getElementById("spw");
			var spw2=document.getElementById("spw2");
			var sName=document.getElementById("sName");
			var sage=document.getElementById("sage");

			
			function sub()
			{
				if(document.frm.id.value.length==0)
				{
					sid.style.display="block";
					document.frm.id.focus()
					return false;
				}
				if(document.frm.id.value.length!=0)
				{
					sid.style.display="none";
				}
				
				if(green.innerHTML=="중복된 아이디입니다" || green.innerHTML=="")
				{
					alert("아이디 중복 체크를 확인해 주세요");
					return false;
				}
				
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
				if(document.frm.age.value.length==0)
				{
					sage.style.display="block";
					document.frm.age.focus();
					return false;
				}
				if(document.frm.age.value.length!=0)
				{
					sage.style.display="none";
				}

				return true;
			}
			


		</script>
	</body>
</html>









