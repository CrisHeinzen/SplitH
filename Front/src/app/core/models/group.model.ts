import { User } from './user.model';

export interface Group {
  id: number;
  name: string;
  description: string;
  members: User[];
}
