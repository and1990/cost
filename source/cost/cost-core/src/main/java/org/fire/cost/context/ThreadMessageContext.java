package org.fire.cost.context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liutengfei
 */
public class ThreadMessageContext
{

    public static final List<UserContext> userContextList = new ArrayList<UserContext>();


    private static final ThreadLocal<UserContext> threadLocal = new ThreadLocal<UserContext>();

    public static void set(UserContext userContext)
    {
        if (userContext != null)
        {
            threadLocal.remove();
            threadLocal.set(userContext);
        }

    }

    public static UserContext getUserContext()
    {
        UserContext userContext = threadLocal.get();

        return userContext;
    }

}
