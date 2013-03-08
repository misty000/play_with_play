package controllers.http

import play.api.mvc.{Action, Controller}

/**
 * 重要：Flash 上下文只应用在非ajax请求的普通应用中，用来传输类似success/error的消息。
 * 因为数据仅保存到下一次请求，又因在复杂的应用中无法担保请求顺序，Flash会受竞争条件影响。
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午3:39
 */
object Flashs extends Controller {
	def index = Action {
		implicit request =>
			Ok {
				flash.get("success").getOrElse("Welcome!")
			}
	}

	def save = Action {
		Redirect("/home").flashing {
			"success" -> "The item has been created"
		}
	}
}
