<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import ="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>장바구니 보기</title>
		<link rel="stylesheet" href="styleCartList.css">
		<link rel="stylesheet" href="styleCart2List.css">

	</head>
	<body>
		<jsp:include page="header.jsp"/>
		
		
		<div id="wrap">
			<h1>장바구니</h1>
			<form action="cartAllDelete.do" method="post" name="eachFrm" id="eachFrm">
				<input type="hidden" name="id" value="${loginUser.getId() }">
				<input type="hidden" name="eachDelete"><!-- 선택 삭제용 -->
				<input type="hidden" name="payInsert"><!-- 선택 결제용 -->
				<input type="hidden" name="priceHap" value="${priceHap }">
				<input type="hidden" name="pointHap" value="${pointHap }">
				
				<input type="hidden" name="gender" value="${loginUser.getGender() }">
				<input type="hidden" name="age" value="${loginUser.getAge() }">

				<table>
					<tr>
						<th style="width:5%"><input type="checkbox" id="checkAll"></th>
						<th style="width:10%">상품이미지</th>
						<th style="width:10%">수량</th>
						<th style="width:45%">상품명</th>
						<th style="width:15%">가격</th>
						<th style="width:15%">포인트</th>
					</tr>
					<c:if test="${priceHap ==0 }">
						<tr>
							<td colspan='6'>장바구니에 담긴 상품이 없습니다.</td>
						</tr>
					</c:if>
					
					
					
					<c:forEach items="${cartList }" var="cart">
						<c:choose>
							<c:when test="${cart.getProductQuantity()>0 }">
								<tr>
									<td>
										<input type="checkbox" class="chkbox" name="each" value="${cart.getNum() }">
									</td>
									<td>
										<a href="startEachProduct.do?num=${cart.getProduct_num() }">
											<img style="width:115px; height:115px;" src=".${cart.getPictureurl() }">
										</a>
									</td>
									<td>
										${cart.getPurchase_quantity()}개
									</td>
									<td>
										<a href="startEachProduct.do?num=${cart.getProduct_num() }">${cart.getName() }</a>
									</td>	
									<td>
										<fmt:formatNumber type="number" value="${cart.getPrice() }"/>원
									</td>
									<td>
										<fmt:formatNumber type="number" value="${cart.getPoint() }"/>원
									</td>	
								</tr>
							</c:when>
							<c:when test="${cart.getProductQuantity()<=0 }">
								<tr>
									<td>
										<input type="checkbox" class="chkbox" name="each" value="${cart.getNum() }">
									</td>
									<td>
										<a href="startEachProduct.do?num=${cart.getProduct_num() }">
											<img style="width:115px; height:115px;" src=".${cart.getPictureurl() }">
										</a>
									</td>
									<td>
										${cart.getPurchase_quantity()}개
									</td>
									<td>
										<a href="startEachProduct.do?num=${cart.getProduct_num() }">${cart.getName() }</a>
										<div class="checkRed">품절되었습니다.</div>
									</td>	
									<td>
										<fmt:formatNumber type="number" value="${cart.getPrice() }"/>원
									</td>
									<td>
										<fmt:formatNumber type="number" value="${cart.getPoint() }"/>원
									</td>	
								</tr>
							</c:when>
						</c:choose>

					</c:forEach>
				</table>
			</form>
			
			
			<!-- 카트에 아무것도 없을 경우 -->
			<c:if test="${priceHap ==0 }">
				<div id="cartNull"></div>
			</c:if>
			
			
			<c:if test="${priceHap !=0 }">			
				<form id="frm" method="get" action="cartAllDelete.do">
					<input onclick="CartEachDelete()" type="button" value="선택삭제">
					<input type="hidden" name="id" value="${loginUser.getId() }">
					<input type="button" id="CartAllDelete" value="전체삭제">
				</form>
				
				

				
				
				<div id="payment">
					<div>
						<div>배송비</div>
						<div>적립금</div>
						<div>총 결제금액</div>
					</div>
					<div>
						<c:choose>
							<c:when test="${priceHap >50000 }">
								<div>배송비 제외</div>
							</c:when>
							<c:otherwise>
								<div>3,000원</div>
							</c:otherwise>
						</c:choose>
						
						<div>
							<fmt:formatNumber type="number" value="${pointHap }"/>원
						</div>
							
						<c:choose>
							<c:when test="${priceHap >50000 }">
								<div>
									<fmt:formatNumber type="number" value="${priceHap }"/>원
								</div>
							</c:when>
							<c:otherwise>
								<div>
									<fmt:formatNumber type="number" value="${priceHap+3000 }"/>원
								</div>
							</c:otherwise>
						</c:choose>		
					</div>
				</div>
				<div id="shipping">50,000원 이상 구매시 배송비가 제외됩니다.</div>
	
				<div id="paymentGo">
					<div onclick="payGO()">결제하기</div>
				</div>
			</c:if>			



			<!-- 결제 창 판업 띄우기 -->
			<div id="wrapPonup">
				<div id="ponup">
					<div id="wrap2">
						<div>
							<div>제품명 </div>
							<div class="checkRed">구매수량 </div>
							<div>남은재고</div>
						</div>
						<div>
							<c:forEach items="${ponupList }" var="ponupList">
								<div>${ponupList.getName() }</div>
								<div class="checkRed">${ponupList.getPurchase_quantity() }</div>
								<div>${ponupList.getProductQuantity() }</div>
							</c:forEach>
						</div>
						
						<div class="ment" id="red">재고 수량이 부족합니다.</div>
						<div class="ment">위의 상품을 제외하고 구입하시겠습니까?</div>
		
						<div>
							<form id="ponupFrm" method="post" action="payInsert.do">
								<input type="hidden" name="id" value="${loginUser.getId() }">
								<input type="hidden" name="payInsert" value="${payInserts }">
								<!-- payInsert.do 88번째 줄 참고 -->
								
								<input type="hidden" name="gender" value="${loginUser.getGender() }">
								<input type="hidden" name="age" value="${loginUser.getAge() }">
								
								<input class="inputment" type="button" value="구매" onclick="subPay()">

								<a href="main.jsp?cartList=0"><input class="inputment" type="button" value="취소" onclick="cansle()"></a>
								
							</form>
						</div>

					</div>
				</div>
			</div>
			
			
			

			<!-- 결제 판업을 띄우기 위해 존재 자세한 정보는 바로밑 script로 -->
			<c:choose>
				<c:when test="${clik==1 }">
					<input type="hidden" id="cl" onclick="realPaygo()">
				</c:when>
				<c:otherwise>
					<input type="hidden" id="cl">
				</c:otherwise>
			</c:choose>
			


			<script>

				//물어볼꺼 반복해서 판업창 안 뜨게하는 법
			
				var wrapPonup=document.getElementById("wrapPonup");
			
				var cl=document.getElementById("cl");
				
				
				cl.click();
				
				function realPaygo()
				{
					wrapPonup.style.display="block";
				}

				
				//스크롤 height //생각해보니까 그냥 바디 크기를 잡으면 될 것 같은데
				window.onload =function()
				{
					//판업창 디자인
					var ponup=document.getElementById("ponup");
					
					var scrollHeight = Math.max(
							  document.body.scrollHeight, document.documentElement.scrollHeight,
							  document.body.offsetHeight, document.documentElement.offsetHeight,
							  document.body.clientHeight, document.documentElement.clientHeight
							);
					
					var width=(window.screen.width/2)-(400/2);
					var height=(window.screen.height/2)-(500/2);
					
					var fullWidth=window.screen.width;
					var fullHeight=scrollHeight;
					function pon()
					{
						ponup.style.left=width+"px";	
						ponup.style.top=height+"px";	
						wrapPonup.style.width=fullWidth+"px";
						wrapPonup.style.height=fullHeight+"px";
					}
					pon();
					var ponupFrm = document.getElementById("ponupFrm");



				<!-- 결제 창 판업 띄우기 -->
				}
				function cansle()
				{
					wrapPonup.style.display="none";
					
				}
				function subPay()
				{
					ponupFrm.submit();
				}
				//결제 판업 버튼들


 	
				//전체 삭제
				var frm=document.getElementById("frm");
				var CAD=document.getElementById("CartAllDelete");
				CAD.addEventListener("click", function(){
					frm.submit();
				});
				//전체 삭제
					
					
				//맨 위에 checkbox를 누르면 전체가 눌린다.
				var checkAll=document.querySelector('#checkAll');
				var chkbox=document.querySelectorAll('.chkbox');
					
				checkAll.onclick=function()
				{
					if(checkAll.checked==false)
					{
						for(var i=0; i<chkbox.length; i++)
						{
							chkbox[i].checked=false;
						}
					}
					else
					{
						for(var i=0; i<chkbox.length; i++)
						{
							chkbox[i].checked=true;
						}
					}
				}
					
					
					
				//선택 삭제	//checkbox가 선택된 배열을 하나의 변수에 ,를 찍고 몰아서 넣어준 다음 서블릿에서 분해한다.
					
				var eachFrm=document.getElementById("eachFrm");//frm
				var each=document.getElementsByName("each");//checkBox
					
				var ed="";
				function CartEachDelete()
				{
					for(var i=0; i<each.length; i++)
					{
						if(each[i].checked==true)
						{
							ed +=each[i].value+","	
						}
					}
					if(ed!="")
					{
						document.eachFrm.eachDelete.value=ed;//88 번째 줄에 만들어둔 input hidden에 넣는다.
						eachFrm.submit();
					}
					else if(ed=="")
					{
						alert("항목을 클릭해주세요.");
					}
				}
				//선택 삭제
					
					
					
				//선택 결제
				var pg="";
				function payGO()
				{
					for(var i=0; i<each.length; i++)
					{
						if(each[i].checked==true)
						{
							pg+=each[i].value+",";
						}
					}
					if(pg!="")
					{
						document.eachFrm.payInsert.value=pg;//89 번째 줄에 만들어둔 input hidden에 넣는다.
																//굳이 이걸 또만든 이유는 name을 구별하기 위함
						eachFrm.action="payInsert.do";
						eachFrm.method="get";
						eachFrm.submit();				
					}
					else if(pg=="")
					{
						alert("하나 이상의 항목을 클릭하여 결제를 진행하여주세요.");
					}
				}
			</script>

		</div>
		
		

		
		<jsp:include page="footer.jsp"/>
	</body>
</html>

		
		
		
	