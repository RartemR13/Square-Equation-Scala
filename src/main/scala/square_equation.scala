import scala.io.StdIn

object Greeter:
    def AskCoeffitients() =
        println("Please, write coeffitient as Doubles.")

    def AskCoeffitientA() =
        println("Ok, now write first coeffitient: ")

    def AskCoeffitientB() =
        println("Yep, next, second: ")

    def AskCoeffitientC() =
        println("At last, free member: ")
end Greeter

class SquareEquationCoeffitients:
    private var _a: Double = 0
    private var _b: Double = 0
    private var _c: Double = 0

    def a: Double = _a
    def b: Double = _b
    def c: Double = _c

    def a_=(newValue: Double) = 
        _a = newValue

    def b_=(newValue: Double) =
        _b = newValue

    def c_=(newValue: Double) =
        _c = newValue

    override def toString(): String = 
        _a.toString() + " " + _b.toString() + " " + _c.toString()
end SquareEquationCoeffitients


val GetSquareEquationCoeffitient = () =>
    var coeffitients = SquareEquationCoeffitients()
    Greeter.AskCoeffitients()

    Greeter.AskCoeffitientA()
    coeffitients.a = StdIn.readDouble()
    
    Greeter.AskCoeffitientB()
    coeffitients.b = StdIn.readDouble()

    Greeter.AskCoeffitientC()
    coeffitients.c = StdIn.readDouble()
    
    coeffitients
end GetSquareEquationCoeffitient

@main def main() =
    println(GetSquareEquationCoeffitient())
    