package generic.networking.grdon;

public interface TemporarySolution {

    long AFTER_NOTIFICATION_SLEEP_MS = 1000;

    static <E extends Exception> void rethrowAsError(ThrowingRunnable<E> runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    interface ThrowingRunnable<E extends Exception> {
        void run() throws E;
    }
}
