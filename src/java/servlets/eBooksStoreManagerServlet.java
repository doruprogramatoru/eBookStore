/*
 * Servlet eBooksStoreManagerServlet.java serves eBooksStoreManagerServlet.JSP.
 * Servlet manages CRUD operations for books.
 */
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class eBooksStoreManagerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // declare specific DB info
        String user = "APP";
        String password = "APP";
        String url = "jdbc:derby://localhost:1527/ebooksstore;create=true;";
        String driver = "org.apache.derby.jdbc.ClientDriver";
        // check push on Insert button
        if (request.getParameter("admin_users_insert") != null) { // insert values from fields
            // set connection paramters to the DB
            // read values from page fields
            String isbn = request.getParameter("admin_isbn");
            String title = request.getParameter("admin_title");
            String category = request.getParameter("admin_category");
            String name = request.getParameter("admin_user_name");
            //String customer = request.getParameter("admin_user_customer");
            //String transporter = request.getParameter("admin_user_transporter");
            //int customerID = -1;
            //int transporterID = -1;
            int ID = -1;
            // declare specific variables
            ResultSet resultSet = null;
            Statement statement = null;
            Connection connection = null;
            PreparedStatement pstmnt = null;
            try {
                //check driver and create connection
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                // User ID should be generated incremental based on last value of ID
                //statement = connection.createStatement();
                //String query = "SELECT MAX(ID) AS MAXID FROM EBOOKS";
                //resultSet = statement.executeQuery(query);
                // boolean resultSetHasRows = resultSet.next();
                //boolean noOperation = false;
                //if (resultSetHasRows) {
                // create new ID as MAXID + 1
                //int maxid = resultSet.getInt(1);
                //ID = maxid + 1;

                //} else {
                //ID = 1;
                //}
                // if nothing bad happen until now
                //if (!noOperation) {
                // realize the insert
                //call stored procedure to insert a new person
                String DML = "INSERT INTO EBOOKS VALUES (?, ?, ?, ?)";
                pstmnt = connection.prepareStatement(DML);
                pstmnt.setString(1, isbn);
                pstmnt.setString(2, title);
                pstmnt.setString(3, category);
                pstmnt.setString(4, name);
                pstmnt.execute();
                // display a message for ok
                //}
            } catch (ClassNotFoundException | SQLException ex) {
                // display a message for NOT OK
                Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // redirect page to its JSP as view
                request.getRequestDispatcher("./eBooksStoreManager.jsp").forward(request, response);
            }
        } // check push on Update button
        else if (request.getParameter("admin_users_update") != null) { // update
            // declare specific variables
            ResultSet resultSet = null;
            Statement statement = null;
            PreparedStatement pstmnt = null;
            Connection connection = null;
            try {
                //check driver and create connection
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                // identify selected checkbox and for each execute the update operation
                String[] selectedCheckboxes = request.getParameterValues("admin_users_checkbox");
                String isbn = request.getParameter("admin_isbn");
                String title = request.getParameter("admin_title");
                String category = request.getParameter("admin_category");
                String name = request.getParameter("admin_user_name");
                //int customerID = -1;
                boolean noOperation = false;
                // if nothing bad happen move forward
                if (!noOperation) {
                    // if both username and password are "" do nothing
                    if (!(("".equals(isbn)) && ("".equals(title)) && ("".equals(category) && ("".equals(name))))) {
                        // operate updates for all selected rows
                        for (String s : selectedCheckboxes) {
                            // realize update of all selected rows
                            int ISBN = Integer.parseInt(s);
                            if ("".equals(title)) { // only password/s should be updated
                                String DML = "UPDATE EBOOKS SET category=?,name=? WHERE ISBN=?";
                                pstmnt = connection.prepareStatement(DML);
                                pstmnt.setString(1, category);
                                pstmnt.setString(2, name);
                                pstmnt.setInt(3, ISBN);
                            } else if ("".equals(category)) {// only username should be updated
                                String DML = "UPDATE EBOOKS SET title=?,name=? WHERE ISBN=?";
                                pstmnt = connection.prepareStatement(DML);
                                pstmnt.setString(1, title);
                                pstmnt.setString(2, name);
                                pstmnt.setInt(3, ISBN);
                            } else {
                                String DML = "UPDATE EBOOKS SET title=?, category=?,name=? WHERE ISBN=?";
                                pstmnt = connection.prepareStatement(DML);
                                pstmnt.setString(1, title);
                                pstmnt.setString(2, category);
                                pstmnt.setString(3, name);
                                pstmnt.setInt(4, ISBN);
                            }
                            boolean execute = pstmnt.execute();
                        }
                    } else { // update one or more roles for one or more users
                        for (String s : selectedCheckboxes) {
                            // realize update of all selected rows
                            int ISBN = Integer.parseInt(s);
                            String DML = "UPDATE EBOOKS SET name=? WHERE ISBN=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, name);
                            pstmnt.setInt(2, ISBN);
                            boolean execute = pstmnt.execute();
                        }
                    }
                }
            } catch (ClassNotFoundException | SQLException ex) {
                // display a message for NOT OK
                Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);

            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // redirect page to its JSP as view
                request.getRequestDispatcher("./eBooksStoreManager.jsp").forward(request, response);
            }
        } // check push on Delete button
        else if (request.getParameter("admin_users_delete") != null) { // delete 
            // declare specific variables
            ResultSet resultSet = null;
            PreparedStatement pstmnt = null;
            Connection connection = null;
            try {

                //check driver and create connection
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                // identify selected checkbox and for each execute the delete operation
                String[] selectedCheckboxes = request.getParameterValues("admin_users_checkbox");
                // more critical DB operations should be made into a transaction
                connection.setAutoCommit(false);
                for (String s : selectedCheckboxes) {
                    // realize delete of all selected rows
                    String DML = "DELETE FROM EBOOKS WHERE ISBN=?";
                    //int ID = Integer.parseInt(s);
                    pstmnt = connection.prepareStatement(DML);
                    pstmnt.setString(1, s);
                    pstmnt.execute();
                }
                connection.commit();
                connection.setAutoCommit(true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.setAutoCommit(true);
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                // redirect page to its JSP as view
                request.getRequestDispatcher("./eBooksStoreManager.jsp").forward(request, response);
            }
        } // check push on Cancel button
        else if (request.getParameter("admin_users_cancel") != null) { // cancel
            request.getRequestDispatcher("./eBooksStoreMainPage.jsp").forward(request, response);
        } // check push on admin user roles button
        else if (request.getParameter("admin_usernames_open") != null) { // insert values from fields
            request.getRequestDispatcher("./eBooksStoreManager.jsp").forward(request, response);
        } // check push on admin customers button
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet is serving eBooksStoreManager.jsp";
    }// </editor-fold>

}
