/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.CategoryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Category;

/**
 *
 * @author Leo
 */
public class EditCategory extends HttpServlet {

    private CategoryDAO categoryDao;

    @Override
    public void init() {
        categoryDao = CategoryDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter("type");
        String idStr = request.getParameter("id");

        if (type != null && idStr != null && type.equals("edit")) {
            int id = 0;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                System.out.println(e);
                request.setAttribute("message", "Invalid category ID");
                request.getRequestDispatcher("edit-category.jsp").forward(request, response);
                return;
            }
            Category c = categoryDao.getCategoryById(id);
            if (c != null) {
                request.setAttribute("editedCategory", c);
            } else {
                request.setAttribute("message", "Category not found for editing");
            }

        }
        request.getRequestDispatcher("edit-category.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter("type");
        String idStr = request.getParameter("id");

        String categoryName = request.getParameter("categoryName");
        String description = request.getParameter("description");

        HttpSession session = request.getSession();

        if (type != null && type.equals("add")) {
            categoryDao.insertCategory(categoryName, description);
            session.setAttribute("message", "Add category successfully!");
        }

        if (type != null && type.equals("edit")) {
            int id = 0;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                System.out.println(e);
            }

            Category c = categoryDao.getCategoryById(id);
            if (c != null) {
                c.setCategoryName(categoryName);
                c.setDescription(description);
                categoryDao.updateCategory(id, c);
                session.setAttribute("message", "Update category successfully!");
            } else {
                session.setAttribute("message", "Category not found for update");
            }

        }

        response.sendRedirect("categories");
    }

}
