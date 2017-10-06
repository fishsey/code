package _temp._springMVC.basic;

/**
 * Created by root on 6/13/17.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class _HelloController
{

    @RequestMapping("/hello")
    public @ResponseBody
    String test(HttpSession httpsession)
    {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>       get ");
        System.out.println(httpsession.getId());
        return "hello, world! This com from spring!";
    }

}
