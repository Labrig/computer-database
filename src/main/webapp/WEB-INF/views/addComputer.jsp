<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
            <a class="navbar-brand" href="<c:url value="/"/>"> <spring:message code="application.title"/> </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="addComputer.title"/></h1>
                    <form:form modelAttribute="dto">
                        <fieldset>
                        	<legend><spring:message code="modifyComputer.information"/></legend>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="application.computerNameColumn"/></label>
                                <form:input type="text" class="form-control" path="name" />
                                <form:errors path="name" />
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="application.computerIntroColumn"/></label>
                                <form:input type="date" class="form-control" path="introduced" />
                                <form:errors path="introduced" />
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="application.computerDiscoColumn"/></label>
                                <form:input type="date" class="form-control" path="discontinued" />
                                <form:errors path="discontinued" />
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="application.companyColumn"/></label>
                                <form:select class="form-control" path="companyId">
                                    <form:option value="0" label="--" />
					                <form:options items="${companies}" itemValue="id" itemLabel="name"/>
                                </form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="addComputer.addButton"/>" class="btn btn-primary">
                            <spring:message code="modifyComputer.or"/>
                            <a href="<c:url value="/"/>" class="btn btn-default"><spring:message code="modifyComputer.cancel"/></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
       		<spring:message code="application.language"/> : <a href="?lang=en">English</a> | <a href="?lang=fr">Français</a>
       	</div>
    </footer>
<script src="<c:url value="/js/jquery.min.js"/>"></script>
<script src="<c:url value="/js/validatorComputer.js"/>"></script>
</body>
</html>