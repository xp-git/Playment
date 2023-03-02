package cn.j3code.admire.service;

import cn.j3code.admire.controller.api.web.query.CommodityListQuery;
import cn.j3code.admire.controller.api.web.vo.CommodityVO;
import cn.j3code.admire.controller.api.web.vo.LookContentVO;
import cn.j3code.admire.controller.api.web.vo.PayCommodityVO;
import cn.j3code.admire.po.Commodity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author J3code
* @description 针对表【ap_commodity】的数据库操作Service
* @createDate 2023-02-04 12:01:32
*/
public interface CommodityService extends IService<Commodity> {
    IPage<CommodityVO> page(CommodityListQuery query);

    CommodityVO one(Long id);

    LookContentVO lookContent(Long id);

    PayCommodityVO payCommodity(Long id);

    void setCommodityLookNumber();

    void setCommodityBuyNumber();

}
