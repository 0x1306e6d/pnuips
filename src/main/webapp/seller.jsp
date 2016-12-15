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
    <p>code : <%=seller.getSellercode()%>
    </p>
    <p>name : <%=seller.getSellername()%>
    </p>
    <p>total sell count : <%=sellerProcessor.searchSellCount(sellercode)%>
    </p>

    <div class="panel panel-default">
        <div class="panel-heading">Item List</div>
        <div class="panel-body">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>seller</th>
                    <th>name</th>
                    <th>brand</th>
                    <th>price</th>
                    <th>numberOfStock</th>
                    <th>numberOfSales</th>
                </tr>
                </thead>
                <tbody style="cursor: hand;">
                <%
                    List<SellBean> sellBeanList = sellProcessor.searchSellBeanListOfSeller(sellercode);

                    for (SellBean sellBean : sellBeanList) {
                %>
                <tr onclick="location.href='sell.jsp?sellercode=<%=sellBean.getSeller().getSellercode()%>&itemcode=<%=sellBean.getItem().getItemcode()%>'">
                    <td><%=sellBean.getSeller().getSellername()%>
                    </td>
                    <td><%=sellBean.getItem().getItemname()%>
                    </td>
                    <td><%=sellBean.getItem().getBrand()%>
                    </td>
                    <td><%=sellBean.getSell().getPrice()%>
                    </td>
                    <td><%=sellBean.getSell().getNumberOfStock()%>
                    </td>
                    <td><%=sellBean.getSell().getNumberOfSales()%>
                    </td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
    <%
        }
    %>
</div>
</body>
</html>
