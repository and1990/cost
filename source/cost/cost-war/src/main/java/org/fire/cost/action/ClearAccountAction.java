package org.fire.cost.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.fire.cost.service.GroupAccountService;
import org.fire.cost.vo.GroupVO;
import org.fire.cost.vo.Message;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 注释：结算action
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-14 下午2:09
 */
@Namespace("/")
@Controller
public class ClearAccountAction extends BaseAction {
    @Resource
    private GroupAccountService groupAccountService;

    /**
     * 根据查询条件查询结算信息
     *
     * @param vo
     * @return
     */
    public Message getGroupAccountByFilter(GroupVO vo) {
        Message message = new Message();
        return message;
    }
}
