import { SeatStatus } from "../enums/SeatStatus.js";
import { SeatType } from "../enums/SeatType.js";
import { Movie } from "./Movie.js";
import { Screen } from "./Screen.js";
import { Seat } from "./Seat.js";
import { Theater } from "./Theater.js";

export class Show {
  private readonly _id: string;
  private readonly _movie: Movie;
  private readonly _theater: Theater;
  private readonly _screen: Screen;
  private readonly _startTime: Date;
  private readonly _seatPricing = new Map<SeatType, number>();
  private readonly _seatStatusMap = new Map<string, SeatStatus>();

  constructor(
    id: string,
    movie: Movie,
    theater: Theater,
    screen: Screen,
    startTime: Date,
  ) {
    this._id = id;
    this._movie = movie;
    this._theater = theater;
    this._screen = screen;
    this._startTime = startTime;

    // initially mark all seats for this show as available
    for (const seat of screen.seats) {
      this._seatStatusMap.set(seat.id, SeatStatus.AVAILABLE);
    }
  }

  get id(): string {
    return this._id;
  }

  get movie(): Movie {
    return this._movie;
  }

  get theater(): Theater {
    return this._theater;
  }

  get screen(): Screen {
    return this._screen;
  }

  get startTime(): Date {
    return this._startTime;
  }

  getPriceForSeatType(seatType: SeatType): number | undefined {
    return this._seatPricing.get(seatType);
  }

  setSeatPricing(seatType: SeatType, price: number): void {
    this._seatPricing.set(seatType, price);
  }

  getSeatStatus(seat: Seat): SeatStatus | undefined {
    return this._seatStatusMap.get(seat.id);
  }

  setSeatStatus(seat: Seat, seatStatus: SeatStatus): void {
    this._seatStatusMap.set(seat.id, seatStatus);
  }
}
