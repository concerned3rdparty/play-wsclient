package controllers

import java.net._
import java.lang._
import play.api._
import libs.concurrent.Akka
import play.api.mvc._
import org.jboss.jbossas.quickstarts.wshelloworld.helloworld.HelloWorldService_Service
import play.api.libs.json._
import play.api._
import models._
import play.libs.F.Promise
import play.api.Play.current

object Application extends Controller {

  var counter = 0

  private def sayHello: String = {
    var s: HelloWorldService_Service = null
    try {
      s = new HelloWorldService_Service(new java.net.URL("http://localhost:8080/jboss-as-helloworld-ws?wsdl"))
    } catch {
      case e: MalformedURLException => {
        throw new RuntimeException(e)
      }
    }
    return s.getHelloWorld.sayHello
  }

  case class Message(content: String, counter: Int)

  implicit object MessageFormat extends Format[Message] {
    def writes(o: Message): JsValue = JsObject(
      List("counter" -> JsNumber(o.counter),
        "content" -> JsString(o.content)))

    def reads(json: JsValue): Message = Message(
      (json \ "content").as[String],
      (json \ "counter").as[Int])
  }

  def index = Action {
    counter = counter + 1
    val promiseOfMessage = Akka.future {
      Message(sayHello, counter)
    }
    Async {
      promiseOfMessage.map(m => Ok(Json.toJson(m)))
    }
    // val m = Message(sayHello, counter)
    // Ok(Json.toJson(m))
    // Ok(views.html.index("Your new application is ready."))
  }

}
