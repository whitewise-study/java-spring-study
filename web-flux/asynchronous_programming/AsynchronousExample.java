import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * 비동기 예제
 */
public class AsynchronousExample {
    private final static Logger log = Logger.getGlobal();

    public static void main(String[] args) {
        log.info("Start Main");
        CompletableFuture<Integer> future = getResult();
        future.thenAccept(integer -> {
            int i = integer + 1;
            log.info(String.valueOf(i));
        });
        log.info("Finish Main");
    }

    private static CompletableFuture<Integer> getResult() {
        log.info("Start getResult");

        CompletableFuture<Integer> future = new CompletableFuture<>();

        // Simulate some asynchronous operation
        new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            int result = 0; // Some computation
            future.complete(result); // Complete the future with the result
            log.info("Finish getResult");

        }).start();

        return future;
    }
}

/**
 * 콘솔
 * 정보: Start Main
 * 정보: Start getResult
 * 정보: Finish Main
 * 정보: 1
 * 정보: Finish getResult
 */

