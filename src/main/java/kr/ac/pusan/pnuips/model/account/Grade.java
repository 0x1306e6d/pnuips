package kr.ac.pusan.pnuips.model.account;

public enum Grade {

    NORMAL(0),
    VIP(1),
    VVIP(2);

    private final int value;

    Grade(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Grade valueOf(int value) {
        switch (value) {
            case 0:
                return NORMAL;
            case 1:
                return VIP;
            case 2:
                return VVIP;
        }
        return NORMAL;
    }
}
