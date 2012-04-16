
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
<h1>Seleção Sambatech - Problema 1</h1>
<p>Carlos Eduardo Rodrigues Lopes - <a href="mailto:dlopes@gmail.com">dlopes@gmail.com</a><br></p>
<p>Cadastre um novo usuário:<br></p>
<div>
<form action="/register" method="post">	
<table cellspacing="0.5" cellpadding="1" border="0" >
	<tr>
		<td>Nome:</td>
		<td align="left"><input type="text" id="name" name="name" align="right" size="45" value="${param.name}"/>*</td>
		<td class="error"><font color="red">${errors.name}</font></td>
	</tr>
	<tr>
		<td>E-mail:</td>
		<td align="left"><input type="text" id="email" name="email" align="right" size="45" value="${param.email}"/>*</td>
		<td class="error"><font color="red">${errors.email}</font></td>
	</tr>
	<tr>
		<td>Telefone:</td>
		<td align="left"><input type="text" id="phone" name="phone" align="right" size="45" value="${param.phone}"/>*</td>
		<td class="error"><font color="red">${errors.phone}</font></td>
	</tr>
	<tr>
		<td>Twitter:</td>
		<td align="left"><input type="text" name="twitter" align="right" size="45"/></td>
	</tr>
</table>
<p>(*) Campos de preenchimento obrigatório.</p>
<input type="submit" value="Cadastrar" >
</form>
<p class="error"><font color="red">${errors.general}</font></p>
</div>
</body>
</html>