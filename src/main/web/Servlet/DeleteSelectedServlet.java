package main.web.Servlet;

import main.Domain.Contact;
import main.Domain.Manager;
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

@WebServlet("/deleteSelectedServlet")
public class DeleteSelectedServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求编码
        request.setCharacterEncoding("utf-8");
        //获取所有数据
        String[] uids = request.getParameterValues("uid");
        //调用service删除
        UserService userService = new userServiceImpl();
        userService.deleteSelectedContact(uids);
        //跳转到查询servlet
        response.sendRedirect(request.getContextPath()+"/contactListServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
