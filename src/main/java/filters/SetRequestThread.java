package filters;

import controllers.AuthorizationManager;

import javax.servlet.http.HttpServletRequest;

public class SetRequestThread implements Runnable{
    private HttpServletRequest req;

    public SetRequestThread(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void run() {
        AuthorizationManager.getInstance().setReq(req);
    }
}
