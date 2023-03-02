package cn.j3code.admire.mapper;

import cn.j3code.admire.controller.api.web.query.OrderListQuery;
import cn.j3code.admire.controller.api.web.vo.OrderVO;
import cn.j3code.admire.po.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* @author J3code
* @description 针对表【ap_order】的数据库操作Mapper
* @createDate 2023-02-04 12:01:46
* @Entity cn.j3code.admire.po.Order
*/
public interface OrderMapper extends BaseMapper<Order> {

    IPage<OrderVO> page(@Param("page") Page<OrderVO> page, @Param("query") OrderListQuery query);

    OrderVO getBy(@Param("userId") Long userId, @Param("commodityId") Long commodityId);
}




