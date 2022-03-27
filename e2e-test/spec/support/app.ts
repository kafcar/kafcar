import axios from 'axios';
import { Message } from './message';

export const produceMessage = async (message: Message) => {
  await axios.post('/produce', message);
};
