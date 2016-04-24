package ua.teachme.web;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.teachme.model.Notation;
import ua.teachme.util.notation.NotationUtil;
import ua.teachme.util.time.TimeUtil;
import ua.teachme.web.notation.NotationController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

//todo set correct logging
public class NotationServlet extends HttpServlet {

    private static final Logger LOG = getLogger(NotationServlet.class);
    ConfigurableApplicationContext springContext;
    private NotationController notationController;

    @Override
    public void init() throws ServletException {
        super.init();
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        notationController = springContext.getBean(NotationController.class);
        MDC.put("logger_id","spring_context");
        LOG.debug("context created");
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
        MDC.put("logger_id","spring_context");
        LOG.debug("context destroyed");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MDC.put("logger_id","web");

        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("notations", NotationUtil.getFilteredWithExceed(notationController.getAll(), NotationUtil.HOURS_PER_DAY));
            setDefaultDateAndTime(request);
            request.getRequestDispatcher("/notations.jsp").forward(request, response);
            LOG.debug("forward to /notations.jsp with all notations");
        }
        else if ("delete".equals(action)) {
            notationController.delete(getIdFromRequest(request));
            response.sendRedirect("notations");
            setDefaultDateAndTime(request);
            LOG.debug("redirect to notations, notation id={}, action={}.", request.getParameter("id"), request.getParameter("action"));
        }
        else if ("create".equals(action)) {
            request.setAttribute("notation", new Notation("", "", "", 0, LocalDateTime.now()));
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
            LOG.debug("forward to /edit.jsp with new notation");
        }
        else if ("update".equals(action)) {
            request.setAttribute("notation", notationController.get(getIdFromRequest(request)));
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
            LOG.debug("forward to /edit.jsp, notation id={}, action={}.", request.getParameter("id"), request.getParameter("action"));
        }
        else {
            response.sendRedirect("index.jsp");
            LOG.debug("redirect to /index.jsp wrong parameters");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MDC.put("logger_id","web");

        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (null == action) {
            String id = request.getParameter("id");
            Notation notation = new Notation(
                    id.isEmpty() ? null : Integer.valueOf(id),
                    request.getParameter("name"),
                    request.getParameter("url"),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("hours")),
                    id.isEmpty() ? LocalDateTime.now() : LocalDateTime.parse(request.getParameter("dateTime"))
            );
            notationController.save(notation);
            response.sendRedirect("notations");
            LOG.debug("redirect to /notations.jsp, save new notation.");
        }
        else if ("filter".equals(action)) {
            String startDate = request.getParameter("startDate");
            String startTime = request.getParameter("startTime");
            String endDate = request.getParameter("endDate");
            String endTime = request.getParameter("endTime");
            request.setAttribute("notations", notationController.getBetween(
                    TimeUtil.toLocalDate(startDate),
                    TimeUtil.toLocalTime(startTime),
                    TimeUtil.toLocalDate(endDate),
                    TimeUtil.toLocalTime(endTime)
                    )
            );
            request.setAttribute("startDate", startDate);
            request.setAttribute("startTime", startTime);
            request.setAttribute("endDate", endDate);
            request.setAttribute("endTime", endTime);

            request.getRequestDispatcher("/notations.jsp").forward(request, response);
            LOG.debug("redirect to /notations.jsp, filtering.");
        }
        else {
            response.sendRedirect("notations");
            LOG.debug("redirect to /notations.jsp, wrong parameters.");
        }
    }

    private int getIdFromRequest(HttpServletRequest request) {
        return Integer.valueOf(request.getParameter("id"));
    }
    private void setDefaultDateAndTime(HttpServletRequest request){
        request.setAttribute("startDate", TimeUtil.TODAY);
        request.setAttribute("startTime", TimeUtil.MIN_TIME);
        request.setAttribute("endDate", TimeUtil.TODAY);
        request.setAttribute("endTime", TimeUtil.MAX_TIME);
    }
}
