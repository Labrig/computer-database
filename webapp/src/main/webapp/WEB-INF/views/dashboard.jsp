<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/css/font-awesome.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/css/main.css"/>" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="<c:url value="/"/>"> <spring:message code="application.title"/> </a>
        </div>
        <div class="container">
             <a class="btn btn-default" href="<c:url value="/LogoutProcess"/>"> <spring:message code="dashboard.logoutButton"/> </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <spring:message code="dashborad.computersFound" arguments="${totalComputer}"/>
            </h1>
            <p>${error}</p>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="<c:url value="/FindComputerByName"/>" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="<spring:message code="dashboard.searchPlaceholder"/>" />
                        <input type="submit" id="searchsubmit" value="<spring:message code="dashboard.searchButton"/>" class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="<c:url value="/AddComputer"/>"><spring:message code="dashboard.addComputerButton"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.editComputerButton"/></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="<c:url value="/DeleteComputer"/>" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <em class="fa fa-trash-o fa-lg"></em>
                                    </a>
                            </span>
                        </th>
                        <th>
                            <spring:message code="application.computerNameColumn"/>
                        </th>
                        <th>
                            <spring:message code="application.computerIntroColumn"/>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <spring:message code="application.computerDiscoColumn"/>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <spring:message code="application.companyColumn"/>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items="${computers}" var="computer" varStatus="status">
	                	<tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="${computer.getId()}">
	                        </td>
	                        <td>
	                            <a href="<c:url value="/EditComputer?idEditComputer=${computer.getId()}"/>" onclick="">${computer.getName()}</a>
	                        </td>
	                        <td>${computer.getIntroduced()}</td>
	                        <td>${computer.getDiscontinued()}</td>
	                        <td>${computer.getCompanyName()}</td>
	
	                    </tr>
					</c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
       		<div class="pull-left" >
        		<spring:message code="application.language"/> : <a href="?lang=en">English</a> | <a href="?lang=fr">Français</a>
        	</div>
            <ul class="pagination">
                <li>
                    <a href="<c:url value="/?pageNumber=${pageNumber-1}&pageSize=${pageSize}"/>" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
              <c:choose>
         
		         <c:when test = "${pageNumber < 3}">
			          <li><a href="<c:url value="/?pageNumber=1&pageSize=${pageSize}"/>">1</a></li>
		              <li><a href="<c:url value="/?pageNumber=2&pageSize=${pageSize}"/>">2</a></li>
		              <li><a href="<c:url value="/?pageNumber=3&pageSize=${pageSize}"/>">3</a></li>
		              <li><a href="<c:url value="/?pageNumber=4&pageSize=${pageSize}"/>">4</a></li>
		              <li><a href="<c:url value="/?pageNumber=5&pageSize=${pageSize}"/>">5</a></li>
		         </c:when>
		         
		         <c:when test = "${pageNumber > pageTotal-3}">
		         	  <li><a href="<c:url value="/?pageNumber=${pageTotal-4}&pageSize=${pageSize}"/>">${pageTotal-4}</a></li>
		              <li><a href="<c:url value="/?pageNumber=${pageTotal-3}&pageSize=${pageSize}"/>">${pageTotal-3}</a></li>
		              <li><a href="<c:url value="/?pageNumber=${pageTotal-2}&pageSize=${pageSize}"/>">${pageTotal-2}</a></li>
		              <li><a href="<c:url value="/?pageNumber=${pageTotal-1}&pageSize=${pageSize}"/>">${pageTotal-1}</a></li>
		              <li><a href="<c:url value="/?pageNumber=${pageTotal}&pageSize=${pageSize}"/>">${pageTotal}</a></li>
		         </c:when>
		         
		         <c:otherwise>
			          <li><a href="<c:url value="/?pageNumber=${pageNumber-2}&pageSize=${pageSize}"/>">${pageNumber-2}</a></li>
		              <li><a href="<c:url value="/?pageNumber=${pageNumber-1}&pageSize=${pageSize}"/>">${pageNumber-1}</a></li>
		              <li><a href="<c:url value="/?pageNumber=${pageNumber}&pageSize=${pageSize}"/>">${pageNumber}</a></li>
		              <li><a href="<c:url value="/?pageNumber=${pageNumber+1}&pageSize=${pageSize}"/>">${pageNumber+1}</a></li>
		              <li><a href="<c:url value="/?pageNumber=${pageNumber+2}&pageSize=${pageSize}"/>">${pageNumber+2}</a></li>
		         </c:otherwise>
		      </c:choose>
              <li>
                <a href="<c:url value="/?pageNumber=${pageNumber+1}&pageSize=${pageSize}"/>" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
           </ul>

        <div class="btn-group btn-group-sm pull-right" role="group" >
	        <form>
	            <a href="<c:url value="/?pageNumber=1&pageSize=10"/>"><button type="button" class="btn btn-default">10</button></a>
	            <a href="<c:url value="/?pageNumber=1&pageSize=50"/>"><button type="button" class="btn btn-default">50</button></a>
	            <a href="<c:url value="/?pageNumber=1&pageSize=100"/>"><button type="button" class="btn btn-default">100</button></a>
            </form>
        </div>
     </div>

   </footer>
<script src="<c:url value="/js/jquery.min.js"/>"></script>
<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/js/dashboard.js"/>"></script>

</body>
</html>