import { produceMessage } from './support/app';
import { kafkaHasReceived } from './support/kafka';
import { Message } from './support/message';

describe('Message producer', () => {
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
