<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css" media="screen">
<link href="<c:url value="/css/font-awesome.css"/>" rel="stylesheet" type="text/css" media="screen">
<link href="<c:url value="/css/main.css"/>" rel="stylesheet" type="text/css" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        <p>id: ${editComputer.getId()}</p>
                    </div>
                    <h1>Edit Computer</h1>
                    <form action="<c:url value="/EditComputer"/>" method="POST">
                        <input type="hidden" value="${editComputer.getId()}" id="computerId" name="computerId"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                        	<legend>Computer information</legend>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name" value="${editComputer.getName()}" required>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" value="${editComputer.getIntroduced()}">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" value="${editComputer.getDiscontinued()}">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <option value="0">--</option>
                                    <c:forEach items="${companies}" var="company" varStatus="status">
                                    	<c:choose>
                                    		<c:when test="${company.getId().equals(editComputer.getCompanyId())}">
                                    			<c:set var="selected" value="selected" scope="page" />
                                    		</c:when>
                                    		<c:otherwise>
                                    			<c:set var="selected" value="" scope="page" />
                                    		</c:otherwise>
                                    	</c:choose>
					                	<option value="${company.getId()}" ${selected}>${company.getName()}</option>
									</c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="<c:url value="/"/>" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
<script src="<c:url value="/js/jquery.min.js"/>"></script>
<script src="<c:url value="/js/validatorComputer.js"/>"></script>
</body>
</html>