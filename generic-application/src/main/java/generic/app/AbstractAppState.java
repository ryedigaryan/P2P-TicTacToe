package generic.app;

import generic.app.exception.IllegalAppStateLifecycleException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public abstract class AbstractAppState implements AppState {
    final Integer id;

    private boolean isStarted;
    private boolean isPaused;
    private boolean isStopped;

    @Setter
    AppStateEventHandler appStateEventHandler;
    @Setter
    AppStateLifecycleListener appStateLifecycleListener;

    @Override
    public void pause() {
        if(!isStarted())
            throw new IllegalAppStateLifecycleException(this, "not started", "pause");
        if(isStopped())
            throw new IllegalAppStateLifecycleException(this, "stopped", "pause");
        isPaused = true;
    }

    @Override
    public void resume() {
        if(!isStarted())
            throw new IllegalAppStateLifecycleException(this, "not started", "resume");
        if(isStopped())
            throw new IllegalAppStateLifecycleException(this, "stopped", "resume");
        isPaused = false;
    }

    @Override
    public void stop() {
        isStopped = true;
    }

    @Override
    public void start() {
        if(isStarted())
            throw new IllegalAppStateLifecycleException(this, "started", "start");
        if(isStopped())
            throw new IllegalAppStateLifecycleException(this, "stopped", "start");
        isStarted = true;
    }
}
