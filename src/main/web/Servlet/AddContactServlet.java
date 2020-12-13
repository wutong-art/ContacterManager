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

@WebServlet("/addContactServlet")
public class AddContactServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求编码
        request.setCharacterEncoding("utf-8");
        //获取所有数据
        Map<String, String[]> map = request.getParameterMap();
        //对数据进行封装
        Contact contact = new Contact();
        try {
            BeanUtils.populate(contact,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        Manager manager = (Manager) session.getAttribute("manager");
        UserService userService = new userServiceImpl();
        userService.addContact(contact,manager.getId());

        response.sendRedirect(request.getContextPath()+"/contactListServlet");



    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);


    }
}
