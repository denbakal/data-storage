package ua.challenge.service.transaction.handler;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;

@Log4j2
@Component
@RequiredArgsConstructor
public class PersonIndexHandler implements TransactionSynchronization {
    private final Client client;

    public void afterCommit() {
        log.debug("After commit.");
    }

    public void afterCompletion(int status) {
        int STATUS_ROLLED_BACK = 1;

        if (status == STATUS_ROLLED_BACK) {
            System.out.println("client = " + client);
            BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                    .source("person_index")
                    .get();
            long deleted = response.getDeleted();
            log.warn("The count of deleted persons from ES: " + deleted);
        }
    }
}
