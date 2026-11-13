export class Movie {
  private readonly _id: string;
  private readonly _name: string;
  private readonly _durationInMinutes: number;

  constructor(id: string, name: string, durationInMinutes: number) {
    this._id = id;
    this._name = name;
    this._durationInMinutes = durationInMinutes;
  }

  get id(): string {
    return this._id;
  }

  get name(): string {
    return this._name;
  }

  get durationInMinutes(): number {
    return this._durationInMinutes;
  }
}
