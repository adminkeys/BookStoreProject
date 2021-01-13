package com.hasaki_w_c.web;

import com.google.gson.Gson;
import com.hasaki_w_c.pojo.Book;
import com.hasaki_w_c.pojo.Cart;
import com.hasaki_w_c.pojo.CartItem;
import com.hasaki_w_c.service.BookService;
import com.hasaki_w_c.service.impl.BookServiceImpl;
import com.hasaki_w_c.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hasaki_w_c
 * @version 1.0
 * @date 2021/1/4 19:45
 */
@WebServlet("/cartServlet")
public class CartServlet extends BaseServlet {

    private BookService bookService = new BookServiceImpl();

    /**
     * 加入购物车
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的参数 商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 调用 bookService.queryBookById(id):Book 得到图书的信息
        Book book = bookService.queryBookById(id);
        // 把图书信息，转换成为 CartItem 商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        // 调用 Cart.addItem(CartItem);添加商品项
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);
        //System.out.println(cart);
        //System.out.println("请求头 Referer 的值：" + request.getHeader("Referer"));

        // 重定向回原来商品所在的地址页面
        response.sendRedirect(request.getHeader("Referer"));

        //最后一个添加的商品名称
        request.getSession().setAttribute("lastName",cartItem.getName());
    }

    /**
     * 删除商品项
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 获取购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            //删除了购物车商品
            cart.deleteItem(id);
            // 重定向回原来的购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }


    /**
     * 清空购物车
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取购物车对象
         Cart cart = (Cart) request.getSession().getAttribute("cart");
         if (cart != null) {
             //清空购物车
             cart.clear();
             // 重定向回原来的购物车展示页面
             response.sendRedirect(request.getHeader("Referer"));
         }
    }

    /**
     * 修改商品数量
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求的参数，商品编号，商品数量
        int id = WebUtils.parseInt(request.getParameter("id"),0);
        int count = WebUtils.parseInt(request.getParameter("count"),1);
        //获取 Cart 购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            //修改商品数量
            cart.updateCount(id,count);
            // 重定向回原来的购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求的参数 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        // 调用 bookService.queryBookById(id):Book 得到图书的信息
        Book book = bookService.queryBookById(id);
        // 把图书信息，转换成为 CartItem 商品项
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());
        // 调用 Cart.addItem(CartItem);添加商品项
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);
        // 最后一个添加的商品名称
        req.getSession().setAttribute("lastName", cartItem.getName());
        //6、返回购物车总的商品数量和最后一个添加的商品名称
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("totalCount", cart.getTotalCount());

        resultMap.put("lastName",cartItem.getName());
        Gson gson = new Gson();
        String resultMapJsonString = gson.toJson(resultMap);
        resp.getWriter().write(resultMapJsonString);
    }

}