package io.github.rothschil.war.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import io.github.rothschil.base.aop.annotation.ApplicationLog;
import io.github.rothschil.war.domain.entity.Location;
import io.github.rothschil.war.domain.service.LocationService;

/**
 * @author WCNGS@QQ.COM
 * @date 20/11/18 11:04
 * @since 1.0.0
*/
@Validated
@Controller
public class HtmlController {

    @Autowired
    @Qualifier("locationService")
    private LocationService locationService;

    /** idx 请求参数在URL中，需要在 @ApiImplicitParam 中加上 "paramType="path""
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:57
     * @Param
     * @return String
     **/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/list";
    }

    /** 查询列表 请求参数在URL中，需要在 @ApiImplicitParam 中加上 "paramType="path""
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:58
     * @param model MODEL
     * @param pageNum   页数
     * @param pageSize  每页大小
     * @return String
     **/
    @ApplicationLog
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Location location = new Location();
        location.setLv(0);
        Page<Location> pages = locationService.getList(location,pageNum,pageSize);
        model.addAttribute("pages", pages);
        return "location";
    }
}

