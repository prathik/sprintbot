package rocks.thiscoder.dsb;

/**
 * @author prathik.raj
 */
public class W8Svc {
    private W8Svc() {

    }

    private static W8Svc w8Svc;

    public static W8Svc getInstance() {
        if(w8Svc == null) {
            w8Svc = new W8Svc();
        }

        return w8Svc;
    }

    public void sleep(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    public static void setW8Svc(W8Svc w8Svc) {
        W8Svc.w8Svc = w8Svc;
    }
}
