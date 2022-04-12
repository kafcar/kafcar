/* eslint-disable prefer-arrow-callback */
import produceMessage from './support/app';
import {
  kafkaHasReceived, OutputTopic, startMonitoringKafka, stopMonitoringKafka,
} from './support/kafka';
import { Message } from './support/message';

describe('Message producer', function () {
  before(async function () {
    await startMonitoringKafka();
  });

  it('should produce a simple message', async function () {
    const message: Message = {
      topic: OutputTopic,
      value: {
        time: Date.now(),
        message: 'hello',
      },
    };
    await produceMessage(message);
    await kafkaHasReceived(message);
  });

  after(function (done) {
    this.timeout(8000);
    stopMonitoringKafka().then(() => done());
  });
});
