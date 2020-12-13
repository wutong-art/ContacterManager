package main.web.Servlet;


import main.Domain.Contact;
import main.Domain.Manager;
import main.Service.Impl.userServiceImpl;
import main.Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/contactListServlet")
public class ContactListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用userService完成查询
        HttpSession session = request.getSession();
        Manager manager = (Manager) session.getAttribute("manager");
        UserService userService = new userServiceImpl();
        List<Contact> contacts = userService.findAllContactsByManagerId(manager.getId());
        //2.将users存入request域
        request.setAttribute("contacts", contacts);
        //3.转发到list.jsp页面
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }
}
