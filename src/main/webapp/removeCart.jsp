<%@ page import="kr.ac.pusan.pnuips.bean.SigninBean" %>
<%@ page import="kr.ac.pusan.pnuips.model.cart.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getParameter("itemcode") == null
            || request.getParameter("sellercode") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    if (session.getAttribute("signin") == null) {
        response.sendRedirect("signin.jsp");
        return;
    }
    int itemcode = Integer.parseInt(request.getParameter("itemcode").toString());
    int sellercode = Integer.parseInt(request.getParameter("sellercode").toString());
    SigninBean signinBean = (SigninBean) session.getAttribute("signin");
%>
<jsp:useBean id="cartProcessor" class="kr.ac.pusan.pnuips.processor.CartProcessor"/>
<html>
<head>
    <title>Remove Cart</title>
</head>
<body>
<%
    Cart cart = cartProcessor.removeCart(itemcode, sellercode, signinBean.getEmail());

    if (cart == null) {
%>
<div class="alert alert-error">
    <strong>Error</strong>
    <p>Failed to remove cart.</p>
</div>
<%
    } else {
        response.sendRedirect("mypage.jsp");
        return;
    }
%>
</body>
</html>
