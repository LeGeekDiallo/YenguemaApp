package project.yenguema.yenguema.services

import project.yenguema.yenguema.library.*
import project.yenguema.yenguema.utils.SignInResponse



fun changeUserAvatarRespMapper(respWrapper: ChangeUserAvatarRespWrapper):ChangeUserAvatarResponse{
    return ChangeUserAvatarResponse(
        respWrapper.resp.status
    )
}

fun getUserInfoResponse(respWrapper: UserInfosRespWrapper):UserInfos{
    return UserInfos(
        respWrapper.resp.user_infos,
        respWrapper.resp.carShops,
        respWrapper.resp.trips_posted,
        respWrapper.resp.prestS,
        respWrapper.resp.ads_posted,
        respWrapper.resp.aparts_posted,
        respWrapper.resp.officeShopLand_posted,
        respWrapper.resp.taxi,
        respWrapper.resp.studios_posted,
        respWrapper.resp.houses_posted,
        respWrapper.resp.jobs_posted,
        respWrapper.resp.jobs_applied,
        respWrapper.resp.courses_posted
    )
}
fun mapYenguemaLibraryDataToUser(respWrapper: SignInRespWrapper):SignInResponse{
    return SignInResponse(
        respWrapper.resp.response,
        respWrapper.resp.registered,
        respWrapper.resp.useremail,
        respWrapper.resp.password
    )
}

fun newPrestSResponse(respWrapper: NewPrestSRespWrapper):NewPrestSResponse{
    return NewPrestSResponse(respWrapper.resp.status)
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