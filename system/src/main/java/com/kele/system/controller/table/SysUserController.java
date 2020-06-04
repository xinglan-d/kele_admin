package com.kele.system.controller.table;

import com.kele.base.controller.BusinessController;
import com.kele.system.dao.dto.UserDO;
import com.kele.system.vo.UserVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:用户控制层
 * @author: duzongyue
 * @createDate: 2020/02/24 15:39
 * @version: 1.0
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController extends BusinessController<UserVO, UserDO> {


}
