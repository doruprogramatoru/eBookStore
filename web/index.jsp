<%-- 
    Document   : index
    Created on : May 17, 2017, 7:41:04 PM
    Author     : Doru
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index Page</title>
        <link rel="stylesheet" type="text/css" href="./css/eBooksStoreCSS.css">
    </head>
    <body>
        <c:set var="activePage" value="index" scope="session"></c:set>
            <h3>Welcome to Electronic Books Store</h3>  

            <table class="tablecenterdwithborder">
                <form action="${pageContext.request.contextPath}/index" method="POST">
                <tr><td>Username: </td><td><input class = "inputlarge" type="text" name="LoginPage_Username"></input></td></tr> 
                <tr><td>Password: </td><td><input class = "inputlarge" type="password" name="LoginPage_Password"></input></td></tr> 
                <tr><td></td><td><input type="submit" name="LoginPage_Login" value="Login"></input></td></tr>
            </form>
        </table>
    </body>
</html>

