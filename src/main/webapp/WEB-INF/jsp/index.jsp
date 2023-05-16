<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<jsp:include page="./common/head.jsp">
	<jsp:param name="title" value="Insight Informática" />
</jsp:include>

<body>
	<jsp:include page="./common/header.jsp"></jsp:include>

	<nav aria-label="breadcrumb">
		<ol class="breadcrumb ms-2">
			<li class="breadcrumb-item">Você está em:&nbsp<a href="#">Home</a></li>
		</ol>
	</nav>
	
	<h3>Horários de trabalho</h3>
	
	<a href="/calcponto-1.0/horario-trabalho/add-horario-trabalho" class="btn btn-primary ms-3 mb-3">Adicionar novo horário de trabalho</a>
 	
 	<c:if test="${isHorarioTrabalhoExcluido}">
         <div class="alert alert-success alert-dismissible fade show" role="alert">
         	Horário de trabalho excluído com sucesso
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
             <c:forEach items="${horariosTrabalhos}" var="horarioTrabalho">
                 <tr>
                     <td>${horarioTrabalho.id}</td>
                     <td>${horarioTrabalho.entrada}</td>
                     <td>${horarioTrabalho.saida}</td>
                     <td>
                     	<a href="/calcponto-1.0/horario-trabalho/edit-horario-trabalho?id=<c:out value='${horarioTrabalho.id}' />" class="btn btn-primary btn-sm">Editar</a>
                     	<a href="/calcponto-1.0/horario-trabalho/delete-horario-trabalho?id=<c:out value='${horarioTrabalho.id}' />" class="btn btn-danger btn-sm">Excluir</a>
                     </td>
                 </tr>
             </c:forEach>
         </tbody>
     </table>
     
     <hr>
     <h3>Marcações feitas</h3>
     
     <a href="/calcponto-1.0/marcacao-feita/add-marcacao-feita" class="btn btn-primary ms-3 mb-3">Adicionar nova marcação feita</a>
 	
 	<c:if test="${isMarcacaoFeitaExcluida}">
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
                     	<a href="/calcponto-1.0/marcacao-feita/edit-marcacao-feita?id=<c:out value='${marcacao.id}' />" class="btn btn-primary btn-sm">Editar</a>
                     	<a href="/calcponto-1.0/marcacao-feita/delete-marcacao-feita?id=<c:out value='${marcacao.id}' />" class="btn btn-danger btn-sm">Excluir</a>
                     </td>
                 </tr>
             </c:forEach>
         </tbody>
     </table>
     
     <br>
     <br>
     <br>
     <hr>
     <div class="container">
     	<div class="row">
     		<div class="col">
     			<h3 class="text-center">Intervalos de Atraso</h3>
     
			     <table class="table table-striped ms-3 me-3">
			         <thead>
			             <tr>
			                 <th class="text-center">Início</th>
			                 <th class="text-center">Fim</th>
			             </tr>
			         </thead>
			         <tbody>
			             <c:forEach items="${atrasos}" var="atraso">
			                 <tr>
			                     <td>${atraso.inicio}</td>
			                     <td>${atraso.fim}</td>
			                 </tr>
			             </c:forEach>
			         </tbody>
			     </table>
			     
			     <div test="${duracaoAtrasos}">
			     	<p>Total: ${duracaoAtrasos}h</p>
			     </div>
     		</div>
     		<div class="col">
     			<h3 class="text-center">Horas Extras</h3>
     
			     <table class="table table-striped ms-3 me-3">
			         <thead>
			             <tr>
			                 <th class="text-center">Início</th>
			                 <th class="text-center">Fim</th>
			             </tr>
			         </thead>
			         <tbody>
			             <c:forEach items="${horasExtras}" var="horaExtra">
			                 <tr>
			                     <td>${horaExtra.inicio}</td>
			                     <td>${horaExtra.fim}</td>
			                 </tr>
			             </c:forEach>
			         </tbody>
			     </table>
			     
			     <div test="${duracaoHorasExtras}">
			     	<p>Total: ${duracaoHorasExtras}h</p>
			     </div>
     		</div>
     	</div>
     </div>
</body>
</html>