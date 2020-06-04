package com.kele.system.vo;

import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.edit.FormColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.system.dao.dto.BlockListDO;
import lombok.Data;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/02/24 16:09
 * @version: 1.0
 */
@Data
public class BlockListVO extends BusinessBaseVO<BlockListDO> {

    @BusinessColumn(value = "介绍")
    @TableColumn(search = true)
    @FormColumn(required = true)
    private String name;

    @BusinessColumn(value = "连接")
    @TableColumn(search = true)
    @FormColumn(required = true)
    private String url;
}
