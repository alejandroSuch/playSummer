import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {

    private ApplicationContext ctx;

    @Override
    public void onStart(Application app) throws BeansException {
        ctx = new ClassPathXmlApplicationContext("context.xml");
    }

    @Override
    public <A> A getControllerInstance(Class<A> clazz) throws BeansException {
        return ctx.getBean(clazz);
    }

}