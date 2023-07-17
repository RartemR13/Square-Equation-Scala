import org.scalatest.funsuite.AnyFunSuite

import square_equation_scala.Greeter
class GreeterTest extends AnyFunSuite:
    private def prepare(func: () => Unit): String = 
        val output = java.io.ByteArrayOutputStream()
        Console.withOut(output) (func)
        output.toString()

    test("Greeter.first_argument") (
        assert(prepare(Greeter.AskCoeffitients) === "Please, write coeffitient as Doubles.\r\n")
    )
end GreeterTest