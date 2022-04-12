import { Kafka, KafkaMessage } from 'kafkajs';
import { busywait } from 'busywait';
import { isDeepStrictEqual } from 'util';
import { Message } from './message';

const kafka = new Kafka({
  clientId: 'e2e-app',
  brokers: ['localhost:9092'],
});

export const OutputTopic = 'e2e.output.topic';

const producedMessages: Message[] = [];
const consumer = kafka.consumer({ groupId: 'e2e-tests' });

function save(message: any) {
  producedMessages.push({
    topic: OutputTopic,
    value: JSON.parse(message.value),
  });
}

export const startMonitoringKafka = async () => {
  await consumer.connect();
  await consumer.subscribe({ topic: OutputTopic });
  await consumer.run({
    eachMessage: async ({ message }) => {
      save(message);
    },
  });
};

export const kafkaHasReceived = async (message: Message) => {
  await busywait(() => {
    if (!producedMessages.find((e) => isDeepStrictEqual(e, message))) {
      throw new Error('Message wasn\'t produced yet');
    }
  }, {
    sleepTime: 100,
    maxChecks: 10,
  });
};

export const stopMonitoringKafka = async () => {
  await consumer.disconnect();
};
