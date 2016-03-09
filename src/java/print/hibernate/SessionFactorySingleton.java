/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.hibernate;

/**
 *
 * @author apopovkin
 */

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;



/**
 *
 * @author apopovkin
 */
public class SessionFactorySingleton {

    private volatile static SessionFactory instance;

    private SessionFactorySingleton() {
    }

    public static SessionFactory getConfigHibernate(String config) {
        config="/rzdd/Hibernate.cfg.xml";
        if (instance == null) {
            synchronized (SessionFactorySingleton.class) {
                if (instance == null) {
                    Configuration configuration = new Configuration();
                    configuration.configure(config);
                    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                            .applySettings(configuration.getProperties())
                            .buildServiceRegistry();
                    instance = configuration
                            .buildSessionFactory(serviceRegistry);
                }
            }
        }
        return instance;
    }
}
