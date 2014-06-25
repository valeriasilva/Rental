package util;


import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

@WebFilter(servletNames={"Faces Servlet"})
public class JPAFilter implements Filter {
    
    static private EntityManagerFactory factory;
    
          /**
     * @return the factory
     */
    public static EntityManagerFactory getFactory() {
        return factory;
    }

    /**
     * @param aFactory the factory to set
     */
    public static void setFactory(EntityManagerFactory aFactory) {
        factory = aFactory;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        JPAFilter.factory = Persistence.createEntityManagerFactory("persistence");
    }

    @Override
    public void destroy() {
        JPAFilter.factory.close();
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        
        EntityManager entityManager = JPAFilter.factory.createEntityManager();
        request.setAttribute("entityManager" , entityManager);
        entityManager.getTransaction().begin();
                          
        chain.doFilter(request, response);        
        try {
            entityManager.getTransaction().commit();
            //entityManager.clear();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }
}