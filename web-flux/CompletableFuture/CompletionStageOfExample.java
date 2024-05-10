import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

/**
 * CompletionStage
 * - 550개에 가까운 연산자들을 활용하여 비동기 task들을 실행하고 값을 변형하는 등 chaining을 이용한 조합 가능
 * - 에러를 처리하기 위한 콜백 제공
 * -  CompletableFuture는 내부적으로 비동기 함수들을 실행하기 위해 ForkJoinPool을 사용
 */
public class CompletionStageOfExample {

    private final static Logger log = Logger.getGlobal();

    public static void main(String[] args) throws InterruptedException {
        /**
         * thenAccept And thenAcceptAsync
         * */


        /*
         * thenAccept
         * */
        System.out.printf("=================================thenAccept================================================");
        log.info("start main");
        CompletionStage<Integer> stage = Helper.finishedStage();
        stage.thenAccept(i -> {
            log.info(i + "in thenAccept");
        }).thenAccept(i -> {
            log.info(i + " in thenAccept2");
        });
        log.info("after thenAccept");
        Thread.sleep(100);

        System.out.printf("=================================thenAcceptAsync================================================");
        /*
         * thenAcceptAsync
         * */
        log.info("start main");
        CompletionStage<Integer> stage2 = Helper.finishedStage();
        stage2.thenAcceptAsync(i -> {
            log.info(i + " in thenAcceptAsync");
        }).thenAcceptAsync(i -> {
            log.info(i + "in thenAcceptAsync2");
        });
        log.info("after thenAccept");
        Thread.sleep(100);

    }

    public static class Helper {

        //1을 반환하는 완료된 CompletableFuture 반환
        public static CompletionStage<Integer> finishedStage() throws InterruptedException {
            var future = CompletableFuture.supplyAsync(() -> {
                log.info("supplyAsync");
                return 1;
            });
            Thread.sleep(100);
            return future;
        }

        //1초를 sleep한 후 1을 반환하는 completableFuture
        public static CompletionStage<Integer> runningStage() throws InterruptedException {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                    log.info("I'm running!");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return 1;
            });
        }
    }


}

