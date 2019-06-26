package greedySkewBinaryDecomposition

import greedySkewBinaryDecomposition.GSBD._
import org.scalacheck.Prop.forAll
import org.scalacheck.Gen
import org.scalacheck.Properties

object Tests extends Properties("gsbd"){
    val gsbdPairs = for {
        x <- Gen.choose(0, 2048)
        y <- Gen.choose(0, 2048)
        if y != x
    } yield (gsbd(x), gsbd(y))

    val lessThanTwoTermsOrNonEqualFirstTwoTermsPairs = for {
        n <- Gen.choose(0, 2048)
        if { 
            val gn = gsbd(n)
            gn.size < 2 || gn(0) != gn(1) 
        }
    } yield (n, gsbd(n))

    val equalFirstTwoTermsPairs = for {
        n <- Gen.choose(0, 2048)
        if { 
            val gn = gsbd(n)
            gn.size >= 2 && gn(0) == gn(1)
        }
    } yield (n, gsbd(n))

    import Math._
    // prop1: Sum of GSBD(n) = n
    property("prop1") = forAll(Gen.choose(0, 2048)) { n => gsbd(n).sum == n }
    // prop2: GSBD(n) is unique for all n
    property("prop2") = forAll(gsbdPairs) { case (gx, gy) => gx != gy }
    // prop3: Maximum size of GSBD(n)
    property("prop3") = forAll(Gen.choose(0, 2048)) { n => gsbd(n).size <= ceil(log(n + 1) / log(2)).toInt}
    // prop4: GSBD(n + 1) in terms of GSBD(n) when GSBD(n) has less than two terms or first two terms of GSBD(n) are not equal
    property("prop4") = forAll(lessThanTwoTermsOrNonEqualFirstTwoTermsPairs) { case (n, gn) => gsbd(n + 1) == 1::gn }
    // prop5: GSBD(n + 1) in terms of GSBD(n) when first two terms of GSBD(n) are equal
    property("prop5") = forAll(equalFirstTwoTermsPairs) { case (n, gn) => gsbd(n + 1) == (2 * gn(0) + 1)::gn.drop(2) }
}
