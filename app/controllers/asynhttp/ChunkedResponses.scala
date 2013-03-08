package controllers.asynhttp

import play.api.mvc.{Action, Controller}
import java.io.FileInputStream
import play.api.libs.iteratee.Enumerator

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午9:30
 */
object ChunkedResponses extends Controller {
	def getDataStream = new FileInputStream("")

	def chunked = Action {
		val data = getDataStream
		val dataContent = Enumerator.fromStream(data)
		Ok.stream(dataContent)
	}

	def eof = Action {
		Ok.stream(Enumerator("kiki", "foo", "bar").andThen(Enumerator.eof))
	}
}
