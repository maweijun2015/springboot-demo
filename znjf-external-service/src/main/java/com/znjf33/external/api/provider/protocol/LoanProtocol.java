package com.znjf33.external.api.provider.protocol;

import com.znjf33.common.utils.DateUtil;
import com.znjf33.common.utils.OrderNoUtils;
import com.znjf33.common.utils.PdfHelper;
import com.znjf33.common.utils.ProtocolBean;
import com.znjf33.general.api.service.SystemConfigService;
import com.znjf33.useraccount.api.dto.UserDto;
import com.znjf33.useraccount.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by henry on 2017/6/13.
 */
@Component("loanProtocol")
public class LoanProtocol extends ProtocolBean {

    @Autowired
    private UserService userService;

    @Autowired
    private SystemConfigService systemConfigService;


    /**
     *
     * @param buzId
     * @param content
     * @param userId
     */
    @Override
    public void executor(String buzId, int tenderId, String content, int userId){
        String realPath = systemConfigService.getConfigValue("upload_download_temp_path");
        String serialNumber = OrderNoUtils.getSerialNumber();
        this.downloadFileName = serialNumber + ".pdf";
        this.inPdfName = realPath + "/" + serialNumber + "-dest.pdf";
        this.pdf = PdfHelper.instance(inPdfName);

        Map<String, Object> data = prepareData(null, 0,userId);

        pdf.createPdf(inPdfName,content,data);
    }

    @Override
    public Map<String, Object> prepareData(String buzId, int tenderId, int userId) {
        Map<String,Object> data = new HashMap<>();

        UserDto bondUser = userService.getByUserId(userId);
        String username = bondUser.getUserName();
        String realname = bondUser.getRealName();
        String idNo = bondUser.getCardId();

        data.put("userName",username);
        data.put("realName",realname);
        data.put("idNo",idNo);
        data.put("date", DateUtil.getNow());
        return data;
    }
}
