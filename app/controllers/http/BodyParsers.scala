package controllers.http

import play.api.mvc._
import java.io.File

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午3:55
 */
object BodyParsers extends Controller {
	def save = Action(request => {
		val body = request.body
		val textBody = body.asText
		textBody map {
			text => Ok(s"Got: $text")
		} getOrElse {
			BadRequest("Expecting text/plain request body")
		}
	})

	def save2 = Action(parse.text)(request => {
		Ok(s"Got: ${request.body}")
	})

	def save3 = Action(parse.tolerantText)(request => {
		Ok(s"Got: ${request.body}")
	})

	def save4 = Action(parse.file(to = new File("/tmp/upload")))(request => {
		Ok(s"Saved the request content to ${request.body}")
	})

	val storeInUserFile = parse.using(request => {
		import parse._
		request.session.get("username") map {
			user => file(to = new File(s"/tmp/$user.upload"))
		} getOrElse {
			error(Unauthorized("You don't have the right to upload here"))
		}
	})

	def save5 = Action(storeInUserFile)(request => {
		Ok(s"Saved the request content to ${request.body}")
	})

	/**
	 * 默认最大长度为100KB
	 * 最大内容长度可以在application.conf中设置:
	 * parsers.text.maxLength=128K
	 */
	def save6 = Action(parse.text(maxLength = 1024 * 10))(request => {
		Ok(s"Got: ${request.body}")
	})

	def save7 = Action(parse.maxLength(1024 * 10, storeInUserFile))(request => {
		Ok(s"Saved the request content to ${request.body}")
	})
}
