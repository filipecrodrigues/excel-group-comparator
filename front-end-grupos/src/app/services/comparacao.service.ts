import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpRequest,
  HttpEvent
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ComparacaoService {

  private readonly API_COMPARAR = 'http://localhost:8080/excel/comparar';
  private readonly API_TEMPLATE = 'http://localhost:8080/excel/template';

  constructor(private http: HttpClient) {}

  /**
   * Upload do Excel com acompanhamento de progresso
   */
  compararExcel(file: File): Observable<HttpEvent<Blob>> {

    const formData = new FormData();
    formData.append('file', file);

    const request = new HttpRequest(
      'POST',
      this.API_COMPARAR,
      formData,
      {
        reportProgress: true,
        responseType: 'blob'
      }
    );

    return this.http.request(request);
  }

  /**
   * Download do template padrão
   */
  baixarTemplate(): Observable<Blob> {
    return this.http.get(this.API_TEMPLATE, {
      responseType: 'blob'
    });
  }
}
