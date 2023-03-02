package cn.j3code.admire.mapper;

import cn.j3code.admire.controller.api.web.query.CommodityListQuery;
import cn.j3code.admire.controller.api.web.vo.CommodityVO;
import cn.j3code.admire.po.Commodity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author J3code
* @description 针对表【ap_commodity】的数据库操作Mapper
* @createDate 2023-02-04 12:01:32
* @Entity cn.j3code.admire.po.Commodity
*/
public interface CommodityMapper extends BaseMapper<Commodity> {

    IPage<CommodityVO> page(@Param("page") Page<CommodityVO> page, @Param("query") CommodityListQuery query);

    CommodityVO one(@Param("id") Long id);

    int getLogCountByCommodityIdAndUserId(@Param("commodityId") Long commodityId, @Param("userId") Long userId);

    IPage<CommodityVO> pagePay(@Param("page") Page<CommodityVO> page, @Param("query") CommodityListQuery query);

    IPage<CommodityVO> pageNotPay(@Param("page") Page<CommodityVO> page, @Param("query") CommodityListQuery query);

    List<Commodity> getCommodityBuyNumber();

}




