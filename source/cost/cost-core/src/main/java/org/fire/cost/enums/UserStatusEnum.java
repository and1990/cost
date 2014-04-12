package org.fire.cost.enums;

/**
 * 用户状态枚举
 * 
 * @author liutengfei
 * 
 */
public enum UserStatusEnum
{
	Disable(1, "不可用"), Enable(2, "可用");

	private int code;
	private String name;

	private UserStatusEnum(int code, String name)
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
		for (UserStatusEnum enumType : UserStatusEnum.values())
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
		for (UserStatusEnum enumType : UserStatusEnum.values())
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
