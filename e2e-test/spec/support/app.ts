import axios from 'axios';
import { Message } from './message';

const appClient = axios.create({
  baseURL: `${process.env.APP_URL}/api/v1`,
});

const produceMessage = async (message: Message) => {
  await appClient.post('/produce', message);
};

export default produceMessage;
