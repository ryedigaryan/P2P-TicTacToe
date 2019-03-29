package generic.networking.grdon;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    class InetAddressSetFrame extends JFrame {

        private JList<InetAddress> hostsList;

        @Getter
        SetListModel<InetAddress> listModel;

        public InetAddressSetFrame() throws HeadlessException {
            super("GRDON: Hosts list");
            hostsList = new JList<>();

            hostsList.setModel(listModel = new SetListModel<>());

            setLayout(new GridLayout(1, 1));
            add(hostsList);

            setSize(500, 700);
            setVisible(true);
        }
    }

    class SetListModel<E> extends AbstractListModel<E> {

        Set<E> elementSet = new HashSet<>();

        @Override
        public synchronized int getSize() {
            return 0;
        }

        @Override
        public synchronized E getElementAt(int index) {
            return new ArrayList<>(elementSet).get(index);
        }

        public synchronized void add(E element) {
            elementSet.add(element);
            fireIntervalAdded(this, 0, 0);
            fireContentsChanged(this, 0, elementSet.size() - 1);
        }

        public synchronized void remove(E element) {
            elementSet.remove(element);
            fireIntervalRemoved(this, 0, 0);
            fireContentsChanged(this, 0, elementSet.size() - 1);
        }
    }
}
