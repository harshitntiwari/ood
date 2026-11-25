import { SeatType } from "../enums/SeatType.js";

export class Seat {
  private readonly _id: string;
  private readonly _type: SeatType;

  constructor(id: string, type: SeatType) {
    this._id = id;
    this._type = type;
  }

  get id(): string {
    return this._id;
  }

  get type(): SeatType {
    return this._type;
  }
}
