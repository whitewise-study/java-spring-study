import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * 논블로킹 예제
 */
public class NonBlockingExample {
    private final static Logger log = Logger.getGlobal();

    public static void main(String[] args) {
        log.info("Start Main");
        getResult();
        try {
            Thread.sleep(500);
        } catch (Exception e) {

        }
        log.info("Finish Main");
    }

    private static void getResult() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    log.info("Start getResult");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        log.info("Finish getResult");
                    }
                }
            });
        } finally {
            executorService.shutdown();
        }
    }
}

/**
 * 콘솔
 5월 01, 2024 7:27:47 오후 NonBlockingExample main
 정보: Start Main
 5월 01, 2024 7:27:47 오후 NonBlockingExample$1 run
 정보: Start getResult
 5월 01, 2024 7:27:47 오후 NonBlockingExample main
 정보: Finish Main
 5월 01, 2024 7:27:48 오후 NonBlockingExample$1 run
 정보: Finish getResult
 */

