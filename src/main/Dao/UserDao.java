package main.Dao;

import main.Domain.Contact;
import main.Domain.Manager;

import java.util.List;
import java.util.Map;

public interface UserDao {
    //查询该管理员下所有联系人
    public List<Contact> findAllContactsByManagerId(int managerid);
    //查询指定id的联系人
    public Contact findContactById(int id);
    //用作登录验证，查询数据库中是否有该管理员
    public Manager findManagerByManagernameAndPassword(String managername, String password);
    //给该管理员添加新的联系人
    public void addContact(Contact contact , int managerid);
    //删除指定id的联系人
    public void deleteContact(int id);
    //修改指定id的联系人
    public void updateContatct(Contact contact);
    //查询所有联系人数目
    public int findTotalCount(Map<String, String[]> condition);
    //根据起始索引和查询条数查询联系人
    List<Contact> findByPage(int start, int rows, Map<String, String[]> condition);
}
