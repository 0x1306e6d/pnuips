<%@ page import="kr.ac.pusan.pnuips.bean.SellBean" %>
<%@ page import="kr.ac.pusan.pnuips.bean.SigninBean" %>
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
<jsp:useBean id="couponProcessor" class="kr.ac.pusan.pnuips.processor.CouponProcessor"/>
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
        <div class="col-md-6">
            <div class="jumbotron">
                <h1 class="text-center">
                    <%=sellBean.getItem().getItemname()%>
                </h1>
            </div>

            <%
                if (session.getAttribute("signin") != null) {
            %>
            <div class="text-center">
                <div class="btn-group btn-group-lg">
                    <button type="button" class="btn btn-default">Add cart</button>
                    <button type="button" class="btn btn-default"
                            onclick="location.href='purchase.jsp?itemcode=<%=itemcode%>&sellercode=<%=sellercode%>'">
                        Purchase
                    </button>
                </div>
            </div>
            <%
            } else {
            %>
            <div class="alert alert-info">
                You have to sign in to purchase or add cart this item.
            </div>
            <%
                }
            %>
        </div>
        <div class="col-md-6">
            <ul class="list-group">
                <li class="list-group-item">
                    <label for="brand">brand</label>
                    <h4 id="brand" class="text-center">
                        <%=sellBean.getItem().getBrand()%>
                    </h4>
                </li>
                <li class="list-group-item">
                    <label for="price">price</label>
                    <h4 id="price" class="text-center">
                        <%=sellBean.getSell().getPrice()%>
                    </h4>
                </li>
                <li class="list-group-item"
                    onclick="location.href='seller.jsp?sellercode=<%=sellBean.getSeller().getSellercode()%>'"
                    style="cursor: hand;">
                    <label for="sellerName">sellerName</label>
                    <h4 id="sellerName" class="text-center">
                        <%=sellBean.getSeller().getSellername()%>
                    </h4>
                </li>
                <li class="list-group-item">
                    <label for="numberOfStock">numberOfStock</label>
                    <h4 id="numberOfStock" class="text-center">
                        <%=sellBean.getSell().getNumberOfStock()%>
                    </h4>
                </li>
                <li class="list-group-item">
                    <label for="numberOfSales">numberOfSales</label>
                    <h4 id="numberOfSales" class="text-center">
                        <%=sellBean.getSell().getNumberOfSales()%>
                    </h4>
                </li>
            </ul>
        </div>
    </div>
    <%
        if (session.getAttribute("signin") != null) {
            SigninBean signinBean = (SigninBean) session.getAttribute("signin");
    %>
    <div id="add-cart-modal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Add cart</h4>
                </div>
                <div class="modal-body">
                    <p>Do you really want to add cart?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">add</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">close</button>
                </div>
            </div>
        </div>
    </div>
    <%
        }
    %>
    <%
        }
    %>
</div>
</body>
</html>
