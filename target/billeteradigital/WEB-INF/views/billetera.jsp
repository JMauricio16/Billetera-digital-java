<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mi Billetera Digital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
</head>
<body class="bg-body-tertiary">

    <!-- Header oscuro -->
   <header class="bg-primary text-white py-3 px-4 mb-4">
        <div class="container-lg d-flex justify-content-center align-items-center text-center">
            <h5 class="mb-0 fw-semibold ">Mi Billetera Digital</h5>
        </div>
    </header>

    <main class="container py-4" style="max-width: 950px;">

        <!-- Saldo y Detalle GET -->
        <div class="row g-3 mb-4">
            <div class="col-12">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <span class="text-secondary small fw-bold text-uppercase">SALDO DISPONIBLE</span>
                        <h2 class="display-6 fw-bold text-success my-1">
                            S/ <fmt:formatNumber value="${saldo}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
                        </h2>
                        <small class="text-secondary">Movimientos todales: ${movimientos.size()} </small>
                    </div>
                </div>
            </div>
            
        </div>

        <!--Historial -->
        <div class="row g-3 mb-4 align-items-start">
            <div class="${not empty errores ? 'col-md-12' : 'col-md-12'}">
                <h6 class="fw-bold mb-2">Movimientos recientes</h6>
                <div class="card shadow-sm overflow-hidden">
                    <table class="table table-hover align-middle mb-0 text-center">
                        <thead class="bg-navy text-white">
                            <tr class="table-primary">
                                <th scope="col" class="w-25 text-muted">Tipo</th>
                                <th scope="col" class="w-25 text-muted">Monto (S/)</th>
                                <th scope="col" class="w-25 text-muted">Orden</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty movimientos}">
                                    <tr>
                                        <td colspan="3" class="text-muted py-4">Aún no hay movimientos registrados.</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="mov" items="${movimientos}">
                                        <tr>
                                            <td>
                                                <span class="${mov.tipo == 'Recarga' ? 'badge-recarga' : 'badge-gasto'}">
                                                    <c:out value="${mov.tipo}"/>
                                                </span>
                                            </td>
                                            <td class="fw-bold ${mov.tipo == 'Recarga' ? 'text-success' : 'text-danger'}">
                                                ${mov.tipo == 'Recarga' ? '+ ' : '- '}
                                                <fmt:formatNumber value="${mov.monto}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
                                            </td>
                                            <td class="text-secondary small"><c:out value="${mov.orden}"/></td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!--Nuevo movimiento -->
        <div class="card shadow-sm p-3 mb-5">
            <h6 class="fw-bold mb-3">Nuevo movimiento</h6>
            <form method="post" action="<c:url value='/'/>" novalidate>
                <div class="row g-2 align-items-end">
                    <div class="col-md-3">
                        <label for="tipo" class="form-label text-muted small mb-1">Tipo</label>
                        <select class="form-select form-select-sm" id="tipo" name="tipo">
                            <option value="Recarga" ${param.tipo == 'Recarga' ? 'selected' : ''}>Recarga</option>
                            <option value="Gasto" ${param.tipo == 'Gasto' ? 'selected' : ''}>Gasto</option>
                        </select>
                    </div>
                    <div class="col-md-5">
                        <label for="monto" class="form-label text-muted small mb-1">Monto (S/)</label>
                        <input type="number" step="0.01" class="form-control form-control-sm" id="monto" name="monto"
                               placeholder="0.00" value="<c:out value='${param.monto}'/>">
                    </div>
                    <div class="col-md-4">
                        <button type="submit" class="btn btn-primary btn-sm w-100 py-1">Registrar movimiento</button>
                    </div>
                </div>
            </form>
        </div>
        <!-- Panel de validación de errores -->
            <c:if test="${not empty errores}">
                <div class="col-12">
                    <div class="card border-danger-subtle bg-danger bg-opacity-10 p-3 shadow-sm">
                        <h6 class="text-danger fw-bold mb-2">¡¡ERROR!!</h6>
                        <c:if test="${not empty param.monto}">
                            <p class="small text-secondary mb-1">Gasto solicitado: S/ <c:out value="${param.monto}"/></p>
                        </c:if>
                        <p class="small text-secondary mb-2">
                            Saldo disponible: S/ <fmt:formatNumber value="${saldo}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
                        </p>
                        <ul class="text-danger mb-0 ps-3 small">
                            <c:forEach var="err" items="${errores}">
                                <li><c:out value="${err}"/></li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:if>

    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>