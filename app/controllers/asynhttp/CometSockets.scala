package controllers.asynhttp

import play.api.mvc.{Action, Controller}
import play.api.libs.iteratee.{Enumeratee, Enumerator}
import play.api.templates.Html
import play.api.libs.Comet

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午9:58
 */
object CometSockets extends Controller {
	def comet = Action {
		val events = Enumerator(
			"<script>console.log('kiki')</script>",
			"<script>console.log('foo')</script>",
			"<script>console.log('bar')</script>"
		) >>> Enumerator.eof
		Ok.stream(events).as(HTML)
	}

	val toCometMessage = Enumeratee.map[String](data => {
		Html(s"<script>console.log('$data')</script>")
	})

	def comet2 = Action {
		val events = Enumerator("kiki", "foo", "bar")
		Ok.stream((events &> toCometMessage) >>> Enumerator.eof)
	}

	def comet3 = Action {
		val events = Enumerator("kiki", "foo", "bar") &> Comet(callback = "console.log")
		Ok.stream(events >>> Enumerator.eof)
	}
}
