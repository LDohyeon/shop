<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import ="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>게시판 목록</title>
		<link rel="stylesheet" href="styleBoard.css">
		<style>
			#tit
			{
				background-color:lightgray;
				padding-left:15px;
			}
			textarea
			{
				width:60%; height:80px;
			}
			input[type=text]
			{
				width:40%;
			}
			.cpDiv
			{
				color:red;
			}
			.ccpDiv
			{
				color:red;
			}
	
		</style>
	</head>
	<body>
	
	<!-- 이건 댓글을 삭제한 후 한 번 새로고침을 하기 위해 만듬 -->
		<form name="panupDelete" method="get" action="boardContent.do">
			<input name="num" type="hidden" value="${num }">
		</form>
	
	<!--  -->
	
	
	
		<jsp:include page="header.jsp"/>

		
		<div id="one">
			<h1>1:1 문의</h1>
		</div>
		<hr>
		<div id="wrap">
			<br>
			
				<c:forEach items="${data }" var="DTO">
					<h3>${DTO.getTitle() }</h3>
					<hr>
					<div id="tit">
						<div>${DTO.getId() } </div>
						<div>${DTO.getDate() }</div>
						<div>조회수 ${DTO.getHits() } </div>
					</div>
					<hr>
					<br>
					<div>${DTO.getContent() }</div>
					<br>
					
					<c:if test="${loginUser.id==DTO.getId() }">
						<span><a href="boardWriting.jsp?updateTitle=${DTO.getTitle() }&&updateContent=${DTO.getContent()}&&num=${num }">수정</a></span>
						<span><a href="boardDelete.do?num=${num }&&startPage=1&&listPage=10">삭제</a></span>
					</c:if>
				</c:forEach>
				<!-- delete에 &&startPage=1&&listPage=10를 붙여준 이유는
					 삭제 후 board.do로 갈 텐데 그 때 저 값들을 전달해주기 위함이다.-->

			
			

			<br>
			<br>
			<hr>
			<p>댓글</p>
			<c:forEach items="${cs }" var="cs">
				<c:choose>
					<c:when test="${cs.getCcomment_order() == 0}">
						<span><b>${cs.getId() }</b></span>
						<span>업로드된 시간 : ${cs.getCreate_date() }</span>
						<div>${cs.getComment() }</div>
					</c:when>
					<c:when test="${cs.getCcomment_order()%6 == 1 || cs.getCcomment_order()%6 == 5}">
						<span><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${cs.getId() }</b></span>
						<span>업로드된 시간 : ${cs.getCreate_date() }</span>
						<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${cs.getComment() }</div>
					</c:when>
					<c:when test="${cs.getCcomment_order()%6 == 2 || cs.getCcomment_order()%6 == 4 || cs.getCcomment_order()%6==0}">
						<span><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${cs.getId() }</b></span>
						<span> 업로드된 시간 : ${cs.getCreate_date() }</span>
					
						<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${cs.getComment() }</div>
					</c:when>
					<c:when test="${cs.getCcomment_order()%6 == 3}">
						<span><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${cs.getId() }</b></span>
						<span> 업로드된 시간 : ${cs.getCreate_date() }</span>
						<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${cs.getComment() }</div>
					</c:when>
				</c:choose>
	
	
	
				
				<c:if test="${loginUser.getId()==cs.getId() }">
					<input type="button" value="수정" onclick="commentUpdate(${cs.getCount()})">
					<input type="button" value="삭제" onclick="commentDelete(${cs.getPk()})">
				</c:if>
				<!-- 댓글 삭제는 칸을 띄워서 물어본다. -->
				<form name="cup" method="get" action="commentUpdate.do">
					<input class="coUpdate" style="display:none;" type="text" name="commentUpdate" value="${cs.getComment()}">
					<input class="cpRest" style="display:none;" type="button" value="취소!" onclick="backCoUpdate(${cs.getCount()})">
					<input class="cpSubmit" style="display:none;" type="submit" value="수정!" onclick="return cpSub(${cs.getCount()})">
					<div class="cpDiv" style="display:none;">댓글을 입력해주세요</div>
					<input type="hidden" name="pk" value="${cs.getPk() }">
					<input type="hidden" name="id" value="${loginUser.getId() }">
					<input type="hidden" name="num" value="${num }">
				</form>



				
			
				<c:if test="${loginUser.getId()!=null }">
					<p class="pTag" onclick="cmm(${cs.getCount()})">답글 쓰기</p>
					<form name="fm" method="post" action="comment.do">
						<input class="cm" type="text" style="display:none;" name="comment2" value="${cs.getId()  }에게 답변 : ">
						<input type="hidden" name="id" value="${loginUser.id }">
						<input type="hidden" name="comment_order" value="${cs.getComment_order() }">
						<input type="hidden" name="num" value="${num }">
						<input class="cmRemove" style="display:none;" onclick="backCmm(${cs.getCount()})" type="button" value="취소">
						<input class="cmSubmit" style="display:none;" type="submit" value="답글 쓰기!" onclick="return ccpSub(${cs.getCount()})">
						<div class="ccpDiv" style="display:none;">댓글을 입력해주세요</div>
					</form>
				</c:if>
				<hr>
				<br>	
			</c:forEach>
			
		
		<script>
			//댓글 보기 누르면 보이게 하기
			var cm =document.getElementsByClassName("cm");
			var cmRemove =document.getElementsByClassName("cmRemove");
			var cmSubmit =document.getElementsByClassName("cmSubmit");
		
			function cmm(i)
			{
				cm[i].style.display="";
				cmSubmit[i].style.display="";
				cmRemove[i].style.display="";
			}
			function backCmm(i)
			{
				cm[i].style.display="none";
				cmSubmit[i].style.display="none";
				cmRemove[i].style.display="none";
				ccpDiv[i].style.display="none";
			}
			function ccpSub(i)
			{
				if(cm[i].value=="")
				{
					ccpDiv[i].style.display="";
					return false;
				}
				return true;
			}

			
			
			//댓글 수정
			var coUpdate=document.getElementsByClassName("coUpdate");
			var cpSubmit=document.getElementsByClassName("cpSubmit");
			var cpRest=document.getElementsByClassName("cpRest");
			
			var cpDiv=document.getElementsByClassName("cpDiv");
			var ccpDiv=document.getElementsByClassName("ccpDiv");
			
			
			function commentUpdate(i)
			{

				coUpdate[i].style.display="";
				cpSubmit[i].style.display="";	
				cpRest[i].style.display="";	

			}
			function backCoUpdate(i)
			{
				coUpdate[i].style.display="none";
				cpSubmit[i].style.display="none";	
				cpRest[i].style.display="none";			
				
				cpDiv[i].style.display="none";
			}
			
			
			function cpSub(i)
			{
				if(coUpdate[i].value.length==0)
				{
					cpDiv[i].style.display="";
					return false;
				}
				return true;
			}
			
			

			//댓글 삭제
			function commentDelete(pk)
			{
				var url="commentDelete.do?pk="+pk;
				var popupX=(window.screen.width/2)-(400/2);
				var popupY=(window.screen.height/2)-(350/2);
				
				window.open(url, "_blank_1", "width=400, height=150, left="+popupX+", top="+popupY);
			}

			
		</script>



		<!-- 코멘트 올리기 시작 -->

			<br>

			<c:if test="${loginUser.id!=null }">
				<span>댓글 입력칸</span>
					<form method="get" action="comment.do">
						<textarea id="txt" name="comment" maxlength="1000">댓글.</textarea><br>
						<input type="hidden" name="id" value="${loginUser.id }">
						<input type="hidden" name="num" value="${num }">
						<input type="submit" value="댓글 입력!">
					</form>

			</c:if>

			


			<br>
			<hr>
			<a href="board.do?startPage=1&&listPage=10">목록</a>	
		</div>
		
		
		

		
		
		<jsp:include page="footer.jsp"/>
					
		
	</body>
</html>

