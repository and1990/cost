import junit.framework.Assert;
import org.fire.cost.dao.StreamDao;
import org.fire.cost.domain.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 注释：
 * 时间：2014年05月07日 下午6:01
 * 作者：刘腾飞[liutengfei]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath*:applicationContext.xml")
public class StreamDaoTest {
    @Resource
    private StreamDao streamDao;

    @Test
    public void getStreamData() {
        List<Stream> streamList = streamDao.findStreamByYear(2014);
        Assert.assertNotNull(streamList);
        System.out.println(streamList.size());
    }

    public void detetStreamData(){

    }

}
