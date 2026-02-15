package com.customersupport.config;

import com.customersupport.events.PolicyUpdatedEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@SuppressWarnings("deprecation")
public class KafkaConfig {

    // ---- Admin (topic creation in dev) ----
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap;

    @Value("${topics.ticket.created}")
    private String ticketCreatedTopic;

    @Value("${topics.policy.change.requested}")
    private String policyChangeRequestedTopic;

    @Value("${topics.policy.updated}")
    private String policyUpdatedTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicTicketCreated() {
        return new NewTopic(ticketCreatedTopic, 3, (short)1);
    }

    @Bean
    public NewTopic topicPolicyChangeRequested() {
        return new NewTopic(policyChangeRequestedTopic, 3, (short)1);
    }

    @Bean
    public NewTopic topicPolicyUpdated() {
        // Only needed if you also produce this from somewhere else and want it auto-created here in dev
        return new NewTopic(policyUpdatedTopic, 3, (short)1);
    }

    // ---- Producer (generic JSON) ----
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // ---- Consumer Factory for PolicyUpdatedEvent ----
    @Bean
    public ConsumerFactory<String, PolicyUpdatedEvent> policyUpdatedConsumerFactory(
            @Value("${spring.kafka.consumer.group-id}") String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        
        JsonDeserializer<PolicyUpdatedEvent> jsonDeserializer = new JsonDeserializer<>(PolicyUpdatedEvent.class);
        jsonDeserializer.addTrustedPackages("com.insurance.support.events", "com.customersupport.events");
        jsonDeserializer.ignoreTypeHeaders();

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PolicyUpdatedEvent>
    policyUpdatedKafkaListenerFactory(ConsumerFactory<String, PolicyUpdatedEvent> cf) {
        ConcurrentKafkaListenerContainerFactory<String, PolicyUpdatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);
        factory.setConcurrency(3);
        factory.getContainerProperties().setObservationEnabled(true);
        return factory;
    }
}
