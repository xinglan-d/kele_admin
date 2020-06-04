package com.kele.system.controller.table;

import com.kele.base.controller.BusinessController;
import com.kele.system.dao.dto.BlockListDO;
import com.kele.system.service.BlockListService;
import com.kele.system.vo.BlockListVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:部门控制层
 * @author: duzongyue
 * @createDate: 2020/05/09 11:42
 * @version: 1.0
 */

@RestController
@RequestMapping("/sysBlockList")
public class SysBlockListController extends BusinessController<BlockListVO, BlockListDO> {

    private final BlockListService blockListService;

    public SysBlockListController(BlockListService blockListService) {
        this.blockListService = blockListService;
    }

    @Override
    protected void afterEdit(BlockListVO vo) {
        blockListService.initBlockList();
    }
}
