import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpEventType } from '@angular/common/http';
import { ComparacaoService } from './services/comparacao.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  arquivo?: File;
  nomeArquivo?: string;

  carregando = false;
  progresso = 0;

  tempoMinimoBarra = 600; // ms
  inicioUpload = 0;

  constructor(private service: ComparacaoService) {}

  // ========================
  // SELEÇÃO VIA INPUT
  // ========================
  onFile(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.arquivo = input.files[0];
      this.nomeArquivo = this.arquivo.name;
      this.progresso = 0;
    }
  }

  // ========================
  // DRAG & DROP
  // ========================
  onDragOver(event: DragEvent) {
    event.preventDefault();
  }

  onDrop(event: DragEvent) {
    event.preventDefault();

    if (event.dataTransfer && event.dataTransfer.files.length > 0) {
      this.arquivo = event.dataTransfer.files[0];
      this.nomeArquivo = this.arquivo.name;
      this.progresso = 0;
    }
  }

  // ========================
  // ENVIO
  // ========================
  enviar() {
    if (!this.arquivo) return;

    this.carregando = true;
    this.progresso = 0;
    this.inicioUpload = Date.now();

    this.service.compararExcel(this.arquivo).subscribe({
      next: event => {

        if (event.type === HttpEventType.UploadProgress && event.total) {
          this.progresso = Math.round(
            (event.loaded / event.total) * 100
          );
        }

        if (event.type === HttpEventType.Response) {

          const tempoDecorrido = Date.now() - this.inicioUpload;
          const atraso = Math.max(0, this.tempoMinimoBarra - tempoDecorrido);

          setTimeout(() => {
            this.download(event.body!, 'resultado_grupos.xlsx');
            this.carregando = false;
            this.progresso = 0;
          }, atraso);
        }
      },
      error: () => {
        this.carregando = false;
        this.progresso = 0;
      }
    });
  }

  baixarTemplate() {
    this.service.baixarTemplate().subscribe(blob => {
      this.download(blob, 'template_comparacao_grupos.xlsx');
    });
  }

  private download(blob: Blob, nomeArquivo: string) {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = nomeArquivo;
    a.click();
    window.URL.revokeObjectURL(url);
  }
}
