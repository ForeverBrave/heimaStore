package com.hero.store.utils;

import com.hero.store.dao.UserDao;
import com.hero.store.domain.User;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class BeanFactory {

    //解析xml
    public static Object createObject(String name){
        //通过传递过来的name获取application.xml中name对应的class值

        try {
            //获取Document对象
            SAXReader reader = new SAXReader();
            //如果获取application.xml文件的输入流（application.xml文件必须位于src下）
            InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
            Document doc = reader.read(is);
            //通过Document对象获取根节点 beans
            Element rootElement = doc.getRootElement();
            //通过根节点获取到根节点下所有子节点的bean，返回集合
            List<Element> list = rootElement.elements();
            //遍历集合，判断每个元素上的id的值是否和当前的name值一致
            for (Element ele:list) {
                //ele相当于beans节点下的每个bean
                //获取到当前节点的id属性值
                String id = ele.attributeValue("id");
                //如果一致，获取到当前元素上的class属性值
                if(id.equals(name)){
                    String str = ele.attributeValue("class");
                    //通过反射创建对象并且返回
                    Class clazz = Class.forName(str);
                    //利用class值通过反射创建对象返回
                    return clazz.newInstance();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args)throws Exception{
        UserDao ud = (UserDao) BeanFactory.createObject("UserDao");
        User user = new User();
        user.setUsername("aaa");
        user.setPassword("aaa");
        User uu = ud.userLogin(user);
        System.out.println(uu);
    }
}
