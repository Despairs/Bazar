package org.investsoft.bazar.utils.events;

import android.util.SparseArray;

import java.util.ArrayList;

/**
 * Created by Despairs on 03.03.16.
 */
public class EventManager {

    private SparseArray<ArrayList<EventReceiver>> receivers = new SparseArray<>();

    private static EventManager instance = new EventManager();

    public interface EventReceiver {
        void onEventReceive(int eventId, Object... data);
    }

    public static EventManager getInstance() {
        return instance;
    }

    public void registerReceiver(int eventId, EventReceiver receiver) {
        ArrayList<EventReceiver> eventReceivers = receivers.get(eventId);
        if (eventReceivers == null) {
            eventReceivers = new ArrayList<>();
            receivers.put(eventId, eventReceivers);
        }
        if (!eventReceivers.contains(receiver)) {
            eventReceivers.add(receiver);
        }
    }

    public void unregisterReceiver(int eventId, EventReceiver receiver) {
        ArrayList<EventReceiver> eventReceivers = receivers.get(eventId);
        if (eventReceivers == null) {
            eventReceivers = new ArrayList<>();
            receivers.put(eventId, eventReceivers);
        }
        if (eventReceivers.contains(receiver)) {
            eventReceivers.remove(receiver);
        }
    }

    public void sendEvent(int eventId, Object... data) {
        ArrayList<EventReceiver> eventReceivers = receivers.get(eventId);
        if (eventReceivers != null && !eventReceivers.isEmpty()) {
            for (EventReceiver receiver : eventReceivers) {
                receiver.onEventReceive(eventId, data);
            }
        }
    }
}
