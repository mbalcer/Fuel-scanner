import {User} from "./user";

export interface Counter {
    counterState: number,
    counterLocalDate: string,
    fuelTank: number,
    user: User
}
