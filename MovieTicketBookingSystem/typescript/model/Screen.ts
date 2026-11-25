import { Seat } from "./Seat.js";

export class Screen {
  private readonly _id: string;
  private readonly _seats: Seat[] = [];

  constructor(id: string) {
    this._id = id;
  }

  get id(): string {
    return this._id;
  }

  get seats(): Seat[] {
    return this._seats;
  }

  addSeat(seat: Seat): void {
    this._seats.push(seat);
  }
}
