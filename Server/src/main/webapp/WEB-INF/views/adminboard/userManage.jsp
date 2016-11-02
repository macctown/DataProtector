<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


    <!-- Main content -->
    <section class="content">

  	 <div class="content">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Users Management
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">Users Management</a></li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-md-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">User Info Table</h3>
            </div>
             <div class="col-md-2">
             	<c:url var="createUserUrl" value="/newuser" />
              	<a class="btn btn-block btn-success btn-sm" href="${createUserUrl}"><i class="fa fa-user"></i> Create</a>
              </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>#ID</th>
                  <th>SSO</th>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Email</th>
                  <th>Operation</th>
                </tr>
                </thead>
                <tbody>
                	<c:choose>
					    <c:when test="${fn:length(usersList) gt 0}">
					        <c:forEach items="${usersList}" var="user">
							     <tr>
				                    <td>#${user.id}</td>
				                    <td>${user.ssoId}</td>
				                    <td>${user.firstName}</td>
				                    <td>${user.lastName}</td>	
				                    <td>${user.email}</td>				   
				                    <td class="mailbox-date">
				                    	<c:url var="editUserUrl" value="/edit-user-${user.ssoId}" />
				                    	<a class="btn btn-block btn-info btn-sm" href="${editUserUrl}" target="_blank"><i class="fa fa-edit"></i> Edit</a>
				                    	<c:url var="deleteUserUrl" value="/delete-user-${user.ssoId}" />
				                    	<a class="btn btn-block btn-danger btn-sm" href="${deleteUserUrl}"><i class="fa fa-trash"></i> Delete</a>
				                    </td>
				                  </tr>
							</c:forEach>
					    </c:when>    
					    <c:otherwise>
					        <p>No Users For Now!</p>
					        <br />
					    </c:otherwise>
					</c:choose>
               
                </tbody>
                <tfoot>
                <tr>
                   <th>#ID</th>
                  <th>SSO</th>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Email</th>
                  <th>Operation</th>
                </tr>
                </tfoot>
              </table>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->
  </div>

    </section>
    <!-- /.content -->