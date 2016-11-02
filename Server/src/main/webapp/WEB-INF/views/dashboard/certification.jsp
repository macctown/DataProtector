<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.0.272/jspdf.debug.js"></script>
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="<c:url value='/static/bootstrap/css/bootstrap.min.css' />">
<!-- jSQRCode 2.2.3 -->
<script src="<c:url value='/static/js/qrcode.js'/> "></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Certificate</title>
</head>
<body>

<div id="certificate" style="width:800px; height:600px; padding:20px; text-align:center; border: 10px solid #787878">
<div style="width:750px; height:550px; padding:20px; text-align:center; border: 5px solid #787878">
       <span style="font-size:40px; font-weight:bold">Certificate of Information Assurance</span>
       <br><br>
       <span style="font-size:25px"><i>This is to certify that</i></span>
       <br><br>
       <span style="font-size:30px"><b>${userInfo.firstName} ${userInfo.lastName}</b></span><br/><br/>
       <span style="font-size:25px"><i>has got the certificate for file</i></span> <br/><br/>
       <span style="font-size:30px">${certInfo.file_name}</span> <br/><br/>
       <span style="font-size:20px">with Hash Value of <b>${certInfo.hash_value}</b></span> <br/><br/>
       <span style="font-size:25px"><i>dated on ${certInfo.upload_time}</i></span><br>
       <div id="qrcode"></div>
</div>
</div>
<div style="width:100px;margin-left:350px;margin-top:50px;" id="buttonDiv">
<button class="btn btn-block btn-info btn-sm" id="cmd"><i class="fa fa-download"></i> Download</button>
</div>
<script language="javascript">
var doc = new jsPDF('p','pt','a4');
var specialElementHandlers = {
	    '#editor': function (element, renderer) {
	        return true;
	    }
	};

var qrcode = new QRCode(document.getElementById("qrcode"), {
	width : 100,
	height : 100
});
qrcode.makeCode('${certInfo.hash_value}'); // make another code.

	$('#cmd').click(function () {
		var options = {
				background: '#fff'
		}
		
		
		
		html2canvas($("#certificate"), {
			  onrendered: function(canvas) {
				  
				  var nest = document.getElementById('certificate');
				  var button = document.getElementById('buttonDiv');
				  document.body.removeChild(nest);
				  document.body.removeChild(button);
				  document.body.appendChild(canvas);
				 
				}
		});
		
		var margins = {
				   top: 25,
				   bottom: 60,
				   left: 20,
				   width: 522
		};
		
		 doc.addHTML(document.body, margins.top, margins.left, options, function() {
			doc.save('Certificate.pdf');
		});

	});
</script>
</body>
</html>