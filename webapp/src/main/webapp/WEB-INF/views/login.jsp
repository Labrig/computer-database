<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
            <a class="navbar-brand" href="<c:url value="/Login"/>"> <spring:message code="application.title"/> </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                	<h1><spring:message code="login.title"/></h1>
                	<c:if test="${error}">
                		<div>You can't login with this credentials</div>
                	</c:if>
                	<c:if test="${logout}">
                		<div>You have been logout</div>
                	</c:if>
					<form action="<c:url value="/LoginProcess" />" method="post">
						<fieldset>
							<legend><spring:message code="login.information"/></legend>
							<div class="form-group">
                                <label for="username"><spring:message code="login.username"/></label>
                                <input type="text" name="username" />
                            </div>
                            <div class="form-group">
                                <label for="password"><spring:message code="login.password"/></label>
                                <input type="password" name="password" />
                            </div>
                            <div class="form-group">
                                <input name="submit" type="submit" value="<spring:message code="login.button"/>" />
                            </div>
						</fieldset>
					</form>
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