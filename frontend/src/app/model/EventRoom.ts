import {RoomSeat} from './RoomSeat';

export interface EventRoom {
  id: number;
  name: string;
  nRows: number;
  nColumns: number;
  seats: RoomSeat[];
}
