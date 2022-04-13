import axios from 'axios';
import { Message } from './message';

const appUrl = process.env.APP_URL || 'http://localhost:8080';

const appClient = axios.create({
  baseURL: `${appUrl}/api/v1`,
});

const produceMessage = async (message: Message) => {
  try {
    await appClient.post('/produce', message);
  } catch (e: any) {
    throw new Error(`Failed to produce a message with error: ${e.message}`);
  }
};

export default produceMessage;
