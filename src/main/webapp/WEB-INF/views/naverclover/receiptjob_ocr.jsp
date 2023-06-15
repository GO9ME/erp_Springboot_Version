<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/erp/common/css/main.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script type="text/javascript">
		//ajax 요청 - 파일업로드
		//Ajax를 이용해서 파일업로드를 하는 경우 FormData라는 객체를 이용해서 작업
		//일반적인 파일업로드는 enctype="multipart/form-data"로 정의하면 업로드 되는 파일내용과 입력하는 폼데이터를 자동으로 포멧팅해서 서버로 전송
		//Ajax는 자동으로 넘기지 못하므로 FormData라는 객체를 이용해서 작업
		//<form>태그에서 작업하는 것처럼 데이터가 처리되도록 지원
		//이 FormData객체를 ajax로 전송하면 <form>객체 내부에서 데이터 전송하는 것과 동일
		//ajax통신을 통해서 데이터를 전송하는 경우 기본값이 key=value의 형식으로 전송
		$(document).ready(function(){
			$("#upload").click(function(){
				var myfile = $("#myfile")[0];
				
				//자바에서 발생한 값을 자바스크립트에서 사용하기 위해 작업 - 세션에서 데이터 꺼내기
				var id = "${user.id}";
				var deptname = "${user.deptname}";
				
				var myformdata = new FormData();
				myformdata.append("id",id);
				myformdata.append("dept",deptname);
				myformdata.append("state","false");
				myformdata.append("file",myfile.files[0]);
				/* 
					FormData의 값이 제대로 저장이 되어 있는지 체크
					필드 => key
					FormData에 저장된 값을 추출
				 */
				 // values추출
				for ( key of myformdata.keys()){
					 console.log(key);
				 }
				for ( val of myformdata.values()){
					 console.log(val);
				 }
				
				// 네이버 서비스를 이용할 수 있도록 ajax요청
				// processData의 기본값은 true -> false로 변경해야 Query스트링(key=value)로 전송하지 않음
				// ==> <form>태그에서 multipart/form-data와 동일
				$.ajax({
					//name(매개변수명 - 고정): value(매개 변수 값 - 내맘대로)
					 url : "/erp/naverclova/ocr",
					 type:"post",
					 processData : false, 
					 contentType : false,
					 cache : false,
					 data : myformdata,
					 success  : function (resultdata){ //result data는 ajax요청 성공 후 서버에서 전달하는 데이터
						 console.log(resultdata);
					 	//json문자열을 파싱한 후
					 	var jsonObj = JSON.parse(resultdata);
					 	console.log(jsonObj);
					 }
				})	
			})
		});
			
	
	</script>
</head>

<body>
	<div class="container">
		<h3>경비처리하기</h3>
		<form method="post" enctype="multipart/form-data"
			action="/erp/naverclova/ocr">
			<div class="row">

				<input type="file" name="file" id="myfile"
					onchange="document.getElementById('userImage').src = window.URL.createObjectURL(this.files[0])"
					accept="image/*">

			</div>
			<div class="row">
				<div class="col-sm-4">
					<div class="thumbnail">
						<img src="/erp/images/myphoto.jpg" id="userImage" width="400"
							height="300">
						<p>
							<strong>영수증을 첨부하세요</strong>
						</p>
					</div>
				</div>
				<div class="col-sm-8">

					<div class="form-group">
						<label for="email">매장이름:</label> <span id="storename"></span>
					</div>
					<div class="form-group">
						<label for="pwd">매장주소:</label> <span id="storeaddr"></span>
					</div>
					<div class="form-group">
						<label for="pwd">매장전화번호:</label> <span id="storetel"></span>
					</div>
					<div class="form-group">
						<label for="pwd">총결제금액:</label> <span id="totalprice"></span>
					</div>
					<button type="button" class="btn btn-default" id="upload">파일업로드</button>


				</div>
			</div>
		</form>
	</div>

</body>
</html>