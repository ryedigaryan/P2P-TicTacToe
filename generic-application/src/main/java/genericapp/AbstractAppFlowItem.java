package genericapp;

import genericapp.exception.IllegalAppFlowItemStateException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class AbstractAppFlowItem implements AppFlowItem {
    final Integer id;

    private boolean isStarted;
    private boolean isPaused;
    private boolean isStopped;

    @Setter
    AppFlowItemEventHandler<? extends AppFlowItemEvent> eventHandler;

    @Override
    public void pause() {
        if(!isStarted)
            throw new IllegalAppFlowItemStateException(this, "not started", "pause");
        if(isStopped)
            throw new IllegalAppFlowItemStateException(this, "stopped", "pause");
        isPaused = true;
    }

    @Override
    public void resume() {
        if(!isStarted)
            throw new IllegalAppFlowItemStateException(this, "not started", "resume");
        if(isStopped)
            throw new IllegalAppFlowItemStateException(this, "stopped", "resume");
        isPaused = false;
    }

    @Override
    public void stop() {
        isStopped = true;
    }

    @Override
    public void run() {
        if(isStarted)
            throw new IllegalAppFlowItemStateException(this, "started", "start");
        if(isStopped)
            throw new IllegalAppFlowItemStateException(this, "stopped", "start");
        isStarted = true;
    }
}
