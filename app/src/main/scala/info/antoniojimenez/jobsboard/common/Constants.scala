package info.antoniojimenez.jobsboard.common

object Constants {
  val emailRegex =
    """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"""

  object endpoints {
    val root   = "http://localhost:4041"
    val signup = s"$root/api/auth/users"
    val login  = s"$root/api/auth/login"
    val logout = s"$root/api/auth/logout"
    val checkToken = s"$root/api/auth/checkToken"
  }

  object cookies {
    val duration = 10 * 24 * 3600 * 1000
    val email    = "email"
    val token    = "token"
  }

}
