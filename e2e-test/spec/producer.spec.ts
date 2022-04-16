/* eslint-disable prefer-arrow-callback */
import { produceMessage, waitForAppToBeReady } from './support/app';
import {
  kafkaHasReceived, OutputTopic, startMonitoringKafka, stopMonitoringKafka,
} from './support/kafka';
import { Message } from './support/message';

const Second = 1000;

describe('Message producer', function () {
  before(async function () {
    this.timeout(4 * Second);
    await waitForAppToBeReady();
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
    this.timeout(8 * Second);
    stopMonitoringKafka().then(() => done());
    console.log('1');
  });
});
