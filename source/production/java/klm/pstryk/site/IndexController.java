package klm.pstryk.site;

import klm.pstryk.config.annotation.WebController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@WebController
public class IndexController
{
    @RequestMapping("/")
    public View index()
    {
        return new RedirectView("/ticket/list", true, false);
    }
}
