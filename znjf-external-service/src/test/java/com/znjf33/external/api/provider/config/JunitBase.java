package com.znjf33.external.api.provider.config;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>Title: JunitBase</p>
 * <p>Description: </p>
 *
 * @author maweijun
 * @date 17/11/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "classpath*:/application-local.yml"})
public class JunitBase{

}
