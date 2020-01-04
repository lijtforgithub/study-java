package com.ljt.study.ee.jta;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LiJingTang
 * @date 2020-01-04 16:29
 */
@WebServlet(description = "全局事物测试", urlPatterns = { "/lijt/javaee/jta.htm" })
public class JTATest extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type").trim();
        String flag = request.getParameter("flag").trim();
        int key = StringUtils.isNotBlank(type) ? Integer.valueOf(type) : 0;

        switch (key) {
            case 1:
                JTA.saveUser("1".equals(flag));
                break;
            case 2:
                JTA.testJTA("1".equals(flag));
                break;
            case 3:
                JTA.testJTA_XA("1".equals(flag));
                break;
            default:
                throw new RuntimeException("未知类型");
        }
    }

}