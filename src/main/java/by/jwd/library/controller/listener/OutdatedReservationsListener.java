package by.jwd.library.controller.listener;

import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OutdatedReservationsListener implements ServletContextListener {
    private ScheduledExecutorService scheduler;

    static class OutdatedReservationsManager implements Runnable {

        private static final Logger logger = LoggerFactory.getLogger(OutdatedReservationsManager.class);

        @Override
        public void run() {

            try {
                ServiceFactory.getInstance().getLibraryService().closeOutdatedReservations();

            } catch (ServiceException e) {
                logger.error("ServiceException in OutdatedReservationsListener - close outdated reservations fail", e);

            }

        }
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = now.withHour(4).withMinute(0).withSecond(0);
        if (now.compareTo(next) > 0) {
            next = next.plusDays(1);
        }
        Duration duration = Duration.between(now, next);
        long delay = duration.getSeconds();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new OutdatedReservationsManager(), delay,
                TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
}

