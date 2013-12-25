package org.fire.cost.enums;

public enum ApproveEnum
{
	Not_Approved(1, "未审批"), Have_Approved(2, "未审批");

	private int code;
	private String name;

	private ApproveEnum(int code, String name)
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
		for (ApproveEnum enumType : ApproveEnum.values())
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
