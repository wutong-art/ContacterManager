package main.Dao.Impl;

import main.Dao.UserDao;
import main.Domain.Contact;
import main.Domain.Manager;
import main.Util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class userDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    //调用JDBC查询数据库
    public List<Contact> findAllContactsByManagerId(int managerid) {

        //1.定义sql
        String sql = "select * from contacts where managerid = ?";
        List<Contact> contacts = template.query(sql, new BeanPropertyRowMapper<Contact>(Contact.class),managerid);
        return contacts;
    }

    public Contact findContactById(int id) {
        try{
        String sql = "select * from contacts where id = ?";
        Contact contact = template.queryForObject(sql, new BeanPropertyRowMapper<Contact>(Contact.class), id);
        return contact;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public Manager findManagerByManagernameAndPassword(String managername, String password) {
        try{
        String sql = "select * from manager where managername=? and password=?";
        Manager manager = template.queryForObject(sql, new BeanPropertyRowMapper<Manager>(Manager.class), managername, password);
        return manager;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    public void addContact(Contact contact , int managerid) {
        String sql = "insert into contacts(id,name,gender,age,address,qq,email,managerid) values(null,?,?,?,?,?,?,?)";
        template.update(sql, contact.getName(), contact.getGender(), contact.getAge(), contact.getAddress(),
                contact.getQq(), contact.getEmail(),managerid);
    }

    public void deleteContact(int id) {
        String sql = "delete from contacts where id = ?";
        template.update(sql, id);
    }

    public void updateContatct(Contact contact) {
        String sql = "update contacts set gender=?,age=?,address=?,qq=?,email=? where id=?";
        template.update(sql,contact.getGender(),contact.getAge(),contact.getAddress(),contact.getQq(),contact.getEmail(),contact.getId());
    }

    public int findTotalCount(Map<String, String[]> condition) {
        //定义模板sql
        String sql = "select count(*) from contacts where 1 = 1";
        //遍历map
        StringBuffer sb = new StringBuffer(sql);
                Set<String> keys = condition.keySet();
                //定义参数的集合
                List<Object> params = new ArrayList<Object>();
                for (String key : keys) {
                    if("currentPage".equals(key) || "rows".equals(key)){
                        continue;
                    }
                    String value = condition.get(key)[0];
                    //判断value是否有值
                    if(value != null && !"".equals(value) ){
                        //有值
                        sb.append(" and "+key+" like ?");
                        params.add("%"+value+"%");//加条件的值
                    }

        }
        return template.queryForObject(sb.toString(),Integer.class,params.toArray());
    }


    public List<Contact> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from contacts where 1 = 1";

        StringBuffer sb = new StringBuffer(sql);
        Set<String> keys = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keys) {
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value) ){
                //有值
                sb.append(" and "+key+" like ?");
                params.add("%"+value+"%");//加条件的值
            }

        }
        sb.append(" limit ?,? ");
        params.add(start);
        params.add(rows);

        sql = sb.toString();

        System.out.println(sql);
        System.out.println(params);

        List<Contact> list = template.query(sql, new BeanPropertyRowMapper<Contact>(Contact.class), params.toArray());
        return list;
    }

}
