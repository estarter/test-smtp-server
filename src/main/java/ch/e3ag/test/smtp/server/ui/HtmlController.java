package ch.e3ag.test.smtp.server.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Alexey Merezhin
 */
@Controller
public class HtmlController {
    private static final Logger logger = LoggerFactory.getLogger(RestService.class);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @RequestMapping("/")
    public String index(Model model) {
        LocalDateTime date = LocalDateTime.now();
        model.addAttribute("createdAt", date.format(formatter));
        logger.info(date.format(formatter));
        return "index";
    }

}
