package io.github.rothschil.war.web;

import io.github.rothschil.war.domain.entity.Location;
import io.github.rothschil.war.domain.service.LocationService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.rothschil.base.aop.annotation.ApplicationLog;
import io.github.rothschil.common.exception.DrunkardException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WCNGS@QQ.COM
 * @date 20/11/18 11:04
 * @since 1.0.0
 */
@Validated
@Api(value = "areas")
@RestController
@RequestMapping(value = "/areas")
public class LocationController {


    private LocationService locationService;

    @Autowired
    @Qualifier("locationService")
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * 请求参数在URL中，需要在 @ApiImplicitParam 中加上 "paramType="path""
     *
     * @param lv 层级
     * @return List<LocationEntity>
     */
    @ApiOperation(value = "获取行政区域列表", notes = "根据层级获取行政列表")
    @ApiImplicitParam(name = "lv", value = "层级", required = true, dataType = "Integer", paramType = "path")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/{lv}", method = RequestMethod.GET)
    public List<Location> getLocationListByLevel(@PathVariable(value = "lv") Integer lv) {
        return locationService.getLocationListByLevel(lv);
    }

    @ApplicationLog
    @GetMapping("/test")
    public Map<String, Object> test() {
        HashMap<String, Object> data = new HashMap<>(3);
        data.put("info", "测试成功");
        return data;
    }

    @ApplicationLog
    @GetMapping("/fail")
    public Integer error() {
        // 查询结果数
        throw new DrunkardException("没有数据");
    }

    @ApplicationLog
    @GetMapping("/vali")
    public Map<String, Object> testValidator() {
        HashMap<String, Object> data = new HashMap<>(3);
        data.put("info", "测试成功");
        return data;
    }


}

