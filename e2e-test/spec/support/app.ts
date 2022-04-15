import axios from 'axios';
import { busywait } from 'busywait';
import { Message } from './message';

const appUrl = process.env.APP_URL || 'http://localhost:8080';

const appClient = axios.create({
  baseURL: `${appUrl}/api/v1`,
});

export const waitForAppToBeReady = async () => {
  await busywait(async () => {
    const response = await appClient.get('/mgmt/health');
    if (response.data.status !== 'UP') {
      throw new Error('App isn\'t ready yet');
    }
  }, {
    sleepTime: 100,
    maxChecks: 20,
  });
};

export const produceMessage = async (message: Message) => {
  try {
    await appClient.post('/produce', message);
  } catch (e: any) {
    throw new Error(`Failed to produce a message with error: ${e.message}`);
  }
};
