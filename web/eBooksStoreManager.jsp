<%-- 
    Document   : eBooksStoreManager
    Created on : Jun 16, 2017, 9:05:15 PM
    Author     : Doru
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Electronic Books Store Manager</title>
        <link rel="stylesheet" type="text/css" href="./css/eBooksStoreCSS.css">
    </head>
    <body>
        <%-- test if actual user is authenticated and authorized --%>
        <c:choose>
            <c:when test="${validUser == true}">   
                <%-- include menu --%>
                <%@ include file="./utils/eBooksStoreMenu.jsp" %>
                <%-- Master view --%>
                <form action="${pageContext.request.contextPath}/eBooksStoreManagerServlet" method="POST">
                    <sql:setDataSource 
                        var="snapshot" 
                        driver="org.apache.derby.jdbc.ClientDriver"
                        url="jdbc:derby://localhost:1527/ebooksstore;create=true;"
                        user="APP"  
                        password="APP"/>
                    <sql:query dataSource="${snapshot}" var="result">
                        SELECT ISBN, TITLE from EBOOKS ORDER BY ISBN ASC
                    </sql:query>

                    <table border="1" width="100%">
                        <tr>
                            <td class="thc"> select </td>
                            <td class="thc">ISBN</td>
                            <td class="thc">TITLE</td>
                        </tr>
                        <c:forEach var="row" varStatus="loop" items="${result.rows}">
                            <tr>
                                <td><input type="checkbox" name="admin_isbn_values_checkbox" value="${row.ISBN}"></td>
                                <td><c:out value="${row.ISBN}"/></td>
                                <td><c:out value="${row.TITLE}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <%-- Details --%>
                    <table class="tablecenterdwithborder">
                        <tr><td>
                                <table>

                                    <tr><td> ISBN </td><td> <input type="text" name="admin_isbn_values"></input></td></tr>
                                </table>
                                <%-- buttons --%>
                                <table>
                                    <tr><td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_isbn_values_insert" value="Insert"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_isbn_values_update" value="Update"></td>
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_isbn_values_delete" value="Delete"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_isbn_values_cancel" value="Cancel"></td>
                                    </tr>     
                                </table>
                            </td></tr>
                    </table>    
                </form>
            </c:when>
            <c:otherwise>
                <c:redirect url="./index.jsp"></c:redirect>
            </c:otherwise>
        </c:choose>     
    </body>
</html>