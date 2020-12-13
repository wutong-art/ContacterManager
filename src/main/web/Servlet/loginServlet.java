package main.web.Servlet;

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

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        //2.获取数据
        //2.1获取用户输入验证码
        String verifycode = request.getParameter("verifycode");

        //验证码校验
        HttpSession session = request.getSession();
        String checkCode_session = (String) session.getAttribute("checkCode_session");
        session.removeAttribute("checkCode_session");//确保验证码一次性

        if(!checkCode_session.equalsIgnoreCase(verifycode)){
            //验证码不正确
            //提示信息
            request.setAttribute("login_msg","验证码错误！");
            //跳转登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);

        }

        //2.2获取用户输入参数集合
        Map<String, String[]> map = request.getParameterMap();
        //4.封装Manager对象
        Manager manager = new Manager();
        try {
            BeanUtils.populate(manager,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //5.调用Service查询
        UserService userService = new userServiceImpl();
        Manager loginmanager = userService.loginManager(manager);
        //6.判断是否登录成功
        if(loginmanager != null){
            //登录成功,将用户存入session
            session.setAttribute("manager",loginmanager);
            //跳转页面
            response.sendRedirect(request.getContextPath()+"/index.jsp");


        }else {
            request.setAttribute("login_msg","用户名或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
