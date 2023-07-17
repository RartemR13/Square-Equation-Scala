import org.scalatest.funsuite.AnyFunSuite

import square_equation_scala.Greeter
class GreeterTest extends AnyFunSuite:
    private def prepare(func: () => Unit): String = 
        var output = java.io.ByteArrayOutputStream()
        Console.withOut(output) {
            func()
        }
        println(output.toString())
        output.toString()

    test("Greeter.first_argument") (
        assert(prepare(Greeter.AskCoeffitients) === "Please, write coeffitient as Doubles.\r\n")
    )