package xyz.wongs.drunkard.oauth2.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 16:35
 * @since 1.0.0
 */
@RestController
@RequestMapping("admin")
public class MainController {

    @RequestMapping("/")
    public String index(){

        return "index" ;
    }

    @RequestMapping("/detail")
    public String hello(){
        return "hello" ;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
