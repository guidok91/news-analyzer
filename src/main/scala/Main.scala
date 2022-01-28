import calculator.Calculator
import config.ConfigManager

@main
def main(args: String*): Unit = {
  val config = ConfigManager.config
  val number = config.getInt("number")
  println(s"The double of $number is ${Calculator.double(number)}")
}
