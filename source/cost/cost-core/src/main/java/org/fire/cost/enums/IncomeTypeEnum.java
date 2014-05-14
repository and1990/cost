package org.fire.cost.enums;

/**
 * 注释：收入方式
 * 时间：2014年05月06日 下午3:05
 * 作者：刘腾飞
 */
public enum IncomeTypeEnum {

    pay(1, "工资"), Interest(2, "利息"), Bonus(3, "奖金"), Other(4, "其他");

    private int code;

    private String name;

    private IncomeTypeEnum(int code, String name) {
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
        for (IncomeTypeEnum enumType : IncomeTypeEnum.values()) {
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
        for (IncomeTypeEnum enumType : IncomeTypeEnum.values()) {
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
