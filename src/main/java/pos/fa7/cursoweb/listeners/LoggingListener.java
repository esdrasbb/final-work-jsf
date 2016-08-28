package pos.fa7.cursoweb.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.bridge.SLF4JBridgeHandler;

@WebListener
public class LoggingListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent arg) {
		System.out.println("contextInitialized....");
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg) {
		System.out.println("contextDestroyed....");

	}

}
