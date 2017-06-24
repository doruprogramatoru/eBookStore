<%-- 
    Document   : eBooksStoreBoughtBooks.jsp
    Author     : doru
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Electronic Sold Books</title>
        <link rel="stylesheet" type="text/css" href="./css/eBooksStoreCSS.css">
    </head>
    <body>
        <%-- test if actual user is authenticated and authorized --%>
        <c:choose>
            <c:when test="${validUser == true}">   
                <h3>This is your Orders Page</h3>  
                <h3>Welcome Adi</h3> 
                <!-- include menu -->
                <%@ include file="./utils/eBooksStoreMenu.jsp" %>
                <%-- Master view --%>
                <form action="${pageContext.request.contextPath}/eBooksStoreSoldBooksServlet" method="POST">
                    <sql:setDataSource 
                        var="snapshot" 
                        driver="org.apache.derby.jdbc.ClientDriver"
                        url="jdbc:derby://localhost:1527/ebooksstore;create=true;"
                        user="APP"  
                        password="APP"/>
                    <sql:query dataSource="${snapshot}" var="result">
                        SELECT SOLD_EBOOKS.USER_ID, SOLD_EBOOKS.USER_NAME, SOLD_EBOOKS.ISBN,  SOLD_EBOOKS.TITLE, SOLD_EBOOKS.CATEGORY, SOLD_EBOOKS.NAME, SOLD_EBOOKS.ACQUIRED_DATE, SOLD_EBOOKS.ACQUIRED_QUANTITY, SOLD_EBOOKS.BOUGHT_DATE, SOLD_EBOOKS.BOUGHT_QUANTITY FROM SOLD_EBOOKS, EBOOKS, USERS WHERE USER_NAME = 'adi' AND SOLD_EBOOKS.USER_ID = USERS.ID AND SOLD_EBOOKS.ISBN = EBOOKS.ISBN ORDER BY USER_ID ASC
                    </sql:query>
                    <table border="1" width="100%">
                        <tr>
                            <%-- <td width="12%" class="thc">USER_ID</td> --%>  
                            <%-- <td width="12%" class="thc">USER_NAME</td> --%>
                            <td width="12%" class="thc">ISBN</td> 
                            <td width="13%" class="thc">TITLE</td>
                            <td width="13%" class="thc">CATEGORY</td>
                            <td width="13%" class="thc">NAME</td>
                            <%-- <td width="13%" class="thc">ACQUIRED_DATE</td> --%>
                            <%-- <td width="13%" class="thc">ACQUIRED_QUANTITY</td> --%>
                            <td width="13%" class="thc">BOUGHT_DATE</td>
                            <td width="13%" class="thc">BOUGHT_QUANTITY</td>
                    </table>    
                    <table border="1" width="100%">    
                        </tr>
                        <c:forEach var="row" varStatus="loop" items="${result.rows}">
                            <tr>
                                <%-- <td width="12%" class="tdc"><c:out value="${row.user_id}"/></td> --%>
                                <%-- <td width="12%" class="tdc"><c:out value="${row.user_name}"/></td> --%>
                                <td width="12%" class="tdc"><c:out value="${row.isbn}"/></td>
                                <td width="13%" class="tdc"><c:out value="${row.title}"/></td>
                                <td width="13%" class="tdc"><c:out value="${row.category}"/></td>
                                <td width="13%" class="tdc"><c:out value="${row.name}"/></td>
                                <%-- <td width="13%" class="tdc"><c:out value="${row.acquired_date}"/></td> --%>
                                <%-- <td width="13%" class="tdc"><c:out value="${row.acquired_quantity}"/></td> --%>
                                <td width="13%" class="tdc"><c:out value="${row.bought_date}"/></td> 
                                <td width="13%" class="tdc"><c:out value="${row.bought_quantity}"/></td> 
                            </tr>
                        </c:forEach>
                    </table>
                </form>
            </c:when>
            <c:otherwise>
                <c:redirect url="./index.jsp"></c:redirect>
            </c:otherwise>
        </c:choose>
    </body>    
</html>
