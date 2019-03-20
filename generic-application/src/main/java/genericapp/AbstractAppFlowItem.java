package genericapp;

import genericapp.exception.IllegalAppFlowItemStateException;
import lombok.Getter;
import lombok.Setter;

public class AbstractAppFlowItem implements AppFlowItem {
    @Getter
    private boolean isStarted;
    @Getter
    private boolean isPaused;
    @Getter
    private boolean isStopped;

    @Getter @Setter
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
