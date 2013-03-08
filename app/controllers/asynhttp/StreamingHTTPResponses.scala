package controllers.asynhttp

import play.api.mvc.{ResponseHeader, SimpleResult, Action, Controller}
import play.api.libs.iteratee.Enumerator
import java.io.File

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午9:14
 */
object StreamingHTTPResponses extends Controller {
	def simple = Action {
		SimpleResult(
			header = ResponseHeader(200),
			body = Enumerator("Hello World")
		)
	}

	def badBigData = Action {
		val file = new File("D:\\Tools\\play-2.1.0\\repository\\local\\ch.qos.logback\\logback-core\\1.0.7\\jars\\logback-core.jar")
		val fileContent = Enumerator.fromFile(file)
		SimpleResult(
			header = ResponseHeader(200),
			body = fileContent
		)
	}

	def fixedBigData = Action {
		val file = new File("D:\\Tools\\play-2.1.0\\repository\\local\\ch.qos.logback\\logback-core\\1.0.7\\jars\\logback-core.jar")
		val fileContent = Enumerator.fromFile(file)
		SimpleResult(
			//通过这种方式，Play將以懒加载的方式使用enumerator，一块一块的將可用数据拷贝到HTTP响应中。
			header = ResponseHeader(200, Map(CONTENT_LENGTH -> file.length().toString)),
			body = fileContent
		)
	}

	def file = Action {
		Ok.sendFile(new File("D:\\Tools\\play-2.1.0\\repository\\local\\ch.qos.logback\\logback-core\\1.0.7\\jars\\logback-core.jar"))
	}

	def renameFile = Action {
		Ok.sendFile(
			content = new File(""),
			fileName = _ => "RenameFile.jar"
			//你也能通过不指定文件名，避免浏览器下载该文件，而仅让浏览器显示该文件内容，如text，HTML或图片等这些浏览器原生支持的文件类型。
		)
	}

	def inlineFile = Action {
		Ok.sendFile(
			content = new File(""),
			inline = true
		)
		//Now you don’t have to specify a file name since the web browser will not try to download it, but will just display the file content in the web browser window. This is useful for content types supported natively by the web browser, such as text, HTML or images.
	}
}
