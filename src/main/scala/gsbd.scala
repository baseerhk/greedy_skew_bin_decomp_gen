package greedySkewBinaryDecomposition

object GSBD {
    def gsbd(x: Int): List[Int] = {
        def inner(x: Int, acc: List[Int]): List[Int] = {
            if (x == 0) acc else {
                import Math._
                val y = (pow(2, floor(log(x + 1) /log(2))) - 1).toInt
                if (y == x) x::acc else inner(x - y, y::acc)
            }
        }
        inner(x, List())
    }   
}