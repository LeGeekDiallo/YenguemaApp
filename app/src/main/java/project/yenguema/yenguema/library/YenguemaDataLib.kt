package project.yenguema.yenguema.library

data class SignInRespWrapper(
    val resp: SignInResponse
)

data class SignInResponse(
    val response:String,
    val registered:Boolean,
    val useremail:String,
    val password:String,
)

const val baseURLDev = "https://127.0.0.1:8000/"

