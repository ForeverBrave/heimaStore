package com.hero.store.web.servlet;

import com.hero.store.domain.Category;
import com.hero.store.domain.PageModel;
import com.hero.store.domain.Product;
import com.hero.store.service.CategoryService;
import com.hero.store.service.ProductService;
import com.hero.store.service.serviceimpl.CategoryServiceImpl;
import com.hero.store.service.serviceimpl.ProductServiceImpl;
import com.hero.store.utils.UUIDUtils;
import com.hero.store.utils.UploadUtils;
import com.hero.store.web.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminProductServlet",value = "/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
    //findAllProductsWithPage
    public String findAllProductsWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        //获取当前页
        int curNum = Integer.parseInt(req.getParameter("num"));
        //调用业务层查全部商品信息返回PageModel
        ProductService productService = new ProductServiceImpl();
        PageModel pageModel = productService.findAllProductsWithPage(curNum);
        //将PageModel放入request中
        req.setAttribute("page",pageModel);
        //转发到 /admin/product/list.jsp
        return "/admin/product/list.jsp";
    }

    //addProductUI
    public String addProductUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        CategoryService categoryService = new CategoryServiceImpl();
        //获取全部分类信息
        List<Category> list = categoryService.getAllCats();
        //将获取到的全部分类信息放入request中
        req.setAttribute("allCats",list);
        //转发到  /admin/product/add.jsp
        return "/admin/product/add.jsp";
    }

    //addProduct
    public String addProduct(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //存储表单中的数据
        Map<String,String> map = new HashMap<String,String>();
        //携带表单中的数据向service，dao
        Product product = new Product();
        try{
            //利用req.getInputStream()；获取到请求体中全部数据，进行拆分和封装
            DiskFileItemFactory fac = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(fac);
            List<FileItem> list = upload.parseRequest(req);
            //遍历集合
            for (FileItem item : list) {
                if(item.isFormField()){
                    //如果当前的FileItem对象是普通项
                    //将普通项上name属性的值作为键，将获取到的内容作为值，放入Map中
                    //{ username<==>tom,password<==>123456 }
                    map.put(item.getFieldName(),item.getString("utf-8"));
                }else {
                    //如果当前FileItem对象是上传项

                    //获取到原始的文件名称
                    String oldFileName = item.getName();
                    //获取到要保存文件的名称  ps： 111.doc   123412.doc
                    String newFileName = UploadUtils.getUUIDName(oldFileName);

                    //通过FileItem获取到输入流对象，通过输入流可以获取到图片二进制数据
                    InputStream is = item.getInputStream();
                    //获取到当前项目下products/3下的真实路径
                    String realPath = getServletContext().getRealPath("/products/3/");
                    String dir = UploadUtils.getDir(newFileName);
                    String path = realPath+dir;
                    //内存中声明一个目录
                    File newDir = new File(path);
                    if(!newDir.exists()){
                        newDir.mkdirs();
                    }
                    //在服务端创建一个空文件（文件后缀名必须和上传到服务端的文件名后缀一致）
                    File finalFile = new File(newDir,newFileName);
                    if(!finalFile.exists()){
                        finalFile.createNewFile();
                    }
                    //建立和空文件对应的输出流
                    OutputStream os = new FileOutputStream(finalFile);
                    //将输入流中的数据刷到输出流中
                    IOUtils.copy(is,os);
                    //释放资源
                    IOUtils.closeQuietly(is);
                    IOUtils.closeQuietly(os);
                    //向map中存入一个键值对的数据  userhead<======> /image/11.bmp
                    //{ username<==>tom,password<==>123456,userhead<==>image/11.bmg }
                    map.put("pimage","/products/3/"+dir+"/"+newFileName);
                }
            }

            //利用BeanUitls将Map中的数据填充到Product对象上
            BeanUtils.populate(product,map);
            product.setPid(UUIDUtils.getId());
            product.setPdate(new Date());
            product.setPflag(0);

            //调用service_dao将user上携带的数据存入数据仓库，重定向到查询全部商品信息路径
            ProductService productService = new ProductServiceImpl();
            productService.saveProduct(product);

            resp.sendRedirect("/store_v5/AdminProductServlet?method=findAllProductsWithPage&num=1");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
