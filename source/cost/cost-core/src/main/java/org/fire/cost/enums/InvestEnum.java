package org.fire.cost.enums;

/**
 * 投资类型枚举：定期存款、活期存款、购买保险、购买基金、购买股票
 *
 * @author liutengfei
 */
public enum InvestEnum {
    Deposit(1, "存款"), Insurance(2, "保险"), Fund(3, "基金"), Shares(4, "股票");

    private int code;
    private String name;

    private InvestEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据名称得到码
     *
     * @param name
     * @return
     */
    public static int getCode(String name) {
        for (InvestEnum enumType : InvestEnum.values()) {
            if (name != null && name.equals(enumType.getName())) {
                return enumType.getCode();
            }
        }
        return -1;
    }

    /**
     * 根据码得到名称
     *
     * @param code
     * @return
     */
    public static String getName(int code) {
        for (InvestEnum enumType : InvestEnum.values()) {
            if (code == enumType.getCode()) {
                return enumType.getName();
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
