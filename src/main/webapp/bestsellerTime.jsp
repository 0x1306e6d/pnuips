<%@ page import="kr.ac.pusan.pnuips.bean.SellBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    StringBuilder start = new StringBuilder();
    if (request.getParameter("startDate") != null
            && request.getParameter("startHour") != null
            && request.getParameter("startMinute") != null
            && request.getParameter("startSecond") != null) {
        start.append(request.getParameter("startDate")).append(' ');
        start.append(request.getParameter("startHour")).append(':');
        start.append(request.getParameter("startMinute")).append(':');
        start.append(request.getParameter("startSecond"));
    }
    StringBuilder end = new StringBuilder();
    if (request.getParameter("endDate") != null
            && request.getParameter("endHour") != null
            && request.getParameter("endMinute") != null
            && request.getParameter("endSecond") != null) {
        end.append(request.getParameter("endDate")).append(' ');
        end.append(request.getParameter("endHour")).append(':');
        end.append(request.getParameter("endMinute")).append(':');
        end.append(request.getParameter("endSecond"));
    }
%>
<jsp:useBean id="sellProcessor" class="kr.ac.pusan.pnuips.processor.SellProcessor"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bestseller</title>

    <jsp:include page="header.jsp"/>
</head>
<body>
<nav class="navbar navbar-inverse">
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
                <li class="dropdown active">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Best Seller<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="bestsellerSales.jsp">By numbef of sales</a></li>
                        <li class="active"><a href="bestsellerTime.jsp">Between time</a></li>
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
                <%
                    if (session.getAttribute("signin") == null) {
                %>
                <li><a href="signin.jsp"><span class="glyphicon glyphicon-log-in"></span> Sign in</a></li>
                <li><a href="signup.jsp"><span class="glyphicon glyphicon-user"></span> Sign up</a></li>
                <%
                } else {
                %>
                <li><a href="mypage.jsp"><span class="glyphicon glyphicon-user"></span> My Page</a></li>
                <li><a href="cart.jsp"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
                <li><a href="signout.jsp"><span class="glyphicon glyphicon-log-out"></span> Sign out</a></li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <form class="form-horizontal" action="bestsellerTime.jsp" method="get">
                <div class="form-group">
                    <div class="col-md-offset-1 col-md-10">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="startDate">startDate</label>
                                <input id="startDate" class="form-control" type="date" name="startDate" required>
                            </div>
                            <div class="col-md-2">
                                <label for="startHour">startHour</label>
                                <input id="startHour" class="form-control" type="number" name="startHour" min="0"
                                       max="23" value="0" required>
                            </div>
                            <div class="col-md-2">
                                <label for="startMinute">startMinute</label>
                                <input id="startMinute" class="form-control" type="number" name="startMinute" min="0"
                                       max="59" value="0" required>
                            </div>
                            <div class="col-md-2">
                                <label for="startSecond">startSecond</label>
                                <input id="startSecond" class="form-control" type="number" name="startSecond" min="0"
                                       max="59" value="0" required>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-1 col-md-10">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="endDate">endDate</label>
                                <input id="endDate" class="form-control" type="date" name="endDate" required>
                            </div>
                            <div class="col-md-2">
                                <label for="endHour">endHour</label>
                                <input id="endHour" class="form-control" type="number" name="endHour" min="0" max="23"
                                       value="0" required>
                            </div>
                            <div class="col-md-2">
                                <label for="endMinute">endMinute</label>
                                <input id="endMinute" class="form-control" type="number" name="endMinute" min="0"
                                       max="59" value="0" required>
                            </div>
                            <div class="col-md-2">
                                <label for="endSecond">endSecond</label>
                                <input id="endSecond" class="form-control" type="number" name="endSecond" min="0"
                                       max="59" value="0" required>
                            </div>
                        </div>
                    </div>
                </div>
                <button class="btn btn-default btn-block" type="submit">search</button>
            </form>
        </div>
    </div>

    <%
        List<SellBean> sellBeanList = sellProcessor.searchBestSellBeanListBetweenTime(start.toString(), end.toString());

        if (sellBeanList.size() == 0) {
    %>
    <div class="alert alert-info">
        There is no item.
    </div>
    <%
    } else {
    %>
    <h1 class="text-center">Search Result</h1>
    <ul class="list-group">
        <li class="list-group-item list-header">
            <div class="row">
                <div class="col-md-5">
                    <h4 class="text-center">
                        Item name
                    </h4>
                </div>
                <div class="col-md-3">
                    <h4 class="text-center">
                        Seller name
                    </h4>
                </div>
                <div class="col-md-4 row">
                    <div class="col-md-5">
                        <h4 class="text-center">
                            Brand
                        </h4>
                    </div>
                    <div class="col-md-5">
                        <h4 class="text-center">
                            Price
                        </h4>
                    </div>
                    <div class="col-md-1">
                        <h4 class="text-center">
                            Sales
                        </h4>
                    </div>
                </div>
            </div>
        </li>
        <%
            for (SellBean sellBean : sellBeanList) {
        %>
        <li class="list-group-item"
            onclick="location.href='sell.jsp?itemcode=<%=sellBean.getItem().getItemcode()%>&sellercode=<%=sellBean.getSeller().getSellercode()%>'"
            style="cursor: hand;">
            <div class="row">
                <div class="col-md-5">
                    <h4 class="text-center">
                        <%=sellBean.getItem().getItemname()%>
                    </h4>
                </div>
                <div class="col-md-3">
                    <h4 class="text-center">
                        <%=sellBean.getSeller().getSellername()%>
                    </h4>
                </div>
                <div class="col-md-4 row">
                    <div class="col-md-5">
                        <h4 class="text-center">
                            <%=sellBean.getItem().getBrand()%>
                        </h4>
                    </div>
                    <div class="col-md-5">
                        <h4 class="text-center">
                            <%=sellBean.getSell().getPrice()%>
                        </h4>
                    </div>
                    <div class="col-md-1">
                        <h4 class="text-center">
                            <span class="badge"><%=sellBean.getSell().getNumberOfSales()%></span>
                        </h4>
                    </div>
                </div>
            </div>
        </li>
        <%
            }
        %>
    </ul>
    <%
        }
    %>
</div>
</body>
</html>
