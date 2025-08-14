import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AccountCreationBody, LoginRequestBody } from '../models/account-creation-body';
import { AccountResponseBody, LoginResponseBody } from '../models/account-response-body';
import { ProfileGeneralInfoUpdateRequest, UpdateProfileGeneralInfoResponse } from '../models/account-patch-body';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private httpClient : HttpClient) { }

  private accountEndPointUri = environment.backBaseUrl;

  createAccount(body : AccountCreationBody) : Observable<AccountResponseBody> {
    const url = this.accountEndPointUri + "/accounts";
    return this.httpClient.post<AccountCreationBody>(url, body);
  }
  login(body : LoginRequestBody) : Observable<LoginResponseBody> {
    const url = this.accountEndPointUri + "/accounts/log-in";
    return this.httpClient.post<LoginResponseBody>(url, body);
  }

  updateAccountInfo(body : ProfileGeneralInfoUpdateRequest) : Observable<UpdateProfileGeneralInfoResponse> {
    const url = this.accountEndPointUri + "/member-profile-info";
    return this.httpClient.patch<UpdateProfileGeneralInfoResponse>(url,body)
  }

}
