import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Transaction, TransactionPayload } from '../models/transaction.model';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  private http = inject(HttpClient);
  
  private apiUrl = `${environment.apiUrl}/transactions`;

  create(transaction: TransactionPayload): Observable<Transaction> {
    return this.http.post<Transaction>(this.apiUrl, transaction);
  }

  getAll(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(this.apiUrl);
  }

  update(id: number, transaction: TransactionPayload): Observable<Transaction> {
    return this.http.put<Transaction>(`${this.apiUrl}/${id}`, transaction);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getByGroup(groupName: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/group/${groupName}`);
  }

// Front/src/app/core/services/transaction-service.ts

uploadStatement(groupName: string, file: File, bank: string): Observable<{ message?: string }> {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('bank', bank);
  
  // Confirme se o seu TransactionController.java espera o parâmetro como 'groupName' ou 'groupId'
  formData.append('groupName', groupName); 

  return this.http.post(`${this.apiUrl}/import`, formData);
}

}