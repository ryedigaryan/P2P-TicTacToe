package tictactoe.app.flow.item;

import genericapp.AbstractAppFlowItem;
import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.event.ui.base.UIElementBase;

@Getter @Setter
public abstract class AbstractTicTacToeAppFlowItem<U extends UIElementBase> extends AbstractAppFlowItem {
    U ui;

    public AbstractTicTacToeAppFlowItem(Integer id, U ui) {
        super(id);
        setUi(ui);
    }

    @Override
    public void run() {
        super.run();
        getUi().start();
        getAppFlowItemStateChangeListener().appFlowItemStarted(this);
    }

    @Override
    public void pause() {
        super.pause();
        getUi().pause();
        getAppFlowItemStateChangeListener().appFlowItemPaused(this);
    }

    @Override
    public void resume() {
        super.resume();
        getUi().resume();
        getAppFlowItemStateChangeListener().appFlowItemResumed(this);
    }

    @Override
    public void stop() {
        super.stop();
        getUi().stop();
        getAppFlowItemStateChangeListener().appFlowItemStopped(this);
    }
}
