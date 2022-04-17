import {EventPrice} from './EventPrice';
import {Show} from './Show';
import {EventRoom} from './EventRoom';

export interface NormalEvent {
  id: number;
  date: string;
  startTime: string;
  endTime: string;
  price: EventPrice;
  show: Show | null;
  room: EventRoom;
}
