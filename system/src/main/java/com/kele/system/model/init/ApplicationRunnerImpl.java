package com.kele.system.model.init;

import com.kele.base.model.util.SpringUtil;
import com.kele.system.service.BlockListService;
import com.kele.system.service.impl.BlockListServiceImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author lnj
 * createTime 2018-11-07 22:37
 **/
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        BlockListService blockListService = SpringUtil.getBean(BlockListServiceImpl.class);
        blockListService.initBlockList();
    }
}
