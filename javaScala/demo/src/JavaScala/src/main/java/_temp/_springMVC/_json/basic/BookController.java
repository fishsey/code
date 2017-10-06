package _temp._springMVC._json.basic;

import com.alibaba.fastjson.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 肖文吉    36750064@qq.com
 * @version V1.0
 * @Description: <br>网站：<a href="http://www.fkit.org">疯狂Java</a>
 */
@Controller
public class BookController
{
    @ResponseBody
    @RequestMapping(value="/jsonSelf/testResponseBody")
    public Object getJson()
    {
        System.out.println("get json");
        List<Book> list = new ArrayList<Book>();
        list.add(new Book(1, "Spring MVC企业应用实战", "肖文吉"));
        list.add(new Book(2, "轻量级JavaEE企业应用实战", "李刚"));
        return list;
    }

    @RequestMapping(value = "/json/testRequestBody")
    public void setJson(@RequestBody Book book, HttpServletResponse response) throws Exception
    {
        System.out.println("process ... " + book);

        ObjectMapper mapper = new ObjectMapper();
        book.setAuthor("肖文吉");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(mapper.writeValueAsString(book));
    }

    @RequestMapping(value = "/jsonSelf/testRequestBody2")
    public void setJsonSelf2(HttpServletResponse response) throws Exception
    {
        System.out.println("process2 ... ");
        // 将book对象转换成json写出到客户端
        response.getWriter().println("is ok");
    }


    // @RequestBody根据json数据，转换成对应的Object
    @RequestMapping(value="/jsonSelf/testRequestBody")
    public void setJsonSelf(@RequestBody Book book, HttpServletResponse response) throws Exception
    {
        System.out.println("process ... " + book);

        // JSONObject-lib包是一个beans,collections,maps,java arrays和xml和JSON互相转换的包。
        // 使用JSONObject将book对象转换成json输出
        book.setAuthor("肖文吉");
        response.setContentType("text/html;charset=UTF-8");
        // 将book对象转换成json写出到客户端
        response.getWriter().println(JSONObject.toJSONString(book));
    }


}
