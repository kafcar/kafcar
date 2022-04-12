import axios from 'axios';
import { Message } from './message';

const appUrl = process.env.APP_URL || 'http://localhost:8080';

const appClient = axios.create({
  baseURL: `${appUrl}/api/v1`,
});

const produceMessage = async (message: Message) => {
  await appClient.post('/produce', message);
};

export default produceMessage;
