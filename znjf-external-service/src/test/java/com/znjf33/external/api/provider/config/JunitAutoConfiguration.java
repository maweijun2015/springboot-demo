package com.znjf33.external.api.provider.config;

import com.znjf33.external.api.provider.mapper.SupplyChainLmjMapper;
import com.znjf33.external.api.provider.mapper.ZnjfExceptionRecordMapper;
import com.znjf33.external.api.service.CallbackExceptionService;
import com.znjf33.external.api.service.SupplyChainLmjService;
import com.znjf33.general.api.service.NoticeService;
import com.znjf33.general.api.service.SystemConfigService;
import com.znjf33.investment.api.service.BorrowUpdateService;
import com.znjf33.useraccount.api.service.UserSignatureService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

/**
 * <p>Title: JunitBase</p>
 * <p>Description: </p>
 *
 * @author maweijun
 * @date 17/11/16
 */
@Configuration
public class JunitAutoConfiguration {

    @Bean
    protected SupplyChainLmjMapper sweepstakesMapper() {
        return mock(SupplyChainLmjMapper.class);
    }
    @Bean
    protected NoticeService noticeService() {
        return mock(NoticeService.class);
    }
    @Bean
    protected SystemConfigService systemConfigService() {
        return mock(SystemConfigService.class);
    }
    @Bean
    protected UserSignatureService userSignatureService() {
        return mock(UserSignatureService.class);
    }
    @Bean
    protected BorrowUpdateService borrowUpdateService() {
        return mock(BorrowUpdateService.class);
    }
}
