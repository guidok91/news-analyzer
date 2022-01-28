import org.scalatest.funsuite.AnyFunSuite
import calculator.Calculator

class CalculatorDoubleTest extends AnyFunSuite:
  test("Test double with positive argument") {
    assert(Calculator.double(4) == 8)
  }

  test("Test double with zero argument") {
    assert(Calculator.double(0) == 0)
  }

  test("Test double with negative argument") {
    assert(Calculator.double(-5) == -10)
  }
