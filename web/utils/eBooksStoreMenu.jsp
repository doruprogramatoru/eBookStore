<%-- 
    Document   : eBooksStoreMenu.jsp
    Created on : May 17, 2017, 8:45:04 PM
    Author     : Doru
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <link href="./css/menu.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <ul id="nav">
            <c:choose>
                <c:when test="${actualUserRole == 'admin'}">
                    <li><a href="#">Manage</a>
                        <ul>
                            <c:choose>
                                <c:when test="${actualUserRole == 'admin'}">
                                    <li><a href="./eBooksStoreAdminUsersPage.jsp">Users</a></li>
                                    <li><a href="./eBooksStoreAdminUserRolesPage.jsp">User roles</a></li>
                                    <li><a href="./eBooksStoreAuthorsPage.jsp">Manage Authors</a></li>
                                    <li><a href="./eBooksStoreManager.jsp">Manage Books</a></li>
                                    </c:when>
                                </c:choose>                              
                        </ul>
                    </li>
                </c:when>
            </c:choose>        
            <c:choose>
                <c:when test="${actualUserRole == 'admin'}">
                    <li><a href="#">Orders</a>
                        <ul>
                            <li><a href="./eBooksStoreSoldBooks.jsp">Sold Books</a></li>
                        </ul>
                    </li>    
                </c:when>
                <c:when test="${actualUserRole == 'user'}">
                    <li><a href="#">Orders</a>
                        <ul>
                            <li><a href="./eBooksStoreBoughtBooks.jsp">Bought Books</a></li>
                        </ul>
                    </li>    
                </c:when>
            </c:choose>                        
            <li><a href="./eBooksStoreExit.jsp">Log out</a></li>
        </ul>
        <script src="js/script.js"></script>
    </body>
</html>
