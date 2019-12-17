package yizhit.workerlib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.reactive.config.EnableWebFlux;
import yizhit.workerlib.timer.SelectQuartzAllUserInfo;
import yizhit.workerlib.timer.SelectQuartzArvhivesInfo;
import yizhit.workerlib.timer.SelectQuartzProjectInfof;
import yizhit.workerlib.timer.SelectQuartzUnitrInfo;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@ImportResource("classpath:quartz-config.xml")
//@EnableHystrixDashboard
//@EnableDiscoveryClient
//@EnableFeignClients
//@EnableHystrix
//@EnableZuulProxy
@EnableWebFlux
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"ccait.ccweb", "yizhit.workerlib"})
public class Application {

    private static final Logger log = LogManager.getLogger( Application.class );
    private static boolean uat = false;
    public static void main(String[] args) throws FileNotFoundException, MalformedURLException {

        String flag = System.getProperty("isuat");
        if("true".equals( flag )) {
            setUat( true );
        }

        else {
            setUat( false );
        }

        SpringApplication.run(Application.class, args);

        //SelectQuartzProjectInfof projectInfo = new SelectQuartzProjectInfof();
        // projectInfo.batchInsertProjectInfo();
        //SelectQuartzAllUserInfo userInfo = new SelectQuartzAllUserInfo();
        //userInfo.batchInsertArchivesInfo();
        //userInfo.updateAllUserUnit_ID();
        //SelectQuartzUnitrInfo selectQuartzUnitrInfo = new SelectQuartzUnitrInfo();
        //selectQuartzUnitrInfo.batchInsertUnitrInfo();
        SelectQuartzArvhivesInfo selectQuartzArvhivesInfo = new SelectQuartzArvhivesInfo();
        selectQuartzArvhivesInfo.batchInsertArvhivesInfo();
        //selectQuartzArvhivesInfo.updateArchivesPhoto();
        //SelectQuartzCheckWorkceInfo selectQuartzCheckWorkceInfo= new SelectQuartzCheckWorkceInfo();
        //selectQuartzCheckWorkceInfo.batchInsertCheckWorkceInfo();
        System.out.println("Workerlib has been started!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public static boolean isUat()
    {
        return uat;
    }

    private static void setUat( boolean isUat )
    {
        Application.uat = isUat;
    }
}
