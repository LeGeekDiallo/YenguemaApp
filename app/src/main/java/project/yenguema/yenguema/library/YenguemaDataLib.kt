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

data class LogInRespWrapper(
    val resp: LogInResponse
)

data class LogInResponse(
    val logIn: Boolean?,
    val u_id: Int?,
    val email: String?,
    val usergender: Int?,
    val avatar: String?,
    val avatar_URL: String?
)
const val baseURLDev = "https://www.leyenguema.com/"