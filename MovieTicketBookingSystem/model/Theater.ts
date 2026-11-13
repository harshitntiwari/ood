import { Screen } from "./Screen.js";

export class Theater {
  private readonly _id: string;
  private readonly _name: string;
  private readonly _screens: Screen[] = [];

  constructor(id: string, name: string) {
    this._id = id;
    this._name = name;
  }

  get id(): string {
    return this._id;
  }

  get name(): string {
    return this._name;
  }

  get screens(): Screen[] {
    return this._screens;
  }

  addScreen(screen: Screen): void {
    this._screens.push(screen);
  }
}
