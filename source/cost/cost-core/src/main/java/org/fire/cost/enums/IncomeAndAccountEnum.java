package org.fire.cost.enums;

/**
 * 收支类型，1：收入；2：支出
 *
 * @author liutengfei
 */
public enum IncomeAndAccountEnum {
    Income(1, "收入"), Account(2, "支出");

    private int code;
    private String name;

    private IncomeAndAccountEnum(int code, String name) {
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
        for (IncomeAndAccountEnum enumType : IncomeAndAccountEnum.values()) {
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
        for (IncomeAndAccountEnum enumType : IncomeAndAccountEnum.values()) {
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
