import junit.framework.Assert;
import org.fire.cost.dao.StreamDao;
import org.fire.cost.domain.Stream;
import org.fire.cost.service.ClearAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.List;

/**
 * 注释：
 * 时间：2014年05月07日 下午6:01
 * 作者：刘腾飞[liutengfei]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath*:applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class StreamDaoTest {
    @Resource
    private StreamDao streamDao;

    @Resource
    private ClearAccountService clearAccountService;

    @Test
    public void getStreamData() {
        List<Stream> streamList = streamDao.findStreamByYear(2014);
        Assert.assertNotNull(streamList);
        System.out.println(streamList.size());
    }


    @Test
    public void updateClearDetailTest() {
        clearAccountService.updateClearDetail(2, 43L);
    }

}
