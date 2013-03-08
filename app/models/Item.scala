package models

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午8:39
 */
object Item {

	import collection.JavaConversions._

	def findAll: java.util.List[Item] = List(new Item, new Item)
}

case class Item()