export interface AccountCreationBody {
  firstname : string
  lastname : string
  username : string
  email : string
  password : string
  isRgpdAgreed : boolean

}

export interface LoginRequestBody {
  username : string
  password : string
}
