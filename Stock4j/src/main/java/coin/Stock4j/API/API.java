package coin.Stock4j.API;

import coin.Stock4j.API.event.MarketUpdateEvent;
import coin.Stock4j.lang.APINotFoundException;

import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

public class API {
    protected static TimerTask timerTask;
    public API() {

    }
    public void await(String methodName, String className) throws ClassNotFoundException, NoSuchMethodException {
        Class c = Class.forName(className);
        final Object event = new MarketUpdateEvent();
        final Method method = c.getMethod(methodName, event.getClass());
        Timer timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    method.invoke(c.newInstance(), event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 60000);
    }
    public void end(int exitCode) {
        System.exit(exitCode);
    }
    public void end() {
        System.exit(1);
    }
}
