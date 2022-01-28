@main
def main(x: Int, others: String*): Unit = {
  println(s"The double of $x is ${double(x)}")
}

def double(x: Int): Int = x * 2
