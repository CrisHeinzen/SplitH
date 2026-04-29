import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Group } from '../models/group.model';

@Injectable({
  providedIn: 'root',
})
export class GroupService {
  private http = inject(HttpClient);
  private api = `${environment.apiUrl}/groups`;

  create(group: { name: string, description: string }): Observable<Group> {
    return this.http.post<Group>(this.api, group);
  }

  getAll(): Observable<Group[]> {
    return this.http.get<Group[]>(this.api);
  }

  getById(id: number): Observable<Group> {
    return this.http.get<Group>(`${this.api}/${id}`);
  }

  addMember(groupId: number, email: string): Observable<Group> {
    return this.http.post<Group>(`${this.api}/${groupId}/members`, { email });
  }
  
}