import { User } from './user.model';

export interface Transaction {
  id?: number;
  description: string;
  amount: number;
  date: string;
  type: 'INCOME' | 'EXPENSE';
  category: string;
  account: string;
  groupName?: string;
  user?: User;
}

export interface TransactionPayload {
  description: string;
  amount: number;
  date: string;
  type: string;
  category: string;
  account: string;
  groupName: string;
  participantIds?: number[];
}
