<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

 	<jsp:include page="../common/head.jsp">
		<jsp:param name="title" value="Novo Horário de Trabalho"/>
	</jsp:include>
	 
	 <body>
	 	<jsp:include page="../common/header.jsp"></jsp:include>
	 	
	 	<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item">Você está em:&nbsp<a href="/index">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Novo Horário de Trabalho</li>
			</ol>
		</nav>
	 	
	 	<c:if test="${addHorarioTrabalhoSuccess}">
 			<div class="alert alert-success alert-dismissible fade show" role="alert">
	         	Horário de Trabalho adicionado com sucesso
	         	<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
	         </div>
	     </c:if>
	     
	     <c:if test="${updateHorarioTrabalhoSuccess}">
	     	<div class="alert alert-success alert-dismissible fade show" role="alert">
	         	Horário de Trabalho atualizado com sucesso
	         	<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
	         </div>
	     </c:if>
	 
 		<c:url var="edit_horario_trabalho_url" value="/horario-trabalho/edit-horario-trabalho"/>
 		<c:url var="add_horario_trabalho_url" value="/horario-trabalho/add-horario-trabalho"/>
	    
	    <form:form action="${isEditing ? edit_horario_trabalho_url : add_horario_trabalho_url}" method="post" modelAttribute="horarioTrabalho">
	     	<c:if test="${isEditing}">
	     		<div class="mb-3 ms-3 w-25">
	     			<form:label path="id" for="inpuId" class="form-label">ID:</form:label> 
	         		<form:input type="text" path="id" class="form-control" id="inpuId" readonly="true"/>
	     		</div>
		     </c:if>	
	     
	     	 <div class="mb-3 ms-3 w-25">
	     	 	<form:label path="entrada" for="inputHorarioEntrada" class="form-label">Horário de entrada:</form:label> 
	         	<form:input type="time" path="entrada" class="form-control" id="inputHorarioEntrada"/>
	     	 </div>
	     	 
	     	 <div class="mb-3 ms-3 w-25">
	     	 	<form:label path="saida" for="inputHorarioEntrada" class="form-label">Horário de saida:</form:label> 
	     	 	<form:input type="time" path="saida" class="form-control" id="inputHorarioEntrada"/>
	     	 </div>
	     	 
     	 	<input type="submit" value="${isEditing ? 'Atualizar' : 'Adicionar'}" class="btn btn-success ms-3"/>
	     </form:form>
	 </body>
</html>