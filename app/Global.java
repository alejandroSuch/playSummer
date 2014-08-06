import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import play.Application;
import play.GlobalSettings;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.libs.F.Promise;
import play.mvc.SimpleResult;

public class Global extends GlobalSettings {
    private class ActionWrapper extends Action.Simple {
        public ActionWrapper(Action<?> action) {
            this.delegate = action;
        }

        @Override
        public Promise<SimpleResult> call(Http.Context ctx) throws java.lang.Throwable {
            Promise<SimpleResult> call = this.delegate.call(ctx);
            Http.Response response = ctx.response();
            response.setHeader("Access-Control-Allow-Origin", "*");
            return call;
        }
    }

    private ApplicationContext ctx;

    @Override
    public void onStart(Application app) throws BeansException {
        ctx = new ClassPathXmlApplicationContext("context.xml");
    }

    @Override
    public <A> A getControllerInstance(Class<A> clazz) throws BeansException {
        return ctx.getBean(clazz);
    }

    @Override
    public Action<?> onRequest(Http.Request request, java.lang.reflect.Method actionMethod) {
        return new ActionWrapper(super.onRequest(request, actionMethod));
    }

}