package org.fire.cost.enums;

/**
 * 结算方式枚举
 *
 * @author liutengfei
 */
public enum ClearTypeEnum {
    All(1, "全部分摊"), Part(2, "部分分摊"), Single(3, "个人消费");

    private int code;
    private String name;

    private ClearTypeEnum(int code, String name) {
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
        for (ClearTypeEnum enumType : ClearTypeEnum.values()) {
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
        for (ClearTypeEnum enumType : ClearTypeEnum.values()) {
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
