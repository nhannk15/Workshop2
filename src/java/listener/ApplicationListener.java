package listener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import model.Accounts;
import model.AccountsJpaController;
import model.ProductsJpaController;
import model.ViewedProducts;
import utils.JPAUtils;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Set<String> activeAccounts = new HashSet<>();
        context.setAttribute("ACTIVE_ACCOUNTS", activeAccounts);
        
        ViewedProducts viewedProducts = new ViewedProducts();
        context.setAttribute("VIEWED_PRODUCTS", viewedProducts);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
