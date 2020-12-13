package main.Service.Impl;

import main.Dao.Impl.userDaoImpl;
import main.Dao.UserDao;
import main.Domain.Contact;
import main.Domain.Manager;
import main.Domain.PageBean;
import main.Service.UserService;

import java.util.List;
import java.util.Map;

public class userServiceImpl implements UserService {

    private UserDao userDao = new userDaoImpl();
    //查询所有用户信息
    public List<Contact> findAllContactsByManagerId(int managerid) {
        return userDao.findAllContactsByManagerId(managerid);
    }

    public Contact findContactById(int id) {
        return userDao.findContactById(id);
    }

    public Manager loginManager(Manager manager) {
        return userDao.findManagerByManagernameAndPassword
            (manager.getManagername(), manager.getPassword());
    }

    public void addContact(Contact contact , int managerid) {
        userDao.addContact(contact,managerid);
    }

    public void deleteContact(int id) {
        userDao.deleteContact(id);
    }

    public void updateContact(Contact contact) {
        userDao.updateContatct(contact);
    }

    //批量删除联系人
    public void deleteSelectedContact(String[] uids) {
        if(uids != null && uids.length>0) {
            for (String uid : uids) {
                userDao.deleteContact(Integer.parseInt(uid));
            }
        }
    }

    public PageBean<Contact> findContactByPage(String cp, String r, Map<String, String[]> condition) {

        int currentPage = Integer.parseInt(cp);
        int rows = Integer.parseInt(r);

        if(currentPage <=0){
            currentPage=1;
        }
        //1.创建空的PageBean对象
        PageBean<Contact> pageBean = new PageBean<Contact>();
        //2.设置参数
        //pageBean.setCurrentpage(currentPage);
        pageBean.setRows(rows);
        //3.调用dao查询totalCount
        int totalCount = userDao.findTotalCount(condition);
        pageBean.setTotalCount(totalCount);

        //5.计算总页码
        int totalPage = totalCount % rows ==0 ? totalCount/rows : totalCount/rows+1;
        pageBean.setTotalPage(totalPage);

        if(currentPage >= totalPage){
            currentPage = totalPage;
        }
        pageBean.setCurrentpage(currentPage);
        //4.调用dao查询每页人数
        int start = (currentPage-1)*rows;
        List<Contact> contacts =  userDao.findByPage(start,rows,condition);
        pageBean.setList(contacts);



        return pageBean;
    }
}
