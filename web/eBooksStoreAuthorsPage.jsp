<%-- 
    Document   : eBooksStoreAuthorsPage
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Electronic Books Store Manage Authors Page</title>
        <link rel="stylesheet" type="text/css" href="./css/eBooksStoreCSS.css">
    </head>
    <body>
        <%-- test if actual user is authenticated and authorized --%>
        <c:choose>
            <c:when test="${validUser == true}">   
                <%-- Build the table containing actual user names and their roles in a master-detail view--%>
                <%-- include menu --%>
                <%@ include file="./utils/eBooksStoreMenu.jsp" %>
                <%-- Master view --%>
                <form action="${pageContext.request.contextPath}/eBooksStoreAuthorsServlet" method="POST">
                    <sql:setDataSource 
                        var="snapshotname" 
                        driver="org.apache.derby.jdbc.ClientDriver"
                        url="jdbc:derby://localhost:1527/ebooksstore;create=true;"
                        user="APP"  
                        password="APP"/>
                    <sql:query dataSource="${snapshotname}" var="result">
                        SELECT NAME from AUTHORS ORDER BY NAME ASC 
                    </sql:query>
                    <table border="1" width="100%">
                        <tr>
                            <td class="thc">select</td>    
                            <td class="thc">NAME</td>
                        </tr>
                        <c:forEach var="row" varStatus="loop" items="${result.rows}">
                            <tr>
                                <td class="tdc"><input type="checkbox" name="admin_user_names_checkbox" value="${row.name}"></td>
                                <td class="tdc"><c:out value="${row.name}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <%-- Details --%>
                    <table class="tablecenterdwithborder">
                        <tr><td>
                                <table>
                                    <tr>
                                        <td> NAME </td>
                                        <td> <input type="text" name="admin_user_names_name"></input></td>
                                    </tr>
                                </table>
                                <%-- buttons --%>
                                <table>
                                    <tr><td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_user_names_insert" value="Insert"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_user_names_update" value="Update"></td>
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_user_names_delete" value="Delete"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_user_names_cancel" value="Cancel"></td>
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
