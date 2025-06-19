export interface AccountCreationBody {
  firstname : string
  lastname : string
  username : string
  email : string
  password : string

}

export interface LoginRequestBody {
  username : string
  password : string
}
