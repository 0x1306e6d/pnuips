package kr.ac.pusan.pnuips.bean;

public class BestSellerBean {

    private int rank;
    private SellBean sellBean;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public SellBean getSellBean() {
        return sellBean;
    }

    public void setSellBean(SellBean sellBean) {
        this.sellBean = sellBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BestSellerBean{");
        sb.append("rank=").append(rank);
        sb.append(", sellBean=").append(sellBean);
        sb.append('}');
        return sb.toString();
    }
}
