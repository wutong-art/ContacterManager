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

@WebServlet("/findContactServlet")
public class FindContactServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取id
        int id = Integer.parseInt(request.getParameter("id"));
        //调用查询
        userServiceImpl userService = new userServiceImpl();
        Contact contact = userService.findContactById(id);

        request.setAttribute("contact",contact);

        request.getRequestDispatcher("/update.jsp").forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);


    }
}
