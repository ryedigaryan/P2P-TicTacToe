package tictactoe.backend.app;

public class AppFlowItemEventTest {

    public static void main(String[] args) {
        AppFlowItemEvent<String> abc = new AppFlowItemEvent<>("idofabc");
        abc.setStopPreviousAppFlowItem(false);
        AppFlowItemEvent<String> abc2 = new AppFlowItemEvent<>("idofabc");
        abc2.setStopPreviousAppFlowItem(true);

        AppFlowItemEvent<Integer> i9 = new AppFlowItemEvent<>(9);
        i9.setStopPreviousAppFlowItem(false);
        AppFlowItemEvent<Integer> i92 = new AppFlowItemEvent<>(9);
        i9.setStopPreviousAppFlowItem(true);

        assert abc.hashCode() == abc2.hashCode();
        assert i9.hashCode() == i92.hashCode();

    }
}
