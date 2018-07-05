package com.seanmcapp.util.requestbuilder

import com.seanmcapp.config.TelegramConf
import com.seanmcapp.repository.Photo

import scalaj.http.{Http, HttpRequest}

trait TelegramRequestBuilder extends HttpRequestBuilder {

  val telegramConf = TelegramConf()
  override val baseUrl = telegramConf.endpoint

  def getTelegramSendPhoto(chatId: Long, photo: Photo, prefix: String =""): HttpRequest = {
    val photoId = photo.id
    val inlineKeyboard =
      s"""
        |{
        |"inline_keyboard":[
        |[
        |{"text":"1","callback_data":"1:$photoId"},
        |{"text":"2","callback_data":"2:$photoId"},
        |{"text":"3","callback_data":"3:$photoId"},
        |{"text":"4","callback_data":"4:$photoId"},
        |{"text":"5","callback_data":"5:$photoId"}
        |]
        |]
        |}
      """.stripMargin.replaceAll("\n", "")

    val urlString = baseUrl + "/sendphoto" +
      "?chat_id=" + chatId +
      "&photo=" + photo.thumbnailSrc +
      "&caption=" + prefix + photo.caption +
      "%0A%40" + photo.account +
      "&reply_markup=" + inlineKeyboard
    Http(urlString)
  }

  def getTelegramSendMessege(chatId: Long, text: String): HttpRequest = {
    val urlString = baseUrl + "/sendmessage?chat_id=" + chatId + "&text=" + text
    Http(urlString)
  }

  def getAnswerCallbackQuery(queryId: String, notificationText: String) = {
    val urlString = baseUrl + "/answerCallbackQuery?callback_query_id=" + queryId + "&text=" + notificationText
    Http(urlString)
  }

}
