<%@ page import="kr.ac.pusan.pnuips.bean.SigninBean" %>
<%@ page import="kr.ac.pusan.pnuips.model.cart.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getParameter("itemcode") == null
            || request.getParameter("sellercode") == null
            || request.getParameter("count") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    if (session.getAttribute("signin") == null) {
        response.sendRedirect("signin.jsp");
        return;
    }
    int itemcode = Integer.parseInt(request.getParameter("itemcode").toString());
    int sellercode = Integer.parseInt(request.getParameter("sellercode").toString());
    int count = Integer.parseInt(request.getParameter("count").toString());
    SigninBean signinBean = (SigninBean) session.getAttribute("signin");
%>
<jsp:useBean id="cartProcessor" class="kr.ac.pusan.pnuips.processor.CartProcessor"/>
<html>
<head>
    <title>Add cart</title>
</head>
<body>
<%
    Cart cart = cartProcessor.addCart(itemcode, sellercode, signinBean.getEmail(), count);

    if (cart == null) {
%>
<div class="alert alert-error">
    <strong>Error</strong>
    <p>Failed to add cart.</p>
</div>
<%
    } else {
        response.sendRedirect("mypage.jsp");
        return;
    }
%>
</body>
</html>
