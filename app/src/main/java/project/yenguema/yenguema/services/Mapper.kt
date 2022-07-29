package project.yenguema.yenguema.services

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