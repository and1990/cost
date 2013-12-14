package org.fire.cost.enums;

/**
 * 消费类型枚举
 * 
 * @author liutengfei
 * 
 */
public enum AccountTypeEnum
{
	Food(1, "公共"), Life(2, "组"), Fun(3, "个人");

	private int code;
	private String name;

	private AccountTypeEnum(int code, String name)
	{
		this.code = code;
		this.name = name;
	}

	/**
	 * 根据名称得到码
	 * 
	 * @param name
	 * @return
	 */
	public static int getCode(String name)
	{
		for (ApproveEnum enumType : ApproveEnum.values())
		{
			if (name != null && name.equals(enumType.getName()))
			{
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
	public static String getName(int code)
	{
		for (AccountTypeEnum enumType : AccountTypeEnum.values())
		{
			if (code == enumType.getCode())
			{
				return enumType.getName();
			}
		}
		return null;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
