package org.fire.cost.context;

/**
 * @author liutengfei
 */
public class ThreadMessageContext {

    private static final ThreadLocal<UserContext> threadLocal = new ThreadLocal<UserContext>();

    public static void set(UserContext userContext) {
        if (userContext != null) {
            threadLocal.remove();
            threadLocal.set(userContext);
        }
    }

    public static UserContext getUserContext() {
        UserContext userContext = threadLocal.get();

        return userContext;
    }

}
