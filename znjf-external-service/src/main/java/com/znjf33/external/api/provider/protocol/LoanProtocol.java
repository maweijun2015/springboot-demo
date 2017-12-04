package com.znjf33.external.api.provider.protocol;

import com.znjf33.common.utils.*;
import com.znjf33.external.api.dto.SupplyChainLmjParamDTO;
import com.znjf33.external.api.provider.domain.ProtocolInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by henry on 2017/6/13.
 */
@Component("loanProtocol")
public class LoanProtocol {
    protected String inPdfName;
    protected String downloadFileName;
    protected PdfHelper pdf;

    /**
     *
     * @param supplyChainLmjParamDTO
     * @param content
     */
    public void executor(SupplyChainLmjParamDTO supplyChainLmjParamDTO, String content){
        String serialNumber = OrderNoUtils.getSerialNumber();
        this.downloadFileName = serialNumber + ".pdf";
        this.inPdfName = supplyChainLmjParamDTO.getRealPath() + "/" + serialNumber + "-dest.pdf";
        this.pdf = PdfHelper.instance(inPdfName);

        Map<String, Object> data = prepareData(supplyChainLmjParamDTO);

        pdf.createPdf(inPdfName,content,data);
    }

    private Map<String, Object> prepareData(SupplyChainLmjParamDTO supplyChainLmjParamDTO) {
        Map<String,Object> data = new HashMap<>();

        ProtocolInfo protocolInfo = new ProtocolInfo();
        //乙方
        protocolInfo.setLoanUserRealName(supplyChainLmjParamDTO.getLoanUserRealName());
        protocolInfo.setLoanLegalUserName(supplyChainLmjParamDTO.getLoanLegalUserName());

        //借款明细
        protocolInfo.setLoanAmount(supplyChainLmjParamDTO.getLoanAmount());
        protocolInfo.setLoanApr(supplyChainLmjParamDTO.getLoanApr());
        protocolInfo.setLoanStartDate(supplyChainLmjParamDTO.getLoanStartDate());
        protocolInfo.setLoanEndDate(supplyChainLmjParamDTO.getLoanEndDate());
        protocolInfo.setLoanDuration(supplyChainLmjParamDTO.getDuration());
        protocolInfo.setLoanUse(supplyChainLmjParamDTO.getLoanUse());

        data.put("protocolInfo",protocolInfo);
        data.put("agreementNo",supplyChainLmjParamDTO.getAgreementNo());
        data.put("dealNoHead",supplyChainLmjParamDTO.getDealHeadNo());
        data.put("loanUserRealName",supplyChainLmjParamDTO.getLoanUserRealName());


        return data;
    }

    public String getInPdfName() {
        return inPdfName;
    }

    public void setInPdfName(String inPdfName) {
        this.inPdfName = inPdfName;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }

    public PdfHelper getPdf() {
        return pdf;
    }

    public void setPdf(PdfHelper pdf) {
        this.pdf = pdf;
    }
}
