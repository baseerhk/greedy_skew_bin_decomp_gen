package greedySkewBinaryDecomposition

import greedySkewBinaryDecomposition.GSBD._
import org.scalacheck.Prop.forAll
import org.scalacheck.Gen
import org.scalacheck.Properties

object Tests extends Properties("gsbd"){
    val pairs = for {
        x <- Gen.choose(0, 2048)
        y <- Gen.choose(0, 2048)
        if y != x
    } yield (gsbd(x), gsbd(y))

    import Math._

    property("sum")        = forAll(Gen.choose(0, 2048)) { n => gsbd(n).sum == n }
    property("uniqueness") = forAll(pairs) { p => p._1 != p._2}
    property("max_size")   = forAll(Gen.choose(0, 2048)) { n => gsbd(n).size <= ceil(log(n + 1) / log(2)).toInt}
}
