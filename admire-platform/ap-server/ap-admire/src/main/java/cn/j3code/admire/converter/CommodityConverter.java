package cn.j3code.admire.converter;

import cn.j3code.admire.controller.api.web.vo.CommodityVO;
import cn.j3code.admire.po.Commodity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.user.converter
 * @createTime 2023/2/4 - 15:23
 * @description
 */
@Mapper(componentModel = "spring", typeConversionPolicy = ReportingPolicy.ERROR)//交给spring管理
public interface CommodityConverter {

    CommodityVO converter(Commodity commodity);
}
