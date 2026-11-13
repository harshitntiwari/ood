export class InvalidMoveException extends Error {
    constructor(msg: string) {
        super(msg);
        this.name = "InvalidMoveException";
    }
}
