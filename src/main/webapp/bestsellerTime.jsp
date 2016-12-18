<%@ page import="kr.ac.pusan.pnuips.bean.SellBean" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String start = StringUtils.EMPTY;
    String end = StringUtils.EMPTY;
    if (request.getParameter("start") != null) {
        start = (String) request.getParameter("start");
    }
    if (request.getParameter("end") != null) {
        end = (String) request.getParameter("end");
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
        <div class="col-md-offset-3 col-md-6">
            <form class="form-horizontal" action="bestsellerTime.jsp" method="get">
                <div class="form-group">
                    <label class="control-label col-md-2" for="start">start</label>
                    <div class="col-md-10">
                        <input id="start" class="form-control" type="datetime-local" name="start">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2" for="end">end</label>
                    <div class="col-md-10">
                        <input id="end" class="form-control" type="datetime-local" name="end">
                    </div>
                </div>
                <button class="btn btn-default btn-block" type="submit">search</button>
            </form>
        </div>
    </div>

    <%
        List<SellBean> sellBeanList = sellProcessor.searchBestSellBeanListBetweenTime(start, end);

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
