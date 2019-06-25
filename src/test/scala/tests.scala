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

    val num_size_pairs = for {
        n <- Gen.choose(0, 2048)
    } yield (n, gsbd(n).length)

    import Math._


    property("sum") = forAll(Gen.choose(0, 2048)) { n => gsbd(n).sum == n}
    property("uniqueness") = forAll(pairs) { p => p._1 != p._2}
    property("max_size") = forAll(num_size_pairs) { p => p._2 <= ceil(log(p._1 + 1) / log(2)).toInt}

}
