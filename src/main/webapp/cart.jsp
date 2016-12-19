<%@ page import="kr.ac.pusan.pnuips.bean.SellBean" %>
<%@ page import="kr.ac.pusan.pnuips.bean.SigninBean" %>
<%@ page import="kr.ac.pusan.pnuips.model.cart.Cart" %>
<%@ page import="kr.ac.pusan.pnuips.model.coupon.CouponType" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("signin") == null) {
        response.sendRedirect("signin.jsp");
        return;
    }
    int cartSize = 0;
    int couponSize = 0;
    SigninBean signinBean = (SigninBean) session.getAttribute("signin");
%>
<jsp:useBean id="cartProcessor" class="kr.ac.pusan.pnuips.processor.CartProcessor"/>
<jsp:useBean id="itemProcessor" class="kr.ac.pusan.pnuips.processor.ItemProcessor"/>
<jsp:useBean id="sellProcessor" class="kr.ac.pusan.pnuips.processor.SellProcessor"/>
<jsp:useBean id="sellerProcessor" class="kr.ac.pusan.pnuips.processor.SellerProcessor"/>
<jsp:useBean id="couponProcessor" class="kr.ac.pusan.pnuips.processor.CouponProcessor"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cart</title>

    <!-- Bootstrap -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

    <!-- Custom -->
    <link rel="stylesheet" href="css/style.css">
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
    <h1 class="text-center">Cart List</h1>
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
    <form action="purchaseProcess.jsp" method="post">

        <ul class="list-group">
            <%
                cartSize = cartList.size();
                for (int i = 0; i < cartSize; i++) {
                    Cart cart = cartList.get(i);
                    SellBean sell = sellProcessor.searchSellBean(cart.getItemcode(), cart.getSellercode());
            %>
            <li class="list-group-item">
                <div class="row">
                    <div class="col-md-4">
                        <h4 class="text-center">
                            <%=sell.getItem().getItemname()%>
                        </h4>
                    </div>
                    <div class="col-md-3">
                        <h4 class="text-center">
                            <%=sell.getSeller().getSellername()%>
                        </h4>
                    </div>
                    <div class="col-md-2">
                        <h4 id="cart-price-<%=i%>" class="text-center">
                            <%=sell.getSell().getPrice()%>
                        </h4>
                    </div>
                    <div class="col-md-2">
                        <input id="cart-count-<%=i%>"
                               class="form-control" type="number" name="count" value="<%=cart.getCount()%>" min="1"
                               max="<%=sell.getSell().getNumberOfStock()%>" onchange="change()">
                    </div>
                    <div class="col-md-1">
                        <input id="cart-checkbox-<%=i%>"
                               class="form-control" type="checkbox" name="check" onchange="change()">
                    </div>
                </div>
            </li>
            <%
                }
            %>
        </ul>
        <br>
        <%
            List<CouponType> couponTypeList = couponProcessor.searchCouponList(signinBean.getEmail());

            if (couponTypeList.size() == 0) {
        %>
        <div class="alert alert-info">
            There is no coupon.
        </div>
        <%
        } else {
        %>
        <h1 class="text-center">Available Coupons</h1>
        <ul class="list-group">
            <%
                couponSize = couponTypeList.size();
                for (int i = 0; i < couponSize; i++) {
                    CouponType couponType = couponTypeList.get(i);
            %>
            <li class="list-group-item">
                <div class="row">
                    <div class="col-md-6">
                        <h4 class="text-center">
                            <%=couponType.getName()%>
                        </h4>
                    </div>
                    <div class="col-md-4">
                        <h4 id="coupon-discount-<%=i%>" class="text-center">
                            <%=couponType.getDiscount()%>%
                        </h4>
                    </div>
                    <div class="col-md-2">
                        <input id="coupon-checkbox-<%=i%>" class="form-control" type="checkbox" name="coupon"
                               onchange="change()">
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
        <div class="row">
            <div class="col-md-6">
                <h2 class="text-center">
                    Price
                </h2>
            </div>
            <div class="col-md-6">
                <h2 id="price" class="text-center"></h2>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <h2 class="text-center">
                    Discount
                </h2>
            </div>
            <div class="col-md-6">
                <h2 id="discount" class="text-center"></h2>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-md-6">
                <h2 class="text-center">
                    Total
                </h2>
            </div>
            <div class="col-md-6">
                <h2 id="total" class="text-center"></h2>
            </div>
        </div>
        <div class="row">
            <div class="col-md-offset-3 col-md-6">
                <button class="btn btn-default btn-block" type="submit">purchase</button>
            </div>
        </div>
    </form>
    <%
        }
    %>
</div>
<script type="text/javascript">
    function change() {
        var price = 0;
        var discount = 0;
        var discountPercentage = 0;
        var total = 0;

        for (var i = 0; i < <%=cartSize%>; i++) {
            var itemPrice = Number($('#cart-price-' + i).text());
            var count = Number($('#cart-count-' + i).val());
            var checked = $('#cart-checkbox-' + i).is(':checked');

            if (checked == true) {
                price += (itemPrice * count);
            }
        }

        for (var i = 0; i < <%=couponSize%>; i++) {
            var couponDiscount = Number($('#coupon-discount-' + i).text().replace('%', ''));
            var checked = $('#coupon-checkbox-' + i).is(':checked');

            if (checked == true) {
                discountPercentage += couponDiscount;
            }
        }

        discount = price * (discountPercentage / 100);
        total = price - discount;

        updateUI(price, discount, total);
    }

    function updateUI(price, discount, total) {
        $('#price').text(price);
        $('#discount').text("-" + discount);
        $('#total').text(total);
    }

    (function () {
        change();
    }());
</script>
</body>
</html>
