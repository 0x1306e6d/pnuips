<%@ page import="kr.ac.pusan.pnuips.bean.SigninBean" %>
<%@ page import="kr.ac.pusan.pnuips.model.cart.Cart" %>
<%@ page import="kr.ac.pusan.pnuips.model.item.Item" %>
<%@ page import="kr.ac.pusan.pnuips.model.sell.Seller" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("signin") == null) {
        response.sendRedirect("signin.jsp");
        return;
    }
    SigninBean signinBean = (SigninBean) session.getAttribute("signin");
%>
<jsp:useBean id="cartProcessor" class="kr.ac.pusan.pnuips.processor.CartProcessor"/>
<jsp:useBean id="itemProcessor" class="kr.ac.pusan.pnuips.processor.ItemProcessor"/>
<jsp:useBean id="sellerProcessor" class="kr.ac.pusan.pnuips.processor.SellerProcessor"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cart</title>

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
                            <input class="form-control" type="text" name="itemname" placeholder="search..">
                        </div>
                        <button class="btn btn-default" type="submit">
                            <i class="glyphicon glyphicon-search"></i>
                        </button>
                    </form>
                </li>
                <li><a href="mypage.jsp"><span class="glyphicon glyphicon-user"></span> My Page</a></li>
                <li class="active"><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
                <li><a href="signout.jsp"><span class="glyphicon glyphicon-log-out"></span> Sign out</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <%
        List<Cart> cartList = cartProcessor.searchCartListByOwener(signinBean.getEmail());

        if (cartList.size() == 0) {
    %>
    <div class="alert alert-info">
        There is no cart.
    </div>
    <%
    } else {
    %>
    <ul class="list-group">
        <%
            for (Cart cart : cartList) {
                Item item = itemProcessor.searchItem(cart.getItemcode());
                Seller seller = sellerProcessor.searchSeller(cart.getSellercode());
        %>
        <li class="list-group-item">
            <div class="row">
                <div class="col-md-6">
                    <h4 class="text-center">
                        <%
                            if (item == null) {
                        %>
                        Unknown
                        <%
                        } else {
                        %>
                        <%=item.getItemname()%>
                        <%
                            }
                        %>
                    </h4>
                </div>
                <div class="col-md-4">
                    <h4 class="text-center">
                        <%
                            if (seller == null) {
                        %>
                        Unknown
                        <%
                        } else {
                        %>
                        <%=seller.getSellername()%>
                        <%
                            }
                        %>
                    </h4>
                </div>
                <div class="col-md-2">
                    <h4 class="text-center">
                        <%=cart.getCount()%>
                    </h4>
                </div>
                <div class="col-md-2">
                    <input class="form-control" type="checkbox">
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
