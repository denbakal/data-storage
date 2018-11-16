package ua.challenge.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EventListener {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(String payload) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TransactionPhase.AFTER_COMMIT");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> payload = " + payload);
    }
}
