package fr.sco.activitytracker;

import fr.sco.activitytracker.camel.component.ComponentFactory;
import fr.sco.activitytracker.config.ConfigTracker;
import fr.sco.activitytracker.config.bind.Component;
import fr.sco.activitytracker.exception.ConfigException;
import fr.sco.activitytracker.routeBuilder.RouteBuilderTracker;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by stiffler on 14/03/15.
 */
public class ActivityTracker {

    private static final Logger logger = LoggerFactory.getLogger(ActivityTracker.class);

    private CamelContext context;

    private ConfigTracker configTracker;

    public ActivityTracker() {
        this.setContext(new DefaultCamelContext());
        configTracker = new ConfigTracker();
    }
    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        final ActivityTracker tracker = new ActivityTracker();

        logger.info("----------------- Lancement du tracker -------------------");
        try {
            tracker.checkArgs(args);

            tracker.configTracker.configure(args[0]);

        } catch (ConfigException e) {
            tracker.stop();
            return;
        }

        tracker.configureComponent();

        tracker.getContext().addRoutes(new RouteBuilderTracker(tracker.getConfigTracker()));
        tracker.getContext().start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    tracker.stop();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        tracker.waitForStop();
    }


    public void configureComponent() {

        for(Component cc : this.configTracker.getConfig().getListComponent().getComponent())
            this.getContext().addComponent(cc.getName(), ComponentFactory.getComponent(cc));
    }

    private void stop() throws Exception{

        if (!this.getContext().isSuspended())
            this.getContext().stop();

        logger.info("----------------- Arret du tracker -------------------");
    }

    private void checkArgs(String... args) throws ConfigException {
        if (args.length != 1) {
            throw new ConfigException("Argument non valide");
        }
    }

    private void waitForStop() {
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public CamelContext getContext() {
        return context;
    }

    public void setContext(CamelContext context) {
        this.context = context;
    }
	public ConfigTracker getConfigTracker() {
		return configTracker;
	}
	public void setConfigTracker(ConfigTracker configTracker) {
		this.configTracker = configTracker;
	}

}
