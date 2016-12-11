<%@ page import="kr.ac.pusan.pnuips.processor.SigninProcessor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="signinBean" class="kr.ac.pusan.pnuips.bean.SigninBean"/>
<jsp:setProperty name="signinBean" property="email"/>
<jsp:setProperty name="signinBean" property="password"/>
<jsp:useBean id="signinProcessor" class="kr.ac.pusan.pnuips.processor.SigninProcessor"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sign in</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <!-- Custom -->
    <link rel="stylesheet" href="css/style.css">
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
                <li><a href="bestseller.jsp">Best Seller</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
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
        case NO_EXISTS_ACCOUNT:
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
