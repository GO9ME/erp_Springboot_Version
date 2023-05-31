<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/erp/common/css/jquery.treeview.css" />
	<link rel="stylesheet" href="/erp/common/css/screen.css" />
	<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="/erp/common/js/jquery.cookie.js"></script>
	<script src="/erp/common/js/jquery.treeview.js"></script>

	<script type="text/javascript" src="/erp/common/js/demo.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			//selector가 class=folder 인 객체를 클릭하면 실행되도록 정의
			$(".folder").on("click",function(){
				var ulnode = $(this).next();
				var deptno = ulnode.attr("id");
				//alert("부서선택:"+deptno);
				$.ajax({
					url:"/erp/emp/ajaxtreedata",
					type:"get",
					data:{"deptno":deptno},
					success:function(resultdata){
						//alert(resultdata);
						//서버에서 보내온 json문자열을 JSON객체로 만들기
						jsonobj = JSON.parse(resultdata);
						/* <li class="closed">
							<span class="folder">${dept.deptname}</span>
							<ul id="${dept.deptno }">
								<!-- <li><span class="file">Item 1.1</span></li> -->
							</ul>
						</li> */
						/* <li><span class="file">File 2.1.1</span></li> */
						myli="";
						for(i in jsonobj.deptlist){
							myli = myli + "<li class='closed'>"
							  + "<span class='folder'>"+jsonobj.deptlist[i].deptname+"</span>"
							  +"<ul id='" + jsonobj.deptlist[i].deptno+"'></ul></li>"
						}
						for(i in jsonobj.memberlist){
							myli = myli + "<li>"
							  + "<span class='file'>"+jsonobj.memberlist[i].name+"</span></li>"
						}
						$(ulnode).html(myli);
						//동적으로 생성된 엘리먼트에 이벤트연결하기
						//$(document).on("이벤트명",이벤트를 연결할 객체명,이벤트가발생하면 실행할 함수)
						$(document).on("click",".folder",function(){
							var subulnode = $(this).next();
							var subdeptno = subulnode.attr("id");
							alert("부서선택:"+subdeptno)
							//ajax요청해서 부서에 근무하는 직원이나 서브부서 정보 받아오기
							//=> 동일한 컨트롤러로 작업할 수 있음,이작업을 하기 위해서는 deptlist나 memberlist의 값체크해서 작업하기
						})
					}//end success
				})
			});
		})
	
	</script>
</head>
<body>
<h4>조직도</h4>
	<ul id="browser" class="filetree">
		<c:forEach var="dept" items="${deptlist }">
			<c:if test="${dept.deptlevel==1 }">
				<li class="closed">
					<span class="folder">${dept.deptname}</span>
					<ul id="${dept.deptno }">
						<!-- <li><span class="file">Item 1.1</span></li> -->
					</ul>
				</li>
			</c:if>
		</c:forEach>
<!-- 		<li><span class="folder">Folder 2</span>
			<ul>
				<li><span class="folder">Subfolder 2.1</span>
					<ul id="folder21">
						<li><span class="file">File 2.1.1</span></li>
						<li><span class="file">File 2.1.2</span></li>
					</ul>
				</li>
				<li><span class="file">File 2.2</span></li>
			</ul>
		</li>
		<li class="closed"><span class="folder">Folder 3 (closed at start)</span>
			<ul>
				<li><span class="file">File 3.1</span></li>
			</ul>
		</li>
		<li><span class="file">File 4</span></li> -->
	</ul>
</body>
</html>