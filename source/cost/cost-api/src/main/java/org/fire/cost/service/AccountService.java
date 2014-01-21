package org.fire.cost.service;

import org.fire.cost.vo.AccountVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 账单service接口
 * 
 * @author liutengfei
 * 
 */
public interface AccountService
{

	/**
	 * 根据过滤条件查找账单
	 * 
	 * @param vo
	 * @return
	 */
	List<AccountVO> getAccountByFilter(AccountVO vo);

	/**
	 * 增加账单
	 * 
	 * @param vo
	 * @return
	 */
	boolean addAccount(AccountVO vo);

	/**
	 * 修改账单
	 * 
	 * @param vo
	 * @return
	 */
	boolean modifyAccount(AccountVO vo);

	/**
	 * 删除账单
	 * 
	 * @param accountId
	 * @return
	 */
	boolean deleteAccount(Long accountId);

    /**
     * 文件上传
     * @param request
     * @param response
     * @return
     */
    boolean fileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
