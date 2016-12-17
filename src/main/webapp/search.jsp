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
<nav class="navbar navbar-inverse" style="margin-bottom: 0">
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
        List<SellBean> sellBeanList = sellProcessor.searchSellBeanListByName(itemname);
        List<SellBean> similarSellBeanList = sellProcessor.searchSellBeanListBySimilarName(itemname);

        if (sellBeanList.size() == 0 && similarSellBeanList.size() == 0) {
    %>
    <div class="alert alert-info">no item exist</div>
    <%
    } else {
    %>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>seller</th>
            <th>name</th>
            <th>brand</th>
            <th>price</th>
            <th>numberOfStock</th>
            <th>numberOfSaes</th>
        </tr>
        </thead>
        <tbody style="cursor: hand;">
        <%
            for (SellBean sellBean : sellBeanList) {
        %>
        <tr onclick="location.href='sell.jsp?itemcode=<%=sellBean.getItem().getItemcode()%>&sellercode=<%=sellBean.getSeller().getSellercode()%>'">
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
            for (SellBean sellBean : similarSellBeanList) {
        %>
        <tr onclick="location.href='sell.jsp?itemcode=<%=sellBean.getItem().getItemcode()%>&sellercode=<%=sellBean.getSeller().getSellercode()%>'">
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
    <%
        }
    %>
</div>
</body>
</html>
