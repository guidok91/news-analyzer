import config.ConfigManager

@main
def main(args: String*): Unit = {
  val r = requests.get("https://api.github.com/users/lihaoyi")
  val json = ujson.read(r.text()).obj
  println(s"Response json keys: ${json.keys}")
  println(s"Response json login: ${json.obj.get("login")}")
}
