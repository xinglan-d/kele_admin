package com.kele.system.service.impl;

import com.kele.base.util.RedisCacheUtil;
import com.kele.system.dao.BlockListDao;
import com.kele.system.dao.dto.BlockListDO;
import com.kele.system.service.BlockListService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/06/03 17:02
 * @version: 1.0
 */
@Service
public class BlockListServiceImpl implements BlockListService {

    private final BlockListDao blockListDao;
    private final RedisCacheUtil redisUtil;

    public BlockListServiceImpl(BlockListDao blockListDao, RedisCacheUtil redisUtil) {
        this.blockListDao = blockListDao;
        this.redisUtil = redisUtil;
    }

    @Override
    public void initBlockList() {
        List<BlockListDO> lists = blockListDao.findAll();
        List<String> urls = lists.stream().map(BlockListDO::getUrl).collect(Collectors.toList());
        redisUtil.deleteObject("blockList");
        redisUtil.setCacheList("blockList", urls);
    }
}
