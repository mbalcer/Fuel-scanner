import {User} from "./user";

export interface Receipt {
  url: string,
  content: string,
  receiptLocalDate: string,
  litres: number,
  pricePerLitres: number,
  cost: number,
  user: User
}
