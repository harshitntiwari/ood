import { PaymentStatus } from "../enums/PaymentStatus.js";
import { PaymentType } from "../enums/PaymentType.js";

export class Payment {
  private readonly _amount: number;
  private readonly _type: PaymentType;
  private _status: PaymentStatus;

  constructor(amount: number, type: PaymentType) {
    this._amount = amount;
    this._type = type;
    this._status = PaymentStatus.PENDING;
  }

  get amount(): number {
    return this._amount;
  }

  get type(): PaymentType {
    return this._type;
  }

  get status(): PaymentStatus {
    return this._status;
  }

  setStatus(status: PaymentStatus): void {
    this._status = status;
  }
}
