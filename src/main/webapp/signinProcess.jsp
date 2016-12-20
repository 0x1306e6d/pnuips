<%@ page import="kr.ac.pusan.pnuips.processor.SigninProcessor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="signinBean" class="kr.ac.pusan.pnuips.bean.SigninBean"/>
<jsp:setProperty name="signinBean" property="email"/>
<jsp:setProperty name="signinBean" property="password"/>
<jsp:useBean id="signinProcessor" class="kr.ac.pusan.pnuips.processor.SigninProcessor"/>
<%
    if (session.getAttribute("signin") != null) {
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign in</title>

    <jsp:include page="header.jsp"/>

    <script type="text/javascript">
        function redirect() {
            setTimeout(function () {
                window.location.replace("signin.jsp");
            }, 2 * 1000);
        }
    </script>
</head>
<body>
<nav class="navbar navbar-inverse" style="margin-bottom: 0; border-radius: 0">
    <div class="container-fluid">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation bar</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.jsp">PNUIPS</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="index.jsp">Home</a></li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Best Seller<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="bestsellerAge.jsp">By numbef of sales</a></li>
                        <li><a href="bestsellerTime.jsp">Between time</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Manage<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="manageAccount.jsp">Account</a></li>
                        <li><a href="manageSeller.jsp">Seller</a></li>
                        <li><a href="manageStock.jsp">Stock</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form class="navbar-form" action="search.jsp" method="get">
                        <div class="form-group">
                            <input class="form-control" type="text" name="itemname" placeholder="search..">
                        </div>
                        <button class="btn btn-default" type="submit">
                            <i class="glyphicon glyphicon-search"></i>
                        </button>
                    </form>
                </li>
                <li class="active"><a href="signin.jsp">
                    <span class="glyphicon glyphicon-log-in"></span> Sign in</a>
                </li>
                <li><a href="signup.jsp">
                    <span class="glyphicon glyphicon-user"></span> Sign up</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="jumbotron text-center" style="margin-top: 0;">
    <h1>PNUIPS</h1>
    <p>PNU Item Purchase System</p>
</div>
<div class="container">
    <%
        SigninProcessor.SigninResult result = signinProcessor.signin(signinBean);

        switch (result) {
            case SUCCESS:
                session.setAttribute("signin", signinBean);
                response.sendRedirect("index.jsp");
                break;
            case INVALID_PASSWORD:
    %>
    <div class="alert alert-danger">
        <h3 class="alert-heading">Invalid password</h3>
        <p>Redirect signin page 2 seconds later...</p>
    </div>
    <script>
        redirect();
    </script>
    <%
            break;
        case NOT_EXISTS_ACCOUNT:
    %>
    <div class="alert alert-danger">
        <h3 class="alert-heading">Not exists account</h3>
        <p>Redirect signin page 2 seconds later...</p>
    </div>
    <script>
        redirect();
    </script>
    <%
            break;
        case SYSTEM_ERROR:
    %>
    <div class="alert alert-danger">
        <h3 class="alert-heading">System error</h3>
        <p>Redirect signin page 2 seconds later...</p>
    </div>
    <script>
        redirect();
    </script>
    <%
                break;
        }
    %>
</div>
</body>
</html>
