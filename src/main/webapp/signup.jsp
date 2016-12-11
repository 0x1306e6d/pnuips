<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sign up</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <!-- Custom -->
    <link rel="stylesheet" href="css/style.css">
</head>
<body style="background-color: #eeeeee">
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
                <li><a href="signin.jsp"><span class="glyphicon glyphicon-log-in"></span> Sign in</a></li>
                <li class="active"><a href="#"><span class="glyphicon glyphicon-user"></span> Sign up</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="jumbotron text-center" style="margin-top: 0;">
    <h1>PNUIPS</h1>
    <p>PNU Item Purchase System</p>
</div>
<div class="container">
    <div class="col-md-offset-4 col-md-4">
        <form method="post" action="signupProcess.jsp">
            <div class="form-group">
                <label for="email">email</label>
                <input id="email" class="form-control" type="email" name="email" placeholder="Enter email" required/>
            </div>
            <div class="form-group">
                <label for="password">password</label>
                <input id="password" class="form-control" type="password" name="password" placeholder="Enter password"
                       required/>
            </div>
            <div class="form-group">
                <label for="name">name</label>
                <div id="name" class="row">
                    <div class="col-md-7">
                        <input id="firstname" class="form-control" type="text" name="firstname"
                               placeholder="firstname" required/>
                    </div>
                    <div class="col-md-5">
                        <input id="lastname" class="form-control" type="text" name="lastname"
                               placeholder="lastname" required/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="birthday">birthday</label>
                <input id="birthday" class="form-control" type="date" name="birthday" required/>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Sign up</button>
        </form>
    </div>
</div>
</body>
</html>
