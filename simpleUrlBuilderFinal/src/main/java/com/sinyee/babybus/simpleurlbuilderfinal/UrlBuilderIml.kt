package com.sinyee.babybus.simpleurlbuilderfinal

import android.app.Activity
import com.sinyee.babybus.simpleurlbuilderfinal.sdk.AppsFlayerDataBuilder
import com.sinyee.babybus.simpleurlbuilderfinal.sdk.referrer.ReferrerAccountId
import com.sinyee.babybus.simpleurlbuilderfinal.utils.DomenHolder
import com.sinyee.babybus.simpleurlbuilderfinal.utils.FacebookConst
import com.sinyee.babybus.simpleurlbuilderfinal.utils.decrypt

object SimpleUrlBuilder {

    suspend fun build(
        domen: String? = null,
        fbKey: String,
        devKey: String,
        battery: String? = null,
        facebookId: String,
        facebookToken: String,
        isDevSettings: Boolean? = null,
        context: Activity,
        isStaticFacebook: Boolean
    ): GameInfoData {
        val deviceData = DeviceDataBuilder(
            battery = battery.toString(),
            isDevSettings = isDevSettings,
            context = context,
            devKey = devKey,
            facebookId = facebookId,
            facebookToken = facebookToken,
        ).getDeviceInfoUseCase()

        FacebookConst.setFacebookConst(id = facebookId, token = facebookToken, isStaticFacebook)

        val tracker: String = domen ?: DomenHolder.getRandomDome()
        val appsFlyerData =
            AppsFlayerDataBuilder.getAppsFlyerData(activity = context, devKey = devKey)
        val campaign = appsFlyerData.score
        val appsFlyerStr = appsFlyerData.info
        val deviceDataStr = deviceData.info
        val afUserId = deviceData.userId
        val subsData = SubBuilder.getSubData(campaign)
        val push = subsData.gameItem
        val subsStr = subsData.gameItems
        val pushStr = "${"JnB1c2g9".decrypt()}$push"
        val referrerAccountId = ReferrerAccountId(context).accountId(fbKey)
        val url = "$tracker$referrerAccountId$appsFlyerStr$deviceDataStr$subsStr$pushStr"
        return GameInfoData(info = url, userIdInfo = afUserId, push = push)
    }
}

