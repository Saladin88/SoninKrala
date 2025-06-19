import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-account-validation',
  imports: [CommonModule],
  templateUrl: './account-validation.component.html',
  styleUrl: './account-validation.component.css'
})
export class AccountValidationComponent implements OnInit{

  message! : string | null;

  constructor(private route : ActivatedRoute, private router : Router ) {}

  ngOnInit(): void {
    const status = this.route.snapshot.queryParamMap.get('validation');

    switch (status) {
      case 'valid':
        this.message = '✅ Votre compte a été validé avec succès !';
        break;
      case 'expired':
        this.message = '⚠️ Le lien de validation a expiré.';
        break;
      case 'error':
        this.message = '❌ Le lien est invalide ou déjà utilisé.';
        break;
      default:
        this.message = null;
    }

    //Ajouter un spinner entre le moment d'attente et la redirection
    setTimeout(() => {
      this.router.navigate(['/home']);
    }, 5000);
  }




}
