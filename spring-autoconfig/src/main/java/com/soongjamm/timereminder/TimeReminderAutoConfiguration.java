package com.soongjamm.timereminder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "time-reminder.enabled", havingValue = "true")
public class TimeReminderAutoConfiguration implements BeanFactoryAware, EnvironmentAware, SmartLifecycle, FactoryBean<TimeReminder> {

    private boolean isRunning;
    private BeanFactory beanFactory;
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void start() {
        log.info("Starting TimeReminder.");
        if (isRunning) {
            return;
        }
        isRunning = true;
        TimeReminder reminder = beanFactory.getBean(TimeReminder.class);

        reminder.remind();
        log.info("started.");
    }

    @Override
    public void stop() {
        log.info("Stopping TimeReminder.");
        if (!isRunning) {
            return;
        }

        try {
            TimeReminder reminder = beanFactory.getBean(TimeReminder.class);
            reminder.stop();
            isRunning = false;

            log.info("stopped.");
        } catch (Exception e) {
            log.error("Error stopping TimeReminder.", e);
        }
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public boolean isAutoStartup() {
        return environment.getProperty("time-reminder.auto-startup", Boolean.class, true);
    }

    @Override
    public TimeReminder getObject() {
        log.info("Creating TimeReminder bean.");
        Integer time = environment.getProperty("time-reminder.time", Integer.class, 10);
        TimeUnit unit = environment.getProperty("time-reminder.unit", TimeUnit.class, TimeUnit.SECONDS);
        return new TimeReminder(time, unit);
    }

    @Override
    public Class<?> getObjectType() {
        log.info("TimeReminder class.");
        return TimeReminder.class;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
