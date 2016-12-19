<%@ page import="kr.ac.pusan.pnuips.bean.SellBean" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="sellProcessor" class="kr.ac.pusan.pnuips.processor.SellProcessor"/>
<%
    if (request.getParameter("itemname") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    String itemname = (String) request.getParameter("itemname");
    if (StringUtils.isEmpty(itemname)) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search</title>

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
                <li class="active"><a href="#">Home</a></li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Best Seller<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="bestsellerSales.jsp">By numbef of sales</a></li>
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
                            <input class="form-control" type="text" name="itemname" value="<%=itemname%>">
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
    <%
        List<SellBean> sellBeanList = sellProcessor.searchSellBeanListByName(itemname);
        List<SellBean> similarSellBeanList = sellProcessor.searchSellBeanListBySimilarName(itemname);

        if (sellBeanList.size() == 0 && similarSellBeanList.size() == 0) {
    %>
    <div class="alert alert-info">
        Item <%=itemname%> is not exist.
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
                <div class="col-md-2">
                    <h4 class="text-center">
                        Brand
                    </h4>
                </div>
                <div class="col-md-2">
                    <h4 class="text-center">
                        Price
                    </h4>
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
                <div class="col-md-2">
                    <h4 class="text-center">
                        <%=sellBean.getItem().getBrand()%>
                    </h4>
                </div>
                <div class="col-md-2">
                    <h4 class="text-center">
                        <%=sellBean.getSell().getPrice()%>
                    </h4>
                </div>
            </div>
        </li>
        <%
            }
            for (SellBean sellBean : similarSellBeanList) {
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
                <div class="col-md-2">
                    <h4 class="text-center">
                        <%=sellBean.getItem().getBrand()%>
                    </h4>
                </div>
                <div class="col-md-2">
                    <h4 class="text-center">
                        <%=sellBean.getSell().getPrice()%>
                    </h4>
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
