package _temp._springMVC.basic;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


@Controller
public class _GoController implements EnvironmentAware
{
    //private final Log logger = LogFactory.getLog(_GoController.class);
    private Environment environment;

    @RequestMapping(value = {"/go"})
    public String index(Model model, HttpSession httpSession) throws Exception
    {
        //logger.info("======processed by index=======");
        System.out.println("======processed by index=======");
        System.out.println(httpSession.getId());
        model.addAttribute("msg", "Go Go Go!");
        //return "Go.jsp";
        return "index.html";
    }

    @Override
    public void setEnvironment(Environment environment)
    {
        this.environment = environment;
    }
}
