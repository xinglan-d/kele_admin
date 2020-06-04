package com.kele.system.controller.table;

import com.kele.base.controller.BusinessController;
import com.kele.base.controller.Result;
import com.kele.base.service.ResultService;
import com.kele.base.vo.SelectVO;
import com.kele.base.vo.Selects;
import com.kele.system.dao.SysDeptDao;
import com.kele.system.dao.dto.DeptDO;
import com.kele.system.vo.DeptVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:部门控制层
 * @author: duzongyue
 * @createDate: 2020/05/09 11:42
 * @version: 1.0
 */

@RestController
@RequestMapping("/sysDept")
public class SysDeptController extends BusinessController<DeptVO, DeptDO> {

    /**
     * 系统部门DAO
     */
    private SysDeptDao sysDeptDao;


    /**
     * 系统部门控制器
     *
     * @param sysDeptDao 系统部门dao
     */
    public SysDeptController(SysDeptDao sysDeptDao) {
        this.sysDeptDao = sysDeptDao;
    }


    /**
     * 部门树
     *
     * @param request 请求
     * @return {@link Result<Selects>}
     */
    @GetMapping("/tree")
    //TODO 差通过用户过滤部门信息
    public Result<Selects> deptTree(HttpServletRequest request) {
        List<DeptDO> sysDeptList = sysDeptDao.findAll();
        Selects selects = new Selects();
        sysDeptList.forEach(sysDept -> {
            selects.getSelects().add(new SelectVO(sysDept.getDeptId(), sysDept.getDeptPid(), sysDept.getDeptName()));
        });
        return ResultService.success(selects);
    }

}
