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

class LinearEquationCoeffitients:
    private var _b: Double = 0
    private var _c: Double = 0

    def b: Double = _b
    def c: Double = _c

    def b_=(newValue: Double) =
        _b = newValue

    def c_=(newValue: Double) =
        _c = newValue

    override def toString(): String =
        _b.toString() + " " + _c.toString()

    def ==(other: LinearEquationCoeffitients): Boolean =
        (_b == other.b &&
         _c == other.c)

    def !=(other: LinearEquationCoeffitients): Boolean =
        !(this == other)

    def this(b: Double, c: Double) = 
        this()

        _b = b
        _c = c
end LinearEquationCoeffitients

class SquareEquationCoeffitients extends LinearEquationCoeffitients:
    private var _thisLinearPart: LinearEquationCoeffitients = this
    private var _a: Double = 0

    def a: Double = _a

    def a_=(newValue: Double) = 
        _a = newValue

    override def toString(): String = 
        _a.toString() + " " + _thisLinearPart.toString()

    def ==(other: SquareEquationCoeffitients) : Boolean =
        val otherLinearPart: LinearEquationCoeffitients = other

        (_a == other.a &&
         _thisLinearPart == otherLinearPart)

    def !=(other: SquareEquationCoeffitients) : Boolean =
        !(this == other)

    def this(a: Double, b: Double, c: Double) =
        this()

        _a = a
        this.b = b
        this.c = c
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

class LinearEquationSolver(coeffitients: LinearEquationCoeffitients):
    private var _hasSolution: Boolean = false
    private var _hasInfinityCountSolutions: Boolean = false
    private var _solution: Double = 0

    class LinearEquationHasNoSolutions extends RuntimeException
    class LinearEquationHasInfinityCountSolutions extends RuntimeException

    def Solve(): Unit = 
        if coeffitients == LinearEquationCoeffitients(0, 0) then
            _hasSolution = true
            _hasInfinityCountSolutions = true
        else
            if coeffitients.b != 0 then
                _hasSolution = true
                _solution = -coeffitients.c / coeffitients.b

    def GetSolution(): Double = 
        if _hasInfinityCountSolutions then
            throw LinearEquationHasInfinityCountSolutions()
            
        if !_hasSolution then
            throw LinearEquationHasNoSolutions()

        _solution

    def HasSolution(): Boolean =
        _hasSolution
    
    def HasInfinityCountSolutions(): Boolean =
        _hasInfinityCountSolutions

class SquareEquationSolver(coeffitients: SquareEquationCoeffitients) 
extends LinearEquationSolver(coeffitients: LinearEquationCoeffitients):
    private var _hasSecondSolution:Boolean = false
    private var _secondSolution = 0
    
    private def GetSolution() = 0

    override def Solve(): Unit =
        if coeffitients.a == 0 then
            super.Solve()
        else
            var disctiminant = 
                coeffitients.b * coeffitients.b -
                4 * coeffitients.a * coeffitients.c
            
            if disctiminant == 0 then
                super._hasSolution = true

                
                

end SquareEquationSolver

@main def main() =
    var coeffitients = GetSquareEquationCoeffitient()
    var solver = LinearEquationSolver(coeffitients)
    solver.Solve()

    println(solver.GetSolution())
    