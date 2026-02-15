package com.customersupport.messaging;

import com.customersupport.events.PolicyUpdatedEvent;
import com.customersupport.model.Ticket;
import com.customersupport.repo.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PolicyEventConsumer {

    private final TicketRepository ticketRepository;

    @Value("${topics.policy.updated}")
    private String topic;

    @KafkaListener(
            topics = "${topics.policy.updated}",
            containerFactory = "policyUpdatedKafkaListenerFactory"
    )
    public void onPolicyUpdated(@Payload PolicyUpdatedEvent event) {
        log.info("Received PolicyUpdatedEvent: {}", event);
        if (event.getPolicyId() == null) return;

        // Example reaction: just log tickets linked to this policy
        List<Ticket> tickets = ticketRepository.findByPolicyId(event.getPolicyId());
        log.info("Found {} tickets for policyId={}", tickets.size(), event.getPolicyId());

        // You can update ticket descriptions, add comments, or notify agents here.
        // Keeping side-effects minimal for now.
    }
}