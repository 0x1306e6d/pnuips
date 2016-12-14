<%@ page import="kr.ac.pusan.pnuips.bean.SigninBean" %>
<%@ page import="kr.ac.pusan.pnuips.item.Item" %>
<%@ page import="kr.ac.pusan.pnuips.order.Order" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("signin") == null) {
        response.sendRedirect("index.jsp");
    }
%>
<jsp:useBean id="orderProcessor" class="kr.ac.pusan.pnuips.processor.OrderProcessor"/>
<jsp:useBean id="itemProcessor" class="kr.ac.pusan.pnuips.processor.ItemProcessor"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Page</title>

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
                <li class="active"><a href="#"><span class="glyphicon glyphicon-user"></span> My Page</a></li>
                <li><a href="signout.jsp"><span class="glyphicon glyphicon-log-out"></span> Sign out</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <%
        SigninBean signin = (SigninBean) session.getAttribute("signin");
    %>

    <div class="panel panel-default">
        <div class="panel-heading">Purchase List</div>
        <div class="panel-body">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>itemcode</th>
                    <th>purchaser</th>
                    <th>count</th>
                    <th>discount</th>
                    <th>time</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Order> orderList = orderProcessor.searchOrderList(signin.getEmail());

                    for (Order order : orderList) {
                        Item item = itemProcessor.searchItem(order.getItemcode());
                %>
                <tr>
                    <%
                        if (item == null) {
                    %>
                    <td>Unknown</td>
                    <%
                    } else {
                    %>
                    <td><%=item.getItemname()%>
                    </td>
                    <%
                        }
                    %>
                    <td><%=order.getPurchaser()%>
                    </td>
                    <td><%=order.getCount()%>
                    </td>
                    <td><%=order.getDiscount()%>
                    </td>
                    <td><%=order.getTime()%>
                    </td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
