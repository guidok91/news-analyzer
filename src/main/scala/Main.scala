import calculator.Calculator

@main
def main(x: Int, others: String*): Unit = {
  println(s"The double of $x is ${Calculator.double(x)}")
}
