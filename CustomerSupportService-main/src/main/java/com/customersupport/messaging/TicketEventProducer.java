package com.customersupport.messaging;

import com.customersupport.events.PolicyChangeRequestedEvent;
import com.customersupport.events.TicketCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketEventProducer {

    private final KafkaTemplate<String, Object> kafka;

    @Value("${topics.ticket.created}")
    private String ticketCreatedTopic;

    @Value("${topics.policy.change.requested}")
    private String policyChangeRequestedTopic;

    public void publishTicketCreated(TicketCreatedEvent event) {
        String key = event.getPolicyId() != null ? String.valueOf(event.getPolicyId())
                                                 : String.valueOf(event.getTicketId());
        kafka.send(ticketCreatedTopic, key, event);
    }

    public void publishPolicyChangeRequested(PolicyChangeRequestedEvent event) {
        String key = String.valueOf(event.getPolicyId());
        kafka.send(policyChangeRequestedTopic, key, event);
    }
}