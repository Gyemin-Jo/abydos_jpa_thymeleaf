package com.jgm.learning.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Component
public class SendMessage extends AbstractView {
    private	final Logger logger	= LogManager.getLogger(getClass());

    public SendMessage() {
        setContentType("text/html");
    }
    @Override

    protected void renderMergedOutputModel(Map<String,Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out		= null;

        String			message	= (String)model.get("message");
        String			location= (String)model.get("location");
        String			popupUrl= (String)model.get("popupurl");
        String			confirm= (String)model.get("confirm");
        String			confirmY= (String)model.get("confirmY");
        String			confirmN= (String)model.get("confirmN");

        try {
            response.setHeader("Content-Type", "text/html; charset=UTF-8");
            out	= response.getWriter();

            out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
            out.write("<html lang=\"ko\">");
            out.write("<head>");
            out.write("<script type=\"text/javascript\">");
            if(!"".equals(Utils.nvl(message))) {
                out.write("alert('"); out.write(message); out.write("');");
            }

            if(!"".equals(Utils.nvl(popupUrl))) {
                out.write("window.open('"+ popupUrl +"', 'popup', '');");
            }

            if("".equals(Utils.nvl(location))) {
                out.write("history.back();");

            } else if("self.close".equals(Utils.nvl(location))) {
                out.write("self.close();");

            } else if("location.reload".equals(Utils.nvl(location))) {
                out.write("opener.location.reload();");
                out.write("self.close();");

            } else if("confirm".equals(Utils.nvl(confirm))) {

                out.write("if(confirm('" + confirm + "')) { ");
                out.write(confirmY);
                out.write("} else { ");
                out.write(confirmN);
                out.write("} ");

            } else {
                out.write("location.replace('"); out.write(location); out.write("');");
            }
            out.write("</script>");
            out.write("</head>");
            out.write("</html>");
            out.flush();
            out.close();
        } catch(Exception e) {
            logger.error("Send Message View 처리중 에러", e);

            if(null != out) try { out.close(); } catch(Exception ex) {}
        }
    }

}
