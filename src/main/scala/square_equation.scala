import scala.io.StdIn
import scala.math

def AlmostEqual(firstValue: Double,
                secondValue: Double,
                precision: Int = 0): Boolean =
    
    if precision < 0 then
        class PrecisionMustBeGreaterThenZero extends RuntimeException
        throw PrecisionMustBeGreaterThenZero()

    var ret:Boolean = false
    if precision == 0 then
        ret = firstValue == secondValue
    else
        val forCompare: Double = math.pow(10, -precision)
        ret = math.abs(firstValue - secondValue) < forCompare
    ret

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
    protected var _hasSolution: Boolean = false
    private var _hasInfinityCountSolutions: Boolean = false
    private var _solution: Double = 0

    class LinearEquationHasNoSolutions extends RuntimeException
    class LinearEquationHasInfinityCountSolutions extends RuntimeException

    protected def SetSolution(solution: Double) =
        _solution = solution
        _hasSolution = true
    

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
end LinearEquationSolver

class SquareEquationSolver(coeffitients: SquareEquationCoeffitients) 
extends LinearEquationSolver(coeffitients: LinearEquationCoeffitients):
    private var _hasSecondSolution:Boolean = false
    private var _secondSolution:Double = 0

    class LinearEquationSolverUnsupportedMethod extends RuntimeException
    
    override def GetSolution():Double = 
        throw LinearEquationSolverUnsupportedMethod()
        0.0
    
    override def HasSolution(): Boolean = 
        throw LinearEquationSolverUnsupportedMethod()
        false

    override def Solve(): Unit =
        if coeffitients.a == 0 then
            super.Solve()
        else
            var discriminant = 
                coeffitients.b * coeffitients.b -
                4 * coeffitients.a * coeffitients.c
            
            if AlmostEqual(discriminant, 0, 6) then
                SetSolution(-coeffitients.b / (2*coeffitients.a))
            else
                if discriminant > 0 then
                    _hasSecondSolution = true
                    _secondSolution = (-coeffitients.b - math.sqrt(discriminant)) /
                                      2 * coeffitients.a

                    SetSolution((-coeffitients.b + math.sqrt(discriminant)) /
                                2 * coeffitients.a)
    end Solve

    class SquareEquationHasNoFirstSolution extends RuntimeException
    class SquareEquationHasNoSecondSolution extends RuntimeException

    def GetFirstSolution():Double =
        if !super.HasSolution() then
            throw SquareEquationHasNoFirstSolution()
        super.GetSolution()

    def GetSecondSolution():Double =
        if !_hasSecondSolution then
            throw SquareEquationHasNoSecondSolution()
        _secondSolution

    def HasFirstSolution():Boolean = 
        super.HasSolution()

    def HasSecondSolution():Boolean =
        _hasSecondSolution
end SquareEquationSolver

@main def main() =
    val coeffitients = GetSquareEquationCoeffitient()
    var solver = SquareEquationSolver(coeffitients)
    solver.Solve()

    if solver.HasFirstSolution() || solver.HasSecondSolution() then
        println("Ok, I think i have solution! Look...")
        if solver.HasFirstSolution() then
            println(solver.GetFirstSolution())
            if solver.HasSecondSolution() then
                println(solver.GetSecondSolution())
        else
            println("All of you want")
    else
        println("I think this square equation has no solutions")
    