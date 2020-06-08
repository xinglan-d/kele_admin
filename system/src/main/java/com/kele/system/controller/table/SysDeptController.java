package com.kele.system.controller.table;

import com.kele.base.controller.BusinessController;
import com.kele.base.controller.BusinessInterface;
import com.kele.base.controller.Result;
import com.kele.base.service.ResultService;
import com.kele.base.vo.SelectVO;
import com.kele.base.vo.Selects;
import com.kele.system.dao.SysDeptDao;
import com.kele.system.dao.dto.DeptDO;
import com.kele.system.vo.DeptVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @description:部门控制层
 * @author: duzongyue
 * @createDate: 2020/05/09 11:42
 * @version: 1.0
 */

@RestController
@RequestMapping("/sysDept")
public class SysDeptController extends BusinessController<DeptVO, DeptDO> implements BusinessInterface<DeptDO> {

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


    @Override
    public void addBefore(DeptDO doData) {
        List<DeptDO> depts;
        String seq;
        if (StringUtils.isNotBlank(doData.getDeptPid())) {
            //查询父部门的seq
            Optional<DeptDO> byId = sysDeptDao.findById(doData.getDeptPid());
            DeptDO deptDO = byId.orElse(null);
            //获取父部门下所以的部门seq最高值
            depts = deptDO.getDepts();
            seq = deptDO.getSeq() + ".";
        } else {
            seq = "";
            depts = sysDeptDao.findAllBySeqIsNull();
        }
        int integer = 0;
        if (depts != null && depts.size() != 0) {
            integer = depts.stream().map(dept -> {
                String pSeq = dept.getSeq();
                if (pSeq == null) {
                    return 0;
                }
                String[] split = dept.getSeq().split("[.]");
                String s = split[split.length - 1];
                if (StringUtils.isNotBlank(s)) {
                    return Integer.valueOf(s);
                }
                return 0;
            })
                    .max(Comparator.naturalOrder())
                    .get();
        }

        seq += (integer + 1);
        doData.setSeq(seq);
    }
}
