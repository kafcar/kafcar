import { Kafka, KafkaMessage } from 'kafkajs';
import { Message } from './message';

const kafka = new Kafka({
  clientId: 'e2e-app',
  brokers: ['localhost:9092'],
});

const producedMessages = [];
const consumer = kafka.consumer({ groupId: 'e2e-tests' });

function save(message: KafkaMessage) {
  producedMessages.push({
    key: message.key!.toString(),
    value: message.value!.toString(),
  });
}

export const startMonitoringKafka = async () => {
  console.log('1')
  await consumer.connect();
  console.log('2')
  await consumer.subscribe({ topic: 'e2e.output.topic' });
  console.log('3')
  await consumer.run({
    eachMessage: async ({ message }) => {
      save(message);
    },
  });
};

export const kafkaHasReceived = async (message: Message) => {
};

export const stopMonitoringKafka = async () => {
  await consumer.disconnect();
};
