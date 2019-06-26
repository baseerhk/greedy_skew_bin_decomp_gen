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

    val moreThanOneTerm = for {
        n <- Gen.choose(0, 2048)
        if gsbd(n).size > 1
    } yield gsbd(n)

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
    // prop6: in GSBD(n), x(i) < x(i + 1) for all i, except for the first two terms, where x(1) <= x(2)
    property("prop6") = forAll(moreThanOneTerm) { gn => 
        val List(x, y) = gn.take(2)
        if (x > y) false else {
            def inner(xs: List[Int]): Boolean = {
                if (xs.size < 2) true else {
                    val List(a, b) = xs.take(2)
                    if (a >= b) false else inner(xs.drop(1))
                }
            }
            inner(gn.drop(1))
        }
    }
}
