import produceMessage from './support/app';
import {
  kafkaHasReceived, OutputTopic, startMonitoringKafka, stopMonitoringKafka,
} from './support/kafka';
import { Message } from './support/message';

describe('Message producer', () => {
  before(async () => startMonitoringKafka());
  after(function (done) {
    this.timeout(8000);
    stopMonitoringKafka().then(() => {
      done();
    });
  });

  it('should produce a simple message', async () => {
    const message: Message = {
      topic: OutputTopic,
      value: 'message content',
    };
    await produceMessage(message);
    await kafkaHasReceived(message);
  });
});
