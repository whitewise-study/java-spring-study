import java.util.logging.Logger;

/**
 * 동기 예제
 */
public class SynchronousExample {

    private final static Logger log = Logger.getGlobal();

    public static void main(String[] args) throws InterruptedException {
        log.info("Start Main");
        int result = getResult();

        log.info(String.valueOf(result + 1));
        log.info("finish Main");
    }

    public static int getResult() throws InterruptedException {
        log.info("Start getResult");

        Thread.sleep(1000);

        var result = 0;

        try {
            return result;
        } finally {
            log.info("finish getResult");
        }
    }
}


/**
 * 콘솔
 * INFO: Start Main
 * INFO: Start getResult
 * INFO: finish getResult
 * INFO: 1
 * INFO: finish Main
 */