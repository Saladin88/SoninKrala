import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { ReferentialDataService } from '../referential-data/referential-data.service';
import { Subscription } from 'rxjs';
import { TermPolicy } from '../referential-data/models/referential-data-response';
import versionConfigVar from '../../config/terms-condition-config.json';
import { formatPublishedAt } from '../utils/date-utils';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-term-agrement-view-component',
  imports: [DatePipe],
  templateUrl: './term-agrement-view-component.component.html',
  styleUrl: './term-agrement-view-component.component.css'
})
export class TermAgrementViewComponentComponent implements OnInit, OnDestroy {

  private readonly versionService = inject(ReferentialDataService);
  private readonly subscriptions: Subscription[] = [];

  version = this.versionService.version;
  versionLabel : string = versionConfigVar.config.label.version;
  formatPublishedAt = formatPublishedAt;

  ngOnInit(): void {
    this.fetchVersion();
  }

  fetchVersion() {
    const subVersionForm = this.versionService.fetchTermVersion().subscribe({
      next : (response : TermPolicy) => {
        console.log(response);
        this.versionService.setVersion(response);
      },
      error : (err) => console.error(err.error.message)
    })
    this.subscriptions.push(subVersionForm);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
