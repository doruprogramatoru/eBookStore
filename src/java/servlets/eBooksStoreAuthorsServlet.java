/*
    Servlet eBooksStoreAuthorsServlet.java serves eBooksStoreAuthorsPage.jsp
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

/**
 * Servlet is serving eBooksStoreAuthorsPage.jsp
 *
 * @doru
 */
public class eBooksStoreAuthorsServlet extends HttpServlet {

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
        if (request.getParameter("admin_user_names_insert") != null) { // insert values from fields
            // set connection paramters to the DB
            // read values from page fields
            String name = request.getParameter("admin_user_names_name");
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
                // realize the insert
                String DML = "INSERT INTO AUTHORS VALUES (?)";
                pstmnt = connection.prepareStatement(DML);
                pstmnt.setString(1, name);
                pstmnt.execute();
                // display a message for ok
            } catch (ClassNotFoundException | SQLException ex) {
                // display a message for NOT OK
                Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // redirect page to its JSP as view
                request.getRequestDispatcher("./eBooksStoreAuthorsPage.jsp").forward(request, response);
            }
        } // check push on Update button
        else if (request.getParameter("admin_user_names_update") != null) { // update
            // declare specific variables
            ResultSet resultSet = null;
            PreparedStatement pstmnt = null;
            Connection connection = null;
            try {
                //check driver and create connection
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                // identify selected checkbox and for each execute the update operation
                String[] selectedCheckboxes = request.getParameterValues("admin_user_names_checkbox");
                String newname = request.getParameter("admin_user_names_name");
                // if both username and password are "" do nothing
                if (!("".equals(newname))) {
                    connection.setAutoCommit(false);
                    for (String s : selectedCheckboxes) {
                        // realize update of all selected rows
                        String DML = "UPDATE AUTHORS SET name=? WHERE name=?";
                        pstmnt = connection.prepareStatement(DML);
                        pstmnt.setString(1, newname);
                        pstmnt.setString(2, s);
                        boolean execute = pstmnt.execute();
                    }
                    connection.commit();
                    connection.setAutoCommit(true);
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.setAutoCommit(true);
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                // redirect page to its JSP as view
                request.getRequestDispatcher("./eBooksStoreAuthorsPage.jsp").forward(request, response);
            }
        } // check push on Delete button
        else if (request.getParameter("admin_user_names_delete") != null) { // delete 
            // declare specific variables
            ResultSet resultSet = null;
            PreparedStatement pstmnt = null;
            Connection connection = null;
            try {
                //check driver and create connection
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                // identify selected checkbox and for each execute the delete operation
                String[] selectedCheckboxes = request.getParameterValues("admin_user_names_checkbox");
                // multiple db critical operations should be executed into a transaction
                connection.setAutoCommit(false);
                for (String s : selectedCheckboxes) {
                    // realize delete of all selected rows
                    String DML = "DELETE FROM AUTHORS WHERE NAME=?";
                    pstmnt = connection.prepareStatement(DML);
                    pstmnt.setString(1, s);
                    pstmnt.execute();
                }
                connection.commit();
                connection.setAutoCommit(true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.setAutoCommit(true);
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(eBooksStoreAuthorsServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                // redirect page to its JSP as view
                request.getRequestDispatcher("./eBooksStoreAuthorsPage.jsp").forward(request, response);
            }
        } // check push on Cancel button
        else if (request.getParameter("admin_user_names_cancel") != null) { // cancel
            request.getRequestDispatcher("./eBooksStoreMainPage.jsp").forward(request, response);
        }

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
        return "Servlet is serving eBooksStoreAuthorsPage.jsp";
    }// </editor-fold>

}
