<%@ page import="com.google.common.collect.Sets" %>
<%@ page import="kr.ac.pusan.pnuips.bean.SigninBean" %>
<%@ page import="org.apache.commons.lang3.tuple.ImmutableTriple" %>
<%@ page import="org.apache.commons.lang3.tuple.Triple" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="purchaseProcess" class="kr.ac.pusan.pnuips.processor.PurchaseProcessor"/>
<jsp:useBean id="couponProcessor" class="kr.ac.pusan.pnuips.processor.CouponProcessor"/>
<jsp:useBean id="accountProcessor" class="kr.ac.pusan.pnuips.processor.AccountProcessor"/>
<%
    if (session.getAttribute("signin") == null) {
        response.sendRedirect("signin.jsp");
        return;
    }
    SigninBean signinBean = (SigninBean) session.getAttribute("signin");
    Set<Triple<Integer, Integer, Integer>> items = Sets.newHashSet();
    Set<Integer> coupons = Sets.newHashSet();

    Enumeration names = request.getParameterNames();
    while (names.hasMoreElements()) {
        String name = names.nextElement().toString();
        if (name.startsWith("cart-")) {
            String[] codes = name.split("-");
            int itemcode = Integer.parseInt(codes[1]);
            int sellercode = Integer.parseInt(codes[2]);
            int count = Integer.parseInt(request.getParameter(name).toString());
            items.add(new ImmutableTriple<>(itemcode, sellercode, count));
        } else if (name.startsWith("coupon-")) {
            String[] codes = name.split("-");
            int type = Integer.parseInt(codes[1]);
            coupons.add(type);
        }
    }

    int discount = 0;
    for (Integer coupon : coupons) {
        discount += couponProcessor.getDiscount(coupon);
    }
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    for (Triple<Integer, Integer, Integer> item : items) {
        int itemcode = item.getLeft();
        int sellercode = item.getMiddle();
        int count = item.getRight();

        try {
            purchaseProcess.purchase(itemcode, sellercode, signinBean.getEmail(), count, discount);
            couponProcessor.deleteCoupons(signinBean.getEmail(), coupons);

            response.sendRedirect("mypage.jsp");
        } catch (SQLException e) {
            LoggerFactory.getLogger(page.getClass()).error("Failed to purchase item.", e);
        }
    }
%>
</body>
</html>
