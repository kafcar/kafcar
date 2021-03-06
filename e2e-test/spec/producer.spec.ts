/* eslint-disable prefer-arrow-callback */
import { produceMessage, waitForAppToBeReady } from './support/app';
import {
  kafkaHasReceived, OutputTopic, startMonitoringKafka, stopMonitoringKafka,
} from './support/kafka';
import { Message } from './support/message';

describe('Message producer', function () {
  this.timeout(0);
  before(async function waitForApp() {
    await waitForAppToBeReady();
  });

  before(async function connectingKafka() {
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
    stopMonitoringKafka().then(() => done());
  });
});
