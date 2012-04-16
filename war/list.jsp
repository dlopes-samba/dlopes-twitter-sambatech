<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="br.com.sambatech.selecao.twitterapp.util.PersistenceFactoryHelper" %>
<%@ page import="br.com.sambatech.selecao.twitterapp.model.User" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
	function openPage(pageURL) {
		window.location.href = pageURL;
 	}
</script>

</head>
<body>
<h1>Seleção Sambatech - Problema 1</h1>
<p>Carlos Eduardo Rodrigues Lopes - <a href="mailto:dlopes@gmail.com">dlopes@gmail.com</a><br></p>
<p>Usuários cadastrados:<br></p>
<div>
<table border="1">
<%
    PersistenceManager persistenceManager = PersistenceFactoryHelper.getFactory().getPersistenceManager();
    String query = "select from " + User.class.getName();
    List<User> users = (List<User>) persistenceManager.newQuery(query).execute();
    if (users.isEmpty()) {
%>

<tr><td>Não existem usuários cadastrados na aplicação.</td></tr>

<%  
	} else {
		for(User user : users) {		
 %>
<tr>
<td><%= user.getName() %> </td>
<td><%= user.getPhone() %> </td>
<td><%= user.getEmail() %> </td>
<td><%= user.getTwitter() %> </td>
</tr>
<% 
		}
	//	query = "delete from " + User.class.getName();
	//	persistenceManager.newQuery(query).execute();
	}
	persistenceManager.close();
 %>
</table>
</div>
<p>Existem <%= users.size() %> usuários cadastrados.</p>
<form>
	<div>
		<input type="button" value="Cadastrar novo usuário" onclick="openPage('register.jsp')">
	</div>
</form>	

</body>
</html>