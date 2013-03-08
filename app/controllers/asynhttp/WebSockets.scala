package controllers.asynhttp

import play.api.mvc.{WebSocket, Controller}
import play.api.libs.iteratee.{Enumerator, Iteratee}

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午10:29
 */
object WebSockets extends Controller {
	def websocket = WebSocket.using[String](req => {
		val in = Iteratee.foreach[String](println) mapDone (_ => {
			println("Disconnected")
		})
		val out = Enumerator("Hello!")
		(in, out)
	})
}
