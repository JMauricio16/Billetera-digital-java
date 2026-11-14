package com.billeteradigital;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;


@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        request.setAttribute("name", (name == null || name.isBlank()) ? "world" : name);
        request.setAttribute("now", LocalDateTime.now());

        
        request.getRequestDispatcher("/WEB-INF/views/hello.jsp").forward(request, response);
    }
}
