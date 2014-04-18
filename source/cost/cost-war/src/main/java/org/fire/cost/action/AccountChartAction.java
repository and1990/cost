package org.fire.cost.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.vo.AccountVO;
import org.fire.cost.vo.TypeVo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * 注释：账单图表action
 * 时间：2014-04-19 上午12:58
 * 作者：liutengfei【刘腾飞】
 */
@Namespace("/")
@Controller
public class AccountChartAction extends BaseAction {

    //图表数据
    private List<TypeVo> voList;

    private AccountVO accountVO;

    /**
     * 根据类型查看图表
     *
     * @return
     */
    @Action(value = "getAccountByType", results = {@Result(type = "json", params = {"root", "voList", "contentType", "text/html"})})
    public String getAccountByType() {
        if (voList == null) {
            voList = new ArrayList<TypeVo>();
        }
        for (int i = 0; i < 5; i++) {
            TypeVo vo = new TypeVo();
            vo.setName("test" + i);
            vo.setCode(i * i);
            voList.add(vo);
        }
        return SUCCESS;
    }

    public AccountVO getAccountVO() {
        return accountVO;
    }

    public void setAccountVO(AccountVO accountVO) {
        this.accountVO = accountVO;
    }

    public List<TypeVo> getVoList() {
        return voList;
    }

    public void setVoList(List<TypeVo> voList) {
        this.voList = voList;
    }
}
