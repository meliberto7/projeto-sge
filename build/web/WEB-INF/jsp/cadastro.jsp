
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro</title>
    </head>
    <body>
        <h1>Criação da conta</h1>
        <br>
        <form action="./cadastrar" method="post">
            <input type="text" name="inputNome" placeholder="Nome">
            <input type="password" name="inputSenha" placeholder="Senha">
            <input type="number" name="inputCpf" placeholder="Cpf">
            <input type="date" name="inputDate">
            <select name="selectArea">
                <option selected>Selecione a area</option>
                <c:forEach var="area" items="${areas}">
                    <option value="${area.id_area}">${area.nome}</option>
                </c:forEach>
            </select>
            <button type="submit">Cadastrar</button>
        </form>
    </body>
</html>
