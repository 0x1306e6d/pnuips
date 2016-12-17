<%@ page import="kr.ac.pusan.pnuips.bean.SellBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getParameter("itemcode") == null || request.getParameter("sellercode") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    int itemcode = Integer.parseInt(request.getParameter("itemcode").toString());
    int sellercode = Integer.parseInt(request.getParameter("sellercode").toString());
%>
<jsp:useBean id="sellProcesspr" class="kr.ac.pusan.pnuips.processor.SellProcessor"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sell detail</title>

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
                <li><a href="bestseller.jsp">Best Seller</a></li>
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
    <%
        SellBean sellBean = sellProcesspr.searchSellBean(itemcode, sellercode);

        if (sellBean == null) {
    %>
    <div class="alert alert-danger">
        <strong>Error</strong>
        <p>Item is not exist</p>
    </div>
    <%
    } else {
    %>
    <div class="row">
        <div class="col-md-4">
            <p>price : <%=sellBean.getSell().getPrice()%>
            </p>
            <p>numberOfStock : <%=sellBean.getSell().getNumberOfStock()%>
            </p>
            <p>numberOfSales : <%=sellBean.getSell().getNumberOfSales()%>
            </p>
            <button type="button" class="btn btn-default btn-block">
                purchase
            </button>
            <button type="button" class="btn btn-default btn-block">
                go cart
            </button>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">Item</div>
                <div class="panel-body">
                    <p>code : <%=sellBean.getItem().getItemcode()%>
                    </p>
                    <p>name : <%=sellBean.getItem().getItemname()%>
                    </p>
                    <p>brand : <%=sellBean.getItem().getBrand()%>
                    </p>
                </div>
                <div class="panel-footer">
                    <button class="btn btn-default btn-block">detail</button>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">Seller</div>
                <div class="panel-body">
                    <p>code : <%=sellBean.getSeller().getSellercode()%>
                    </p>
                    <p>name : <%=sellBean.getSeller().getSellername()%>
                    </p>
                </div>
                <div class="panel-footer">
                    <button type="button" class="btn btn-default btn-block"
                            onclick="location.href='seller.jsp?sellercode=<%=sellBean.getSeller().getSellercode()%>'">
                        detail
                    </button>
                </div>
            </div>
        </div>
    </div>
    <%
        }
    %>
</div>
</body>
</html>
