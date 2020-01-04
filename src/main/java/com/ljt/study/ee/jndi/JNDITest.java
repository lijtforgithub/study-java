package com.ljt.study.ee.jndi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author LiJingTang
 * @date 2020-01-04 16:37
 */
@WebServlet(description = "JNDI测试", urlPatterns = {"/lijt/javaee/jndi.htm"})
public class JNDITest extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Writer writer = response.getWriter();
        writer.write("JNDI-DataSource: " + JNDI.newInstance().toString() + " Data: " + JNDI.findTestDataById(1));
        writer.flush();
        writer.close();
    }

}