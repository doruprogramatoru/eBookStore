<%-- 
   eBooksStoreManager
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Electronic Books Store Manage</title>
        <link rel="stylesheet" type="text/css" href="./css/eBooksStoreCSS.css">
    </head>
    <body>
        <%-- test if actual user is authenticated and authorized --%>
        <c:choose>
            <c:when test="${validUser == true}">   
                <!-- include menu -->
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
                        SELECT EBOOKS.ISBN, EBOOKS.TITLE, EBOOKS.CATEGORY, AUTHORS.NAME FROM EBOOKS, AUTHORS WHERE EBOOKS.NAME = AUTHORS.NAME ORDER BY ISBN ASC
                    </sql:query>
                    <table border="1" width="100%">
                        <tr>
                            <td width="4%" class="thc"> select </td>   
                            <td width="24%" class="thc"> ISBN </td> 
                            <td width="24%" class="thc">TITLE</td>
                            <td width="24%" class="thc">CATEGORY</td>
                            <td width="24%" class="thc">NAME</td>
                    </table>    
                    <table border="1" width="100%">    
                        </tr>
                        <c:forEach var="row" varStatus="loop" items="${result.rows}">
                            <tr>
                                <td width="4%" class="tdc"><input type="checkbox" name="admin_users_checkbox" value="${row.isbn}"></td>
                                <td width="24%" class="tdc"><c:out value="${row.isbn}"/></td>
                                <td width="24%" class="tdc"><c:out value="${row.title}"/></td>
                                <td width="24%" class="tdc"><c:out value="${row.category}"/></td>
                                <td width="24%" class="tdc"><c:out value="${row.name}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <%-- Details --%>
                    <sql:setDataSource 
                        var="snapshotname" 
                        driver="org.apache.derby.jdbc.ClientDriver"
                        url="jdbc:derby://localhost:1527/ebooksstore;create=true;"
                        user="APP"  
                        password="APP"/>
                    <sql:query dataSource="${snapshot}" var="result">
                        SELECT * from EBOOKS 
                    </sql:query>
                    <table class="tablecenterdwithborder">
                        <tr><td>    
                                <table>
                                    <tr>
                                        <td> ISBN: </td>
                                        <td> <input type="text" name="admin_isbn"></input></td>
                                    </tr>
                                    <tr>
                                        <td> TITLE: </td>
                                        <td> <input type="text" name="admin_title"></input></td>
                                    </tr>
                                    <tr>
                                        <td> CATEGORY: </td>
                                        <td> <input type="text" name="admin_category"></input></td>
                                    </tr>
                                    <tr>
                                        <td> NAME: </td>
                                        <td> <input type="text" name="admin_user_name"></input></td>
                                    </tr>     
                                </table>
                                <%-- buttons --%>
                                <table>

                                    <tr><td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_users_insert" value="Insert"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_users_update" value="Update"></td>
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_users_delete" value="Delete"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_users_cancel" value="Cancel"></td>
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
