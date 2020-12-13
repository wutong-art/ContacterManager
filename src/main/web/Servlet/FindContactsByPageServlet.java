package main.web.Servlet;

import main.Domain.Contact;
import main.Domain.Manager;
import main.Domain.PageBean;
import main.Service.Impl.userServiceImpl;
import main.Service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/findContactsByPageServlet")
public class FindContactsByPageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求编码
        request.setCharacterEncoding("utf-8");
        //获取请求参数 currentPage和 每页条数rows
        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");
        if(currentPage==null || "".equals(currentPage)){
            currentPage = "1";
        }
        if(rows == null || "".equals(rows)){
            rows = "5";
        }

        //获取条件查询的参数
        Map<String, String[]> condition = request.getParameterMap();



        //调用userService
        UserService userService = new userServiceImpl();
        PageBean<Contact> pb = userService.findContactByPage(currentPage,rows,condition);


        request.setAttribute("pb",pb);
        request.setAttribute("condition",condition);

        request.getRequestDispatcher("/list.jsp").forward(request,response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);


    }
}
