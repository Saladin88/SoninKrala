import { formatDate } from "@angular/common";


export function formatPublishedAt(date: string | Date | null | undefined): string {
    if (!date) return '—';
    return formatDate(date, 'dd/MM/yyyy', 'fr'); // locale FR enregistrée comme ci-dessus
  }