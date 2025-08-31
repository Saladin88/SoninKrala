import { HttpClient } from '@angular/common/http';
import { Injectable, Signal, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AccountCreationBody, LoginRequestBody } from '../models/account-creation-body';
import { AccountResponseBody, LoginResponseBody } from '../models/account-response-body';
import { ProfileGeneralInfoUpdateRequest, UpdateProfileGeneralInfoResponse } from '../models/account-patch-body';
import { AccountGeneralInfo } from '../models/account-general-info';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private httpClient : HttpClient) { }

  private profilInfo = signal<AccountGeneralInfo | null>(null);

  private accountEndPointUri = environment.backBaseUrl;

  createAccount(body : AccountCreationBody) : Observable<AccountResponseBody> {
    const url = this.accountEndPointUri + "/accounts";
    return this.httpClient.post<AccountResponseBody>(url, body);
  }
  login(body : LoginRequestBody) : Observable<LoginResponseBody> {
    const url = this.accountEndPointUri + "/accounts/log-in";
    return this.httpClient.post<LoginResponseBody>(url, body);
  }

  updateAccountInfo(body : ProfileGeneralInfoUpdateRequest, image? : File) : Observable<UpdateProfileGeneralInfoResponse> {
    const url = this.accountEndPointUri + "/accounts/member-profile-info";
    const formData = new FormData();
    formData.append('body', new Blob([JSON.stringify(body)], {type: "application/json"}))
    if (image) formData.append('image', image);
    return this.httpClient.patch<UpdateProfileGeneralInfoResponse>(url,formData)
  }

  retrieveGeneralInfoProfile() : Observable<AccountGeneralInfo> {
    const url = this.accountEndPointUri + "/accounts/member-profile-info";
    return this.httpClient.get<AccountGeneralInfo>(url);
    
  }

  get profilInfoSignal() {
    return this.profilInfo;
  }
}
