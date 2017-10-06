package _temp._springMVC._formHandling;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentController
{

    //在JSP中使用<form:form>标签，spring框架需要一个名称为“command”的对象文件
    //调用student()方法时，它返回 demos/_formHandling/student.jsp视图
    @RequestMapping(value = "/formHandling/student", method = RequestMethod.GET)
    public ModelAndView student()
    {
        System.out.println("get ModelAndView");
        return new ModelAndView("demos/_formHandling/student.jsp", "command", new Student());

        //or just use simple below:
        //return "demos/_formHandling/student.jsp";

    }


    @RequestMapping(value = "/formHandling/addStudent", method = RequestMethod.POST)
    public String addStudent(@ModelAttribute("SpringWeb") Student student, ModelMap model)
    {
        System.out.println("get addStudent");

        model.addAttribute("name", student.getName());
        model.addAttribute("age", student.getAge());
        model.addAttribute("id", student.getId());

        return "_formHandling/result.jsp";
    }

}
