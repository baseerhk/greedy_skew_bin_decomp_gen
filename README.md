Greedy Skew-Binary Decomposition Generator in Scala
===================================================

Skew binary numbers find applications in skew binomial heaps, a variant of binomial heaps that support worst-case O(1) insertion, and in skew binary random access lists, a purely functional data structure. They also find use in bootstrapped skew binomial heaps, which have excellent asymptotic guarantees.

If smoothsort is implemented using perfect binary trees (rather than the more common Leonardo trees), the heap is divided into trees which correspond to the digits of the skew binary representation of the heap size ([from Wikipedia](https://en.wikipedia.org/wiki/Skew_binary_number_system)).

Usage Example:
--------------
```
scala> import greedySkewBinaryDecomposition.GSBD._
import greedySkewBinaryDecomposition.GSBD._

scala> gsbd(0)
res0: List[Int] = List()

scala> gsbd(1)
res1: List[Int] = List(1)

scala> gsbd(2)
res2: List[Int] = List(1, 1)

scala> gsbd(3)
res3: List[Int] = List(3)

scala> gsbd(4)
res4: List[Int] = List(1, 3)

scala> gsbd(5)
res5: List[Int] = List(1, 1, 3)

scala> gsbd(2051)
res10: List[Int] = List(1, 3, 2047)
```

