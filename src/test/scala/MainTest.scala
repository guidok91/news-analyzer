import org.scalatest.funsuite.AnyFunSuite

class MainTest extends AnyFunSuite:
  test("Test double function with positive argument") {
    assert(double(4) == 8)
  }

  test("Test double function with zero argument") {
    assert(double(0) == 0)
  }

  test("Test double function with negative argument") {
    assert(double(-5) == -10)
  }
