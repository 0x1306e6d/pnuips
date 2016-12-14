<%@ page import="kr.ac.pusan.pnuips.csv.ItemData" %>
<%@ page import="kr.ac.pusan.pnuips.csv.UserData" %>
<%@ page import="kr.ac.pusan.pnuips.processor.InsertDataProcessor" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: 0x130
  Date: 2016-12-14
  Time: 오후 3:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="insertDataBean" class="kr.ac.pusan.pnuips.bean.InsertDataBean"/>
<jsp:setProperty name="insertDataBean" property="userData"/>
<jsp:setProperty name="insertDataBean" property="itemData"/>
<jsp:useBean id="insertDataProcessor" class="kr.ac.pusan.pnuips.processor.InsertDataProcessor"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert data</title>

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
        List<UserData> userDataList = insertDataBean.csvToAccountData();
        for (UserData userData : userDataList) {
            InsertDataProcessor.InsertDataResult insertAccountResult = insertDataProcessor.insertAccountData(userData);

            switch (insertAccountResult) {
                case SUCCESS:
    %>
    <div class="alert alert-success">
        <strong>Success</strong>
        <p>Inserted account data.</p>
        <p>account data : <%=userData%>
        </p>
    </div>
    <%
            break;
        case SYSTEM_ERROR:
    %>
    <div class="alert alert-danger">
        <strong>Fail</strong>
        <p>Failed to insert account data.</p>
        <p>account data : <%=userData%>
        </p>
    </div>
    <%
                    break;
            }
        }
    %>

    <%
        List<ItemData> itemDataList = insertDataBean.csvToItemData();
        for (ItemData itemData : itemDataList) {
            InsertDataProcessor.InsertDataResult insertItemResult = insertDataProcessor.insertItemData(itemData);

            switch (insertItemResult) {
                case SUCCESS:
    %>
    <div class="alert alert-success">
        <strong>Success</strong>
        <p>Inserted item data.</p>
        <p>item data : <%=itemData%>
        </p>
    </div>
    <%
            break;
        case SYSTEM_ERROR:
    %>
    <div class="alert alert-danger">
        <strong>Fail</strong>
        <p>Failed to insert item data.</p>
        <p>item data : <%=itemData%>
        </p>
    </div>
    <%
                    break;
            }
        }
    %>
</div>
</body>
</html>
