# Billetera Digital: Recarga y Movimientos

Aplicación web desarrollada en Java utilizando Servlets, JSP y JSTL(persistencia en sesión HTTP con `HttpSession`).

---

## Tecnologías y Versiones Utilizadas

- **Lenguaje:** Java 17 o superior
- **Plataforma:** Jakarta EE 10 
- **Servidor Web:** Apache Tomcat 10.1.0

---

## Arquitectura 

```text
src/main/java/com/billeteradigital/
├── modelo/
│   └── Movimiento.java          
├── datos/
│   └── MovimientoRepositorio.java 
├── negocio/
│   └── BilleteraService.java     
└── presentacion/
    └── BilleteraServlet.java     

src/main/webapp/
├── WEB-INF/
│   ├── views/
│   │   └── billetera.jsp        
│   └── web.xml                  
```

---

## Pasos para ejecutar

1. Descarga el repositorio desde github y descomprimelo.
2. Abre el visual studio code, ve al menú superior (File > Open Folder) y selecciona la carpeta descomprimido
4. Verificas que el proyecto se haya cargado correctamente en visual studio code.
5. Despliega la carpeta target
6. Selecciona el archivo billeteradigital.war
7. Le das click derecho y le das en Run on Server
8. En la parte superior te aparecera para que selecciones el server, en este caso apache tomcat 10.1.0
9. Te aparecera una pregunta "Do you want to edit optional deployment parameters?" le das en no
10. En la barra lateral izquierda, ubica la sección Servers y confirma que el estado de Tomcat figure en (Started).
11. Copias y Pegas la ruta http://localhost:8080/billeteradigital/ en el navegador y debe ejecutarse

## Funcionalidades Principales

- Saldo inicial predeterminado de S/ 250.00
- Registra transacciones: Recarga y/o Gasto.
- Reglas de negocio:
  - Los montos tienen que ser mayores a cero.
  - Se verifica que el saldo sea suficiente procesar un gasto.
- Historial de movimientos automaticos.
