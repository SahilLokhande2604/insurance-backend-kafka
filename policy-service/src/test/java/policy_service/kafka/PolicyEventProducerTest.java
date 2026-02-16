import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import com.policy.kafka.PolicyEventProducer;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"policy_topic"})
public class PolicyEventProducerTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private PolicyEventProducer policyEventProducer;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void testSendPolicyPurchaseEvent() {
        String testMessage = "Test policy purchase event";
        policyEventProducer.sendPolicyPurchaseEvent(testMessage);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
                "testGroup", "true", embeddedKafkaBroker);
        consumerProps.put("key.deserializer", org.apache.kafka.common.serialization.StringDeserializer.class);
        consumerProps.put("value.deserializer", org.apache.kafka.common.serialization.StringDeserializer.class);

        var consumerFactory = new DefaultKafkaConsumerFactory<String, String>(consumerProps);
        var consumer = consumerFactory.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "policy_topic");

        org.apache.kafka.clients.consumer.ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, "policy_topic");
        assertThat(record.value()).isEqualTo(testMessage);
        consumer.close();
    }

    @Test
    void testSendPolicyUpdateEvent() {
        String testMessage = "Test policy update event";
        policyEventProducer.sendPolicyUpdateEvent(testMessage);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
                "testGroup2", "true", embeddedKafkaBroker);
        consumerProps.put("key.deserializer", org.apache.kafka.common.serialization.StringDeserializer.class);
        consumerProps.put("value.deserializer", org.apache.kafka.common.serialization.StringDeserializer.class);

        var consumerFactory = new DefaultKafkaConsumerFactory<String, String>(consumerProps);
        var consumer = consumerFactory.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "policy_topic");

        org.apache.kafka.clients.consumer.ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, "policy_topic");
        assertThat(record.value()).isEqualTo(testMessage);
        consumer.close();
    }

    @Test
    void testMultipleMessages() {
        String message1 = "First policy event";
        String message2 = "Second policy event";
        policyEventProducer.sendPolicyPurchaseEvent(message1);
        policyEventProducer.sendPolicyUpdateEvent(message2);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
                "testGroup3", "true", embeddedKafkaBroker);
        consumerProps.put("key.deserializer", org.apache.kafka.common.serialization.StringDeserializer.class);
        consumerProps.put("value.deserializer", org.apache.kafka.common.serialization.StringDeserializer.class);

        var consumerFactory = new DefaultKafkaConsumerFactory<String, String>(consumerProps);
        var consumer = consumerFactory.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "policy_topic");

        org.apache.kafka.clients.consumer.ConsumerRecord<String, String> record1 = KafkaTestUtils.getSingleRecord(consumer, "policy_topic");
        assertThat(record1.value()).isEqualTo(message1);

        org.apache.kafka.clients.consumer.ConsumerRecord<String, String> record2 = KafkaTestUtils.getSingleRecord(consumer, "policy_topic");
        assertThat(record2.value()).isEqualTo(message2);

        consumer.close();
    }
}