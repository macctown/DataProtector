<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Certificates
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">Certificates</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-md-3">
        
        	<c:if test="${fileName != null}">
				<div class="alert alert-success">
				<p>File:${fileName} Upload Successfully.</p>
				</div>
			</c:if>
			
			<c:if test="${errMsg != null}">
				<div class="alert alert-danger">
				<p>${errMsg}</p>
				</div>
			</c:if>
			
			<c:if test="${deleteFileName != null}">
				<div class="alert alert-success">
				<p>Certificate of File:${deleteFileName} Delete Successfully.</p>
				</div>
			</c:if>
			
			<c:if test="${fileUploadError == true}">
				<div class="alert alert-danger">
				<p>File Upload Failed.</p>
				</div>
			</c:if>
			
			<c:if test="${authResult == true}">
				<div class="alert alert-success">
				<p>Certificate Auth Successfully!</p>
				</div>
			</c:if>
			
			<c:if test="${authResult == false}">
				<div class="alert alert-danger">
				<p>Certificate Auth Failed! Something Changed in This File!</p>
				</div>
			</c:if>
			
          <div class="box box-solid">
            <div class="box-header with-border">
              <h3 class="box-title">Obtain Certificate</h3>

              <div class="box-tools">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
              </div>
            </div>
            <div class="box-body no-padding">       	
           		<c:url var="uploadUrl" value="/upload" />
    			<form action="${uploadUrl}?${_csrf.parameterName}=${_csrf.token}" method="POST" class="form-horizontal" enctype="multipart/form-data" modelattribute="fileUpload">
				<div class="form-group center25">
                  <input type="file" name="file" id="file"class="form-control" required/>
                  <br>
                  <input type="submit" class="btn btn-primary btn-block margin-bottom" value="Upload & Get Cert">
                </div>
                </form>
        	 </div>
            <!-- /.box-body -->
          </div>
          
          
          <div class="box box-solid">
            <div class="box-header with-border">
              <h3 class="box-title">Authenticate Certificate</h3>

              <div class="box-tools">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
              </div>
            </div>
            <div class="box-body no-padding">
           		<c:url var="authUploadUrl" value="/authUpload" />
                <form action="${authUploadUrl}?${_csrf.parameterName}=${_csrf.token}" method="POST" class="form-horizontal" enctype="multipart/form-data" modelattribute="fileUpload">
				<div class="form-group center25">
                  <label for="exampleInputEmail1">Auth File</label>
                  <input type="file" name="file" id="file" class="form-control" required/>
                  <br>
                  <label for="exampleInputEmail1">Cert #ID</label>
                  <input type="text" name="certId" id="certId" class="form-control" placeholder="Enter Cert ID" required>
                  <br>
                 
                  <input type="submit" class="btn btn-success btn-block margin-bottom" value="Upload & Authenticate">
                </div>
                </form>
        	 </div>
            <!-- /.box-body -->
          </div>
          
        </div>
        <!-- /.col -->
        <div class="col-md-9">
          <div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">Certificates</h3>

              <div class="box-tools pull-right">
                <div class="has-feedback"> 
           		  <c:url var="searchCertUrl" value="/searchCert" />
                  <form action="${searchCertUrl}" method="GET" class="form-horizontal">
                  	<input type="text" class="form-control input-sm" name="searchKwd" placeholder="Search Certificate" modelattribute="searchKwd">
                  	<span class="glyphicon glyphicon-search form-control-feedback"></span>
                  </form>
                </div>
              </div>
              <!-- /.box-tools -->
            </div>
            <!-- /.box-header -->
            <div class="box-body no-padding">
              <div class="table-responsive mailbox-messages">
                <table class="table table-hover table-striped" style="table-style:fixed">
                  <tbody>
					<c:choose>
					    <c:when test="${fn:length(certsList) gt 0}">
					        <c:forEach items="${certsList}" var="cert">
							     <tr>
				                    <td class="mailbox-star"><a href="#"><i class="fa fa-star text-yellow"></i></a></td>
				                    <td class="mailbox-name">#${cert.id}</td>
				                    <td class="mailbox-name">${cert.file_name}</td>
				                    <td class="mailbox-subject" style="word-break:break-all"><b>Hash Key</b> : ${cert.hash_value}
				                    </td>
				                    <td class="mailbox-attachment"></td>
				                    <td class="mailbox-date">${cert.upload_time}</td>
				                    <td class="mailbox-date">
				                    	<c:url var="downloadCertUrl" value="/getCert/${cert.id}" />
				                    	<a class="btn btn-block btn-info btn-sm" href="${downloadCertUrl}" target="_blank"><i class="fa fa-download"></i> Download</a>
				                    	<c:url var="deleteCertUrl" value="/deleteCert/${cert.id}" />
				                    	<a class="btn btn-block btn-danger btn-sm" href="${deleteCertUrl}"><i class="fa fa-trash"></i> Delete</a>
				                    </td>
				                  </tr>
							</c:forEach>
					    </c:when>    
					    <c:otherwise>
					        <p>No Certificates For Now!</p>
					        <br />
					    </c:otherwise>
					</c:choose>
                  	
                  </tbody>
                </table>
                <!-- /.table -->
              </div>
              <!-- /.mail-box-messages -->
            </div>
            <!-- /.box-body -->
            <div class="box-footer no-padding">
              <div class="mailbox-controls">
                <div class="btn-group">
                  <button type="button" class="btn btn-default btn-sm"><i class="fa fa-trash-o"></i></button>
                  <button type="button" class="btn btn-default btn-sm"><i class="fa fa-reply"></i></button>
                  <button type="button" class="btn btn-default btn-sm"><i class="fa fa-share"></i></button>
                </div>
                <!-- /.btn-group -->
                <button type="button" class="btn btn-default btn-sm"><i class="fa fa-refresh"></i></button>
                <div class="pull-right">
                  ${fn:length(certsList)} in Total
                  <div class="btn-group">
                    <button type="button" class="btn btn-default btn-sm"><i class="fa fa-chevron-left"></i></button>
                    <button type="button" class="btn btn-default btn-sm"><i class="fa fa-chevron-right"></i></button>
                  </div>
                  <!-- /.btn-group -->
                </div>
                <!-- /.pull-right -->
              </div>
            </div>
          </div>
          <!-- /. box -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->