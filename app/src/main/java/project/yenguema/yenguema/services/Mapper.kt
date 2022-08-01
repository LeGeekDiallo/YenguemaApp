package project.yenguema.yenguema.services

import project.yenguema.yenguema.library.LogInRespWrapper
import project.yenguema.yenguema.library.LogInResponse
import project.yenguema.yenguema.library.SignInRespWrapper
import project.yenguema.yenguema.tools.SignInResponse

fun mapYenguemaLibraryDataToUser(respWrapper: SignInRespWrapper):SignInResponse{
    return SignInResponse(
        respWrapper.resp.response,
        respWrapper.resp.registered,
        respWrapper.resp.useremail,
        respWrapper.resp.password
    )
}

fun logInDataResponse(respWrapper: LogInRespWrapper):LogInResponse{
    if (respWrapper.resp.logIn == null){
        return LogInResponse(
            respWrapper.resp.logIn,
            respWrapper.resp.u_id,
            respWrapper.resp.email,
            respWrapper.resp.usergender,
            respWrapper.resp.avatar,
            respWrapper.resp.avatar_URL
        )
    }else{
        if (respWrapper.resp.logIn){
            return LogInResponse(
                respWrapper.resp.logIn,
                respWrapper.resp.u_id,
                respWrapper.resp.email,
                respWrapper.resp.usergender,
                respWrapper.resp.avatar,
                respWrapper.resp.avatar_URL
            )
        }else{
            return LogInResponse(
                respWrapper.resp.logIn,
                respWrapper.resp.u_id,
                respWrapper.resp.email,
                respWrapper.resp.usergender,
                respWrapper.resp.avatar,
                ""
            )
        }
    }
}