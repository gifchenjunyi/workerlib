package yizhit.workerlib.trigger;

import ccait.ccweb.annotation.OnInsert;
import ccait.ccweb.annotation.Trigger;
import ccait.ccweb.filter.RequestWrapper;
import ccait.ccweb.model.UserModel;
import ccait.ccweb.utils.EncryptionUtil;
import ccait.ccweb.utils.FastJsonUtils;
import ccait.ccweb.utils.ImageUtils;
import ccait.ccweb.utils.UploadUtils;
import org.apache.tomcat.jni.User;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import yizhit.workerlib.entites.AllUserInfo;
import yizhit.workerlib.entites.ArchivesInfo;
import yizhit.workerlib.interfaceuilt.QRCodeUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Scope("prototype")
@Trigger(tablename = "alluser") //触发器注解
public class AllUserTrigger {

    @Value("${entity.upload.workerlib.alluser.qr_code.path}")
    private String qrCodePath;     //图片地址

    @Value("${qrcode.width}")
    private int width;

    @Value("${qrcode.height}")
    private int height;

    @Value("${entity.security.encrypt.AES.publicKey:ccait}")
    private String md5PublicKey;

    @Value("${entity.security.encrypt.AES.publicKey:ccait}")
    private String aesPublicKey;

    @Value("${entity.encoding:UTF-8}")
    private String encoding;


    /***
     * 新增数据事件
     * @param list （提交的数据）
     * @param request （当前请求）
     * @throws Exception
     */
    @OnInsert
    public void onInsert(List<Map<String, Object>> list, HttpServletRequest request) throws Exception {
            for(Map item : list) {
                genQrCode(item, null, md5PublicKey, aesPublicKey, qrCodePath, encoding,width,height);
            }
            RequestWrapper wrapper = (RequestWrapper) request;
        wrapper.setPostParameter(list);
        }

    public static AllUserInfo genQrCode(Map item, UserModel userModel, String md5PublicKey, String aesPublicKey, String qrCodePath, String encoding,int width,int height) throws NoSuchAlgorithmException, java.sql.SQLException, IOException {
        if(userModel == null) {
            userModel = new UserModel();
        }

        String Idnum = (String) item.get("cwrIdnum");
        userModel.setUsername(Idnum);
        String passWord = null;
        if (Idnum.length() < 6) {
            passWord = "123456";
        } else {
            passWord = Idnum.substring(Idnum.length() - 6);
        }
        userModel.setCreateBy((long) item.get("createBy"));
        userModel.setCreateOn(new Date());
        userModel.setPath("0/1");
        if(item.get("userPath") != null) {
            userModel.setPath((String)item.get("userPath"));
        }
        userModel.setPassword(EncryptionUtil.md5(passWord, md5PublicKey, encoding));
        item.put("userid", userModel.insert());
        item.put("eafId", UUID.randomUUID().toString().replace("-", ""));

        UserModel js = userModel.where("[username]=#{username}").select("username,password").first();
        if (js != null){
            //把账号和密码拼接起来
            String IdumPass = js.getUsername() + js.getPassword();
            String token = EncryptionUtil.encryptByAES(IdumPass, aesPublicKey);
            String url = "/mobile/details?token=" + token + "&eafid=" + item.get("eafId");
            String filename = js.getUsername() + ".png";
            byte[] binary = QRCodeUtil.creatRrCode(url,width,height);
            String path = UploadUtils.upload(qrCodePath ,filename,binary);
            item.put("qr_code", path);
        }

        return FastJsonUtils.convert(item, AllUserInfo.class);
    }
}
