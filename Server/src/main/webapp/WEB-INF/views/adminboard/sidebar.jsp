<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="<c:url value="/static/img/avatar5.png"/>" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>${loggedinuser}</p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="header">ADMIN DASHBOARD</li>
        <li>
          <a href="<c:url value="/adminboard/users" />">
            <i class="fa fa-user"></i> <span>User Management</span>
          </a>
        </li>
        <li>
          <a href="<c:url value="/adminboard/certs" />">
            <i class="fa fa-certificate"></i> <span>Cert Management</span>
          </a>
        </li>
        <li><a href="documentation/index.html"><i class="fa fa-book"></i> <span>Tutorial</span></a></li>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>