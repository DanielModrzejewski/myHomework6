package com.infoshareacademy.web;

import com.infoshareacademy.dao.TeacherDao;
import com.infoshareacademy.model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Transactional
@WebServlet(urlPatterns = "/teacher")
public class TeacherServlet extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(CourseServlet.class);

    @Inject
    private TeacherDao teacherDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        final String action = req.getParameter("action");
        LOG.info("Requested action: {}", action);

        if (action == null || action.isEmpty()) {
            resp.getWriter().write("Empty action parameter.");
            return;
        }
        if (action.equals("findAll")) {
            getAll(req, resp);
        } else if (action.equals("add")) {
            getAddTeacher(req, resp);
        } else if (action.equals("delete")) {
            getDeleteTeacher(req, resp);
        } else if (action.equals("update")) {
            getUpdateTeacher(req, resp);
        } else {
            resp.getWriter().write("Unknown action parameter.");
        }
    }

    private void getUpdateTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final String pesel = req.getParameter("pesel");

        LOG.info("Updating Course with id = {}", pesel);
        final Teacher existingTeacher = teacherDao.findByPesel(pesel);
        if (existingTeacher == null) {
            LOG.info("No Teacher found for id = {}, nothing to be updated", pesel);
        } else {
            existingTeacher.setName(req.getParameter("name"));
            existingTeacher.setSurname(req.getParameter("surname"));
            teacherDao.update(existingTeacher);
            LOG.info("Teacher object updated: {}", existingTeacher);
        }
        getAll(req, resp);
    }

    private void getDeleteTeacher (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pesel = req.getParameter("pesel");
        LOG.info("Deleting teacher with pesel" + pesel);
        teacherDao.delete(pesel);
        getAll(req, resp);
    }

    private void getAddTeacher (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final Teacher p = new Teacher();
        p.setPesel(req.getParameter("pesel"));
        p.setName(req.getParameter("name"));
        p.setSurname(req.getParameter("surname"));
        teacherDao.save(p);
        LOG.info("Saved a new Teacher object: {}", p);
        // Return all persisted objects
        getAll(req, resp);
    }

    private void getAll (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<Teacher> result = teacherDao.findAll();
        LOG.info("Found {} objects", result.size());
        for (Teacher p : result) {
            resp.getWriter().write(p.toString() + "\n");
        }
    }

}
