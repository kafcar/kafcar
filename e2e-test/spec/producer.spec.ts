import produceMessage from './support/app';
import { kafkaHasReceived, startMonitoringKafka, stopMonitoringKafka } from './support/kafka';
import { Message } from './support/message';

describe('Message producer', () => {
  before(async () => startMonitoringKafka());
  after(async () => stopMonitoringKafka());

  it('should produce a simple message', async () => {
    const message: Message = {
      topic: 'kafcar.output.e2e',
      key: 'kuku',
      value: 'message content',
    };
    await produceMessage(message);
    await kafkaHasReceived(message);
  });
});
