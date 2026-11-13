import { Payment } from "./Payment.js";
import { Seat } from "./Seat.js";
import { Show } from "./Show.js";

export class Booking {
  private readonly _id: string;
  private readonly _show: Show;
  private readonly _seats: Seat[];
  private readonly _userId: string;
  private _payment?: Payment;

  constructor(id: string, show: Show, seats: Seat[], userId: string) {
    this._id = id;
    this._show = show;
    this._seats = seats;
    this._userId = userId;
  }

  get id(): string {
    return this._id;
  }

  get show(): Show {
    return this._show;
  }

  get seats(): Seat[] {
    return this._seats;
  }

  get userId(): string {
    return this._userId;
  }

  get payment(): Payment | undefined {
    return this._payment;
  }

  setPayment(payment: Payment): void {
    this._payment = payment;
  }
}
