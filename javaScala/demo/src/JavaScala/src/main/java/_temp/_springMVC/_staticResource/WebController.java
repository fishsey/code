package _temp._springMVC._staticResource;


import _temp._springMVC.entity.Names;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController
{

    @RequestMapping(value = "/staticResource/index", method = RequestMethod.GET)
    public ModelAndView index()
    {
        //return "demos/_staticResource/index.jsp";
        return new ModelAndView("_staticResource/index.jsp", "command", new Names());
        //return new ModelAndView("_staticResource/index.jsp");
    }

    @RequestMapping(value = {"/staticResource/staticPage"})
    public String redirct(Names names)
    {
        System.out.println(names.getName());
        return "_staticResource/final.html";
    }

}
