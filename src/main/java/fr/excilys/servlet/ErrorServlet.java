package fr.excilys.servlet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errors")
public class ErrorServlet {
 
	@GetMapping
    public String renderErrorPage(HttpServletRequest httpRequest) {
         
        String errorPage = "";
        int httpErrorCode = getErrorCode(httpRequest);
 
        switch (httpErrorCode) {
            case 403: {
            	errorPage = "403";
                break;
            }
            case 404: {
            	errorPage = "404";
                break;
            }
            case 500: {
            	errorPage = "500";
                break;
            }
            default:
        }
        return errorPage;
    }
     
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
          .getAttribute("javax.servlet.error.status_code");
    }
}
