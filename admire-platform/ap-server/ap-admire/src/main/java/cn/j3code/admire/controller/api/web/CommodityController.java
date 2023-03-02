package cn.j3code.admire.controller.api.web;

import cn.j3code.admire.controller.api.web.query.CommodityListQuery;
import cn.j3code.admire.controller.api.web.vo.CommodityVO;
import cn.j3code.admire.controller.api.web.vo.LookContentVO;
import cn.j3code.admire.controller.api.web.vo.PayCommodityVO;
import cn.j3code.admire.service.CommodityService;
import cn.j3code.common.annotation.ResponseResult;
import cn.j3code.config.constant.UrlPrefixConstants;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.controller.api.web
 * @createTime 2023/2/5 - 11:31
 * @description
 */
@Slf4j
@AllArgsConstructor
@ResponseResult
@RequestMapping(UrlPrefixConstants.V1 + "/commodity")
public class CommodityController {

    private final CommodityService commodityService;

    @GetMapping("/page")
    public IPage<CommodityVO> page(CommodityListQuery query) {
        return commodityService.page(query);
    }

    @GetMapping("/one")
    public CommodityVO one(@RequestParam("id") Long id) {
        return commodityService.one(id);
    }

    @GetMapping("/look-content")
    public LookContentVO lookContent(@RequestParam("id") Long id) {
        return commodityService.lookContent(id);
    }

    @GetMapping("/pay-commodity")
    public PayCommodityVO payCommodity(@RequestParam("id") Long id) {
        return commodityService.payCommodity(id);
    }


}
