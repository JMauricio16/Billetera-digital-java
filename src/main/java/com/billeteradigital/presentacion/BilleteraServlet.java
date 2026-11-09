package com.billeteradigital.presentacion;

import com.billeteradigital.datos.MovimientoRepositorio;
import com.billeteradigital.negocio.BilleteraService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet("")
public class BilleteraServlet extends HttpServlet {

    // Crea una instancia de BilleteraService para manejar la lógica de negocio
    private BilleteraService service;

    // Inicializa el servlet 
    @Override
    public void init() {
        this.service = new BilleteraService(new MovimientoRepositorio());
    }

    // Maneja las solicitudes GET del servlet
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrarPagina(request, response);
    }

    // Maneja las solicitudes POST del servlet
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        
        List<String> errores = service.procesarMovimiento(
                request.getSession(),
                request.getParameter("tipo"),
                request.getParameter("monto")
        );

        if (errores.isEmpty()) {
            // Redirige a la misma página para evitar el reenvío del formulario al actualizar
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setAttribute("errores", errores);
            mostrarPagina(request, response);
        }
    }

    // Muestra la página de la billetera con el saldo y el historial de movimientos
    private void mostrarPagina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("saldo", service.obtenerSaldo(request.getSession()));
        request.setAttribute("movimientos", service.listar(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/views/billetera.jsp").forward(request, response);
    }
}