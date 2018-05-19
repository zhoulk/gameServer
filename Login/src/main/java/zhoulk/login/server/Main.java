package zhoulk.login.server;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zlk on 2018/4/22.
 */
public class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        logger.info("加载Spring配置开始...");

        ApplicationContext context= new ClassPathXmlApplicationContext("loginServer.xml");

        //logger.info("加载Spring配置结束...");
    }
}
