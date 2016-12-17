<%@ page import="kr.ac.pusan.pnuips.bean.SellBean" %>
<%@ page import="kr.ac.pusan.pnuips.model.sell.Seller" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getParameter("sellercode") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    int sellercode = Integer.parseInt(request.getParameter("sellercode").toString());
%>
<jsp:useBean id="sellerProcessor" class="kr.ac.pusan.pnuips.processor.SellerProcessor"/>
<jsp:useBean id="sellProcessor" class="kr.ac.pusan.pnuips.processor.SellProcessor"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Seller detail</title>

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
        Seller seller = sellerProcessor.searchSeller(sellercode);

        if (seller == null) {
    %>
    <div class="alert alert-danger">
        <strong>Error</strong>
        <p>Seller is not exist</p>
    </div>
    <%
    } else {
    %>
    <div class="row">
        <div class="col-md-6">
            <div class="jumbotron">
                <h1 class="text-center">
                    <%=seller.getSellername()%>
                </h1>
            </div>
        </div>

        <div class="col-md-6">
            <ul class="list-group">
                <li class="list-group-item">
                    <label for="sellercode">sellercode</label>
                    <h4 id="sellercode" class="text-center">
                        <%=seller.getSellercode()%>
                    </h4>
                </li>
                <li class="list-group-item">
                    <label for="total-count">total count</label>
                    <h4 id="total-count" class="text-center">
                        <%=sellerProcessor.searchSellCount(sellercode)%>
                    </h4>
                </li>
            </ul>
        </div>
    </div>
    <br>
    <%
        List<SellBean> sellBeanList = sellProcessor.searchSellBeanListOfSeller(sellercode);

        if (sellBeanList.size() == 0) {
    %>
    <div class="alert alert-info">
        Seller does not sell anything.
    </div>
    <%
    } else {
    %>
    <ul class="list-group">
        <%
            for (SellBean sellBean : sellBeanList) {
        %>
        <li class="list-group-item"
            onclick="location.href='sell.jsp?itemcode=<%=sellBean.getItem().getItemcode()%>&sellercode=<%=sellBean.getSeller().getSellercode()%>'"
            style="cursor: hand;">
            <div class="row">
                <div class="col-md-7">
                    <h3 class="text-center">
                        <%=sellBean.getItem().getItemname()%>
                    </h3>
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
        }
    %>
</div>
</body>
</html>
