package info.antoniojimenez.jobsboard.playground

import cats.effect.*

import java.util.Properties
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.internet.MimeMessage
import javax.mail.Message
import javax.mail.Transport

import info.antoniojimenez.jobsboard.core.*
import info.antoniojimenez.jobsboard.config.*

object EmailsPlayground {
  def main(args: Array[String]): Unit = {
    // configs

    val host        = "smtp.ethereal.email"
    val port        = 587
    val user        = "frida.kohler47@ethereal.email"
    val pass        = "CyPzh23BceYqpFkMgw"
    val frontendUrl = "https://google.com"

    val token = "ABCD1234"
    // user, pass, host, port

    // properties file
    val prop = new Properties
    prop.put("mail.smtp.auth", true)
    prop.put("mail.smtp.starttls.enable", true)
    prop.put("mail.smtp.host", host)
    prop.put("mail.smtp.port", port)
    prop.put("mail.smtp.ssl.trust", host)

    // authentication
    val auth = new Authenticator {
      override protected def getPasswordAuthentication(): PasswordAuthentication =
        new PasswordAuthentication(user, pass)
    }

    // session
    val session = Session.getInstance(prop, auth)

    // email itself
    val subject = "Email from Antonio"
    val content = s"""
    <div style ="
        border: 1px solid black;
        padding: 20px;
        font-family: sans-serif;
        line-height: 2;
        font-size: 20px;
    ">
    <h1> Antonio: Password Recovery </h1>
    <p> Your password recovery token is $token </p>
    <p>
        Click <a href="$frontendUrl/login">here</a> to get back to the application
    </p>
    <p>from Antonio </p>
    </div>
    """

    // message = MIME message
    val message = new MimeMessage(session)
    message.setFrom("antonio.jimenez.nieto@gmail.com")
    message.setRecipients(Message.RecipientType.TO, "the.user@gmail.com")
    message.setSubject(subject)
    message.setContent(content, "text/html; charset=utf-8")

    // send
    Transport.send(message)
  }
}

object EmailsEffectPlayground extends IOApp.Simple {

  override def run: IO[Unit] = for {
    emails <- LiveEmails[IO](
      EmailServiceConfig(
        host = "smtp.ethereal.email",
        port = 587,
        user = "frida.kohler47@ethereal.email",
        pass = "CyPzh23BceYqpFkMgw",
        frontendUrl = "https://google.com"
      )
    )
    _ <- emails.sendPasswordRecoveryEmail("someone@swissborg.com", "BORG")
  } yield ()

}
