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
          <img src="<c:url value="/static/img/avatar2.png"/>" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>${loggedinuser}</p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="header">MAIN DASHBOARD</li>
        <li>
          <a href="<c:url value="/dashboard/profile" />">
            <i class="fa fa-user"></i> <span>User Profile</span>
          </a>
        </li>
        <li>
          <a href="<c:url value="/dashboard/certificates" />">
            <i class="fa fa-certificate"></i> <span>Certificates</span>
          </a>
        </li>
        <li><a href="documentation/index.html"><i class="fa fa-book"></i> <span>Tutorial</span></a></li>
        <li class="header">SETTINGS</li>
        <li><a href="#"><i class="fa fa-circle-o text-red"></i> <span>Setting 1</span></a></li>
        <li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>Setting 2</span></a></li>
        <li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>Setting 3</span></a></li>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>