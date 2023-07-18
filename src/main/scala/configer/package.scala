package configer

import scala.io.StdIn
import scala.math
import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.parser._

class Phrases:
    var first: String = ""
    var second: String = ""
    var third: String = ""
    var fourth: String = ""

    def this(first:String, second:String, third:String, fourth:String) =
        this()

        this.first = first
        this.second = second
        this.third = third
        this.fourth = fourth   
end Phrases

class PhrasesBlocks:
    var greeter: Phrases = Phrases()

    def this(greeter:Phrases) = 
        this()

        this.greeter = greeter
end PhrasesBlocks

implicit val phrasesDecoder: io.circe.Decoder[Phrases] = phrasesCursor =>
    for
        first  <- phrasesCursor.get[String]("for_get_coeffitients")
        second <- phrasesCursor.get[String]("for_get_first_coeffitient")
        third  <- phrasesCursor.get[String]("for_get_second_coeffitient")
        fourth <- phrasesCursor.get[String]("for_get_third_coeffitient")
    yield
        Phrases(first, second, third, fourth)

implicit val phrasesBlocksDecoder : io.circe.Decoder[PhrasesBlocks] = phrasesBlocksCursor =>
    for
        greeter <- phrasesBlocksCursor.get[Phrases]("greeter")
    yield
        PhrasesBlocks(greeter)


package object Configer:
    var _phrasesBlocks: PhrasesBlocks = PhrasesBlocks(Phrases("", "", "", ""))
    def phrasesBlocks = _phrasesBlocks

    import io.circe.Json, io.circe.ParsingFailure
    private def getValueJsonEither(jsonEither: Either[ParsingFailure, Json]) = 
        class YamlConfigerParsingFailure extends RuntimeException
        val ret = jsonEither match
            case Right(value) =>
                value
            case Left(value) =>
                throw YamlConfigerParsingFailure()
        ret
    end getValueJsonEither


    def setConfiger(filePath: String) =
        val configInputStream = getClass.
                                getClassLoader.
                                getResourceAsStream(filePath)

        import io.circe.yaml.parser
        val configJsonEither = parser.parse(java.io.InputStreamReader(configInputStream))
        val phrasesBlocksJson = getValueJsonEither(configJsonEither)

        class DecoderConfigerFailure extends RuntimeException
        val phrasesBlocksEither = phrasesBlocksJson.as[PhrasesBlocks]
        _phrasesBlocks = phrasesBlocksEither match
            case Right(value) =>
                value
            case Left(value) =>
                throw DecoderConfigerFailure()