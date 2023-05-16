<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

 	<jsp:include page="../common/head.jsp">
		<jsp:param name="title" value="Nova Marcação Feita"/>
	</jsp:include>
	 
	 <body>
	 	<jsp:include page="../common/header.jsp"></jsp:include>
	 	
	 	<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item">Você está em:&nbsp<a href="/index">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Nova Marcação Feita</li>
			</ol>
		</nav>
	 	
	 	<c:if test="${addMarcacaoFeitaSuccess}">
 			<div class="alert alert-success alert-dismissible fade show" role="alert">
	         	Marcação adicionada com sucesso
	         	<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
	         </div>
	     </c:if>
	     
	     <c:if test="${updateMarcacaoFeitaSuccess}">
	     	<div class="alert alert-success alert-dismissible fade show" role="alert">
	         	Marcação atualizada com sucesso
	         	<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
	         </div>
	     </c:if>
	 
 		<c:url var="edit_marcacao_feita_url" value="/marcacao-feita/edit-marcacao-feita"/>
 		<c:url var="add_marcacao_feita_url" value="/marcacao-feita/add-marcacao-feita"/>
	    
	    <form:form action="${isEditing ? edit_marcacao_feita_url : add_marcacao_feita_url}" method="post" modelAttribute="marcacaoFeita">
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