package main.Service;


import main.Domain.Contact;
import main.Domain.Manager;
import main.Domain.PageBean;

import java.util.List;
import java.util.Map;

//用户管理的业务接口
public interface UserService {
    //根据id查询管理员
    public List<Contact> findAllContactsByManagerId(int managerid);
    //根据id查询联系人
    public Contact findContactById(int id);
    //验证管理员登录
    public Manager loginManager(Manager manager);
    //添加联系人
    public void addContact(Contact contact , int managerid);
    //删除联系人
    public void deleteContact(int id);
    //修改联系人信息
    public void updateContact(Contact contact);
    //批量删除联系人
    public void deleteSelectedContact(String[] uids);

    /**
     *分页条件查询
     * @param currentPage
     * @param rows
     * @param condition
     * @return
     */
    PageBean<Contact> findContactByPage(String currentPage, String rows, Map<String, String[]> condition);


}
