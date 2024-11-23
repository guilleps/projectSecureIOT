import { Injectable } from '@angular/core';

export interface LogEntry {
  timestamp: string;
  category: string;
  location: string;
  action: string;
}

@Injectable({
  providedIn: 'root',
})
export class LogService {
  private logs: LogEntry[] = [];

  constructor() {}

  addLog(category: string, location: string, action: string) {
    const timestamp = new Date().toLocaleTimeString();
    const lastLog = this.logs[this.logs.length - 1];
    
    // Evitar agregar un log duplicado si el último log es el mismo
    if (!lastLog || lastLog.category !== category || lastLog.location !== location || lastLog.action !== action) {
      this.logs.push({ timestamp, category, location, action });
    }
  }

  getLogs() {
    return this.logs;
  }
}
