import {EventPrice} from './EventPrice';
import {Show} from './Show';
import {EventRoom} from './EventRoom';

export interface AddEvent {
  id: number;
  startDate: string;
  endDate: string | null;
  startTimes: string[];
  price: EventPrice;
  show: Show | null;
  room: EventRoom;
}
