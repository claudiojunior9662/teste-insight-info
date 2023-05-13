<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<jsp:include page="../common/head.jsp">
	<jsp:param name="title" value="Marcações Feitas"/>
</jsp:include>
 
 <body>
 	<jsp:include page="../common/header.jsp"></jsp:include>
 	
 	<nav aria-label="breadcrumb">
		<ol class="breadcrumb">
			<li class="breadcrumb-item">Você está em:&nbsp<a href="/index">Home</a></li>
			<li class="breadcrumb-item active" aria-current="page">Marcações Feitas</li>
		</ol>
	</nav>
 	
 	<a href="/marcacao-feita/add-marcacao-feita" class="btn btn-primary ms-3 mb-3">Adicionar</a>
 	
 	<c:if test="${isExcluido}">
         <div class="alert alert-success alert-dismissible fade show" role="alert">
         	Marcação excluída com sucesso
         	<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
         </div>
     </c:if>
 	
 	<table class="table table-striped ms-3 me-3">
         <thead>
             <tr>
                 <th>ID</th>
                 <th>Entrada</th>
                 <th>Saída</th>
                 <th>Ações</th>
             </tr>
         </thead>
         <tbody>
             <c:forEach items="${marcacoesFeitas}" var="marcacao">
                 <tr>
                     <td>${marcacao.id}</td>
                     <td>${marcacao.entrada}</td>
                     <td>${marcacao.saida}</td>
                     <td>
                     	<a href="/marcacao-feita/edit-marcacao-feita?id=<c:out value='${marcacao.id}' />" class="btn btn-primary btn-sm">Editar</a>
                     	<a href="/marcacao-feita/delete-marcacao-feita?id=<c:out value='${marcacao.id}' />" class="btn btn-danger btn-sm">Excluir</a>
                     </td>
                 </tr>
             </c:forEach>
         </tbody>
     </table>
 </body>
</html>